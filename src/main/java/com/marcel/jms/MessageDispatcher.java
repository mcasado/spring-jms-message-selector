package com.marcel.jms;

import java.util.concurrent.CountDownLatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class MessageDispatcher {

  private static final Logger LOGGER =
      LoggerFactory.getLogger(MessageDispatcher.class);

  private CountDownLatch latch = new CountDownLatch(2);

  public CountDownLatch getLatch() {
    return latch;
  }

  @JmsListener(destination = "${queue.boot}",
      selector = "destination = 'service_one'")
  public void receiveServiceOne(String message) {
    LOGGER.info("received message and sending to service_one message='{}'", message);
    latch.countDown();
  }

  @JmsListener(destination = "${queue.boot}",
      selector = "destination = 'service_two'")
  public void receiveServiceTwo(String message) {
    LOGGER.info("received message and sending to service_two message='{}'", message);
  }
}
