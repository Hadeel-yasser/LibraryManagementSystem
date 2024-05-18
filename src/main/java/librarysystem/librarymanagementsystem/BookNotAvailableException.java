package librarysystem.librarymanagementsystem;

public class BookNotAvailableException extends RuntimeException {

    public BookNotAvailableException(String message){
        super(message);
    }

}
