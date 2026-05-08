package com.app.security.dtos;

import jakarta.validation.constraints.*;

import java.util.Set;

public class UserDto {

    @NotBlank(message = "First Name must be required")
    private String firstName;

    @NotBlank(message = "Last Name must be required")
    private String lastName;

    @NotBlank(message = "Email must be required")
    @Email(message = "Please enter a valid email")
    private String email;

    @NotBlank(message = "Password must be required")
    @Size(min=6, message="Password must be of at least 6 characters long.")
    private String password;

    @NotEmpty(message = "roles must be required")
    private Set<@NotBlank(message = "Please enter valid role") String> roles;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<String> getRoles() {
        return roles;
    }

    public void setRoles(Set<String> roles) {
        this.roles = roles;
    }

    @Override
    public String toString() {
        return "UserDto{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", roles=" + roles +
                '}';
    }
}
