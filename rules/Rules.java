package rules;

import observer.implementation.PublisherImplementation;
import compiler.Compiler;

public class Rules extends PublisherImplementation {

	public Rules() {
		this.addSubscriber(Compiler.getInstance());
	}
	
	/***
	 * 
	 * @param User's area text
	 * @return true if name is OK, false if it's not OK
	 */
	public boolean check(String parameter) {
		return false;
	}
	
}
