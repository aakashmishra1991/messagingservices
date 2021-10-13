package com.jms.activemq.p2p;
 
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.Session;
import javax.jms.TextMessage;
 
import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
 
public class MessageReceiver {
 
    // URL of the JMS server
    private static final String URL = ActiveMQConnection.DEFAULT_BROKER_URL;
    // default broker URL is : tcp://localhost:61616"
 
    // Name of the queue we will receive messages from
    private static final String Q_NAME = "MY_DEMO_Q1";
 
    public static void main(String[] args) throws JMSException {
        // Getting JMS connection from the server
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(URL);
        Connection connection = connectionFactory.createConnection();

        // Creating session for seding messages
        Session session = connection.createSession(false,
                Session.AUTO_ACKNOWLEDGE);
 
        // Getting the queue 'MY_DEMO_Q'
        Destination destination = session.createQueue(Q_NAME);
 
        // MessageConsumer is used for receiving (consuming) messages
        MessageConsumer consumer = session.createConsumer(destination);

        connection.start();
        // Here we receive the message.
        Message message = consumer.receive();
 
        // We will be using TestMessage in our example. MessageProducer sent us a TextMessage
        // so we must cast to it to get access to its .getText() method.
        if (message instanceof TextMessage) {
            TextMessage textMessage = (TextMessage) message;
            System.out.println("Received message = '" + textMessage.getText() + "'");
        }
        connection.stop();
        session.close();
        connection.close();
    }
}