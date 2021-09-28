package utn.gallino.msstock.Service.Impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import utn.gallino.msstock.Service.ProvisionService;

import java.time.Instant;

@Component
public class TareasProgramadasImpl {

    private static final Logger logger = LoggerFactory.getLogger(TareasProgramadasImpl.class);

    @Autowired
    ProvisionService provisionService;

    @Scheduled(fixedDelay = 20000)
    public void recibirProvision() {
        logger.warn("probando tareas programadas"+ Instant.now());


        //fijarse se existe
    }

}
