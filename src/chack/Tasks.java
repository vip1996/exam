package chack;

import javax.swing.JTextArea;
import javax.swing.JTextField;

public interface Tasks {
	  void CreateTable();
	  void Update(JTextField idTextField,
			      JTextField descrTextField,
			      JTextField titleTextField,
			      JTextField dateTextField,
			      JTextField compTextField);
	  void Delete(JTextField idTextField);
	  void Completed(JTextArea dataTextField);
	  void CloseDB(JTextArea dataTextField);
	}

