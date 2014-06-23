package fr.loof.jchan.nop;

import fr.loof.jchan.*;
import fr.loof.jchan.Error;

/**
 * @author <a href="mailto:nicolas.deloof@gmail.com">Nicolas De Loof</a>
 */
public class NopSender implements Sender {

    @Override
    public Receiver send(Message msg) throws fr.loof.jchan.Error {
        return new NopReceiver();
    }

    @Override
    public void close() throws Error {

    }
}
