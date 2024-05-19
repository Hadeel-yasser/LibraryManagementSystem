package librarysystem.librarymanagementsystem;

import java.util.Date;
import java.util.Optional;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

@Service
public class BorrowingService {

    @Autowired
    private BooksRepository booksRepository;

    private static final Logger logger = LoggerFactory.getLogger(BorrowingService.class);

    @Autowired
    private PatronRepository patronRepository;
    @Autowired
    private BorrowingRepository borrowingRepository;

    @Transactional
    public boolean borrowBook(Long bookId, Long patronId, String dateString) throws BookNotAvailableException, BookNotFoundException, PatronNotFoundException{
        Book book = booksRepository.findById(bookId)
            .orElseThrow(() -> new BookNotFoundException("Book with id " + bookId + " is not Found"));
        
        Patron patron = patronRepository.findById(patronId)
        .orElseThrow(() -> new PatronNotFoundException("patron with id " + patronId + " is not Found"));

        if (!book.getStatus().equals("Available")) {
          throw new BookNotAvailableException("Book with id " + bookId + " is not available");
        }
        logger.info("date "+dateString);
        LocalDate expectedReturnDate = LocalDate.parse(dateString, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        BorrowingRecord borrowingRecord = new BorrowingRecord(book,patron,new Date(),expectedReturnDate);
         
        borrowingRepository.save(borrowingRecord); 
    
        book.setStatus("Borrowed");
        booksRepository.save(book);
    
        return true;
      }

      @Transactional
      public void returnBook(Long bookId, Long patronId) throws BookNotFoundException {
        Book book = booksRepository.findById(bookId)
            .orElseThrow(() -> new BookNotFoundException("Book with id " + bookId + " not found"));
    
        Optional<BorrowingRecord> borrowingRecordOptional = borrowingRepository.findByPatronIdAndBookId(patronId, bookId);
        if(borrowingRecordOptional.isPresent()){
          //handle logic of updating the borrowing record and changing the return date to the actual one
            BorrowingRecord borrowingRecord = borrowingRecordOptional.get();
            LocalDate currenDate = LocalDate.now();
            borrowingRecord.setExpectedReturnDate(currenDate);
            borrowingRepository.save(borrowingRecord);
            book.setStatus("Available");
            booksRepository.save(book);
        }
        else{
          logger.info("borrowing record for patron with id "+ patronId+ " isn't found");
        }
      }
}


