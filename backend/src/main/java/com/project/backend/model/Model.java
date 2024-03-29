package com.project.backend.model;

import com.google.cloud.firestore.annotation.DocumentId;

public abstract class Model {
    @DocumentId
    private String id;

    public String getId() {
        return id;
    }
}
