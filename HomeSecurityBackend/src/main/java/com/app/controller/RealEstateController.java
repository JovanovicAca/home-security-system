package com.app.controller;

import com.app.dto.RealEstateDTO;
import com.app.dto.UserDTO;
import com.app.model.RealEstate;
import com.app.service.IRealEstateService;
import com.app.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping(value = "/api/real-estate")
public class RealEstateController {

    private final IRealEstateService realEstateService;
    private final IUserService userService;

    @Autowired
    public RealEstateController(IRealEstateService realEstateService, IUserService userService) {
        this.realEstateService = realEstateService;
        this.userService = userService;
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @GetMapping
    public ResponseEntity<?> getAllRealEstates() {
        return new ResponseEntity<>(this.realEstateService.findAll(), HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<?> getOneRealEstate(@PathVariable Integer id) {
        return new ResponseEntity<>(this.realEstateService.findById(id), HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @GetMapping("/users/{id}")
    public ResponseEntity<?> getUsersInRealEstate(@PathVariable Integer id) {
        return new ResponseEntity<>(this.userService.findUsersInRealEstate(id), HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PostMapping(value = "/new-real-estate", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createRealEstate(@RequestBody RealEstateDTO realEstateDTO) {
        RealEstate realEstate = this.realEstateService.createRealEstate(realEstateDTO);
        this.userService.editUserRealEstates(realEstateDTO.getOwnerId(), realEstate);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PostMapping(value = "/user/{userId}/{realEstateId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> changeRealEstateUsers(@PathVariable Integer userId, @PathVariable Integer realEstateId) {
        this.userService.addRealEstateToUser(userId, realEstateId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PostMapping(value = "/remove-user/{userId}/{realEstateId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> removeUserFromRealEstate(@PathVariable Integer userId, @PathVariable Integer realEstateId) {
        this.userService.removeUserFromRealEstate(userId, realEstateId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
