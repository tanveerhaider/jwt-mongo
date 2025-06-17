package com.simplejava.startup;

import com.simplejava.security.model.EmployeeRole;
import com.simplejava.security.model.Role;
import com.simplejava.security.repository.RoleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Set;

/**  JwtMongoApplicationStartup is a CommandLineRunner that initializes the application
 * by inserting default roles into the MongoDB database if they do not already exist.
 * It logs the process of inserting roles and checks for existing roles to avoid duplicates.
 */
@Component
public class JwtMongoApplicationStartup implements CommandLineRunner {
    private static final Logger LOG = LoggerFactory.getLogger(JwtMongoApplicationStartup.class); // Logger for logging errors

    private final RoleRepository roleRepository;

    public JwtMongoApplicationStartup(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        LOG.info("Application starting");
        Set<Role> roles = Set.of(
                new Role(EmployeeRole.ROLE_ADMIN),
                new Role(EmployeeRole.ROLE_USER),
                new Role(EmployeeRole.ROLE_MODERATOR)
        );

        roles.forEach(this::insertRoleIfNotExists);
        LOG.info("All roles inserted successfully");
    }
    private void insertRoleIfNotExists(Role role) {
        roleRepository.findByName(role.getName())
                .ifPresentOrElse(
                        existingRole -> LOG.info("Role {} already exists", existingRole.getName()),
                        () -> {
                            roleRepository.save(role);
                            LOG.info("Inserted role: {}", role.getName());
                        }
                );
    }

}
