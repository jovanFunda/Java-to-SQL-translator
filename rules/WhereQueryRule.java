package rules;

import validator.Validator;

public class WhereQueryRule extends Rules {
	
	@Override
	public boolean check(String parameter) {
		
		boolean returnValue = true;
		int pos;
		char x = ')';
		char y = ',';
		char z = '_';
		String WhereInQ_column = "";
		String WhereEqQ_column = "";
		
		if(parameter.contains(".WhereInQ(")) {
			pos = parameter.indexOf(".WhereInQ(") + 11;
			
			if(!Character.isLetter(parameter.charAt(pos))) {
				Validator.getInstance().getErrorStack().push(".WhereInQ() Error: Query name starts with non-letter character");
				returnValue = false;
			}
			
			for(int i = pos; i < parameter.length() ; i++) {
				if(Character.compare(parameter.charAt(i), x) == 0){
					break;
				}
				if(Character.isLetter(parameter.charAt(i)) || (Character.compare(parameter.charAt(i), z) == 0)) {
					WhereInQ_column += parameter.charAt(i);
				}
				if(Character.compare(parameter.charAt(i), y) == 0) {
					WhereInQ_column += '!';
				}
			}
		} 
		
		if(parameter.contains(".WhereEqQ(")) {
			pos = parameter.indexOf(".WhereEqQ(") + 11;
			
			if(!Character.isLetter(parameter.charAt(pos))) {
				Validator.getInstance().getErrorStack().push(".WhereEqQ() Error: Query name starts with non-letter character");
				returnValue = false;
			}
			
			for(int i = pos; i < parameter.length() ; i++) {
				if(Character.compare(parameter.charAt(i), x) == 0){
					break;
				}
				if(Character.isLetter(parameter.charAt(i)) || (Character.compare(parameter.charAt(i), z) == 0)) {
					WhereEqQ_column += parameter.charAt(i);
				}
				if(Character.compare(parameter.charAt(i), y) == 0) {
					WhereEqQ_column += '!';
				}
			}
		} 
		
		return returnValue;
	}

}