package sg.com.nus.iss.test3.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.validation.Valid;
import sg.com.nus.iss.test3.model.Contact;
import sg.com.nus.iss.test3.repository.RedisRepo;

@Controller
@RequestMapping(path="/")
public class ContactsController {
    
    // Wire to repository
    @Autowired
    RedisRepo repository;


    // Incorporate displaying of Contact object here as well for us to show filled in information back to user after submitting.
    @GetMapping
    public String showHomePage(Model model) {
        model.addAttribute("contact", new Contact());
        return "home";
    }


    // Post method as we are uploading the filled in info to Redis database.
    @PostMapping(consumes = "application/x-www-form-urlencoded", path="/contact")
    public String saveThisContact(@Valid Contact contact, BindingResult bindingResult, Model model) throws Exception {

        // Return to home page if there are any errors not captured within our defined validation constraints.
        if (bindingResult.hasErrors()) {
            return "home";
        }

        // Save contact if there are no errors.
        repository.saveContact(contact);
        model.addAttribute("contact", contact);
        model.addAttribute("successMessage", "Contact saved successfully, with status code: " + HttpStatus.CREATED + ".");
        return "contact";
    }


    // Get method to display information of requested contact.
    @GetMapping("/contact/{contactId}")
    public String getContactById(Model model, @PathVariable String contactId) {
        Contact contact = new Contact();
        contact = repository.getContactById(contactId);

        // Handle contact not found in database. Define error message and route to error html to display it.
        if (contact == null) {
            model.addAttribute("error", "Contact not found");
            return "error";
        }

        model.addAttribute("contact", contactId);
        return "contact";
    }

    // Get method to retrieve all contacts from database. ID and name will be displayed according to contactlist html.
    // We define a link in the html as well so that when clicked, it will direct the user to getContactById method to display full info.
    @GetMapping("/list")
    public String getAllContacts(Model model) {
        List<Contact> allContacts = repository.getAllContacts();
        model.addAttribute("contactlist", allContacts);
        return "contactlist";
    }

}
