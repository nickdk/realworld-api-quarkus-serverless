package org.example.realworldapi;

import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.FirestoreOptions;

import javax.sql.DataSource;
import java.util.Set;

public class DatabaseIntegrationTest {

    public static Firestore firestore;
    private static DataSource dataSource;
    private static Set<String> entities;

    static {
        FirestoreOptions firestoreOptions =
                FirestoreOptions.getDefaultInstance().toBuilder()
                        .build();
        firestore = firestoreOptions.getService();
    }

    public void clear() {
//    transaction(
//        () ->
//            entities.forEach(
//                tableName ->
//                    entityManager
//                        .createNativeQuery(
//                            "SET FOREIGN_KEY_CHECKS = 0; DELETE FROM "
//                                + tableName
//                                + "; SET FOREIGN_KEY_CHECKS = 1;")
//                        .executeUpdate()));
    }
}
