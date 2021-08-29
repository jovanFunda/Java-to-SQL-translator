package compiler;

import java.util.ArrayList;
import java.util.List;

import database.Repository;
import observer.Subscriber;
import observer.messages.Message;

public abstract class Compiler implements Subscriber {
	
	private static Compiler instance;
	protected static List<Message> messages;
	protected String SQLCode = "";
	protected static Repository repo;
	
	public static Compiler getInstance(){
        return instance;
    }
	
	public static void setCompiler(Compiler compiler) {
		messages = new ArrayList<Message>();
		instance = compiler;
	}
	
	public static void setRepository(Repository repository) {
		repo = repository;
	}
	
	@Override
	public void update(Object notification) {
		if(notification instanceof Message) {
			messages.add((Message) notification);
		}
	}
	
	public static List<Message> getMessages() {
		return messages;
	}
	
	public abstract void compile();
}
