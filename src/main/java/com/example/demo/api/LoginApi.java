package com.example.demo.api;

import com.example.demo.controller.LoginController;
import com.example.demo.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

import java.security.Principal;
import java.time.Duration;
import java.time.LocalTime;
import java.util.Collection;

@RestController
public class LoginApi {

    @Autowired
    LoginController loginController;

    @CrossOrigin(origins = "*")
    @PostMapping("/api/login")
    public boolean isValidLogin(@RequestBody UserLoginDetails userLoginDetails) {
        return loginController.isValidLogin(userLoginDetails.getUsername(), userLoginDetails.getPassword());
    }
    @CrossOrigin(origins = "*")
    @PostMapping("/register")
    public boolean registerUser (@RequestBody UserRegisterDetails userRegisterDetails){
        return loginController.registerUser(userRegisterDetails.getUsername(), userRegisterDetails.getPassword(), userRegisterDetails.getEmail(), userRegisterDetails.getFirstName(),
                userRegisterDetails.getLastName(), userRegisterDetails.getPhone());
    }
    @CrossOrigin(origins = "*")
    @GetMapping("/api/getPrincipal")
    public User getCurrentUser(Principal principal) {
        String username = principal.getName();
        return loginController.getUserByUserName(username);
    }

    @CrossOrigin(origins = "*")
    @PostMapping("/api/user/seeNotification")
    public boolean seeNotification(Principal principal) {
        String username = principal.getName();
        return loginController.seeNotification(username);
    }

    @GetMapping(path = "/stream-flux", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> streamFlux() {
        return Flux.interval(Duration.ofSeconds(1))
                .map(sequence -> "Flux - " + LocalTime.now().toString());
    }
    public static class UserLoginDetails implements UserDetails {
        private String username;
        private String password;

        public UserLoginDetails() {
        }

        public UserLoginDetails(String username) {
            this.username = username;
        }

        public UserLoginDetails(String username, String password) {
            this.username = username;
            this.password = password;
        }

        public String getUsername() {
            return username;
        }

        @Override
        public boolean isAccountNonExpired() {
            return false;
        }

        @Override
        public boolean isAccountNonLocked() {
            return false;
        }

        @Override
        public boolean isCredentialsNonExpired() {
            return false;
        }

        @Override
        public boolean isEnabled() {
            return false;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        @Override
        public Collection<? extends GrantedAuthority> getAuthorities() {
            return null;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }
    private static class UserRegisterDetails {
        private String username;
        private String password;
        private String email;
        private String firstName;
        private String lastName;
        private String phone;

        public UserRegisterDetails() {
        }

        public UserRegisterDetails(String username, String password, String email, String fName, String lName, String phone) {
            this.username = username;
            this.password = password;
            this.email = email;
            this.firstName = fName;
            this.lastName = lName;
            this.phone = phone;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getEmail() { return email; }

        public void setEmail(String email) { this.email = email; }

        public String getFirstName() { return firstName; }

        public void setFirstName(String firstName) { this.firstName = firstName; }

        public String getLastName() { return lastName; }

        public void setLastName(String lastName) { this.lastName = lastName; }

        public String getPhone() { return phone; }

        public void setPhone(String phone) { this.phone = phone; }
    }
}
