package com.zeecoder.recipient.security.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;

import static com.zeecoder.recipient.security.user.UserPermission.*;
import static java.util.stream.Collectors.toSet;


@Getter
@AllArgsConstructor
public enum Role {

    ADMIN(generalPermission()),
    USER(Set.of(READ_INTERNAL, WRITE_INTERNAL, DELETE_INTERNAL)),
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
                .collect(toSet());

        userPermissions.add(new SimpleGrantedAuthority("ROLE_" + this.name()));

        return userPermissions;
    }
}