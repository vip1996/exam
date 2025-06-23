package chack;

import javax.swing.*;
import javax.swing.JTextArea;    
public class ToDoFrame extends JFrame{
    JButton Create,Update,Delete,Complete,CloseDB;
    JTextField descrTextField,titleTextField,dateTextField,compTextField,idTextField;
    JTextArea dataTextField;
public ToDoFrame(){
	JFrame frame = new JFrame("ToDo");
	JPanel panel = new JPanel(null);
    Create = new JButton("Create");
	Update = new JButton("Update");
	Delete = new JButton("Delete");
	Complete = new JButton("Complete");
	CloseDB = new JButton("CloseDB");
	
    descrTextField = new JTextField();
	titleTextField = new JTextField();
	dateTextField = new JTextField();
	compTextField = new JTextField();
	idTextField = new JTextField();
	dataTextField = new JTextArea();
	
	idTextField.setBounds(100, 20, 200, 30);
	titleTextField.setBounds(100, 60, 200, 30);
	descrTextField.setBounds(100, 100, 200, 30);
	dateTextField.setBounds(100, 140, 200, 30);
	compTextField.setBounds(100, 180, 200, 30);
	dataTextField.setBounds(350,20,300,300);
	
	descrTextField.setText("descrTextField");
	titleTextField.setText("titleTextField");
	compTextField.setText("status");
	idTextField.setText("ID");
	dateTextField.setText("date");
	
	Delete.setBounds(80, 230, 100, 30); 
	Update.setBounds(220, 230, 100, 30);
	Create.setBounds(80, 280, 100, 30); 
	Complete.setBounds(220, 280, 100, 30);
    CloseDB.setBounds(670,160,100,30);
    
	Create.setFocusable(false);
	Update.setFocusable(false);
	Delete.setFocusable(false);
	Complete.setFocusable(false);
	CloseDB.setFocusable(false);
	dataTextField.setEditable(false);
	dataTextField.setFocusable(false); 
	
	frame.setResizable(false);
	panel.add(descrTextField);
	panel.add(titleTextField);
	panel.add(dateTextField);
	panel.add(compTextField);
	panel.add(idTextField);
	panel.add(dataTextField);

	panel.add(Delete);	
	panel.add(Update);
	panel.add(Create);
	panel.add(CloseDB);
	panel.add(Complete);
	frame.add(panel);
	frame.setSize(800,400);
	frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	frame.setVisible(true);
	
	
	Create.addActionListener(e->{
		sqlManager sq = new sqlManager();
		sq.CreateTable();
	});
	
	
	Delete.addActionListener(e->{
		sqlManager sq = new sqlManager();
		sq.Delete(idTextField);
	});
	
	
	Update.addActionListener(e->{
		sqlManager sq = new sqlManager();
		sq.Update(idTextField, descrTextField, titleTextField, dateTextField, compTextField);
	});
	
	Complete.addActionListener(e->{
		sqlManager sq = new sqlManager();
		sq.Completed(dataTextField);
	});
	
	CloseDB.addActionListener(e->{
		sqlManager sq = new sqlManager();
		sq.CloseDB(dataTextField);
	});
	}	
}

