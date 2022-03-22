import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class JDBC {
    public static void main (String[] args) {
        try {
            Connection connection = DriverManager.getConnection ("jdbc:mysql://localhost:3306/ilmi_book_house","root","Mnismhn7");

            Statement statement = connection.createStatement ();

            ResultSet resultSet = statement.executeQuery ("SELECT * FROM project.author");

            while (resultSet.next ()){
                System.out.println (resultSet.getString ("Author_name"));
            }
        }
        catch (Exception e){
            e.printStackTrace ();
        }

    }
}
