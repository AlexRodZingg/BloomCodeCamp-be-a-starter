package com.hcc.enums;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * Defines all authority types for a user
 */
@JsonFormat
public enum AuthorityEnum {
    ROLE_LEARNER,
    ROLE_REVIEWER,
    ROLE_ADMIN;
}