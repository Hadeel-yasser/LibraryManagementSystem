package librarysystem.librarymanagementsystem;

import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;


@RestController
@RequestMapping("/patrons")
public class PatronController {

    @Autowired
    private PatronService patronService;

    @GetMapping("/view")
    public List<Patron> getAllPatrons() {
        return (List<Patron>)patronService.getAllPatrons();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Patron> getPatronById(@PathVariable Long id){
        try{
            Patron patron= patronService.getPatron(id);
            return ResponseEntity.ok(patron);
        }
        catch (PatronNotFoundException e){
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/create")
    public ResponseEntity<?> createNewPatron(@Valid @RequestBody Patron patron, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            ErrorResponse errorResponse = new ErrorResponse();
            errorResponse.setStatus(HttpStatus.BAD_REQUEST.value());
            errorResponse.setMessage("Incomplete Information provided");

            List<String> details = new ArrayList<>();
            for (FieldError error : bindingResult.getFieldErrors()) {
                details.add(error.getField() + ": " + error.getDefaultMessage());
                }
            errorResponse.setDetails(details);
            return ResponseEntity.badRequest().body(errorResponse);
        }
        patronService.createPatron(patron);
        return ResponseEntity.ok(patron);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updatePatron(@PathVariable Long id, @Valid @RequestBody Patron patron, BindingResult bindingResult) {
    try{
        if (bindingResult.hasErrors()) {
            ErrorResponse errorResponse = new ErrorResponse();
            errorResponse.setStatus(HttpStatus.BAD_REQUEST.value());
            errorResponse.setMessage("Incomplete Information provided");

            List<String> details = new ArrayList<>();
            for (FieldError error : bindingResult.getFieldErrors()) {
                details.add(error.getField() + ": " + error.getDefaultMessage());
                }
            errorResponse.setDetails(details);
            return ResponseEntity.badRequest().body(errorResponse);
        }

        patronService.updatePatron(id, patron);
        return ResponseEntity.ok(patron);
        }
        catch (PatronNotFoundException e){
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePatron(@PathVariable Long id){
        try{
            patronService.deletePatron(id);
            return ResponseEntity.ok("Patron with id "+ id + " deleted successfully");
        }
        catch (PatronNotFoundException e){
            return ResponseEntity.notFound().build();
        }
    }
}

