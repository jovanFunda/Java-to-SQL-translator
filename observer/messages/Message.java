package observer.messages;

public abstract class Message {

	private String info;
	
	public Message(String message) {
		this.info = message;
	}
	
	public String getInfo() {
		return info;
	}
}
