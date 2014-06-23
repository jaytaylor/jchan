package fr.loof.jchan;

/**
 * @author <a href="mailto:nicolas.deloof@gmail.com">Nicolas De Loof</a>
 */
public final class JChan {

    /**
     * RetPipe is a special value for `Message.Ret`.
     * When a Message is sent with `Ret=SendPipe`, the transport must
     * substitute it with the writing end of a new pipe, and return the
     * other end as a return value.
     */
    public static final Sender RetPipe = new Sender() {

        @Override
        public Receiver send(Message msg) {
            return null;
        }

        @Override
        public void close() {

        }
    };

    public static Sender repeater(final Message payload) {
        return new Sender() {
            @Override
            public Receiver send(Message msg) {
                return msg.ret.send(payload);
            }

            @Override
            public void close() {

            }

        };
    }
}
