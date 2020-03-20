package util;

import java.io.FileNotFoundException;
import java.util.HashMap;

public class MessageFactory {
	
	private static MessageFactory messageFactory;
	
	private HashMap<String, String> messages = new HashMap<String, String>();
	
	public static MessageFactory getMessageFactory() {
		if (messageFactory == null) {
			messageFactory = new MessageFactory();
			initMessageFactory();
		}
		return messageFactory;
	}

	private static void initMessageFactory() {
        try {
			FileUtil.readMessages();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}

	public void printMessages() {
		System.out.println(messages);
		
	}

}
