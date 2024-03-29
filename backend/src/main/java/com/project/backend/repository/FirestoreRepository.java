package com.project.backend.repository;

import java.util.Map;

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
    public <T extends Model> void saveDocument(T model) {
        Class<?> type = model.getClass();
        try {
            String collectionName = type.getAnnotation(CollectionName.class).value();
            repository.collection(collectionName).add(model);
        } catch (Exception e) {
            exceptionLog.log(e, this.getClass().getName());
        }
    }
    public void saveDocument(String collectionName, Map<String, Object> model) {
        try {
            repository.collection(collectionName).add(model);
        } catch (Exception e) {
            exceptionLog.log(e, this.getClass().getName());
        }
    }
    public DocumentSnapshot getDocumentById(String collectionName, String id) {
        try {
            CollectionReference collection = repository.collection(collectionName);
            DocumentReference documentReference = collection.document(id);
            return documentReference.get().get();
        } catch (Exception e) {
            exceptionLog.log(e, this.getClass().getName());
        }
        return null;
    }
}
