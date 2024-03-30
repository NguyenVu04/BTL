package com.project.backend.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Query;
import com.google.cloud.firestore.QuerySnapshot;
import com.project.backend.repository.FirestoreRepository;

@Service
public class BackendDetailsService {
    @Autowired
    private FirestoreRepository repository;
    public AuthenticationDetails loadUserByEmail(String email) throws UsernameNotFoundException {
        CollectionReference collection = repository.getCollection(AuthenticationDetails.class);
        if (collection != null && email != null) {
            try {
                Query query = collection.whereEqualTo("email", email).limit(1);
                QuerySnapshot snapshot = query.get()
                                              .get();
                if (!snapshot.isEmpty()) {
                    DocumentReference documentReference = snapshot.getDocuments()
                                                                  .get(0)
                                                                  .getReference();
                    DocumentSnapshot documentSnapshot = documentReference.get()
                                                                         .get();
                    if (documentSnapshot.exists()){
                        return documentSnapshot.toObject(AuthenticationDetails.class);
                    } else {
                        throw new UsernameNotFoundException(this.getClass().getName());
                    }
                } else {
                    throw new UsernameNotFoundException(this.getClass().getName());
                }
            } catch (Exception e) {
                throw new UsernameNotFoundException(this.getClass().getName());
            }
        } else {
            throw new UsernameNotFoundException(this.getClass().getName());
        }
    }

}
