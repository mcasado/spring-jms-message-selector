package com.marcel.jms;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

@Component
public class Producer {

  private static final Logger LOGGER =
      LoggerFactory.getLogger(Producer.class);

  @Autowired
  private JmsTemplate jmsTemplate;

  public void send(String destination, String message,
      String service) {
    LOGGER.info("sending message='{}' for '{}'",
        message, "service_one");
     switch (service) {
         case "service_one":
             jmsTemplate.convertAndSend(destination, message,
                     messagePostProcessor -> {
                         messagePostProcessor.setStringProperty("destination",
                                 "service_one");
                         return messagePostProcessor;
                     });
             break;
         case "service_two":
             jmsTemplate.convertAndSend(destination, message,
                     messagePostProcessor -> {
                         messagePostProcessor.setStringProperty("destination",
                                 "service_two");
                         return messagePostProcessor;
                     });
             break;
     }
  }
}
