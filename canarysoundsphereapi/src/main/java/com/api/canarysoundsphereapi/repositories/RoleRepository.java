package com.api.canarysoundsphereapi.repositories;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.api.canarysoundsphereapi.model.ERole;
import com.api.canarysoundsphereapi.model.Role;

public interface RoleRepository extends MongoRepository<Role, String> {
    Optional<Role> findByName(ERole name);
}