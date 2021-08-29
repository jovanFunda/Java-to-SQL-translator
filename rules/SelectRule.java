package rules;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import observer.messages.AggregateMessage;
import observer.messages.GroupByMessage;
import observer.messages.HavingMessage;
import observer.messages.SelectMessage;
import validator.Validator;

public class SelectRule extends Rules{

	@Override
	public boolean check(String parameter) {
		
		char x = ')';
		char y = ',';
		char z = '_';
		char w = '"';
		String select_column = "";
		int pos;
		boolean returnValue = true;
		
		Map<String, Boolean> alias = new HashMap<String, Boolean>();
		String Avg_column = "";
		String Count_column = "";
		String Min_column = "";
		String Max_column = "";
		String GroupBy_column = "";
		String Having_column = "";
		String AndHaving_column = "";
		String OrHaving_column = "";
		String currFunction = "";
		List<String> aggregateArgs = new ArrayList<String>();
		
		if(parameter.contains(".Avg(")) {
			pos = parameter.indexOf(".Avg(") + 6;
			currFunction = "avg!";
			
			if(!Character.isLetter(parameter.charAt(pos))) {
				Validator.getInstance().getErrorStack().push(".Avg() Error: Query name starts with non-letter character");
				returnValue = false;
			}
			
			for(int i = pos; i < parameter.length() ; i++) {
				if(Character.compare(parameter.charAt(i), x) == 0){
					break;
				}
				if(Character.isLetter(parameter.charAt(i)) || (Character.compare(parameter.charAt(i), z) == 0)) {
					Avg_column += parameter.charAt(i);
				}
				if(Character.compare(parameter.charAt(i), y) == 0) {
					Avg_column += '!';
				}
			}
			
			// Ukoliko postoji drugi argument u avg, onda to znaci da je on alias i stavljamo ga u mapu aliasa kao da nije iskorscen u selektu
			if(Avg_column.split("!").length == 2){
				if(Avg_column.split("!")[1] != null) 
					alias.put(Avg_column.split("!")[1], false);
			}
			
			aggregateArgs.add(Avg_column.split("!")[1]);
			notifySubscribers(new AggregateMessage(currFunction + Avg_column));
			
		} 
		
		if(parameter.contains(".Count(")) {
			pos = parameter.indexOf(".Count(") + 8;
			currFunction = "count!";
			
			if(!Character.isLetter(parameter.charAt(pos))) {
				Validator.getInstance().getErrorStack().push(".Count() Error: Query name starts with non-letter character");
				returnValue = false;
			}
			
			for(int i = pos; i < parameter.length() ; i++) {
				if(Character.compare(parameter.charAt(i), x) == 0){
					break;
				}
				if(Character.isLetter(parameter.charAt(i)) || (Character.compare(parameter.charAt(i), z) == 0)) {
					Count_column += parameter.charAt(i);
				}
				if(Character.compare(parameter.charAt(i), y) == 0) {
					Count_column += '!';
				}
			}
			
			if(Count_column.split("!").length == 2) {
				if(Count_column.split("!")[1] != null) 
					alias.put(Count_column.split("!")[1], false);
			}
			
			aggregateArgs.add(Count_column.split("!")[1]);
			notifySubscribers(new AggregateMessage(currFunction + Count_column));
		}
		
		if(parameter.contains(".Min(")) {
			pos = parameter.indexOf(".Min(") + 6;
			currFunction = "min!";
			
			if(!Character.isLetter(parameter.charAt(pos))) {
				Validator.getInstance().getErrorStack().push(".Min() Error: Query name starts with non-letter character");
				returnValue = false;
			}
			
			for(int i = pos; i < parameter.length() ; i++) {
				if(Character.compare(parameter.charAt(i), x) == 0){
					break;
				}
				if(Character.isLetter(parameter.charAt(i)) || (Character.compare(parameter.charAt(i), z) == 0)) {
					Min_column += parameter.charAt(i);
				}
				if(Character.compare(parameter.charAt(i), y) == 0) {
					Min_column += '!';
				}
			}
		
			if(Min_column.split("!").length == 2){
				if(Min_column.split("!")[1] != null) 
					alias.put(Min_column.split("!")[1], false);
			}
			
			aggregateArgs.add(Min_column.split("!")[1]);
			notifySubscribers(new AggregateMessage(currFunction + Min_column));
		} 
		
		if(parameter.contains(".Max(")) {
			pos = parameter.indexOf(".Max(") + 6;
			currFunction = "max!";
			
			if(!Character.isLetter(parameter.charAt(pos))) {
				Validator.getInstance().getErrorStack().push(".Max() Error: Query name starts with non-letter character");
				returnValue = false;
			}
			
			for(int i = pos; i < parameter.length() ; i++) {
				if(Character.compare(parameter.charAt(i), x) == 0){
					break;
				}
				if(Character.isLetter(parameter.charAt(i)) || (Character.compare(parameter.charAt(i), z) == 0)) {
					Max_column += parameter.charAt(i);
				}
				if(Character.compare(parameter.charAt(i), y) == 0) {
					Max_column += '!';
				}
			}
			
			if(Max_column.split("!").length == 2){
				if(Max_column.split("!")[1] != null) 
					alias.put(Max_column.split("!")[1], false);
			}
				
			aggregateArgs.add(Max_column.split("!")[1]);
			notifySubscribers(new AggregateMessage(currFunction + Max_column));
		} 
		
		String[] selectArgs = new String[32];
		if(parameter.contains(".Select(")) {
			pos = parameter.indexOf(".Select(");
			if(pos == -1) {
				
			}
			else {
			    pos += 9;
				for(int i = pos; i < parameter.length();i++) {
					
					if(Character.compare(parameter.charAt(i), x) == 0)
						break;
					
					if(!(Character.compare(parameter.charAt(i), w) == 0)) 
						select_column += parameter.charAt(i);
					
				}
				
				for(int i = 0; i < select_column.split(",").length; i++) {
					if(alias.get(select_column.split(",")[i]) != null && alias.get(select_column.split(",")[i]) == false) 
						alias.put(select_column.split(",")[i], true);
				}
				
				for (String key : alias.keySet()) {
					if(alias.get(key) == false) { 
						Validator.getInstance().getErrorStack().push("Found alias that is never used..");
						return false;
					}
				}
				
				selectArgs = select_column.split(",");
				
				notifySubscribers(new SelectMessage(select_column));
			}
		}
		
		String[] groupByArgs = new String[32];
		if(parameter.contains(".GroupBy(")) {
			pos = parameter.indexOf(".GroupBy(") + 10;
			
			for(int i = pos; i < parameter.length() ; i++) {
				if(Character.compare(parameter.charAt(i), x) == 0){
					break;
				}
				if(Character.isLetter(parameter.charAt(i)) || (Character.compare(parameter.charAt(i), z) == 0) || (Character.compare(parameter.charAt(i), y) == 0)) {
					GroupBy_column += parameter.charAt(i);
				}
				
			}
			
			groupByArgs = GroupBy_column.split(",");
			
			notifySubscribers(new GroupByMessage(GroupBy_column));
			
		} 
		
		// Mapa koja obelezava da li je konkretan select ubacen u group by ili je aggregated
		Map<String, Boolean> selectMap = new HashMap<String, Boolean>();
		
		if(aggregateArgs.size() > 0) {
			
			for (String selectArg : selectArgs) {
				selectMap.put(selectArg, false);
			}
			
			for (String selectArgument : selectMap.keySet()) {
				
				
				for(int i = 0; i < groupByArgs.length; i++) {
					if(selectArgument.equals(groupByArgs[i])) {
						selectMap.put(selectArgument, true);
					}
				}
				
				for(int i = 0; i < aggregateArgs.size(); i++) {
					if(selectArgument.equals(aggregateArgs.get(i))) {
						selectMap.put(selectArgument, true);
					}
				}
			}
			
			for (String selectArgument : selectMap.keySet()) {
				if(selectMap.get(selectArgument) == false) { 
					Validator.getInstance().getErrorStack().add("Everything that is in select must be in group by section or aggregated");
					return false;
				}
			}
		}
		
		
		
		if(parameter.contains(".Having(")) {
			pos = parameter.indexOf(".Having(") + 9;
			
			for(int i = pos; i < parameter.length(); i++) {
				if(Character.compare(parameter.charAt(i), x) == 0){
					break;
				}
				
				if(Character.compare(parameter.charAt(i), y) == 0) {
					Having_column += '!';
				} else if (Character.compare(parameter.charAt(i), w) == 0) {
					
				} else {
					Having_column += parameter.charAt(i);
				}
			}
			
			boolean flag = false;
			
			for(int i = 0; i < aggregateArgs.size(); i++) {
				if(Having_column.split("!")[0].equals(aggregateArgs.get(i))) 
					flag = true;
			}
			
			if(flag == false) 
				Validator.getInstance().getErrorStack().add("Having can only have functions that are aggregated");
			
			notifySubscribers(new HavingMessage("having!" + Having_column.split("!")[0] + "!" + Having_column.split("!")[1] + "!" + Having_column.split("!")[2]));
		}
		
		if(parameter.contains(".AndHaving(")) {
			pos = parameter.indexOf(".AndHaving(") + 12;
			
			if(!parameter.contains(".Having(")) {
				Validator.getInstance().getErrorStack().add(".AndHaving cannot exist without .Having");
				return false;
			}
			
			for(int i = pos; i < parameter.length() ; i++) {
				if(Character.compare(parameter.charAt(i), x) == 0){
					break;
				}
				if(Character.isLetter(parameter.charAt(i)) || (Character.compare(parameter.charAt(i), z) == 0)) {
					AndHaving_column += parameter.charAt(i);
				}
				if(Character.compare(parameter.charAt(i), y) == 0) {
					AndHaving_column += '!';
				}
			}
			
			boolean flag = false;
			
			for(int i = 0; i < aggregateArgs.size(); i++) {
				if(AndHaving_column.split("!")[0].equals(aggregateArgs.get(i))) 
					flag = true;
			}
			
			if(flag == false) 
				Validator.getInstance().getErrorStack().add("AndHaving can only have functions that are aggregated");
			
			notifySubscribers(new HavingMessage("and!" + Having_column.split("!")[0] + "!" + Having_column.split("!")[1] + "!" + Having_column.split("!")[2]));
		} 
		
		if(parameter.contains(".OrHaving(")) {
			pos = parameter.indexOf(".OrHaving(") + 11;
			
			if(!parameter.contains(".Having(")) {
				Validator.getInstance().getErrorStack().add(".OrHaving cannot exist without .Having");
				return false;
			}
			
			for(int i = pos; i < parameter.length() ; i++) {
				if(Character.compare(parameter.charAt(i), x) == 0){
					break;
				}
				if(Character.isLetter(parameter.charAt(i)) || (Character.compare(parameter.charAt(i), z) == 0)) {
					OrHaving_column += parameter.charAt(i);
				}
				if(Character.compare(parameter.charAt(i), y) == 0) {
					OrHaving_column += '!';
				}
			}
			
			boolean flag = false;
			
			for(int i = 0; i < aggregateArgs.size(); i++) {
				if(OrHaving_column.split("!")[0].equals(aggregateArgs.get(i))) 
					flag = true;
			}
			
			if(flag == false) 
				Validator.getInstance().getErrorStack().add("OrHaving can only have functions that are aggregated");
			
			notifySubscribers(new HavingMessage("or!" + Having_column.split("!")[0] + "!" + Having_column.split("!")[1] + "!" + Having_column.split("!")[2]));
		}
		return true;
	}
}