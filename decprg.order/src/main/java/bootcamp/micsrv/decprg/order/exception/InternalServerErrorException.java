package bootcamp.micsrv.decprg.order.exception;

public class InternalServerErrorException extends RuntimeException {
    public InternalServerErrorException(String message) {
        super(message);
    }
}