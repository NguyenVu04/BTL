package com.project.backend.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.annotation.Nullable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.Query;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.cloud.firestore.WriteResult;
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
    public CollectionReference getCollection(Class<? extends Model> type) {
        return type.isAnnotationPresent(CollectionName.class) ? 
               repository.collection(type.getAnnotation(CollectionName.class).value()) : 
               null;
    }
    public <T extends Model> String saveDocument(T model) {
        if (model == null) {
            exceptionLog.log(new IllegalArgumentException(this.getClass().getName()));
            return null;
        }
        CollectionReference collection = getCollection(model.getClass());
        if (collection != null) {
            ApiFuture<DocumentReference> api = collection.add(model);
            try {
                return api.get().getId();
            } catch (Exception e) {
                exceptionLog.log(e, this.getClass().getName());
                return null;
            }
        } else {
            exceptionLog.log(new IllegalArgumentException(this.getClass().getName()));
            return null;
        }
    }

    public String saveDocument(Class<? extends Model> type, Map<String, Object> model) {
        if (model == null) {
            exceptionLog.log(new IllegalArgumentException(this.getClass().getName()));
            return null;
        }
        CollectionReference collection = getCollection(type);
        if (collection != null) {
            ApiFuture<DocumentReference> api = collection.add(model);
            try {
                return api.get().getId();
            } catch (Exception e) {
                exceptionLog.log(e, this.getClass().getName());
                return null;
            }
        } else {
            exceptionLog.log(new IllegalArgumentException(this.getClass().getName()));
            return null;
        }
    }

    @Nullable
    public DocumentSnapshot getDocumentById(Class<? extends Model> type, String id) {
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
                exceptionLog.log(e);
                return null;
            }
        } else {
            exceptionLog.log(new IllegalArgumentException(this.getClass().getName()));
            return null;
        }
    }
    @Nullable
    public List<DocumentSnapshot> getAllDocumentsByField(Class<? extends Model> type, 
                                                      String fieldName, 
                                                      Object value) {
        CollectionReference collection = getCollection(type);
        if (collection == null || fieldName == null) {
            exceptionLog.log(new IllegalArgumentException(this.getClass().getName()));
            return null;
        }
        Query query = collection.whereEqualTo(fieldName, value);
        try {
            ApiFuture<QuerySnapshot> futureQuerySnapshot = query.get();
            List<DocumentSnapshot> snapshots = new ArrayList<>();
            QuerySnapshot querySnapshot = futureQuerySnapshot.get();
            querySnapshot.getDocuments()
                         .forEach(doc -> snapshots.add(doc));
            return snapshots;
        } catch (Exception e) {
            exceptionLog.log(e);
            return null;
        }
    }
    public List<DocumentSnapshot> getDocumentsByField(Class<? extends Model> type,
                                                      String fieldName,
                                                      Object value,
                                                      String orderBy,
                                                      int limit)
    {
        CollectionReference colletion = getCollection(type);
        if (colletion == null || orderBy == null || fieldName == null) {
            exceptionLog.log(new IllegalArgumentException(this.getClass().getName()));
            return null;
        }
        Query query = colletion.orderBy(orderBy)
                               .whereEqualTo(fieldName, value)
                               .limit(limit);
        ApiFuture<QuerySnapshot> apiQuerySnapshot = query.get();
        List<DocumentSnapshot> snapshots = new ArrayList<DocumentSnapshot>();
        try {
            QuerySnapshot querySnapshot = apiQuerySnapshot.get();
            querySnapshot.getDocuments()
                         .forEach(doc -> snapshots.add(doc));
            return snapshots;
        } catch (Exception e) {
            exceptionLog.log(e);
            return null;
        }
    }
    public List<DocumentSnapshot> getDocumentsByField(Class<? extends Model> type,
                                                      String fieldName,
                                                      Object value,
                                                      DocumentSnapshot lastVisible,
                                                      String orderBy,
                                                      int limit)
    {
        CollectionReference colletion = getCollection(type);
        if (colletion == null || orderBy == null || fieldName == null || lastVisible == null) {
            exceptionLog.log(new IllegalArgumentException(this.getClass().getName()));
            return null;
        }
        Query query = colletion.orderBy(orderBy)
                               .whereEqualTo(fieldName, value)
                               .startAfter(lastVisible)
                               .limit(limit);
        ApiFuture<QuerySnapshot> apiQuerySnapshot = query.get();
        List<DocumentSnapshot> snapshots = new ArrayList<DocumentSnapshot>();
        try {
            QuerySnapshot querySnapshot = apiQuerySnapshot.get();
            querySnapshot.getDocuments()
                         .forEach(doc -> snapshots.add(doc));
            return snapshots;
        } catch (Exception e) {
            exceptionLog.log(e);
            return null;
        }
    }
    public boolean deleteDocumentById(Class<? extends Model> type, String id) {
        if(id == null) {
            exceptionLog.log(new IllegalArgumentException(this.getClass().getName()));
            return false;
        }
        CollectionReference collection = getCollection(type);
        if (collection != null) {
            DocumentReference documentReference = collection.document(id);
            ApiFuture<WriteResult> result = documentReference.delete();
            try {
                result.get();
                return true;
            } catch (Exception e) {
                exceptionLog.log(e);
                return false;
            }
        } else {
            exceptionLog.log(new IllegalArgumentException(this.getClass().getName()));
            return false;
        }
    }

    public <T extends Model> boolean updateDocumentById(T model) {
        if (model == null) {
            exceptionLog.log(new IllegalArgumentException(this.getClass().getName()));
            return false;
        }
        CollectionReference collection = getCollection(model.getClass());
        if (collection != null && model.getId() != null) {
            @SuppressWarnings("null")
            DocumentReference documentReference = collection.document(model.getId());
            ApiFuture<WriteResult> result = documentReference.set(model);
            try {
                result.get();
                return true;
            } catch (Exception e) {
                exceptionLog.log(e);
            return false;
            }
        } else {
            exceptionLog.log(new IllegalArgumentException(this.getClass().getName()));
            return false;
        }
    }

    public <T extends Model> boolean updateDocumentById(Class<? extends Model> type, String id, Map<String, Object> attributes) {
        if (id == null || attributes == null) {
            exceptionLog.log(new IllegalArgumentException(this.getClass().getName()));
            return false;
        }
        CollectionReference collection = getCollection(type);
        if (collection != null) {
            DocumentReference documentReference = collection.document(id);
            ApiFuture<WriteResult> result = documentReference.update(attributes);
            try {
                result.get();
                return true;
            } catch (Exception e) {
                exceptionLog.log(e);
                return false;
            }
        } else {
            exceptionLog.log(new IllegalArgumentException(this.getClass().getName()));
            return false;
        }
    }
}
