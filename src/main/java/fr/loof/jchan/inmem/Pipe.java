package fr.loof.jchan.inmem;

import fr.loof.jchan.*;
import fr.loof.jchan.Error;
import fr.loof.jchan.nop.NopSender;

/**
 * @author <a href="mailto:nicolas.deloof@gmail.com">Nicolas De Loof</a>
 */
public class Pipe implements Sender, Receiver {

    Object lock = new Object();
    volatile Error rerr; // if reader closed, error to give writes
    volatile Error werr; // if writer closed, error to give reads
    volatile Message message;


    @Override
    public Message receive(int mode) throws fr.loof.jchan.Error {
        Message msg = preceive();
        if (msg.ret == null) {
            msg.ret = new NopSender();
        }
        if ((mode & RET) == 0) {
            msg.ret.close();
        }
        return msg;
    }

    public Receiver send(Message msg) {
        Receiver ret = null;
        // Prepare nested Receiver if requested
        if (JChan.RetPipe.equals(msg.ret)) {
            Pipe p = new Pipe();
            msg.ret = p;
            ret = p;
        }
        psend(msg);
        return ret;
    }

    @Override
    public void close() throws Error {

    }

    private void psend(Message msg) {
        try {
            synchronized (lock) {
                this.message = msg;
                // notify receiver about message being available for consumption
                lock.notifyAll();

                // wait for consumption ...
                lock.wait();
                if (message == null) {
                    // Message has been consumed
                    return;
                }
                if (rerr != null) {
                    throw rerr;
                }
                if (werr != null) {
                    throw ErrClosedPipe;
                }
            }
        } catch (InterruptedException e) {
            throw ErrClosedPipe; // Maybe something more specific ?
        }
    }


    private Message preceive() throws Error {
        try {
            synchronized (lock) {
                lock.wait(); // According to javadoc, should be used in a loop, but not sure what to check for
                if (rerr != null) throw ErrClosedPipe;
                if (werr != null) throw werr;

                Message msg = this.message;
                this.message = null;
                lock.notifyAll();
                return msg;
            }
        } catch (InterruptedException e) {
            throw ErrClosedPipe; // Maybe use something more specific ?
        }
    }

    public static final Error ErrClosedPipe = new Error();


}
