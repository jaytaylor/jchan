package fr.loof.jchan;

/**
 * @author <a href="mailto:nicolas.deloof@gmail.com">Nicolas De Loof</a>
 */
public class Error extends RuntimeException {

    public static final Error IncompatibleSender = new Error();
    public static final Error ErrIncompatibleReceiver = new Error();
}
