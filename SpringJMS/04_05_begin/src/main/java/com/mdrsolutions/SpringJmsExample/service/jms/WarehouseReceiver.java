package com.mdrsolutions.SpringJmsExample.service.jms;

import com.mdrsolutions.SpringJmsExample.pojos.BookOrder;

import javax.management.RuntimeErrorException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Service;

@Service
public class WarehouseReceiver {

    private static final Logger LOGGER = LoggerFactory.getLogger(WarehouseReceiver.class);

    @Autowired
    private WarehouseProcessingService warehouseProcessingService;

    @JmsListener(destination = "book.order.queue")
    public void receive(BookOrder bookOrder){
        LOGGER.info("Message received!");
        LOGGER.info("Message is == " + bookOrder);

        if(bookOrder.getBook().getTitle().startsWith("L")) {
        	Error error = new Error("Book Title start with L");
        	throw new RuntimeErrorException(error, "OrderId=" + bookOrder.getBookOrderId() + " begins with L and these are not allowed");
        }	
        warehouseProcessingService.processOrder(bookOrder);
    }
}
