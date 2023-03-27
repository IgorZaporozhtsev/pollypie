package com.zeecoder.recipient.security.user;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum UserPermission {
    /**
     * INTERNAL - access to manage admin panel, crating users, providing permissions
     */
    READ_INTERNAL("internal:read"),
    WRITE_INTERNAL("internal:write"),
    DELETE_INTERNAL("internal:delete"),

    /**
     * INTERNAL - access for clients
     */

    READ_EXTERNAL("external:read"),
    WRITE_EXTERNAL("external::write"),
    DELETE_EXTERNAL("external::delete");

    private final String permission;

}
