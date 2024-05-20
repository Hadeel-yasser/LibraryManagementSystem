package librarysystem.librarymanagementsystem;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

@SpringBootTest
public class BookControllerTest {

    @Autowired
    private BookController bookController;

    @MockBean
    private BookService bookService;

    @Mock
    private BindingResult bindingResult;
    
    @Test
    public void testGetAllBooks_Success() {
    // Mock BookService to return a list of books
    List<Book> expectedBooks = new ArrayList<>();
    expectedBooks.add(new Book("Book Title 1", "Author 1","123456","Available"));
    expectedBooks.add(new Book("Book Title 2", "Author 2","2587413","Available"));
    doReturn(expectedBooks).when(bookService).getAllBooks();

    // Call the controller method
    List<Book> actualBooks = bookController.getAllBooks();

    // Assert the returned list
    assertEquals(expectedBooks, actualBooks);
    }
    @Test
    public void testCreateNewBook_Success() {
    Book newBook = new Book("New Book Title", "New Author", "New ISBN", "Available");
    
    Book createdBook = new Book("New Book Title", "New Author", "New ISBN", "Available");
    doReturn(createdBook).when(bookService).createBook(newBook);
  
    ResponseEntity<?> response = bookController.createNewBook(newBook, bindingResult); 
  
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals("New Book Title", ((Book) response.getBody()).getBookTitle());
    assertEquals("New Author", ((Book) response.getBody()).getAuthor());
    assertEquals("New ISBN", ((Book) response.getBody()).getIsbn());
    }

    @Test
    public void testGetBookById_Success() {
    Long existingId = 1L;
    Book expectedBook = new Book("Book Title 1", "Author 1","New ISBN","Available");
    expectedBook.setBookId(existingId);
    // Mock BookService to return a book for existing ID
    doReturn(expectedBook).when(bookService).getBook(existingId);

    // Call the controller method
    ResponseEntity<Book> response = bookController.getBookById(existingId);

    // Assert response status and body
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals(expectedBook, response.getBody());
    }

    @Test
    public void testGetBookById_NotFound() {
    Long nonExistentId = 10L;

    // Mock BookService to throw BookNotFoundException
    doThrow(new BookNotFoundException("Book not found with id " + nonExistentId)).when(bookService).getBook(nonExistentId);

    // Call the controller method
    ResponseEntity<Book> response = bookController.getBookById(nonExistentId);

    // Assert response status (Not Found)
    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }


    // Invalid book creation
    @Test
    public void testCreateNewBook_BadRequest() {
    Book invalidBook = new Book();
    invalidBook.setAuthor("New Author");
    invalidBook.setBookTitle("");
    invalidBook.setIsbn("");
    // Mock BindingResult to have validation errors 
    BindingResult mockBindingResult = mock(BindingResult.class);
    doReturn(true).when(mockBindingResult).hasErrors();

    // Call the controller method with an invalid book
    ResponseEntity<?> response = bookController.createNewBook(invalidBook, mockBindingResult);

    // Assert response status (Bad Request)
    assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void testUpdateBook_Success() throws Exception {
    Long existingId = 1L;
    Book existingBook = new Book("Old Title", "Old Author","Old ISBN","Old Status");
    Book updatedBook = new Book("New Title", "New Author","Old ISBN","Old Status");
    existingBook.setBookId(existingId);
    updatedBook.setBookId(existingId);
    // Mock BookService to update the book
    doReturn(updatedBook).when(bookService).updateBook(existingId, updatedBook);

    // Call the controller method
    ResponseEntity<?> response = bookController.updateBook(existingId, updatedBook, bindingResult); 

    // Assert response status and body (assuming successful update returns OK)
    assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testUpdateBook_NotFound() throws Exception {
    Long nonExistentId = 10L;
    Book updatedBook = new Book("New Title", "New Author","New ISBN","New Status");
    updatedBook.setBookId(nonExistentId);
    // Mock BookService to throw BookNotFoundException
    doThrow(new BookNotFoundException("Book not found with id " + nonExistentId)).when(bookService).updateBook(nonExistentId, updatedBook);

    // Call the controller method
    ResponseEntity<?> response = bookController.updateBook(nonExistentId, updatedBook, bindingResult); 
    // Assert response status (Not Found)
    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
    
    @Test
    public void testDeleteBook_Success() throws Exception {
    Long existingId = 1L;
    doNothing().when(bookService).deleteBook(existingId);

    ResponseEntity<?> response = bookController.deleteBook(existingId);

    assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testDeleteBook_NotFound() throws Exception {
    Long nonExistentId = 10L;

    doThrow(new BookNotFoundException("Book not found with id " + nonExistentId)).when(bookService).deleteBook(nonExistentId);

    ResponseEntity<?> response = bookController.deleteBook(nonExistentId);

    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }


}
