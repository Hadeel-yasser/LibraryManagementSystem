package librarysystem.librarymanagementsystem;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/borrow")
public class BorrowingController {

    @Autowired
    private BorrowingService borrowingService;

    @PostMapping("/{bookId}/patron/{patronId}")
    public ResponseEntity<String> borrowBook(@PathVariable Long bookId, @PathVariable Long patronId, @RequestBody String expectedReturnDate){

        try{
            borrowingService.borrowBook(bookId, patronId, expectedReturnDate);
            return new ResponseEntity<>("Book borrowed successfully!", HttpStatus.CREATED);
        }
        catch (BookNotFoundException e){
            return new ResponseEntity<>("Book with id " + bookId + " not found", HttpStatus.NOT_FOUND);
        }
        catch (BookNotAvailableException e){
             return new ResponseEntity<>("Book with id "+ bookId + " isn't available", HttpStatus.BAD_REQUEST);
        }
        catch (Exception e) {
            // Catch-all for unexpected exceptions 
            e.printStackTrace(); // Log the exception for debugging
            return new ResponseEntity<>("An error occurred while borrowing the book", HttpStatus.INTERNAL_SERVER_ERROR);
          }
    }

    @PutMapping("/return/{bookId}/patron/{patronId}")
    public ResponseEntity<String> returnBook(@PathVariable Long bookId, @PathVariable Long patronId){
        try{
            borrowingService.returnBook(bookId, patronId);
            return new ResponseEntity<>("Book returned successfully!", HttpStatus.OK);
        }
        catch (BookNotFoundException e){
            return new ResponseEntity<>("Book with id " + bookId + " not found", HttpStatus.NOT_FOUND);
        }
        catch (Exception e) {
            // Catch-all for unexpected exceptions 
            e.printStackTrace(); // Log the exception for debugging
            return new ResponseEntity<>("An error occurred while borrowing the book", HttpStatus.INTERNAL_SERVER_ERROR);
          }
        
     }
}


