package com.jms.activemq.pubsub;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class AsyncMessageSubscriber {
    // URL of the JMS server
    private static final String URL = ActiveMQConnection.DEFAULT_BROKER_URL;
    // default broker URL is : tcp://localhost:61616"

    // Name of the queue we will receive messages from
    private static final String TOPIC_NAME = "MY_DEMO_TOPIC1";

    public static void main(String[] args) throws JMSException, InterruptedException {
        // Getting JMS connection from the server
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(URL);
        Connection connection = connectionFactory.createConnection();
        connection.start();

        // Creating session for sending messages
        Session session = connection.createSession(false,
                Session.AUTO_ACKNOWLEDGE);

        // Getting the queue 'MY_DEMO_TOPIC1'
        Destination destination = session.createTopic(TOPIC_NAME);

        // MessageConsumer is used for receiving (consuming) messages
        MessageConsumer consumer = session.createConsumer(destination);
        consumer.setMessageListener(new ConsumerMessageListener("Consumer"));

        System.out.println("waiting for message to be consumed...");
        Thread.sleep(100000);

        session.close();
        connection.close();
    }
}
