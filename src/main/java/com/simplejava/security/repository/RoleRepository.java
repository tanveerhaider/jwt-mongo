package com.simplejava.security.repository;

import com.simplejava.security.model.EmployeeRole;
import com.simplejava.security.model.Role;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

/**
 * Description :
 * User: Tanveer Haider
 * Date: 6/9/2025
 * Time: 11:44 PM
 */
/**
 * RoleRepository interface for managing Role entities in MongoDB.
 * It extends MongoRepository to provide CRUD operations.
 */
public interface RoleRepository extends MongoRepository<Role, String> {
    /**
     * Finds a role by its name.
     *
     * @param name the name of the role
     * @return an Optional containing the Role if found, or empty if not found
     */
    Optional<Role> findByName(EmployeeRole name);


}
