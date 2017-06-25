package org.example.ws.service;

import org.example.ws.model.Greeting;

import java.util.Collection;

/**
 * Created by z063407 on 6/24/17.
 */
public interface GreetingService {

    Collection<Greeting> findAll();

    Greeting findOne(Long id);

    Greeting create(Greeting greeting);

    Greeting update(Greeting greeting);

    void delete(Long id);

    void evictCache();

}
