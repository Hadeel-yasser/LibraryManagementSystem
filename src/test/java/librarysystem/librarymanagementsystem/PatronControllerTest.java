package librarysystem.librarymanagementsystem;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;

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
public class PatronControllerTest {

    @Autowired
    private PatronController patronController;

    @MockBean
    private PatronService patronService;

    @Mock
    private BindingResult bindingResult;

    @Test
    public void testGetAllPatrons_Success() {
    List<Patron> expectedPatrons = new ArrayList<>();
    ContactInfo patronInfo1 = new ContactInfo("Phone number 1","patron1@xyz.com","Address 1");
    ContactInfo patronInfo2 = new ContactInfo("Phone number 2","patron2@xyz.com","Address 2");
    expectedPatrons.add(new Patron("Name 1",patronInfo1));
    expectedPatrons.add(new Patron("Name 2",patronInfo2));
    
    doReturn(expectedPatrons).when(patronService).getAllPatrons();

    List<Patron> actualPatrons = patronController.getAllPatrons();

    assertEquals(expectedPatrons, actualPatrons);
    }

    @Test
    public void testGetPatronById_Success() throws Exception {
    Long existingId = 1L;
    ContactInfo patronInfo = new ContactInfo("123456789","johndoe@example.com","Address 1");
    Patron expectedPatron = new Patron("John Doe", patronInfo);
    expectedPatron.setPatronId(existingId);

    doReturn(expectedPatron).when(patronService).getPatron(existingId);

    ResponseEntity<Patron> response = patronController.getPatronById(existingId);

    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals(expectedPatron, response.getBody());
    }

    @Test
    public void testGetPatronById_NotFound() throws Exception {
    Long nonExistentId = 10L;

    doThrow(new PatronNotFoundException("Patron not found with id " + nonExistentId)).when(patronService).getPatron(nonExistentId);

    ResponseEntity<Patron> response = patronController.getPatronById(nonExistentId);

    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testCreateNewPatron_Success() {

    ContactInfo patronInfo = new ContactInfo("12345678910","janedoe@example.com","Address 1");
    Patron newPatron = new Patron("Jane Doe", patronInfo);

    ResponseEntity<?> response = patronController.createNewPatron(newPatron, bindingResult);

    assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testUpdatePatron_Success() throws Exception {
    Long existingId = 1L;
    ContactInfo existingPatronInfo = new ContactInfo("Old Number","old@email.com","old Address");
    Patron existingPatron = new Patron("Old Name", existingPatronInfo);
    existingPatron.setPatronId(existingId);
    ContactInfo updatedPatronInfo = new ContactInfo("New Number","new@email.com","new Address");
    Patron updatedPatron = new Patron("New Name", updatedPatronInfo);
    updatedPatron.setPatronId(existingId);

    doReturn(updatedPatron).when(patronService).updatePatron(existingId, updatedPatron);

    ResponseEntity<?> response = patronController.updatePatron(existingId, updatedPatron, bindingResult);

    assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testUpdatePatron_NotFound() throws Exception {
    Long nonExistentId = 10L;
    ContactInfo updatedPatronInfo = new ContactInfo("New Number","new@email.com","new Address");
    Patron updatedPatron = new Patron("New Name", updatedPatronInfo);
    updatedPatron.setPatronId(nonExistentId);

    // Mock PatronService to throw PatronNotFoundException
    doThrow(new PatronNotFoundException("Patron not found with id " + nonExistentId)).when(patronService).updatePatron(nonExistentId, updatedPatron);

    // Call the controller method
    ResponseEntity<?> response = patronController.updatePatron(nonExistentId, updatedPatron, bindingResult); 

    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testDeletePatron_Success() throws Exception {
    Long existingId = 1L;

    doNothing().when(patronService).deletePatron(existingId);

    ResponseEntity<?> response = patronController.deletePatron(existingId);

    assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testDeletePatron_NotFound() throws Exception {
    Long nonExistentId = 10L;

    doThrow(new PatronNotFoundException("Patron not found with id " + nonExistentId)).when(patronService).deletePatron(nonExistentId);

    ResponseEntity<?> response = patronController.deletePatron(nonExistentId);

    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

}
