package fr.loof.jchan.inmem;

import fr.loof.jchan.Message;
import fr.loof.jchan.Receiver;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * @author <a href="mailto:nicolas.deloof@gmail.com">Nicolas De Loof</a>
 */
public class PipeTest {

    @Test
    public void sendReply() {

        Pipe p = new Pipe();

        new Thread(() -> {
            Message msg = p.receive(0);
            assertEquals("this is the request", new String(msg.data));
            msg.ret.send(new Message("this is the response"));
        }).start();

        Receiver ret = p.send(new Message("this is the request"));
        Message response = ret.receive(Receiver.RET);
        assertEquals("this is the response", new String(response.data));
    }
}
