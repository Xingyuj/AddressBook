package com.xingyu.controller;

import com.xingyu.domain.AddressBook;
import com.xingyu.domain.User;
import com.xingyu.group.OnUpdate;
import com.xingyu.repository.AddressBookRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.security.sasl.AuthenticationException;
import javax.validation.Valid;
import java.util.Optional;

import static com.xingyu.utils.ControllerUtil.currentUserVerification;

@RestController
@RequestMapping("/api/v1/books")
@Tag(name = "AddressBook", description = "Operations to manage AddressBook")
public class AddressBookController {
    private final AddressBookRepository addressBookRepository;

    public AddressBookController(AddressBookRepository addressBookRepository) {
        this.addressBookRepository = addressBookRepository;
    }

    @PostMapping
    @Operation(summary = "Create a addressBook", description = "Create a addressBook")
    public ResponseEntity<?> create(@Valid @RequestBody AddressBook addressBook, Authentication authentication) {
        addressBook.setUser((User) (authentication.getPrincipal()));
        return new ResponseEntity<>(addressBookRepository.save(addressBook), HttpStatus.CREATED);
    }

    @GetMapping
    @Operation(summary = "Retrieve a addressBook details by id", description = "Retrieve a addressBook details by id")
    public ResponseEntity<?> list(Authentication authentication) {
        return new ResponseEntity<>(addressBookRepository.findByUser(
                ((User) (authentication.getPrincipal())).getUsername()), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Retrieve a addressBook details by id", description = "Retrieve a addressBook details by id")
    public ResponseEntity<?> get(@PathVariable(value = "id") Long id) throws AuthenticationException {
        if (!addressBookRepository.findById(id).isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        currentUserVerification(addressBookRepository.findById(id).get().getUser().getUsername());
        return new ResponseEntity<>(addressBookRepository.findById(id), HttpStatus.OK);
    }

    @PutMapping
    @Validated(OnUpdate.class)
    @Operation(summary = "Update a addressBook details", description = "Update a addressBook details")
    public ResponseEntity<AddressBook> put(@Valid @RequestBody AddressBook addressBook) throws AuthenticationException {
        Optional<AddressBook> optionalBook = addressBookRepository.findById(addressBook.getId());
        if (!optionalBook.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        User user = optionalBook.get().getUser();
        currentUserVerification(user.getUsername());
        addressBook.setUser(user);
        return new ResponseEntity<>(addressBookRepository.save(addressBook), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a addressBook by id", description = "Delete a addressBook by id")
    public ResponseEntity<?> delete(@PathVariable(value = "id") Long id) throws AuthenticationException {
        currentUserVerification(addressBookRepository.findById(id).orElse(new AddressBook()).getUser().getUsername());
        addressBookRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }
}
