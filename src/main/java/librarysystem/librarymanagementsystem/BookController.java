package librarysystem.librarymanagementsystem;

import java.util.List;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/books")
public class BookController {

    @Autowired
    private BookService bookService;

    @GetMapping("/view")
    public List<Book> getAllBooks() {
        return (List<Book>)bookService.getAllBooks();
    }
   
    @GetMapping("/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable Long id){
        try{
            Book book= bookService.getBook(id);
            return ResponseEntity.ok(book);
        }
        catch (BookNotFoundException e){
            return ResponseEntity.notFound().build();
        }
    }
    
    @PostMapping("/create")
    public ResponseEntity<?> createNewBook(@Valid @RequestBody Book book, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            // Handle validation errors
            return ResponseEntity.badRequest().body(bindingResult.getAllErrors());
        }
        bookService.createBook(book);
        return ResponseEntity.ok(book);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateBook(@PathVariable Long id, @Valid @RequestBody Book book, BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            // Handle validation errors
            return ResponseEntity.badRequest().body(bindingResult.getAllErrors());
        }
        try {
            bookService.updateBook(id, book);
            return ResponseEntity.ok(book);
            
        } catch (BookNotFoundException e) {
            return ResponseEntity.notFound().build();
        }   
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteBook(@PathVariable Long id){
        try{
            bookService.deleteBook(id);
            return ResponseEntity.ok("Book with id "+ id + " deleted successfully");
        }
        catch (BookNotFoundException e){
            return ResponseEntity.notFound().build();
        }
    }
}



