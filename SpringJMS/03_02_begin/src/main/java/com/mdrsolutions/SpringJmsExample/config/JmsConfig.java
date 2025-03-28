package com.mdrsolutions.SpringJmsExample.config;

import java.util.HashMap;
import java.util.Map;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MarshallingMessageConverter;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.MessageType;
import org.springframework.oxm.xstream.XStreamMarshaller;

import com.mdrsolutions.SpringJmsExample.pojos.Book;
import com.mdrsolutions.SpringJmsExample.pojos.BookOrder;
import com.mdrsolutions.SpringJmsExample.pojos.Customer;



@EnableJms
@Configuration
public class JmsConfig {

    //@Bean
    public MessageConverter jacksonJmsMessageConverter(){
        MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
        converter.setTargetType(MessageType.TEXT);
        converter.setTypeIdPropertyName("_type");
        return converter;
    }
    
    @Bean
    public MessageConverter xmlMarshallingMessageConverter() {
    	MarshallingMessageConverter converter = new MarshallingMessageConverter(xmlMarshaller(), xmlMarshaller());
    	converter.setTargetType(MessageType.TEXT);
    	
    	return converter;
    }
    
    @Bean
    public XStreamMarshaller xmlMarshaller() {
    	XStreamMarshaller marshaller = new XStreamMarshaller();
    	marshaller.setSupportedClasses(Book.class, Customer.class, BookOrder.class);
    	
    	/*Map<String, Class<?>> aliases = new HashMap<>();
    	aliases.put("book", Book.class);
    	aliases.put("customer", Customer.class);
    	aliases.put("bookOrder", BookOrder.class);
    	marshaller.setAliases(aliases);
    	*/
    	
    	marshaller.getXStream().addPermission(com.thoughtworks.xstream.security.AnyTypePermission.ANY); // Allow all classes (use cautiously)
    	 
    	 // Restrict to your package
    	 //marshaller.getXStream().allowTypesByWildcard(new String[] {"com.mdrsolutions.SpringJmsExample.pojos.**"});
    	
    	//marshaller.getXStream().allowTypes(new Class[] { BookOrder.class });

    	
    	return marshaller;
    }

    @Bean
    public ActiveMQConnectionFactory connectionFactory(){
        ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory("admin","admin","tcp://localhost:61616");
        return factory;
    }


    @Bean
    public DefaultJmsListenerContainerFactory jmsListenerContainerFactory(){
        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory());
        
        //factory.setMessageConverter(jacksonJmsMessageConverter());
        factory.setMessageConverter(xmlMarshallingMessageConverter());
        
        return factory;
    }
}
