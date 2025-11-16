package com.mcp.userManagement.model;

import jakarta.persistence.*;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "mcp_users_role",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "username"), @UniqueConstraint(columnNames = "email")
        })
@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class Role extends BaseModel{

    @Id
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;


}
