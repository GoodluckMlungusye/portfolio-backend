package com.goodamcodes.service;

import com.goodamcodes.model.Client;
import com.goodamcodes.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClientService {

    @Autowired
    private ClientRepository clientRepository;

    public List<Client> getClients(){
        return clientRepository.findAll();
    }

    public void addClient(Client client){
        Optional<Client> existingClient = clientRepository.findByEmail(client.getEmail());
        if(existingClient.isPresent()){
            throw new IllegalStateException("Email exists");
        }
        clientRepository.save(client);
    }

    public void deleteClient(Long clientId){
        boolean isExisting = clientRepository.existsById(clientId);
        if(!isExisting){
            throw new IllegalStateException("Client does not exist");
        }
        clientRepository.deleteById(clientId);
    }

}
