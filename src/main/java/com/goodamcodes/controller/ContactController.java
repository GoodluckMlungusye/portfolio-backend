package com.goodamcodes.controller;

import com.goodamcodes.configuration.Constant;
import com.goodamcodes.model.Contact;
import com.goodamcodes.service.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = Constant.BASE_URL + "/contact")
public class ContactController {

    @Autowired
    private ContactService contactService;

    @GetMapping("/all")
    public ResponseEntity<?> getContacts(){
        List<Contact> contacts = contactService.getContacts();
        return ResponseEntity.status(HttpStatus.OK).body(contacts);
    }

    @PostMapping("/new")
    public ResponseEntity<String>  addContact(@RequestBody Contact contact){
        contactService.addContact(contact);
        return ResponseEntity.status(HttpStatus.CREATED).body("Contact added successfully");
    }

    @DeleteMapping(path = "/delete/{contactId}")
    public ResponseEntity<String> deleteContact(@PathVariable("contactId") Long contactId){
        contactService.deleteContact(contactId);
        return ResponseEntity.status(HttpStatus.OK).body("Contact deleted successfully");
    }

    @PutMapping(path = "/update/{contactId}")
    public ResponseEntity<String>  updateContact(
          @PathVariable("contactId") Long contactId,
          @RequestBody Contact contact
    )
    {
        contactService.updateContact(contactId,contact);
        return ResponseEntity.status(HttpStatus.CREATED).body("Contact updated successfully");
    }
}
