package fr.loof.jchan;

/**
 * @author <a href="mailto:nicolas.deloof@gmail.com">Nicolas De Loof</a>
 */
public interface Receiver {

    Message receive(int mode) throws Error;

    public static int RET = 1;

    public final static Error EOF = new Error();
}
