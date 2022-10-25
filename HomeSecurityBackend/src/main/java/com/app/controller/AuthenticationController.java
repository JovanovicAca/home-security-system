package com.app.controller;

import com.app.dto.UserTokenState;
import com.app.model.User;
import org.springframework.http.HttpHeaders;
import com.app.security.JwtAuthenticationRequest;
import com.app.service.implementation.UserService;
import com.app.util.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@CrossOrigin
@RequestMapping(value = "/api/auth", produces = MediaType.APPLICATION_JSON_VALUE)
public class AuthenticationController {

    private final TokenUtils tokenUtils;
    private final AuthenticationManager authenticationManager;
    private final UserService userService;

    @Autowired
    public AuthenticationController(TokenUtils tokenUtils, AuthenticationManager authenticationManager,
                                    UserService userService){
        this.tokenUtils = tokenUtils;
        this.authenticationManager = authenticationManager;
        this.userService = userService;
    }

    @GetMapping("/some")
    public String some(){
        return "Https radi";
    }

    @PostMapping("/login")
    public ResponseEntity<UserTokenState> createAuthenticationToken(@RequestBody JwtAuthenticationRequest
                                                                                authenticationRequest,
                                                                    HttpServletResponse response) {

        UsernamePasswordAuthenticationToken u = new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(),
                authenticationRequest.getPassword());
        Authentication authentication;
        try {
            authentication = authenticationManager.authenticate(u);
        } catch (BadCredentialsException | InternalAuthenticationServiceException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }

        SecurityContextHolder.getContext().setAuthentication(authentication);

        User user = (User) authentication.getPrincipal();

        if (user.getAccountLocked()) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }

        String fingerprint = tokenUtils.generateFingerprint();
        String jwt = tokenUtils.generateToken(user.getUsername(), fingerprint);
        int expiresIn = tokenUtils.getExpiredIn();

        String cookie = "Fingerprint=" + fingerprint + "; HttpOnly; Path=/";

        HttpHeaders headers = new HttpHeaders();
        headers.add("Set-Cookie", cookie);

        return ResponseEntity.ok().headers(headers).body(new UserTokenState(jwt, expiresIn));
    }

    @GetMapping("/getLoggedIn")
    public ResponseEntity<User> getLoggedInUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication instanceof AnonymousAuthenticationToken) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        User user = (User) authentication.getPrincipal();
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.BAD_GATEWAY);
        }

        System.out.println(user.getRole().getName());
        return new ResponseEntity<>(user, HttpStatus.OK);

    }

    @GetMapping(value = "/logOut", produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity logoutUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (!(auth instanceof AnonymousAuthenticationToken)){
            SecurityContextHolder.clearContext();

            return new ResponseEntity<>("You successfully logged out!", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("User is not authenticated!", HttpStatus.BAD_REQUEST);
        }

    }
}
