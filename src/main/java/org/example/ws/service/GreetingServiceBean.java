package org.example.ws.service;

import org.example.ws.model.Greeting;
import org.example.ws.repository.GreetingRepository;
import org.hibernate.annotations.Cache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


import java.util.Collection;


/**
 * Created by z063407 on 6/24/17.
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly=true)
public class GreetingServiceBean implements GreetingService {

    @Autowired
    private GreetingRepository greetingRepository;

    public Collection<Greeting> findAll() {
        Collection<Greeting> greetings = greetingRepository.findAll();
        return greetings;
    }

    @Cacheable (value = "greetings", key = "#id")
    public Greeting findOne(Long id) {
        Greeting greeting = greetingRepository.findOne(id);
        return greeting;
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    @CachePut(value = "greetings", key = "#result.id")
    public Greeting create(Greeting greeting) {

        //Assert.isNull(greeting, "Cannot create greeting that has existing ID");
        if (greeting.getId() != null) {
            return null;
        }

        Greeting savedGreeting = greetingRepository.save(greeting);

        if(savedGreeting.getId() == 4L) {
            throw new RuntimeException("Roll me back!");
        }
        return savedGreeting;
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    @CachePut(value = "greetings", key = "#greeting.id")
    public Greeting update(Greeting greeting) {
        Greeting greetingExist = greetingRepository.findOne(greeting.getId());
        if (greetingExist == null) {
            return null;
        }

        Greeting updatedGreeting = greetingRepository.save(greeting);
        return updatedGreeting;
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    @CacheEvict(value = "greetings", key ="#id")
    public void delete(Long id) {
        greetingRepository.delete(id);
    }

    @CacheEvict(value = "greetings", allEntries = true)
    public void evictCache() {

    }
}
