package librarysystem.librarymanagementsystem;


import java.util.List;
import java.util.Optional;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;

@Service
public class BookService {

    @Autowired
    private BooksRepository booksRepository;

    private static final Logger logger = LoggerFactory.getLogger(BookService.class);


    public List<Book> getAllBooks(){
        return (List<Book>)booksRepository.findAll();
    }
    public Book getBook(Long id) throws BookNotFoundException{
        Optional<Book> existingBook = booksRepository.findById(id);
            if (existingBook.isPresent()){
                Book existing = existingBook.get();
                return existing;
            }
            else{
                throw new BookNotFoundException("Book with id "+id+" not found");
            } 
    }

    public Book createBook(Book book){
        booksRepository.save(book);
        logger.info("Book with ID "+book.getBookId()+" have been created successfully");
        return book;
    }

    public Book updateBook(Long id, Book book) throws BookNotFoundException{
        Optional<Book> existingBook = booksRepository.findById(id);
        if (existingBook.isPresent() && existingBook.get().getBookId()==id){
                booksRepository.save(book);
                logger.info("Book "+existingBook.get().getBookTitle()+" have been updated successfully!");
            return book;
        }
        else{
            throw new BookNotFoundException("Book with id "+id+" not found"); 
        }   
    }

    public void deleteBook(Long id) throws BookNotFoundException{
        Optional<Book> existingBook = booksRepository.findById(id);
        if (existingBook.isPresent()){
            booksRepository.deleteById(id);
            logger.info("Book with id "+id+" have been deleted successfully!");
        }
        else{
            throw new BookNotFoundException("Book with id "+id+" not found");
        }
    }
}


