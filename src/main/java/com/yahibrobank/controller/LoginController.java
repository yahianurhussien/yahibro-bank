package com.yahibrobank.controller;

import com.yahibrobank.model.Customer;
import com.yahibrobank.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;


    @PostMapping("/register")
    public ResponseEntity<Customer> registerUser(@RequestBody Customer customer){

      Customer savedCustomer = null;
      ResponseEntity response = null;
      try{
          String encryptedPassword = passwordEncoder.encode(customer.getPwd());
          customer.setPwd(encryptedPassword);
          savedCustomer = customerRepository.save(customer);

          if(savedCustomer.getId()>0){
              response = ResponseEntity
                      .status(HttpStatus.CREATED)
                      .body("Given user details are successfully registered");
          }

      }catch (Exception ex){
         response = ResponseEntity
                 .status(HttpStatus.INTERNAL_SERVER_ERROR)
                 .body("An exception occured due to "+ex.getMessage());
      }


      return response;
    }
}
