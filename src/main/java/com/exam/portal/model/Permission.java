package com.exam.portal.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Permission {

    ADMIN_CREATE("create"),
    ADMIN_READ("read"),
    ADMIN_UPDATE("update"),
    ADMIN_DELETE("delete"),

    USER_CREATE("create"),
    USER_READ("read"),
    USER_UPDATE("update"),
    USER_DELETE("delete");

    private final String permission;

}
