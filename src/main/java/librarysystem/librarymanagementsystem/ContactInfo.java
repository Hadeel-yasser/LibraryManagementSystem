package librarysystem.librarymanagementsystem;


import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotBlank;

@Embeddable
public class ContactInfo {

    @NotBlank(message = "Phone Number is required")
    private String phoneNumber;

    @NotBlank(message = "Email is required")
    private String emailAddress;
    
    private String postalAddress;

    
    public ContactInfo(@NotBlank(message = "Phone Number is required") String phoneNumber,
            @NotBlank(message = "Email is required") String emailAddress, String postalAddress) {
        this.phoneNumber = phoneNumber;
        this.emailAddress = emailAddress;
        this.postalAddress = postalAddress;
    }
    public String getPhoneNumber() {
        return phoneNumber;
    }
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    public String getEmailAddress() {
        return emailAddress;
    }
    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }
    public String getPostalAddress() {
        return postalAddress;
    }
    public void setPostalAddress(String postalAddress) {
        this.postalAddress = postalAddress;
    }
    
}

