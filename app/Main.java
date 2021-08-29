package app;

import gui.MainFrame;
import validator.Validator;
public class Main {

    public static void main(String[] args) {
    	
    	 AppCore appCore = AppCore.getInstance();
         MainFrame mainFrame = MainFrame.getInstance();
         
         mainFrame.setAppCore(appCore);
         mainFrame.setValidator(Validator.getInstance());
    }
}