package librarysystem.librarymanagementsystem;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface BorrowingRepository extends CrudRepository<BorrowingRecord,Long>{

    @Query("SELECT b FROM BorrowingRecord b WHERE b.patron.patronId = ?1 AND b.book.bookId = ?2")
    public abstract Optional<BorrowingRecord> findByPatronIdAndBookId(Long patronId, Long bookId);

}
