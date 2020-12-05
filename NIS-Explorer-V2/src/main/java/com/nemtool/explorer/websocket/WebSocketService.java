package com.nemtool.explorer.websocket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

/**
*
* @author Masker
* @date 2020.10.30
*/
@Service
public class WebSocketService {

    @Autowired
    private SimpMessagingTemplate template;

    public void emitBlock(String data) throws Exception {
	    template.convertAndSend("/ws/block", data);
    }
    
    public void emitMosaic(String data) throws Exception {
    	template.convertAndSend("/ws/mosaic", data);
    }
    
    public void emitUnconfirmedTransaction(String data) throws Exception {
    	template.convertAndSend("/ws/unconfirmed", data);
    }
    
    public void emitTransaction(String data) throws Exception {
    	template.convertAndSend("/ws/transaction", data);
    }
    
    
}
