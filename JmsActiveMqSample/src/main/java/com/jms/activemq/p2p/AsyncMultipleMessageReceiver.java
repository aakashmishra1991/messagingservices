package com.jms.activemq.p2p;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class AsyncMultipleMessageReceiver {
 
    // URL of the JMS server
    private static final String URL = ActiveMQConnection.DEFAULT_BROKER_URL;
    // default broker URL is : tcp://localhost:61616"
 
    // Name of the queue we will receive messages from
    private static final String Q_NAME = "MY_DEMO_Q1";
 
    public static void main(String[] args) throws JMSException, InterruptedException {
        // Getting JMS connection from the server
        // Getting JMS connection from the server
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(URL);
        Connection connection = connectionFactory.createConnection();
        connection.start();

        // Creating session for seding messages
        Session session = connection.createSession(false,
                Session.AUTO_ACKNOWLEDGE);

        // Getting the queue 'MY_DEMO_Q'
        Destination destination = session.createQueue(Q_NAME);

        // MessageConsumer is used for receiving (consuming) messages
        // Consumer
        for (int i = 1; i < 4; i++) {
            MessageConsumer consumer = session.createConsumer(destination);
            consumer.setMessageListener(new ConsumerMessageListener(
                    "Consumer " + i));
        }
        System.out.println("waiting for message to be consumed...");
        Thread.sleep(100000);

        session.close();
        connection.close();
    }
}