package controller;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JTextArea;

import gui.MainFrame;
import validator.Validator;
import compiler.Compiler;

public class EnterButtonController extends AbstractAction {

	private static final long serialVersionUID = 1L;

	@Override
	public void actionPerformed(ActionEvent e) 
	{	
		JTextArea userArea = MainFrame.getInstance().getUserArea();
		JTextArea consoleArea = MainFrame.getInstance().getConsoleArea();
		
		String[] queries = userArea.getText().split("\nvar");
		
		for(int i = 1; i < queries.length; i++) {
			queries[i] = "\nvar" + queries[i];
		}
		
		for(int i = 0; i < queries.length; i++) {
			if(Validator.getInstance().checkSyntax(queries[i]) == true) 
			{
				consoleArea.setText("No errors found");
				Compiler.getInstance().compile();	
				Compiler.getInstance().getMessages().clear();
			} else 
			{
				consoleArea.setText("Errors found at " + (i + 1) + ". query");
				while(!Validator.getInstance().getErrorStack().isEmpty()) 
				{
					String message = (String) Validator.getInstance().getErrorStack().pop();
					consoleArea.setText(consoleArea.getText() + '\n' + message);
				}
				Compiler.getInstance().getMessages().clear();
			}
		}
	}
}