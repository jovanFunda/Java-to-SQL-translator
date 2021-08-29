package gui;

import app.AppCore;
import controller.EnterButtonController;
import observer.Notification;
import observer.Subscriber;
import observer.enums.NotificationCode;
import resource.implementation.InformationResource;
import validator.Validator;

import javax.swing.*;
import javax.swing.plaf.metal.MetalBorders;
import javax.swing.table.TableModel;
import java.awt.*;
import java.util.Stack;

public class MainFrame extends JFrame implements Subscriber {

	private static final long serialVersionUID = 1L;
	private static MainFrame instance = null;

    private AppCore appCore;
    private Validator validator;
    private JTable jTable;
    private JScrollPane jsp;
    private JPanel bottomStatus;
    private JPanel panel;
    private JTextArea userArea;
    private JButton submitButton;
    private JTextArea consoleArea;

    private MainFrame() {

    }

    public static MainFrame getInstance(){
        if (instance==null){
            instance=new MainFrame();
            instance.initialise();
        }
        return instance;
    }


    private void initialise() {

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("Java to SQL project");

        jTable = new JTable();
        panel = new JPanel();
        userArea = new JTextArea();
        consoleArea = new JTextArea();
        submitButton = new JButton();
        userArea.setBorder(new MetalBorders.TextFieldBorder());

        userArea.setText("Use this field to enter SQL statements");
        userArea.setSize(500,150);
        userArea.setPreferredSize(new Dimension(500, 150));
        userArea.setLineWrap(true);
        userArea.setWrapStyleWord(true);
        jTable.setPreferredScrollableViewportSize(new Dimension(500, 400));
        jTable.setFillsViewportHeight(true);

        BorderLayout layout = new BorderLayout();
        panel.setLayout(layout);
        this.setResizable(false);
        this.setSize(1000,500);
        panel.add(new JScrollPane(jTable), BorderLayout.LINE_START);
        panel.add(userArea, BorderLayout.LINE_END);
        JPanel donjiPanel = new JPanel();
        donjiPanel.add(consoleArea);
        consoleArea.setPreferredSize(new Dimension(500, 200));
        consoleArea.setSize(500, 200);
        consoleArea.setBorder(new MetalBorders.TextFieldBorder());
        consoleArea.setEditable(false);
        consoleArea.setText("Console for errors");
        submitButton.setAction(new EnterButtonController());
        submitButton.setText("Enter");
        donjiPanel.add(submitButton);
        panel.add(donjiPanel, BorderLayout.PAGE_END);

        this.add(panel);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    public void setAppCore(AppCore appCore) {
        this.appCore = appCore;
        this.appCore.addSubscriber(this);
        this.jTable.setModel(appCore.getTableModel());
    }


    @Override
    public void update(Object notification) {
    	if(!(notification instanceof Notification)) {
	            jTable.setModel((TableModel) (((Notification)notification).getData()));
    	}
    }

	public AppCore getAppCore() {
		return appCore;
	}
	
	public JTextArea getConsoleArea() {
		return consoleArea;
	}
	
	public JTextArea getUserArea() {
		return userArea;
	}
	
	public void setValidator(Validator validator) {
		this.validator = validator;
		this.validator.addSubscriber(this);
	}
}