package util;

import java.util.HashMap;

public class MessageFactory {
	
	private static MessageFactory messageFactory;
	
	private HashMap<String, String> messages = new HashMap<String, String>();
	
	public MessageFactory getMessageFactory() {
		if (messageFactory == null) {
			messageFactory = new MessageFactory();
			initMessageFactory();
		}
		return messageFactory;
	}

	private void initMessageFactory() {
		// TODO Auto-generated method stub
		
	}

}
