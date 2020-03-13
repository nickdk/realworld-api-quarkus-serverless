package org.example.realworldapi.infrastructure.config;

import com.github.slugify.Slugify;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.FirestoreOptions;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Singleton;

@ApplicationScoped
public class ApplicationConfig {

    @Singleton
    @Produces
    public Slugify slugify() {
        return new Slugify();
    }

    @Singleton
    @Produces
    public Firestore firestore() {
        FirestoreOptions firestoreOptions =
                FirestoreOptions.getDefaultInstance().toBuilder()
                        .build();
        return firestoreOptions.getService();
    }
}
