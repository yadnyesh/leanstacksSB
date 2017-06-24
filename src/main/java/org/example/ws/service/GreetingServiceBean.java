package org.example.ws.service;

import org.example.ws.model.Greeting;
import org.example.ws.repository.GreetingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.math.BigInteger;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by z063407 on 6/24/17.
 */
@Service
public class GreetingServiceBean implements GreetingService {

//    private static Long nextId;
//    private static Map<Long, Greeting> greetingMap;
//
//    private static Greeting save(Greeting greeting) {
//        if (greetingMap == null) {
//            greetingMap = new HashMap<Long, Greeting>();
//            nextId = new Long(1);
//        }
//
//        //if update
//        if (greeting.getId() != null) {
//            Greeting oldGreeting = greetingMap.get(greeting.getId());
//            if (oldGreeting == null) {
//                return null;
//            }
//            greetingMap.remove(greeting.getId());
//            greetingMap.put(greeting.getId(), greeting);
//            return greeting;
//        }
//
//        // only if create
//        greeting.setId(nextId);
//        nextId += 1;
//        greetingMap.put(greeting.getId(), greeting);
//        return greeting;
//    }
//
//    private static boolean remove(Long id) {
//        Greeting deletedGreeting = greetingMap.remove(id);
//        if (deletedGreeting == null) {
//            return false;
//        }
//        return true;
//    }
//
//    static {
//        Greeting g1 = new Greeting();
//        g1.setText("Hello World");
//        save(g1);
//
//        Greeting g2 = new Greeting();
//        g2.setText("Hello Yadnyesh");
//        save(g2);
//    }

    @Autowired
    private GreetingRepository greetingRepository;

    public Collection<Greeting> findAll() {
        Collection<Greeting> greetings = greetingRepository.findAll();
        return greetings;
    }

    public Greeting findOne(Long id) {
        Greeting greeting = greetingRepository.findOne(id);
        return greeting;
    }

    public Greeting create(Greeting greeting) {

        //Assert.isNull(greeting, "Cannot create greeting that has existing ID");
        if (greeting.getId() != null) {
            return null;
        }

        Greeting savedGreeting = greetingRepository.save(greeting);
        return savedGreeting;
    }

    public Greeting update(Greeting greeting) {
        Greeting greetingExist = greetingRepository.findOne(greeting.getId());
        if (greetingExist == null) {
            return null;
        }

        Greeting updatedGreeting = greetingRepository.save(greeting);
        return updatedGreeting;
    }

    public void delete(Long id) {
        greetingRepository.delete(id);
    }
}
