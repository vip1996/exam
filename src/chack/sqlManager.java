package chack;

import java.awt.Font;
import java.sql.*;

import javax.swing.JTextArea;
import javax.swing.JTextField;

public class sqlManager implements Tasks  {

@Override
public void CreateTable() {
    String sql = "CREATE TABLE tasks ("
            + "id INT PRIMARY KEY IDENTITY(1,1),"
            + "title NVARCHAR(50),"
            + "description NVARCHAR(50),"
            + "due_date DATE,"
            + "completed INT)";

    try (Connection conn = ConnectionDB.getconnection(); 
         PreparedStatement pst = conn.prepareStatement(sql)) {
        pst.executeUpdate();
        System.out.println("Table created successfully.");
    } catch (SQLException e) {
        e.printStackTrace();
    }
}


@Override
public void Update(JTextField idTextField,JTextField descrTextField,JTextField titleTextField,
	      JTextField dateTextField,
	      JTextField compTextField) {
	String IDtxt = idTextField.getText();
	int id = Integer.parseInt(IDtxt);
	String title = titleTextField.getText();
	String description = descrTextField.getText();
	String getData = dateTextField.getText();	
	Date data = Date.valueOf(getData);
	String status = compTextField.getText();
	String sql ="Update tasks set title=?,"
			+ "description=?,"
			+ "due_date=?,"
			+ "completed=? where id=?";
		
	try (Connection conn = ConnectionDB.getconnection(); 
	         PreparedStatement pst = conn.prepareStatement(sql)) {
		
		     pst.setString(1, title);
		     pst.setString(2, description);
		     pst.setDate(3, data);
		     pst.setString(4, status);
		     pst.setInt(5, id);
	         pst.executeUpdate();
	        System.out.println("Table Update successfully.");
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	
}

@Override
public void Delete(JTextField idTextField) {
	String IDtxt = idTextField.getText();
	int id = Integer.parseInt(IDtxt);
	String sql = "delete from tasks WHERE id = ?";
	try (Connection conn = ConnectionDB.getconnection(); 
	         PreparedStatement pst = conn.prepareStatement(sql)) {
		    pst.setInt(1, id);
	        pst.executeUpdate();
	        System.out.println("Table created successfully.");
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
}

@Override
public void Completed(JTextArea dataTextField) {
    String sql = "SELECT * FROM tasks";

    try (Connection con = ConnectionDB.getconnection();
         PreparedStatement pst = con.prepareStatement(sql)) {

        ResultSet rs = pst.executeQuery();
        StringBuilder result = new StringBuilder();
        
        result.append("================\n\n");

        int count = 1;
        while (rs.next()) {
            int id = rs.getInt("id");
            String title = rs.getString("title");
            String description = rs.getString("description");
            Date dueDate = rs.getDate("due_date");
            boolean completed = rs.getBoolean("completed");
            
            result.append(count + ". ");
            result.append("ID: " + id + "\n");
            result.append("   სათაური: " + (title != null ? title : "უცნობი") + "\n");
            result.append("   აღწერა: " + (description != null ? description : "არ არის") + "\n");
            result.append("   თარიღი: " + (dueDate != null ? dueDate.toString() : "არ არის") + "\n");
            result.append("   სტატუსი: " + (completed ? "დასრულებული" : "მიმდინარე") + "\n");
            result.append("   ─────────────────────────\n\n");
            
            count++;
        }
        
        if (count == 1) {
            result.append("დავალებები არ არის ნაპოვნი.\n");
        }

        dataTextField.setText(result.toString());

    } catch (SQLException e) {
        e.printStackTrace();
        dataTextField.setText("შეცდომა მონაცემის ამოღებისას: " + e.getMessage());
    }
}


@Override
public void CloseDB(JTextArea dataTextField) {
	try (Connection con = ConnectionDB.getconnection()) {
        dataTextField.setText("DB connection closed successfully.");
    } catch (SQLException e) {
        e.printStackTrace();
        dataTextField.setText("Failed to close DB connection.");
    }
}

}
