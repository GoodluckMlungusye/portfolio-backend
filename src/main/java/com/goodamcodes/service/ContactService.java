package com.goodamcodes.service;

import com.goodamcodes.model.Contact;
import com.goodamcodes.repository.ContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ContactService {

    @Autowired
    private ContactRepository contactRepository;

    public List<Contact> getContacts(){
        return contactRepository.findAll();
    }

    public void addContact(Contact contact){
        Optional<Contact> existingContact = contactRepository.findByMedium(contact.getMedium());
        if(existingContact.isPresent()){
            throw new IllegalStateException("Contact medium exists");
        }
        contactRepository.save(contact);
     }

     public void deleteContact(Long contactId){
        boolean isExisting = contactRepository.existsById(contactId);
        if(!isExisting){
            throw new IllegalStateException("Contact medium does not exist");
        }
        contactRepository.deleteById(contactId);
     }

     public void updateContact(Long contactId, Contact contact){
         Contact existingContact = contactRepository.findById(contactId).orElseThrow(
                 () -> new IllegalStateException("Navigation Link does not exist")
         );
         existingContact.setContactLink(contact.getContactLink());
         existingContact.setMedium(contact.getMedium());
         contactRepository.save(existingContact);
     }
}
