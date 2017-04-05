package core.service;

/**
 * Created by xinszhou on 28/03/2017.
 */
public class ServiceException extends Exception {
    private static final long serialVersionUID = 1039630030458301201L;

    public ServiceException(String message) {
        super(message);
    }

    public ServiceException(String message, Throwable cause) {
        super(message, cause);
    }

}
