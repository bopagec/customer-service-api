package esg.global.customerserviceapi.exception;

public class CustomerNotFoundException extends Exception{
    public CustomerNotFoundException(Throwable throwable){
        super(throwable);
    }
    public CustomerNotFoundException(String message) {
        super(message);
    }
    public CustomerNotFoundException(String message, Exception exception){
        super(message, exception);
    }
}
