package com.beprime.persistance.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.UUID;

@Entity
@Table(name = "T_GROUPE", uniqueConstraints = {@UniqueConstraint(columnNames = {"GR_DELETED_TOKEN", "GR_ID",}),
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"isDeleted", "deletedToken"})
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Group implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "GR_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;
    @Column(name = "GR_PRIVILEGED", unique = true, nullable = false)
    private String privileged;
    @JsonIgnore
    @JsonIgnoreProperties
    @Column(name = "GR_IS_DELETED", columnDefinition = "bit default 0")
    private boolean isDeleted;
    @JsonIgnore
    @JsonIgnoreProperties
    @Column(name = "GR_DELETED_TOKEN")
    private UUID deletedToken;

    public Group(Long id, String privileged) {
        this.id = id;
        this.privileged = privileged;
        this.isDeleted = false;
        this.deletedToken = null;
    }
}