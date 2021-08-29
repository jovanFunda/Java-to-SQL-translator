package rules;

import observer.messages.QueryMessage;
import validator.Validator;

public class QueryRule extends Rules {
	
	@Override 
	public boolean check(String parameter) {
		
		String forCompiling = "";
		int pos = parameter.indexOf("new Query(");
		if(pos == -1) {
			Validator.getInstance().getErrorStack().push("Does not contain proper query creation");
			return false;
		}
		
		char x = '_';
		char y = '"';
		char z = ')';
		pos += 11;
		for(int i = pos; i < parameter.length();i++) {
			if(Character.compare(parameter.charAt(i), z) == 0){
				break;
			}
			
			if(!(Character.compare(parameter.charAt(i), y) == 0)) {
				forCompiling += parameter.charAt(i);
			}
			boolean b1 = !Character.isLetterOrDigit(parameter.charAt(i));
			boolean b2 = (Character.compare(parameter.charAt(i), x) == 0);
			boolean b3 = (Character.compare(parameter.charAt(i), y) == 0);
			if(b2 || b3) {
				
			}
			else if(b1) {
				Validator.getInstance().getErrorStack().push("Query name contains illegal characters");
				return false;
			}
		}
		
		notifySubscribers(new QueryMessage(forCompiling));
		return true;
	}
}