package com.app.controller;

import com.app.dto.UserDTO;
import com.app.dto.UserRealEstateDTO;
import com.app.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/user")
public class UserController {

    private final IUserService userService;

    @Autowired
    public UserController(IUserService userService) {
        this.userService = userService;
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @GetMapping
    public ResponseEntity<?> getAllUsers() {
        return new ResponseEntity<>(userService.findAll(), HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @GetMapping("/owners")
    public ResponseEntity<?> getAllOwners() {
        return new ResponseEntity<>(userService.findAllByRole("ROLE_OWNER"), HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @GetMapping("/tenants")
    public ResponseEntity<?> getAllTenants() {
        return new ResponseEntity<>(userService.findAllByRole("ROLE_TENANT"), HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PostMapping("/delete/{username}")
    public ResponseEntity<?> deleteUser(@PathVariable String username) {
        this.userService.delete(username);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PostMapping(value = "/new-user", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createUser(@RequestBody UserDTO userDTO) {
        this.userService.createUser(userDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PostMapping(value = "/update-user", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateUser(@RequestBody UserDTO userDTO) {
        this.userService.updateUser(userDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping(value = "/lock/{username}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> lockUser(@PathVariable String username) {
        this.userService.lockUser(username);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @GetMapping("/real-estate/{id}")
    public ResponseEntity<?> userRealEstates(@PathVariable Integer id) {
        return new ResponseEntity<>(this.userService.findUserRealEstates(id), HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PostMapping(value = "/access", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> editAccessToRealEstate(@RequestBody UserRealEstateDTO userRealEstateDTO) {
        this.userService.editAccessToRealEstate(userRealEstateDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
