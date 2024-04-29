package com.api.canarysoundsphereapi.controller;

import java.util.ArrayList;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.api.canarysoundsphereapi.model.Author;
import com.api.canarysoundsphereapi.model.UpdateResponse;
import com.api.canarysoundsphereapi.payload.response.MessageResponse;
import com.api.canarysoundsphereapi.services.AuthorService;

/**
 * Controlador para la gestión de autores
 */
@RestController
@RequestMapping("/authors")
public class AuthorControlller {

    @Autowired
    private AuthorService authorService;

    /**
     * Se utiliza para listar authors. 
     * @return Devuelve una lista de todos los authors.
     */
    @GetMapping()
    public ArrayList<Author> findAll() {
        return authorService.findAll();
    }

    /**
     * EndPoint para buscar autores por id
     * @param id
     * @return Devuelve un author si lo encuentra
     */
    @GetMapping("/{id}")
    public Optional<Author> findById(@PathVariable("id") String id) {
        return authorService.findById(id);
    }

    /**
     * Método para registrar un nuevo author. 
     * @param author
     */
    @PostMapping("")   
    public ResponseEntity<?> postAuthor(@RequestBody Author author) {
        authorService.postAuthor(author);
        return ResponseEntity.ok(author);
    }

    /**
     * EndPoint para eliminar un autor
     * @param id
     * @return Un mensaje con el id del autor que se eliminó
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAuthor(@PathVariable("id") String id) {
        authorService.deleteAuthor(id);
        return ResponseEntity.ok(new MessageResponse("The author with ID " + id + " has been deleted."));
    }

    /**
     * EndPoint que se utiliza para actualizar un autor
     * @param id
     * @param author
     * @return Devuelve un mensaje con el id del autor 
     * que se actualizo y un json con la información del objeto actualizado
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> updateAuthor(@PathVariable("id") String id, @RequestBody Author author) {
        authorService.updateAuthor(id, author);       
        UpdateResponse<Author> response = new UpdateResponse<>("The event with ID " + id + " has been updated.", author);
        return ResponseEntity.ok(response);
    }
}
