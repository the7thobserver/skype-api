package com.skylark.the7thobserver.skype_api.main;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.skylark.the7thobserver.skype_api.objects.AuthResponse;

import java.io.IOException;

/**
 * Created by Jared on 9/2/16.
 */
@SpringBootApplication
public class MainApp
{
    private static Logger log = LoggerFactory.getLogger(MainApp.class);

    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);

        SkypeApi api = (SkypeApi) context.getBean("getSkypeApi");
        AuthResponse authResponse = null;

        try
        {
            authResponse = api.auth("", "");

            log.info(authResponse.getAccessToken());
            log.info(authResponse.getTokenType());
            
            api.sendMessage("", 
            		"Hi! (wave)", "message/text");
        } catch (IOException e)
        {
            e.printStackTrace();
        }

        log.info("Program end");
    }
}