package com.xingyu.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.xingyu.group.OnUpdate;
import lombok.Data;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class AddressBook {
    @Id
    @GeneratedValue
    @NotNull(groups = OnUpdate.class, message = "Please provide id to update")
    private Long id;
    @ManyToOne
    @JsonIgnore
    private User user;

    @ElementCollection(fetch = FetchType.EAGER)
    @Fetch(value = FetchMode.JOIN)
    private List<Customer> customer = new ArrayList<>();
}
