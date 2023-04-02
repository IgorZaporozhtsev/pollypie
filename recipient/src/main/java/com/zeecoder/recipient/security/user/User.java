package com.zeecoder.recipient.security.user;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serial;
import java.util.Set;

@Entity
@Table(name = "internal_user")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@EqualsAndHashCode
public class User implements UserDetails {

    @Serial
    private static final long serialVersionUID = -5550656808402899820L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "internal_user_seq")
    @SequenceGenerator(name = "internal_user_seq", allocationSize = 1)
    private int id;
    @Column
    private String username;
    @Column
    private String lastName;
    @Column
    private String email;
    @Column
    private String password;
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "authorities")
    private Set<SimpleGrantedAuthority> authorities;


    @Override
    public Set<SimpleGrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
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
