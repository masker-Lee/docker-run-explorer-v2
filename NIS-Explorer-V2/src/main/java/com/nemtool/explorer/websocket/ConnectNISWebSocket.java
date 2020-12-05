package com.nemtool.explorer.websocket;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.converter.StringMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;
import org.springframework.web.socket.sockjs.client.SockJsClient;
import org.springframework.web.socket.sockjs.client.Transport;
import org.springframework.web.socket.sockjs.client.WebSocketTransport;

import com.nemtool.explorer.config.Config;

/**
* @Description connetct to NIS webSocket 
* @author Masker
* @date 2020.06.30
*/
@Service
public class ConnectNISWebSocket {
	
	private final static Logger logger = LoggerFactory.getLogger(ConnectNISWebSocket.class);
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
	@Autowired
	private Config config;
	@Autowired
	private MyStompSessionHandler handler;
	
	public void connect() {
		//webSocket url
		String WS_URI = "http://" + config.getNisHost() + ":" + config.getWsPort() + config.getWsPath();
		// create WebSocket client
		List<Transport> transports = new ArrayList<Transport>(1);
		transports.add(new WebSocketTransport(new StandardWebSocketClient()));
		WebSocketClient transport = new SockJsClient(transports);
		WebSocketStompClient stompClient = new WebSocketStompClient(transport);
		stompClient.setMessageConverter(new StringMessageConverter());
		stompClient.connect(WS_URI, handler);
		logger.info("WebSocket connected!  " + sdf.format(new Date()));
		
		//keep this stompClient alive
		try {
			Thread.sleep(1000 * 60 * 60 * 24 * 365 * 10);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			logger.error("error" + e.toString());
		}
		
	}

}
