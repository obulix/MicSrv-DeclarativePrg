package bootcamp.micsrv.decprg.product.exception;

public class ProductNotFoundException extends RuntimeException {

    public ProductNotFoundException(String message) {
        super(message);
    }
}