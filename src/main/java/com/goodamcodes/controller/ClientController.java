package com.goodamcodes.controller;

import com.goodamcodes.model.Client;
import com.goodamcodes.model.Constant;
import com.goodamcodes.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = Constant.BASE_URL + "/client")
public class ClientController {

    @Autowired
    private ClientService clientService;

    @GetMapping("/all")
    public ResponseEntity<?> getClients(){
        List<Client> clients = clientService.getClients();
        return ResponseEntity.status(HttpStatus.OK).body(clients);
    }

    @PostMapping("/new")
    public ResponseEntity<String> addClient(@RequestBody Client client){
        clientService.addClient(client);
        return ResponseEntity.status(HttpStatus.CREATED).body("Email sent successfully");
    }

    @DeleteMapping(path = "/delete/{clientId}")
    public ResponseEntity<String>  deleteClient(@PathVariable("clientId") Long clientId){
        clientService.deleteClient(clientId);
        return ResponseEntity.status(HttpStatus.OK).body("Client deleted successfully");
    }

}
