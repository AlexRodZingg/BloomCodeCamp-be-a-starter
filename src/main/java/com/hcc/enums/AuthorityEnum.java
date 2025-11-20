package com.hcc.enums;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat
public enum AuthorityEnum {
    ROLE_LEARNER,
    ROLE_REVIEWER,
    ROLE_ADMIN;
}