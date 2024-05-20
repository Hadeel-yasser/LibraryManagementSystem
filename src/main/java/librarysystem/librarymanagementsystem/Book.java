package librarysystem.librarymanagementsystem;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
    
@Entity
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bookId;

    @NotBlank(message = "Book title is required")
    private String bookTitle;

    @NotBlank(message = "Book author is required")
    private String author;

    private int publicationYear;

    @NotBlank(message = "ISBN is required")
    private String isbn ;
    
    @NotBlank(message = "Book status is require")
    private String status;

    public Book(@NotBlank(message = "Book title is required") String bookTitle,
            @NotBlank(message = "Book author is required") String author,
            @NotBlank(message = "ISBN is required") String isbn,
            @NotBlank(message = "Book status is require") String status) {
        this.bookTitle = bookTitle;
        this.author = author;
        this.isbn = isbn;
        this.status = status;
    }
    
    public Book() {
    }

    public Long getBookId() {
        return bookId;
    }
    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }
    public String getBookTitle() {
        return bookTitle;
    }
    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
    }
    public String getAuthor() {
        return author;
    }
    public void setAuthor(String author) {
        this.author = author;
    }
    public int getPublicationYear() {
        return publicationYear;
    }
    public void setPublicationYear(int publicationYear) {
        this.publicationYear = publicationYear;
    }
    public String getIsbn() {
        return isbn;
    }
    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
        


}
