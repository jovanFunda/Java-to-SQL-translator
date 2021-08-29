package rules;

import observer.messages.WhereMessage;
import validator.Validator;

public class WhereRule extends Rules {

	@Override
	public boolean check(String parameter) {
		
		boolean returnValue = true;
		int pos;
		
		if(parameter.contains(".Where(")) {
			pos = parameter.indexOf(".Where(") + 8;
			
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
			
			notifySubscribers(new WhereMessage("where!" + arg[0] + "!" + arg[1] + "!" + arg[2]));
		}
		
		if(parameter.contains(".OrWhere(")) {

			if(!parameter.contains(".Where(")) {
				Validator.getInstance().getErrorStack().push(".OrWhere() error: There is no .Where() in your command");
				returnValue = false;
			} else {
			
				pos = parameter.indexOf(".OrWhere(") + 10;
				
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
			
				notifySubscribers(new WhereMessage("or" + "!" + arg[0] + "!" + arg[1] + "!" + arg[2]));
			}
		} 
		
		if(parameter.contains(".AndWhere(")) {
			
			if(!parameter.contains(".Where(")) {
				Validator.getInstance().getErrorStack().push(".AndWhere() error: There is no .Where() in your command");
				returnValue = false;
			} else {
			
				pos = parameter.indexOf(".AndWhere(") + 11;
				
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
				
				notifySubscribers(new WhereMessage("and" + "!" + arg[0] + "!" + arg[1] + "!" + arg[2]));
			}
		} 
		
		if(parameter.contains(".WhereBetween(")) {
			
			pos = parameter.indexOf(".WhereBetween(") + 15;
			
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
			
			notifySubscribers(new WhereMessage("between" + "!" + arg[0] + "!" + arg[1] + "!" + arg[2]));
		} 
		
		if(parameter.contains(".WhereIn(")) {
			pos = parameter.indexOf(".WhereIn(") + 10;
			
			if(!parameter.contains(".ParametarList(")) {
				Validator.getInstance().getErrorStack().push(".WhereIn() error: There is no .ParametarList() in your command");
				returnValue = false;
			} /*else {
			
				pos = parameter.indexOf(".WhereIn(") + 11;
				
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
				
				forCompiler += " AND " + arg[0] + " " + arg[1] + " " + arg[2];
			}*/
			
			
		} 
		
		if(parameter.contains(".WhereEndsWith(")) {
			pos = parameter.indexOf(".WhereEndsWith(") + 16;
			
			String[] arg = new String[2];
			String wholeString = "";
			
			for(int i = pos; i < parameter.length();i++) {
				if(parameter.charAt(i) == ')') {
					break;
				}
				wholeString += parameter.charAt(i);
			}
			
			arg = wholeString.split(",");
			
			for(int i = 0; i < 2; i++) {
				arg[i] = arg[i].replace("\"", "");
			}
			
			notifySubscribers(new WhereMessage("endsWith" + "!" + arg[0] + "!" + arg[1]));
		} 
		
		if(parameter.contains(".WhereStartsWith(")) {
			pos = parameter.indexOf(".WhereStartsWith(") + 18;
			
			String[] arg = new String[2];
			String wholeString = "";
			
			for(int i = pos; i < parameter.length();i++) {
				if(parameter.charAt(i) == ')') {
					break;
				}
				wholeString += parameter.charAt(i);
			}
			
			arg = wholeString.split(",");
			
			for(int i = 0; i < 2; i++) {
				arg[i] = arg[i].replace("\"", "");
			}
			
			
			notifySubscribers(new WhereMessage("startsWith" + "!" + arg[0] + "!" + arg[1]));
		} 
		
		if(parameter.contains(".WhereContains(")) {
			pos = parameter.indexOf(".WhereContains(") + 16;
			
			String[] arg = new String[2];
			String wholeString = "";
			
			for(int i = pos; i < parameter.length();i++) {
				if(parameter.charAt(i) == ')') {
					break;
				}
				wholeString += parameter.charAt(i);
			}
			
			arg = wholeString.split(",");
			
			for(int i = 0; i < 2; i++) {
				arg[i] = arg[i].replace("\"", "");
			}
			
			notifySubscribers(new WhereMessage("contains" + "!" + arg[0] + "!" + arg[1]));
		} 
		
		return returnValue;
	}	
}
