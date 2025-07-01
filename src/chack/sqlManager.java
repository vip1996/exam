package chack;

import java.sql.*;

public class SqlManager implements Tasks {
    @Override
    public void createTable() {
    	 String sql =
    		        "IF NOT EXISTS (SELECT * FROM sysobjects WHERE name='tasks' AND xtype='U') " +
    		        "BEGIN " +
    		        "CREATE TABLE tasks (" +
    		        "id INT PRIMARY KEY IDENTITY(1,1), " +
    		        "title NVARCHAR(50), " +
    		        "description NVARCHAR(50), " +
    		        "due_date DATE, " +
    		        "completed INT) " +
    		        "END";
    		    try (Connection conn = ConnectionDb.getConnection();
    		         Statement stmt = conn.createStatement()) {
    		        stmt.execute(sql);
    		        System.out.println("Table created or already exists.");
    		    } catch (SQLException e) {
    		        throw new RuntimeException("Error creating table: " + e.getMessage(), e);
    		    }
    }

    @Override
    public void update(int id, String description, String title, String date, boolean completed) {
        String sql = "UPDATE tasks SET title=?, description=?, due_date=?, completed=? WHERE id=?";
        try (Connection conn = ConnectionDb.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setString(1, title);
            pst.setString(2, description);
            pst.setDate(3, Date.valueOf(date)); // date format: "yyyy-mm-dd"
            pst.setInt(4, completed ? 1 : 0);
            pst.setInt(5, id);
            int rows = pst.executeUpdate();
            if (rows == 0) {
                throw new RuntimeException("No task found with id: " + id);
            }
        } catch (SQLException | IllegalArgumentException e) {
            throw new RuntimeException("Error updating task: " + e.getMessage(), e);
        }
    }

    @Override
    public void delete(int id) {
        String sql = "DELETE FROM tasks WHERE id=?";
        try (Connection conn = ConnectionDb.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setInt(1, id);
            int rows = pst.executeUpdate();
            if (rows == 0) {
                throw new RuntimeException("No task found with id: " + id);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error deleting task: " + e.getMessage(), e);
        }
    }

    @Override
    public String listAllTasks() {
        String sql = "SELECT * FROM tasks";
        StringBuilder result = new StringBuilder();
        try (Connection conn = ConnectionDb.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql);
             ResultSet rs = pst.executeQuery()) {
            result.append("================\n\n");
            int count = 1;
            while (rs.next()) {
                int id = rs.getInt("id");
                String title = rs.getString("title");
                String description = rs.getString("description");
                Date dueDate = rs.getDate("due_date");
                boolean completed = rs.getInt("completed") == 1;
                result.append(count).append(". ")
                    .append("ID: ").append(id).append("\n")
                    .append("   Title: ").append(title != null ? title : "Unknown").append("\n")
                    .append("   Description: ").append(description != null ? description : "None").append("\n")
                    .append("   Date: ").append(dueDate != null ? dueDate.toString() : "None").append("\n")
                    .append("   Status: ").append(completed ? "Completed" : "In progress").append("\n")
                    .append("   ─────────────────────────\n\n");
                count++;
            }
            if (count == 1) {
                result.append("No tasks found.\n");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error retrieving tasks: " + e.getMessage(), e);
        }
        return result.toString();
    }

    @Override
    public void closeDb() {
        try (Connection conn = ConnectionDb.getConnection()) {
        } catch (SQLException e) {
            throw new RuntimeException("Failed to close DB connection: " + e.getMessage(), e);
        }
    }
}
