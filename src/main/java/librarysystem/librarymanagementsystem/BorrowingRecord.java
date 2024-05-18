package librarysystem.librarymanagementsystem;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import java.util.Date;

import jakarta.validation.constraints.NotBlank;
import java.time.LocalDate;

@Entity
public class BorrowingRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long borrowingRecordId;


    @ManyToOne
    @NotBlank(message = "Book Id is required")
    private Book book;

    
    public Book getBook() {
        return book;
    }
    public void setBook(Book book) {
        this.book = book;
    }
    @ManyToOne
    @NotBlank(message = "Patron Id is required")
    private Patron patron;

    
    public Patron getPatron() {
        return patron;
    }
    public void setPatron(Patron patron) {
        this.patron = patron;
    }
    private Date borrowedDate;
    private LocalDate expectedReturnDate;
    
    

    public BorrowingRecord(@NotBlank(message = "Book Id is required") Book book,
            @NotBlank(message = "Patron Id is required") Patron patron, Date borrowedDate,
            LocalDate expectedReturnDate) {
        this.book = book;
        this.patron = patron;
        this.borrowedDate = borrowedDate;
        this.expectedReturnDate = expectedReturnDate;
    }
    public Long getBorrowingRecordId() {
        return borrowingRecordId;
    }
    public void setBorrowingRecordId(Long borrowingRecordId) {
        this.borrowingRecordId = borrowingRecordId;
    }
    
    
    public Date getBorrowedDate() {
        return borrowedDate;
    }
    public void setBorrowedDate(Date borrowedDate) {
        this.borrowedDate = borrowedDate;
    }
    public LocalDate getExpectedReturnDate() {
        return expectedReturnDate;
    }
    public void setExpectedReturnDate(LocalDate expectedReturnDate) {
        this.expectedReturnDate = expectedReturnDate;
    }

    
}

