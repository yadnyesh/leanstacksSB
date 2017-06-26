package org.example.ws.service;

import org.example.ws.model.Greeting;
import org.springframework.stereotype.Service;

import java.util.concurrent.Future;

/**
 * Created by z063407 on 6/25/17.
 */
//@Service
public class EmailServiceBean implements EmailService {

    public void send(Greeting greeting) {

    }

    public void sendAsync(Greeting greeting) {

    }

    public Future<Boolean> sendAsyncWithResult(Greeting greeting) {
        return null;
    }
}
