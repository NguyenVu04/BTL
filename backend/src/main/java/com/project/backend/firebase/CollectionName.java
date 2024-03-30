package com.project.backend.firebase;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.annotation.Nonnull;

@Retention(RetentionPolicy.CLASS)
public @interface CollectionName {
    @Nonnull String value();
}
