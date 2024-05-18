package librarysystem.librarymanagementsystem;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PatronService {

    @Autowired
    private PatronRepository patronRepository;

    private static final Logger logger = LoggerFactory.getLogger(PatronService.class);

    public List<Patron> getAllPatrons(){
        return (List<Patron>)patronRepository.findAll();
    }

    public Patron getPatron(Long id) throws BookNotFoundException{
        Optional<Patron> existingPatron = patronRepository.findById(id);
            if (existingPatron.isPresent()){
                Patron existing = existingPatron.get();
                return existing;
            }
            else{
                throw new PatronNotFoundException("Patron with id "+id+" not found");
            } 
    }

    public Patron createPatron(Patron patron){
        patronRepository.save(patron);
        logger.info("Product with ID "+patron.getPatronId()+" have been created successfully");
        return patron;
    }

    public Patron updatePatron(Long id, Patron patron) throws PatronNotFoundException{
        Optional<Patron> existingPatron = patronRepository.findById(id);
        if (existingPatron.isPresent() && existingPatron.get().getPatronId()==id){
            existingPatron.get().setPatronId(id);
            existingPatron.get().setName(patron.getName());
            existingPatron.get().setContactInfo(patron.getContactInfo());
            patronRepository.save(existingPatron.get());
            logger.info("Patron with name "+existingPatron.get().getName()+existingPatron.get().getPatronId()+" have been updated successfully!");
            return existingPatron.get();
        }
        else{
            throw new PatronNotFoundException("Patron with id "+id+" not found"); 
        }   
    }

    public void deletePatron(Long id) throws PatronNotFoundException{
        Optional<Patron> existingPatron = patronRepository.findById(id);
        if (existingPatron.isPresent()){
            patronRepository.deleteById(id);
            logger.info("Patron with id "+id+" have been deleted successfully!");
        }
        else{
            throw new PatronNotFoundException("Patron with id "+id+" not found");
        }
    }
}
