package com.beprime.persistance.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "T_USER", uniqueConstraints = {@UniqueConstraint(columnNames = {"U_DELETED_TOKEN", "U_EMAIL",}),
        @UniqueConstraint(columnNames = {"U_DELETED_TOKEN", "U_ID"})})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"isDeleted", "deletedToken"})
public class User implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "U_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;
    @Column(name = "U_LAST_NAME")
    @EqualsAndHashCode.Include
    private String lastName;
    @Column(name = "U_FIRST_NAME")
    @EqualsAndHashCode.Include
    private String firstName;
    @Column(name = "U_ADRESS")
    @EqualsAndHashCode.Include
    private String adress;
    @Column(name = "U_FAX")
    @EqualsAndHashCode.Include
    private String fax;
    @Column(name = "U_EMAIL")
    @EqualsAndHashCode.Include
    private String email;
    @Column(name = "U_CITY")
    @EqualsAndHashCode.Include
    private String city;
    @Column(name = "U_PICURE")
    @EqualsAndHashCode.Include
    private String picture;
    @Column(name = "U_ACTIVE")
    @EqualsAndHashCode.Include
    private boolean active;
    @Column(name = "U_DATE_NESS")
    @EqualsAndHashCode.Include
    private LocalDateTime dateNaissanced;
    @Column(name = "U_DATE_CREATED")
    @EqualsAndHashCode.Include
    private LocalDateTime dateCreated;
    @JsonIgnore
    @JsonIgnoreProperties
    @Column(name = "U_IS_DELETED", columnDefinition = "bit default 0")
    private boolean isDeleted;
    @JsonIgnore
    @JsonIgnoreProperties
    @Column(name = "U_DELETED_TOKEN")
    private UUID deletedToken;
    @ManyToMany(fetch = FetchType.EAGER)
    private List<Group> groups=new ArrayList<>();
    @Column(name = "U_USER_NAME")
    private String userName;
    @Column(name = "U_PASSWORD")
    private String password;

    public User(Long id, String lastName, String firstName, String adress, String fax, String email, String city, String picture, boolean active, LocalDateTime dateNaissanced, LocalDateTime dateCreated, String userName, String password) {
        this.id = id;
        this.lastName = lastName;
        this.firstName = firstName;
        this.adress = adress;
        this.fax = fax;
        this.email = email;
        this.city = city;
        this.picture = picture;
        this.active = active;
        this.dateNaissanced = dateNaissanced;
        this.dateCreated = dateCreated;
        this.userName = userName;
        this.password = password;
    }
}

