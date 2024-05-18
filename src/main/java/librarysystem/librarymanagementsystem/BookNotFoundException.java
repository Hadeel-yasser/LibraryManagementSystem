package librarysystem.librarymanagementsystem;

public class BookNotFoundException extends RuntimeException{

    public BookNotFoundException(String message){
        super(message);
    }

}
