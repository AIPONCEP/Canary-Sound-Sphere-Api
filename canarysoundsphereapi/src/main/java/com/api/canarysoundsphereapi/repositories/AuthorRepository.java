package com.api.canarysoundsphereapi.repositories;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.api.canarysoundsphereapi.model.Author;

public interface AuthorRepository extends MongoRepository<Author, String> {

    public abstract ArrayList<Author> findAll();

    public abstract Optional<Author> findById(String id);

    @SuppressWarnings("unchecked")
    public abstract Author save(Author author);

    public abstract void deleteById(String id);
}
