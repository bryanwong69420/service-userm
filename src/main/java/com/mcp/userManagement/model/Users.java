package com.mcp.userManagement.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "mcp_users",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "username"), @UniqueConstraint(columnNames = "email")
        })
@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class Users extends BaseModel implements Serializable {

    @Id
    private Long id;

    @NotBlank
    @Size(max = 200)
    @Column(name = "username")
    private String username;

    @NotBlank
    @Size(max = 50)
    @Email
    @Column(name = "email")
    private String email;

    @NotBlank
    @Size(max = 200)
    private String password;

    @Column(name = "last_login_at")
    private LocalDateTime lastLoginAt;

    @Builder.Default
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "mcp_users_role_map",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

}
