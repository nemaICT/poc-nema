package com.demo.pocnema.controller;


import com.demo.pocnema.exception.ResourceNotFoundException;
import com.demo.pocnema.model.Client;
import com.demo.pocnema.repository.ClientRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


// @RestController annotation is a combination of Spring’s @Controller and @ResponseBody annotations.
@RestController
//("/api") declares that the url for all the apis in this controller will start with /api.
@RequestMapping("/api")
public class ClientController {

//   @Autowired
    private final ClientRepository clientRepository;

    public ClientController(ClientRepository repository) {
        this.clientRepository = repository;
    }


    // Get All clients
    @GetMapping("/clients")
    public List<Client> getAllNotes() {
        return clientRepository.findAll();
    }

    // Create a new Client
    @PostMapping("/client")
    /*The @RequestBody annotation is used to bind the request body with a method parameter.
    The @Valid annotation makes sure that the request body is valid. as Client first and lastname were marked
    with @NotBlank annotation in the Client model.
    If the request body doesn’t have a firstname or  lastname, then spring will return a 400 BadRequest error to the client.*/
    public Client createClient(@Valid @RequestBody Client client) {
        return clientRepository.save(client);
    }

    // Get Client by id
    @GetMapping("/clients/{id}")
    // The @PathVariable annotation, as the name suggests, is used to bind a path variable with a method parameter.
    public Client getClientById(@PathVariable(value = "id") Long id) {
        return clientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Client", "id", id));
        /*a ResourceNotFoundException whenever a Note with the given id is not found.
        This will cause Spring Boot to return a 404 Not Found error to the user
        (as it was added a @ResponseStatus(value = HttpStatus.NOT_FOUND) annotation to the ResourceNotFoundException class).*/
    }


    // Update a Client
    @PutMapping("/clients/{id}")
    public Client updateClient(@PathVariable(value = "id") Long id, @Valid @RequestBody Client clientDetail) {

        Client client = clientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Client", "id", id));

        client.setFirstname(clientDetail.getFirstname());
        client.setLastname(clientDetail.getLastname());

        return clientRepository.save(client);
    }

    // Delete a Client
    @DeleteMapping("/clients/{id}")
    public ResponseEntity<?> deleteClient(@PathVariable(value = "id") Long id) {
        Client client = clientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Client", "id", id));

        clientRepository.delete(client);
        return ResponseEntity.ok().build();
    }

}
