package com.atmosware.library_project.entities;

import com.atmosware.library_project.core.entities.BaseEntity;
import com.atmosware.library_project.entities.enums.MembershipStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "users")
public class User extends BaseEntity implements UserDetails {

    @Column(name = "username")
    private String username;

    @Column(name="password")
    private String password;

    @Column(name="email")
    private String email;

    @OneToMany(mappedBy = "user")
    private List<Transaction> transactions;

    @Column(name = "membership_status")
    @Enumerated(EnumType.STRING)
    private MembershipStatus membershipStatus = MembershipStatus.ACTIVE;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinTable(name="user_role",
            joinColumns = @JoinColumn(name="user_id"),
            inverseJoinColumns = @JoinColumn(name="role_id"))
    private Set<Role> authorities;

    @Column(name="membership_date")
    private LocalDateTime membershipExpirationDate;

    @Column(name="outstanding_balance")
    private Double outstandingBalance = 0.0;

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
