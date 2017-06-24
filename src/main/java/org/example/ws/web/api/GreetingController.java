package org.example.ws.web.api;

import org.example.ws.model.Greeting;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by z063407 on 6/21/17.
 */

@RestController
public class GreetingController {

    private static final Logger logger = Logger.getLogger(GreetingController.class.getName());

    private static BigInteger nextId;
    private static Map<BigInteger, Greeting> greetingMap;

    private static Greeting save(Greeting greeting){
        if(greetingMap == null){
            greetingMap = new HashMap<BigInteger, Greeting>();
            nextId = BigInteger.ONE;
        }

        //if update
        if(greeting.getId() != null) {
            Greeting oldGreeting = greetingMap.get(greeting.getId());
            if(oldGreeting == null) {
                return null;
            }
            greetingMap.remove(greeting.getId());
            greetingMap.put(greeting.getId(), greeting);
            return greeting;
        }

        // only if create
        greeting.setId(nextId);
        nextId = nextId.add(BigInteger.ONE);
        greetingMap.put(greeting.getId(), greeting);
        return greeting;
    }

    private static boolean delete(BigInteger id){
        Greeting deletedGreeting = greetingMap.remove(id);
        if(deletedGreeting == null){
            return false;
        }
        return true;
    }

    static {
        Greeting g1 = new Greeting();
        g1.setText("Hello World");
        save(g1);

        Greeting g2 = new Greeting();
        g2.setText("Hello Yadnyesh");
        save(g2);
    }

    @RequestMapping(value="/api/greetings", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<Greeting>> getGreetings(){
            Collection<Greeting> greetings = greetingMap.values();
            logger.log(Level.INFO,"Loggin in : " + greetings.size());
            return new ResponseEntity<Collection<Greeting>> (greetings, HttpStatus.OK);
    }

    @RequestMapping(value="/api/greetings/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Greeting> getGreeting(@PathVariable("id") BigInteger id){
        Greeting greeting = greetingMap.get(id);
        if (greeting == null) {
            return new ResponseEntity<Greeting>(HttpStatus.NOT_FOUND);
        }
        logger.log(Level.INFO,"Returning : " + greeting.getText());
        return new ResponseEntity<Greeting> (greeting, HttpStatus.OK);
    }

    @RequestMapping(value = "/api/greetings", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Greeting> createGreeting(@RequestBody Greeting greeting){
        Greeting saveGreeting = save(greeting);
        logger.log(Level.INFO, "Adding Greeting: " + greeting.getText());
        return new ResponseEntity<Greeting>(greeting, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/api/greetings", method = RequestMethod.PUT)
    public ResponseEntity<Greeting> updateGreeting(@RequestBody Greeting greeting) {
        Greeting updatedGreeting = save(greeting);
        if(updatedGreeting == null){
            return new ResponseEntity<Greeting>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<Greeting>(updatedGreeting, HttpStatus.OK);
    }

    @RequestMapping(value = "/api/greetings/{id}", method = RequestMethod.DELETE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Greeting> deleteGreeting(@PathVariable("id") BigInteger id, @RequestBody Greeting greeting){
        boolean deleted = delete(id);
        if(!deleted) {
            return new ResponseEntity<Greeting>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<Greeting>(greeting, HttpStatus.NO_CONTENT);
    }
}
