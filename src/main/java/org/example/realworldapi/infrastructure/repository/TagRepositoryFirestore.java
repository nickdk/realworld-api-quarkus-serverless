package org.example.realworldapi.infrastructure.repository;

import com.google.cloud.firestore.Firestore;
import lombok.SneakyThrows;
import org.example.realworldapi.domain.model.entity.Tag;
import org.example.realworldapi.domain.model.repository.TagRepository;

import javax.enterprise.context.ApplicationScoped;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class TagRepositoryFirestore implements TagRepository {

    private Firestore firestore;

    public TagRepositoryFirestore(Firestore firestore) {
        this.firestore = firestore;
    }

    @SneakyThrows
    @Override
    public Optional<Tag> findByName(String tagName) {
        return Optional.ofNullable(firestore.collection("tags").document(tagName).get().get().toObject(Tag.class));
    }

    @Override
    public Tag create(Tag tag) {
        firestore.collection("tags").document(tag.getName()).set(tag);
        return tag;
    }

    @SneakyThrows
    @Override
    public List<Tag> findAllTags() {
        return firestore.collection("tags").get().get().toObjects(Tag.class);
    }

}
