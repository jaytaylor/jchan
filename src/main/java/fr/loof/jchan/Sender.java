package fr.loof.jchan;

import java.io.Closeable;

/**
 * @author <a href="mailto:nicolas.deloof@gmail.com">Nicolas De Loof</a>
 */
public interface Sender extends Closeable {

    Receiver send(Message msg) throws Error;
    void close() throws Error;
}
