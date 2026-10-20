import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnectionStudent {

    public static Connection getConnection() {

        String url = "jdbc:mysql://localhost:3306/java";
        String username = "root";
        String password = "NewPassword123";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            

            Connection con = DriverManager.getConnection(
                    url,
                    username,
                    password
            );

            System.out.println("Database Connected Successfully!");
            return con;

        } catch (Exception e) {
            System.out.println("Database Connection Failed!");
            e.printStackTrace();
            return null;
        }
    }
}
