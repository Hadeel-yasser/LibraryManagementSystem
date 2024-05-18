package librarysystem.librarymanagementsystem;


import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;

@Entity
public class Patron {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long patronId;

    @NotBlank(message = "Patron name is required")
    private String name;
    
    @Embedded
    private ContactInfo contactInfo;

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public Long getPatronId() {
        return patronId;
    }
    public void setPatronId(Long patronId) {
        this.patronId = patronId;
    }
    public ContactInfo getContactInfo() {
        return contactInfo;
    }
    public void setContactInfo(ContactInfo contactInfo) {
        this.contactInfo = contactInfo;
    }
}

