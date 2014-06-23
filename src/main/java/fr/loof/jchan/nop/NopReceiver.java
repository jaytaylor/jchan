package fr.loof.jchan.nop;

import fr.loof.jchan.*;

/**
 * @author <a href="mailto:nicolas.deloof@gmail.com">Nicolas De Loof</a>
 */
public class NopReceiver implements Receiver {
    @Override
    public Message receive(int mode) throws fr.loof.jchan.Error {
        throw EOF;
    }
}
