package com.yahibrobank.Config;

import com.fasterxml.jackson.annotation.JsonBackReference;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.cglib.proxy.NoOp;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.NoOpAuthenticationEntryPoint;

import javax.sql.DataSource;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
public class SecurityConfig {


    @Bean
    public SecurityFilterChain getSecurityFilterChain(HttpSecurity http) throws Exception {

        // approach 1 permit and authenticated requests
        http.csrf(csrf -> csrf.disable())
                .authorizeHttpRequests((requests) -> requests.requestMatchers("/myAccount", "/myBalance", "/myCards", "/myLoans").authenticated().
                requestMatchers("/contact", "/notices","/register").permitAll());

        http.formLogin(withDefaults());
        http.httpBasic(withDefaults());

        return http.build();


         //approach 2 denying all the requests
//       return     http.authorizeHttpRequests(request -> request.anyRequest().denyAll())
//                .formLogin(withDefaults()).httpBasic(withDefaults()).build();
//


         // approach 3 permit all requests
//       return   http.authorizeHttpRequests(requests -> requests.anyRequest().permitAll())
//        .formLogin(withDefaults())
//                .httpBasic(withDefaults()).build();


    }

//    @Bean
//    public InMemoryUserDetailsManager getUserDatails() {

//      approach 1 using defaultPasswordEncoder
//        UserDetails user1 = User.withDefaultPasswordEncoder()
//                .password("1234")
//                .username("admin")
//                .roles("ADMIN").build();
//
//        UserDetails user2 = User.withDefaultPasswordEncoder()
//                .password("1234")
//                .username("user")
//                .roles("USER").build();
//
//        return new InMemoryUserDetailsManager(user1, user2);


//        approach 2 using explicitly specified encoder NoOpPasswordEncoder
//        UserDetails user1 = User.withUsername("admin")
//                .password("1234")
//                .roles("ADMIN").build();
//
//        UserDetails user2 = User.withUsername("user")
//                .password("1234")
//                .roles("USER").build();
//
//        return new InMemoryUserDetailsManager(user1, user2);
//
//
//    }


//    @Bean
//    public UserDetailsService userDetailsService(DataSource dataSource){
//        //we use this manager if we use the built in spring schema / tables
//        return new JdbcUserDetailsManager(dataSource);
//    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return NoOpPasswordEncoder.getInstance();
    }
}
