package com.xingyu.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.xingyu.group.OnUpdate;
import com.xingyu.service.SimpleAuthenticationProvider;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity(name = "sys_user")
@Data
@JsonIgnoreProperties
public class User {
    @Id
    @GeneratedValue
    @NotNull(groups = OnUpdate.class, message = "Please provide id to update")
    private Long id;
    @NotNull
    @Column(unique = true)
    private String username;
    private String firstName;
    private String lastName;
    @NotNull
    private String password;
    @OneToMany
    @JsonIgnore
    private List<AddressBook> addressBooks;

    @PrePersist
    private void onChange() {
        this.password = SimpleAuthenticationProvider.passwordEncoder.encode(password);
    }
}
