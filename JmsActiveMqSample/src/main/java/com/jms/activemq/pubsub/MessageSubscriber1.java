package com.jms.activemq.pubsub;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class MessageSubscriber1 {
 
    // URL of the JMS server
    private static final String URL = ActiveMQConnection.DEFAULT_BROKER_URL;
    // default broker URL is : tcp://localhost:61616"
 
    // Name of the queue we will receive messages from
    private static final String TOPIC_NAME = "MY_DEMO_TOPIC1";
 
    public static void main(String[] args) throws JMSException {
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
 
        // Here we receive the message.
        Message message = consumer.receive();
 
        // We will be using TestMessage in our example. MessageProducer sent us a TextMessage
        // so we must cast to it to get access to its .getText() method.
        if (message instanceof TextMessage) {
            TextMessage textMessage = (TextMessage) message;
            System.out.println("Received message = '" + textMessage.getText() + "'");
        }
        connection.close();
    }
}