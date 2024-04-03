package com.project.backend.security;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.Query;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import com.project.backend.exceptionhandler.ExceptionLog;
import com.project.backend.repository.FirestoreRepository;

@Service
public class BackendDetailsService {
    @Autowired
    private FirestoreRepository repository;
    @Autowired
    private ExceptionLog exceptionLog;
    public AuthenticationDetails loadUserByEmail(String email) throws UsernameNotFoundException {
        CollectionReference collection = repository.getCollection(AuthenticationDetails.class);
        if (collection == null || email == null) {
            exceptionLog.log(new UsernameNotFoundException(this.getClass().getName()));
            return null;
        }
        try {
            Query query = collection.whereEqualTo("email", email)
                                    .limit(1);
            QuerySnapshot querySnapshot = query.get()
                                               .get();
            List<QueryDocumentSnapshot> documentSnapshots = querySnapshot.getDocuments();
            if (!documentSnapshots.isEmpty()) {
                return documentSnapshots.get(0)
                                        .toObject(AuthenticationDetails.class);
            } else {
                exceptionLog.log(new UsernameNotFoundException(this.getClass().getName()));
                return null;
            }
        } catch (Exception e) {
            exceptionLog.log(e);
            return null;
        }
    }

}
