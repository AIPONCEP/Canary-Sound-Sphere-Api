package com.api.canarysoundsphereapi.services;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.api.canarysoundsphereapi.model.Author;
import com.api.canarysoundsphereapi.repositories.AuthorRepository;

/**
 * Clase que proporciona servicios relacionados con los authors.
 */
@Service
public class AuthorService {

    @Autowired // -> es la inyeccion de dependencias
    private AuthorRepository authorRepository;

    /**
     * Se utiliza para listar authors.
     * 
     * @return Devuelve una lista de todos los authors.
     */
    public ArrayList<Author> findAll() {
        return authorRepository.findAll();
    }

    public Optional<Author> findById(String id) {
        return authorRepository.findById(id);
    }

    /**
     * Se utiliza para registrar un nuevo author
     * 
     * @param author
     */
    public void postAuthor(Author author) {
        authorRepository.save(author);
    }

    /**
     * Elimina un author por su id
     * 
     * @param id
     */
    public void deleteAuthor(String id) {
        authorRepository.deleteById(id);
    }

    /**
     * Actualiza un author existente.
     * 
     * @param id
     * @param updatedAuthor
     */
    public void updateAuthor(String id, Author updatedAuthor) {
        Optional<Author> existingAuthor = authorRepository.findById(id);
        if (existingAuthor.isPresent()) { // Verifica si el author existe
            Author authorToUpdate = existingAuthor.get();
            // Verifica y actualiza los campos del author existente con los datos del author
            // actualizado
            if (updatedAuthor.getName() != null) {
                authorToUpdate.setName(updatedAuthor.getName());
            }
            if (updatedAuthor.getImage() != null) {
                authorToUpdate.setImage(updatedAuthor.getImage());
            }
            if (updatedAuthor.getFoundation_year() != 0) {
                authorToUpdate.setFoundation_year(updatedAuthor.getFoundation_year());
            }
            if (updatedAuthor.getMusic_type() != null) {
                authorToUpdate.setMusic_type(updatedAuthor.getMusic_type());
            }
            if (updatedAuthor.getDescription() != null) {
                authorToUpdate.setDescription(updatedAuthor.getDescription());
            }
            if (updatedAuthor.getMusic_list() != null) {
                authorToUpdate.setMusic_list(updatedAuthor.getMusic_list());
            }
            // Guarda el author actualizado en la base de datos
            authorRepository.save(authorToUpdate);
        } else {
            // Manejo de error si el author no se encuentra
            throw new RuntimeException("No se encontró ningún author con el ID proporcionado: " + id);
        }
    }

}
