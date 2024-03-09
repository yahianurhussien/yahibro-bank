package com.yahibrobank.Config;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.cglib.proxy.NoOp;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.NoOpAuthenticationEntryPoint;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import javax.sql.DataSource;

import java.util.Collections;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
public class SecurityConfig {


    @Bean
    public SecurityFilterChain getSecurityFilterChain(HttpSecurity http) throws Exception {

        // approach 1 permit and authenticated requests
        http.cors(corsCustomizer -> corsCustomizer.configurationSource(new CorsConfigurationSource() {
            @Override
            public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
                CorsConfiguration config = new CorsConfiguration();
                config.setAllowedOrigins(Collections.singletonList("http://localhost:4200"));
                config.setAllowedMethods(Collections.singletonList("*"));
                config.setAllowCredentials(true);
                config.setAllowedHeaders(Collections.singletonList("*"));
                config.setMaxAge(3600L);
                return config;
            }
        })).csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests((requests) -> requests.requestMatchers("/myAccount", "/myBalance", "/myCards", "/myLoans","/user").authenticated().
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

    //this password encoder is not recommended for prod env
//    @Bean
//    public PasswordEncoder passwordEncoder(){
//        return NoOpPasswordEncoder.getInstance();
//    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
