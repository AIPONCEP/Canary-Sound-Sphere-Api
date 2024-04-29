package com.api.canarysoundsphereapi.services;

import java.util.ArrayList;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.api.canarysoundsphereapi.model.Event;
import com.api.canarysoundsphereapi.repositories.EventRepository;

/**
 * Clase que proporciona servicios relacionados con los eventos.
 */
@Service
public class EventService {

    @Autowired // -> es la inyeccion de dependencias
    private EventRepository eventRepository;

    /**
     * Se utiliza para listar eventos.
     * 
     * @return Devuelve una lista de todos los eventos.
     */
    public ArrayList<Event> getAllEvents() {
        return eventRepository.findAll();
    }

    /**
     * Encuentra un evento por su id.
     * 
     * @param id
     * @return
     */
    public Optional<Event> findById(String id) {
        return eventRepository.findById(id);
    }

    /**
     * Se utiliza para registrar un nuevo event.
     * 
     * @param event
     */
    public void postEvent(Event event) {
        eventRepository.save(event);
    }

    /**
     * Elimina un event por su id.
     * 
     * @param id
     */
    public void deleteEvent(String id) {
        eventRepository.deleteById(id);
    }

    /**
     * Actualiza un evento existente.
     * 
     * @param id
     * @param updatedEvent
     */
    public void updateEvent(String id, Event updatedEvent) {
        Optional<Event> existingEvent = eventRepository.findById(id);
        if (existingEvent.isPresent()) { // Verifica si el evento existe
            Event eventToUpdate = existingEvent.get();
            // Verifica y actualiza los campos del evento existente con los datos del evento
            // actualizado
            if (updatedEvent.getLogo() != null) {
                eventToUpdate.setLogo(updatedEvent.getLogo());
            }
            if (updatedEvent.getImage() != null) {
                eventToUpdate.setImage(updatedEvent.getImage());
            }
            if (updatedEvent.getName() != null) {
                eventToUpdate.setName(updatedEvent.getName());
            }
            if (updatedEvent.getDate() != null) {
                eventToUpdate.setDate(updatedEvent.getDate());
            }
            if (updatedEvent.getTime() != null) {
                eventToUpdate.setTime(updatedEvent.getTime());
            }
            if (updatedEvent.getCapacity() != 0) {
                eventToUpdate.setCapacity(updatedEvent.getCapacity());
            }
            if (updatedEvent.getDescription() != null) {
                eventToUpdate.setDescription(updatedEvent.getDescription());
            }
            if (updatedEvent.getDirection() != null) {
                eventToUpdate.setDirection(updatedEvent.getDirection());
            }
            if (updatedEvent.getMarker() != null) {
                eventToUpdate.setMarker(updatedEvent.getMarker());
            }
            if (updatedEvent.getTicket_store() != null) {
                eventToUpdate.setTicket_store(updatedEvent.getTicket_store());
            }
            // Guarda el evento actualizado en la base de datos
            eventRepository.save(eventToUpdate);
        } else {
            // Manejo de error si el evento no se encuentra
            throw new RuntimeException("No se encontró ningún evento con el ID proporcionado: " + id);
        }
    }
}