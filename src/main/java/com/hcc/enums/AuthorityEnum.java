package com.hcc.enums;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * Defines all authority types a user can be: learner, reviewer, or admin
 */
@JsonFormat
public enum AuthorityEnum {
    ROLE_LEARNER,
    ROLE_REVIEWER,
    ROLE_ADMIN;
}