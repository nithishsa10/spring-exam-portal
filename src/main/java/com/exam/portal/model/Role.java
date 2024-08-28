package com.exam.portal.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Role {
    @Id
    private String roleName;
    private String description;

    public Role(String role) {
        this.roleName = role;
    }
}
