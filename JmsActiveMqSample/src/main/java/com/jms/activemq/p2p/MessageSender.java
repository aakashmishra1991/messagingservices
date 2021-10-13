package com.jms.activemq.p2p;

import javax.jms.*;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
 
public class MessageSender {
     
    //URL of the JMS server. DEFAULT_BROKER_URL will just mean that JMS server is on localhost
    private static String URL = ActiveMQConnection.DEFAULT_BROKER_URL;
     
    // default broker URL is : tcp://localhost:61616"
    private static String Q_NAME = "MY_DEMO_Q1"; // Queue Name.You can create any/many queue names as per your requirement.
     
    public static void main(String[] args) throws JMSException {        
        // Getting JMS connection from the server and starting it
        QueueConnectionFactory connectionFactory = new ActiveMQConnectionFactory(URL);
        Connection connection = connectionFactory.createConnection();
        connection.start();
         
        //Creating a non transactional session to send/receive JMS message.
        Session session = connection.createSession(false,
                Session.AUTO_ACKNOWLEDGE);  
         
        //Destination represents here our queue 'MY_DEMO_Q1' on the JMS server.
        //The queue will be created automatically on the server.
        Destination destination = session.createQueue(Q_NAME);
         
        // MessageProducer is used for sending messages to the queue.
        MessageProducer producer = session.createProducer(destination);
         
        // We will send a small text message saying 'Hello World!!!' 
        TextMessage message = session
                .createTextMessage("Hello !!! Welcome to the world of ActiveMQ.This is my test");
         
        // Here we are sending our message!
        producer.send(message);
        for (int i = 1; i < 20; i++) {
            producer.send(session.createTextMessage("Hello"+i));
        }
        System.out.println("Message Sent on Q = '" + message.getText() + "'");
        session.close();
        connection.close();
    }
}