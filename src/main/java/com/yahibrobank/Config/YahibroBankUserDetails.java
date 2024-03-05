package com.yahibrobank.Config;

import com.yahibrobank.model.Customer;
import com.yahibrobank.repository.CustomerRepository;
import org.hibernate.annotations.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
@Component
public class YahibroBankUserDetails implements UserDetailsService {

    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

     String userName, passWord = null;
     List<GrantedAuthority> authorities = null;

     List<Customer> users = customerRepository.findByEmail(username);

     if(users.size()==0){
         throw new UsernameNotFoundException("User details not found for the user : "+username);
     }else{
         username = users.get(0).getEmail();
         passWord = users.get(0).getPwd();
         authorities = new ArrayList<>();
         authorities.add(new SimpleGrantedAuthority(users.get(0).getRole()));
     }


        return new User(username,passWord,authorities);
    }
}
