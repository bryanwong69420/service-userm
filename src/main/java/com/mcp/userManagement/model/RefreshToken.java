package com.mcp.userManagement.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "mcp_refresh_token",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "ref_token")
        })
@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class RefreshToken extends BaseModel{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private Users user;

    @Column(name = "ref_token", unique = true)
    private String refreshToken;

    @Column(name = "exp_date")
    private LocalDateTime expDate;
}
