package com.zeecoder.recipient.security.config.users;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;
import java.util.stream.Collectors;

import static com.zeecoder.recipient.security.config.users.UserPermission.*;

@AllArgsConstructor
@Getter
public enum Role {


    ADMIN(generalPermission()),
    USER(Set.of(READ_INTERNAL, WRITE_INTERNAL)),
    CLIENT(Set.of(READ_EXTERNAL)),
    MODERATOR(generalPermission());

    private static Set<UserPermission> generalPermission() {
        return Set.of(
                READ_INTERNAL, WRITE_INTERNAL, DELETE_INTERNAL, READ_EXTERNAL, WRITE_EXTERNAL, DELETE_EXTERNAL);
    }

    private final Set<UserPermission> permissions;

    public Set<SimpleGrantedAuthority> getAuthorities() {
        var userPermissions = getPermissions().stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toSet());

        userPermissions.add(new SimpleGrantedAuthority("ROLE_" + this.name()));

        return userPermissions;
    }
}
