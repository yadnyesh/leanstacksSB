package org.example.ws.repository;

import org.example.ws.model.Greeting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by z063407 on 6/24/17.
 */
@Repository
public interface GreetingRepository extends JpaRepository<Greeting, Long> {

}
