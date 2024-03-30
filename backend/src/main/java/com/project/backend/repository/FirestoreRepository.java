package com.project.backend.repository;

import java.util.Map;

import javax.annotation.Nullable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.project.backend.exceptionhandler.ExceptionLog;
import com.project.backend.firebase.CollectionName;
import com.project.backend.model.Model;

@Repository
public class FirestoreRepository {
    @Autowired
    private Firestore repository;
    @Autowired
    private ExceptionLog exceptionLog;
    @Nullable
    public CollectionReference getCollection(Class<?> type) {
        return type.isAnnotationPresent(CollectionName.class) ? 
               repository.collection(type.getAnnotation(CollectionName.class).value()) : 
               null;
    }
    public <T extends Model> void saveDocument(T model) {
        if (model == null) {
            exceptionLog.log(new IllegalArgumentException(this.getClass().getName()));
            return;
        }
        CollectionReference collection = getCollection(model.getClass());
        if (collection != null) {
            collection.add(model);
        } else {
            exceptionLog.log(new IllegalArgumentException(this.getClass().getName()));
        }
    }

    public void saveDocument(Class<?> type, Map<String, Object> model) {
        if (model == null) {
            exceptionLog.log(new IllegalArgumentException(this.getClass().getName()));
            return;
        }
        CollectionReference collection = getCollection(type);
        if (collection != null) {
            collection.add(model);
        } else {
            exceptionLog.log(new IllegalArgumentException(this.getClass().getName()));
        }
    }

    @Nullable
    public DocumentSnapshot getDocumentById(Class<?> type, String id) {
        if (id == null) {
            exceptionLog.log(new IllegalArgumentException(this.getClass().getName()));
            return null;
        }
        CollectionReference collection = getCollection(type);
        if (collection != null) {
            DocumentReference documentReference = collection.document(id);
            try {
                DocumentSnapshot documentSnapshot = documentReference.get()
                                                                     .get();
                return documentSnapshot.exists() ? documentSnapshot : null;
            } catch (Exception e) {
                exceptionLog.log(new IllegalArgumentException(this.getClass().getName()));
                return null;
            }
        } else {
            exceptionLog.log(new IllegalArgumentException(this.getClass().getName()));
            return null;
        }
    }

    public void deleteDocumentById(Class<?> type, String id) {
        if(id == null) {
            exceptionLog.log(new IllegalArgumentException(this.getClass().getName()));
            return;
        }
        CollectionReference collection = getCollection(type);
        if (collection != null) {
            DocumentReference documentReference = collection.document(id);
            documentReference.delete();
        } else {
            exceptionLog.log(new IllegalArgumentException(this.getClass().getName()));
        }
    }

    public <T extends Model> void updateDocumentById(T model) {
        if (model == null || model.getId() == null) {
            exceptionLog.log(new IllegalArgumentException(this.getClass().getName()));
            return;
        }
        CollectionReference collection = getCollection(model.getClass());
        if (collection != null) {
            @SuppressWarnings("null")
            DocumentReference documentReference = collection.document(model.getId());
            documentReference.set(model);
        } else {
            exceptionLog.log(new IllegalArgumentException(this.getClass().getName()));
        }
    }

    public <T extends Model> void updateDocumentById(Class<?> type, String id, Map<String, Object> attributes) {
        if (id == null || attributes == null) {
            exceptionLog.log(new IllegalArgumentException(this.getClass().getName()));
            return;
        }
        CollectionReference collection = getCollection(type);
        if (collection != null) {
            DocumentReference documentReference = collection.document(id);
            documentReference.update(attributes);
        } else {
            exceptionLog.log(new IllegalArgumentException(this.getClass().getName()));
        }
    }
}
