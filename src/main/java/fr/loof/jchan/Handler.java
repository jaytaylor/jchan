package fr.loof.jchan;

import fr.loof.jchan.inmem.Pipe;
import fr.loof.jchan.nop.NopSender;

import static fr.loof.jchan.JChan.RetPipe;

/**
 * @author <a href="mailto:nicolas.deloof@gmail.com">Nicolas De Loof</a>
 */
@FunctionalInterface
public interface Handler extends Sender {
    void handle(Message m);

    public default Receiver send(Message msg) throws Error {
        Receiver ret = null;
        if (RetPipe.equals(msg.ret)) {
            Pipe p = new Pipe();
            ret = p;
            msg.ret = p;
        }
        if (msg.ret == null) {
            msg.ret = new NopSender();
        }
        // TODO run assynchronous
        handle(msg);
        msg.ret.close();
        /*
        go func () {
            // Ret must always be a valid Sender, so handlers
            // can safely send to it
            if (msg.ret == null) {
                msg.ret = NopSender();
            }
            h(msg)
            msg.ret.Close();
        } ()
        */
        return ret;
    }

    public default void close() {
        // return fmt.Errorf("can't close");
    }

}
