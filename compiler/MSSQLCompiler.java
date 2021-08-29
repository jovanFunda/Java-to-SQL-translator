package compiler;

import java.util.HashMap;
import java.util.Map;

import app.AppCore;
import observer.messages.AggregateMessage;
import observer.messages.GroupByMessage;
import observer.messages.HavingMessage;
import observer.messages.JoinMessage;
import observer.messages.Message;
import observer.messages.OrderMessage;
import observer.messages.QueryMessage;
import observer.messages.SelectMessage;
import observer.messages.WhereMessage;

public class MSSQLCompiler extends Compiler {

	@Override
	public void compile() {
		
		SQLCode += "SELECT ";
		boolean flag = false;
		Map<String, String> aliases = new HashMap<String, String>();
		
		for(Message message : messages) {
			if(message instanceof AggregateMessage) {
				aliases.put(message.getInfo().split("!")[2], " " +message.getInfo().split("!")[0] + "(" + message.getInfo().split("!")[1] + ") ");
			}
		}
		
		for (Message message : messages) {
			
			boolean f = false;
			boolean firstMessage = false;
			if(message instanceof SelectMessage) {
				
				Map<String, Boolean> column_done = new HashMap<String, Boolean>();
				String[] select_columns = message.getInfo().split(",");
				
				for(int i = 0; i < select_columns.length; i++) {
					column_done.put(select_columns[i], false);
				}
				
				for(Message mes : messages) {
					
					if(mes instanceof AggregateMessage) {
						if(mes.getInfo().split("!").length == 3) {
							for(int i = 0; i < select_columns.length; i++) {
								if(select_columns[i].equals((mes.getInfo().split("!")[2]))) {
									SQLCode += " " + mes.getInfo().split("!")[0] + "(" + mes.getInfo().split("!")[1] + ") as " + mes.getInfo().split("!")[2];
									column_done.put(select_columns[i], true);
									flag = true;
									f = true;
									firstMessage = true;
								}
							}
						}
					}
				}
				
				for (String kljuc : column_done.keySet()) {
					if(column_done.get(kljuc) == false) {
						if(firstMessage == true) {
							SQLCode += "," + kljuc;	
						} else {
							SQLCode += " " + kljuc;
							firstMessage = true;
						}
						flag = true;
					}
				}
			}
		}
		
		if(flag == false) 
			SQLCode += " * FROM ";
		else 
			SQLCode += " FROM ";
		
		for (Message message : messages) {
			if(message instanceof QueryMessage) {
				SQLCode += message.getInfo();
			}
		}
		
		
		for(Message message : messages) {
			if(message instanceof WhereMessage) {
				
				String[] split = message.getInfo().split("!");
				
				if(split[0].equals("where")) {
					SQLCode += " WHERE " + split[1] + " " + split[2] + " " + split[3] + " ";
				} else if(split[0].equals("or")) {
					SQLCode += " OR " + split[1] + " " + split[2] + " " + split[3] + " ";
				} else if(split[0].equals("and")) {
					SQLCode += " AND " + split[1] + " " + split[2] + " " + split[3] + " ";
				} else if(split[0].equals("between")) {
					SQLCode += " AND " + split[1] + " > " + split[2] + " AND " + split[1] + " < " + split[3] + " ";
				} else if(split[0].equals("endsWith")) {
					SQLCode += " AND " + split[1] + " LIKE '%" + split[2] + "'";
				} else if(split[0].equals("startsWith")) {
					SQLCode += " AND " + split[1] + " LIKE '" + split[2] + "%'";
				} else if(split[0].equals("contains")) {
					SQLCode += " AND " + split[1] + " LIKE '%" + split[2] + "%'";
				}
				
			}
		}
	
		for (Message message : messages) {
			if(message instanceof JoinMessage) {
				String[] arg = message.getInfo().split("!");
				SQLCode += " join" + arg[0] + " using (" + arg[1] + ")";
			}
		}
		
		for (Message message : messages) {
			if(message instanceof GroupByMessage) {
				SQLCode += " group by " + message.getInfo();
			}
		}
		
		for(Message message : messages) {
			if(message instanceof HavingMessage) {
				String[] arg = message.getInfo().split("!");
				
				for (String alias : aliases.keySet()) {
					if(arg[1].equals(alias)) {
						arg[1] = aliases.get(alias);
					}
				}
				
				SQLCode += " " + arg[0] + " " + arg[1] + " " +arg[2] + " " +arg[3];
			}
		}
		
		for(Message message : messages) {
			if(message instanceof OrderMessage) {
				System.out.println(message.getInfo());
				if(message.getInfo().contains("Desc")) {
					String temp = message.getInfo().replaceFirst("Desc", "");
					SQLCode += " order by " + temp + " desc";
				}else {
					SQLCode += " order by " + message.getInfo();
				}
			}
		}
		
		AppCore.getInstance().readDataFromTable(SQLCode);
		AppCore.getInstance().loadResource();
		messages.clear();
		SQLCode = "";
	}
}