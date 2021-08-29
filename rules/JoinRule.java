package rules;

import observer.messages.JoinMessage;
import validator.Validator;

public class JoinRule extends Rules {
	
	@Override 
	public boolean check(String parameter) {
		
		boolean returnValue = true;
		
		if(parameter.contains(".Join(")) 
		{
			if(!parameter.contains(".On(")) {
				Validator.getInstance().getErrorStack().push(".Join() Error: There is no .On() method with attributes");
				returnValue = false;
			} else {
				
				int pos = parameter.indexOf(".Join(") + 7;
					
				if(!Character.isLetter(parameter.charAt(pos))) {
					Validator.getInstance().getErrorStack().push(".Join() Error: Table name starts with non-letter character");
					returnValue = false;
				}
			}
		}
		
		if(parameter.contains(".On(")) {
			
			if(!parameter.contains(".Join(")) {
				Validator.getInstance().getErrorStack().push(".On() Error: There is no .Join() method..");
				returnValue = false;
			} else {
				
				int pos = parameter.indexOf(".On(") + 5;
					
				String[] arg = new String[3];
				String wholeString = "";
					
				for(int i = pos; i < parameter.length();i++) {
					if(parameter.charAt(i) == ')') {
						break;
					}
					wholeString += parameter.charAt(i);
				}
					
				arg = wholeString.split(",");
					
				for(int i = 0; i < 3; i++) {
					arg[i] = arg[i].replace("\"", "");
				}
				
				if(arg[0].split("\\.")[1].equals(arg[2].split("\\.")[1])) {
					
					String joinWith = arg[2].split("\\.")[0];
					String column_name = arg[0].split("\\.")[1];
					notifySubscribers(new JoinMessage(" " + joinWith + "!" + column_name));
				}
			}
		}
		
		return returnValue;
	}
}