package io.github.dudursn.apicrudclients.repositories;

import io.github.dudursn.apicrudclients.entities.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
}
