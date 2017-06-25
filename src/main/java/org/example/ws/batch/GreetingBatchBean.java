package org.example.ws.batch;

import org.example.ws.model.Greeting;
import org.example.ws.service.GreetingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Collection;


/**
 * Created by z063407 on 6/25/17.
 */
@Component
public class GreetingBatchBean {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private GreetingService greetingService;

    @Scheduled(cron = "0,30 * * * * *")
    public void cronJob() {
        logger.info(">> cronjob >>");

        Collection<Greeting> greetings = greetingService.findAll();
        logger.info("There are {} greetings in the data store.", greetings.size());

        logger.info("<< cronjob <<");
    }

    @Scheduled (initialDelay = 5000, fixedRate = 15000)
    public void fixedRateJobWithInitialDelay() {
        logger.info(">>> fixedRateJobWithInitialDelay >>>");

        long pause = 5000;
        long start = System.currentTimeMillis();

        do{
            if(start + pause < System.currentTimeMillis()){
                break;
            }
        } while(true);

        logger.info("Processing time was {} seconds.", pause/1000);
        logger.info("<<< fixedRateJobWithInitialDelay <<<");
    }

    @Scheduled (initialDelay = 5000, fixedDelay = 15000)
    public void fixedDelayJobWithInitialDelay() {
        logger.info(">>> fixedDelayJobWithInitialDelay >>>");

        long pause = 5000;
        long start = System.currentTimeMillis();

        do{
            if(start + pause < System.currentTimeMillis()){
                break;
            }
        } while(true);

        logger.info("Processing time was {} seconds.", pause/1000);
        logger.info("<<< fixedDelayJobWithInitialDelay <<<");
    }
}
