package core.com;

/**
 * Created by xinszhou on 28/03/2017.
 */
public class CommunicationException extends Exception {

    private static final long serialVersionUID = -3606098863603794463L;

    public CommunicationException() {
        super();
    }

    public CommunicationException(String message) {
        super(message);
    }

    public CommunicationException(Throwable cause) {
        super(cause);
    }

    public CommunicationException(String message, Throwable cause) {
        super(message, cause);
    }

}