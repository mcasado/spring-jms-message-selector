package com.marcel.jms;

import static org.assertj.core.api.Assertions.assertThat;
import java.util.concurrent.TimeUnit;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@DirtiesContext
public class SpringJmsApplicationTest {

  @Autowired
  private Producer sender;

  @Autowired
  private MessageDispatcher receiver;

  @Test
  public void testReceive() throws Exception {
    sender.send("selector.q", "This is message 1 for service one!", "service_one");
    sender.send("selector.q", "This is message 1 for service two!", "service_two");
    sender.send("selector.q", "This is message 2 for service one !", "service_one");

    receiver.getLatch().await(10000, TimeUnit.MILLISECONDS);
    assertThat(receiver.getLatch().getCount()).isEqualTo(0);
  }
}
