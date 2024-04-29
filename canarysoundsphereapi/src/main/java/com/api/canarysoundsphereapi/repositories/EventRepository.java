package com.api.canarysoundsphereapi.repositories;

import java.util.ArrayList;
import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;
import com.api.canarysoundsphereapi.model.Event;

public interface EventRepository extends MongoRepository<Event, String> {
    /**
     * Se utiliza para listar eventos.
     * 
     * @return Devuelve una lista de todos los eventos.
     */
    public abstract ArrayList<Event> findAll();

    public abstract Optional<Event> findById(String id);

    @SuppressWarnings("unchecked")
    public abstract Event save(Event event);

    public abstract void deleteById(String id);
}
