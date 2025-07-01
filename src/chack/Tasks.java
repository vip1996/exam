package chack;

public interface Tasks {
    void createTable();
    void update(int id, String description, String title, String date, boolean completed);
    void delete(int id);
    String listAllTasks();
    void closeDb();
}
