package librarysystem.librarymanagementsystem;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;



@SpringBootTest
public class BorrowingControllerTest {

  @Autowired
  private BorrowingController borrowingController;

  @MockBean
  private BorrowingService borrowingService;

  @Test
  public void testBorrowBookSuccess() {
  // Mock BorrowingService to return true (successful borrow)
  Long bookId = 1L;
  Long patronId = 2L;
  String expectedReturnDate = "2024-06-20";
  Map<String, String> borrowData = new HashMap<>();
  borrowData.put("expectedReturnDate", expectedReturnDate);

  doReturn(true).when(borrowingService).borrowBook(bookId, patronId, expectedReturnDate);

  // Call the borrowBook method
  ResponseEntity<String> response = borrowingController.borrowBook(bookId, patronId, borrowData); 

  // Assert response status and body (assuming successful borrow returns CREATED)
  assertEquals(HttpStatus.CREATED, response.getStatusCode());
  assertEquals("Book borrowed successfully!", response.getBody()); // Adjust message if different

  // Optional verification (ensure borrowBook was called with expected arguments)
  verify(borrowingService, times(1)).borrowBook(bookId, patronId, expectedReturnDate);
}


    @Test
    public void testReturnBookSuccess() {
    // Mock BorrowingService to return success
    Long bookId = 1L;
    Long patronId = 2L;

    // Configure BorrowingService to return success for returnBook
    doNothing().when(borrowingService).returnBook(bookId, patronId);
    ResponseEntity<String> response = borrowingController.returnBook(bookId, patronId);

    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals("Book returned successfully!", response.getBody());
    }

    @Test
    public void testBorrowBookBookNotFound() throws BookNotFoundException{
    // Mock BorrowingService to throw BookNotFoundException
    Long bookId = 1L;
    Long patronId = 2L;
    String expectedReturnDate = "2024-06-20";
    Map<String, String> borrowData = new HashMap<>();
    borrowData.put("expectedReturnDate", expectedReturnDate);

    // Configure BorrowingService to throw BookNotFoundException
    doThrow(new BookNotFoundException("Book with id "+bookId+" not found")).when(borrowingService).borrowBook(bookId, patronId, expectedReturnDate);

    // Call the borrowBook method
    ResponseEntity<String> response = borrowingController.borrowBook(bookId, patronId, borrowData);

    // Assert response status and body
    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    assertTrue(response.getBody().contains("Book with id " + bookId + " not found"));
    }

    @Test
    public void testBorrowBookBookNotAvailable() throws BookNotAvailableException {
    // Mock BorrowingService to throw BookNotAvailableException
    Long bookId = 1L;
    Long patronId = 2L;
    String expectedReturnDate = "2024-06-20";
    Map<String, String> borrowData = new HashMap<>();
    borrowData.put("expectedReturnDate", expectedReturnDate);

    // Configure BorrowingService to throw BookNotAvailableException
    doThrow(new BookNotAvailableException("Book with id "+ bookId+ " not available")).when(borrowingService).borrowBook(bookId, patronId, expectedReturnDate);

    // Call the borrowBook method
    ResponseEntity<String> response = borrowingController.borrowBook(bookId, patronId, borrowData);

    // Assert response status and body
    assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    assertTrue(response.getBody().contains("Book with id " + bookId + " isn't available"));
    }
    @Test
    public void testBorrowBookUnexpectedException() throws Exception {
    // Mock BorrowingService to throw a generic exception
    Long bookId = 1L;
    Long patronId = 2L;
    String expectedReturnDate = "2024-06-20";
    Map<String, String> borrowData = new HashMap<>();
    borrowData.put("expectedReturnDate", expectedReturnDate);

    // Configure BorrowingService to throw a generic exception
    doThrow(new RuntimeException("Unexpected error")).when(borrowingService).borrowBook(bookId, patronId, expectedReturnDate);

    // Call the borrowBook method
    ResponseEntity<String> response = borrowingController.borrowBook(bookId, patronId, borrowData);

    // Assert response status and body
    assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    assertTrue(response.getBody().contains("An error occurred while borrowing the book"));

    // Verify BorrowingService.borrowBook was called with expected arguments
    verify(borrowingService, times(1)).borrowBook(bookId, patronId, expectedReturnDate);
    }
 
}

