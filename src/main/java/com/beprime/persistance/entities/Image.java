package com.beprime.persistance.entities;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.UUID;

@Entity
@Table(name = "T_IMAGE", uniqueConstraints = {@UniqueConstraint(columnNames = {"IMG_DELETED_TOKEN", "IMG_ID",}),
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"isDeleted", "deletedToken"})
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Image implements Serializable {
    @Id
    @Column(name = "IMG_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;
    @Column(name = "IMG_PATH",length = 65555)
    private String data;
    @Column(name = "IMG_IS_DELETED", columnDefinition = "bit default 0")
    private boolean isDeleted;
    @Column(name = "IMG_DELETED_TOKEN")
    private UUID deletedToken;

    public Image(Long id, String data) {
        this.id = id;
        this.data = data;
        this.isDeleted = false;
        this.deletedToken = null;
    }

    public Image(String data) {
        this.id = id;
        this.data=data;
        this.isDeleted = false;
        this.deletedToken = null;
    }
}
