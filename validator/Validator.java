package validator;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

import observer.implementation.PublisherImplementation;
import rules.JoinRule;
import rules.OrderRule;
import rules.QueryRule;
import rules.Rules;
import rules.SelectRule;
import rules.WhereQueryRule;
import rules.WhereRule;

public class Validator extends PublisherImplementation {
	
	static Map<Rules, Boolean> rules;

	private static Validator instance;
	private static Stack<String> errorStack;
	
	 public static Validator getInstance(){
	     if (instance==null) {
	         instance=new Validator();
	         errorStack = new Stack<String>();
	     }
	     return instance;
	 }
	 
	 /*************
	 Returns true if syntax has no errors
	 Returns false if syntax has errors
	 @param text User's Java to SQL code
	 **************/
	public boolean checkSyntax(String text) {
		
		boolean syntaxOK = true;
		
		rules = new HashMap<Rules, Boolean>();
		rules.put(new QueryRule(), true); 
		rules.put(new JoinRule(), true);
		rules.put(new WhereRule(), true); 
		rules.put(new SelectRule(), true); 
		rules.put(new OrderRule(), true);
		rules.put(new WhereQueryRule(), true);
		
		for (Rules rule : rules.keySet()) {
			if(rule.check(text) == false) {
				rules.put(rule, false);
				syntaxOK = false;
			}
		}
		
		return syntaxOK;
	}
	
	public Stack<String> getErrorStack() {
		return errorStack;
	}
}