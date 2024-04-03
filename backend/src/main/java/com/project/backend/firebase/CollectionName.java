package com.project.backend.firebase;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.annotation.Nonnull;

// Annotation to specify the name of a Firestore collection
@Retention(RetentionPolicy.RUNTIME)
public @interface CollectionName {
    // The name of the Firestore collection
    @Nonnull String value();
}