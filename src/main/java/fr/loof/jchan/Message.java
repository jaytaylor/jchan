package fr.loof.jchan;

import fr.loof.jchan.data.Data;

import java.io.File;

/**
 * @author <a href="mailto:nicolas.deloof@gmail.com">Nicolas De Loof</a>
 */
public class Message {

    public byte[] data;
    private File fd;
    public Sender ret;

    public Message(String data) {
        this(data.getBytes());
    }

    public Message(String data, Sender ret) {
        this(data.getBytes(), ret);
    }

    public Message(byte[] data) {
        this.data = data;
        this.ret = JChan.RetPipe;
    }


    public Message(byte[] data, Sender ret) {
        this.data = data;
        this.ret = ret;
    }


    public static Message EMPTY() {
        return new Message(Data.encode(null));
    }


}
