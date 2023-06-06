package sg.com.nus.iss.test3.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import sg.com.nus.iss.test3.model.Contact;

@Repository
public class RedisRepo {
    /*
     *  1. Save contact to Redis after filling form successfully.
     *  2. Retrieve specific contact from Redis.
     *  3. Retrieve list of contacts from Redis.
     */

    // Set the name reference for our database in Redis.
    final String CONTACT_LIST = "contactList";

    // Autowire the RedisTemplate object.
    @Autowired
    RedisTemplate<String, Object> template;

    // Method to save contact upon successful form fill up.
    public void saveContact(Contact contact) {
        
        /*
         *  Initiate hashoperation to save our data in hash.
         *  First parameter: Database reference.
         *  Second parameter: Contact ID reference.
         *  Third parameter: Contact object.
         */
        HashOperations<String, String, Contact> ops = template.opsForHash();
        ops.put(CONTACT_LIST + "_HASH", contact.getId(), contact);
        // Add attribute in Controller if we want to display the details back right after they submitted the form.
    }

    // Method to retrieve contact details by ID, data extracted will then be casted to a Contact object.
    public Contact getContactById(String contactId) {
        Contact contact = (Contact) template.opsForHash().get(CONTACT_LIST + "_HASH", contactId);
        return contact;
    }

    // Method to retrieve a list of all contacts.
    public List<Contact> getAllContacts() {
        List<Contact> contacts = new ArrayList<>();

        // Extract all entries into a Map, before iterating through the map and putting the Keys into the Contacts list.
        Map<Object, Object> contactMap = template.opsForHash().entries(CONTACT_LIST + "__HASH");
        
        for (Map.Entry<Object, Object> entry : contactMap.entrySet()) {
                contacts.add((Contact) entry.getKey()); 
        }
        
        return contacts;
    }
}
