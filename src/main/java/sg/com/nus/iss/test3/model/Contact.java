package sg.com.nus.iss.test3.model;

import java.time.LocalDate;
import java.time.Period;
import java.util.Date;
import java.util.Random;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;

public class Contact {
    
    @NotNull(message = "Name cannot be empty")
    @Size(min = 3, max = 64, message = "Length must be between 3 and 64")
    private String name;

    @NotNull(message = "Email cannot be empty")
    @Email(message = "Invalid email")
    private String email;

    @NotNull(message = "Phone number cannot be empty")
    @Size(min = 7, message = "Phone number must be at least 7 digits")
    private String phoneNumber;

    @NotNull(message = "Date of birth cannot be empty")
    @DateTimeFormat(pattern = "MM-dd-yyyy")
    private LocalDate dateOfBirth;

    @Min(value = 10, message = "Must be at least 10 years old")
    @Max(value = 100, message = "Cannot be over 100 years old")
    private int age;
    
    private String id;
    
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getPhoneNumber() {
        return phoneNumber;
    }
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }
    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }
    public int getAge() {
        return age;
    }
    public void setAge(int age) {

        this.age = Period.between(this.dateOfBirth, LocalDate.now()).getYears();
    }
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    

    public Contact(String name, String email, String phoneNumber, LocalDate dateOfBirth) {
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.dateOfBirth = dateOfBirth;
    }

    @Override
    public String toString() {
        return "Contact [name=" + name + ", email=" + email + ", phoneNumber=" + phoneNumber + ", dateOfBirth="
                + dateOfBirth + "]";
    }

    // Include generateId method here so that we can generate it right along with our empty Contact constructor.
    public String generateId() {
        Random r = new Random();
        StringBuffer sb = new StringBuffer();
        while (sb.length() < 8) {
            sb.append(Integer.toHexString(r.nextInt()));
        }
        return sb.toString().substring(0, 8);
    }

    public Contact() {
        this.id = generateId();
    }
    
}
