package rules;

import observer.messages.OrderMessage;

public class OrderRule extends Rules{

	@Override
	public boolean check(String parameter) {
		
		char x = ')';
		char y = '"';
		char z = '_';
		int pos;
		String OrderBy_column = "";
		String OrderByDesc_column = "";
		
		if(parameter.contains(".OrderBy(")) {
			pos = parameter.indexOf(".OrderBy(") + 10;
			for(int i = pos; i < parameter.length();i++) {
				if(Character.compare(parameter.charAt(i), x) == 0){
					break;
				}
				if(!(Character.compare(parameter.charAt(i), y) == 0) || (Character.compare(parameter.charAt(i), z) == 0)) {
					OrderBy_column += parameter.charAt(i);
				}
			}
			notifySubscribers(new OrderMessage(OrderBy_column));
		}
		
		if(parameter.contains(".OrderByDesc(")) {
			pos = parameter.indexOf(".OrderByDesc(") + 14;
			for(int i = pos; i < parameter.length();i++) {
				if(Character.compare(parameter.charAt(i), x) == 0){
					break;
				}
				if(!(Character.compare(parameter.charAt(i), y) == 0) || (Character.compare(parameter.charAt(i), z) == 0)) {
					OrderByDesc_column += parameter.charAt(i);
				}
			}
			notifySubscribers(new OrderMessage("Desc" + OrderByDesc_column));
		}
			
		return true;
	}

}