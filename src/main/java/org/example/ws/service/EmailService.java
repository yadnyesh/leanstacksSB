package org.example.ws.service;

import org.example.ws.model.Greeting;

import java.util.concurrent.Future;

/**
 * Created by z063407 on 6/25/17.
 */
public interface EmailService {
    void send(Greeting greeting);

    void sendAsync (Greeting greeting);

    Future<Boolean> sendAsyncWithResult(Greeting greeting);
}
