import java.sql.*;
public class CreateSchema {
    public static void main(String[] args) {
        try {
            Connection c = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521/xe", "system", "Aa@102030");
            Statement s = c.createStatement();
            try {
                s.execute("ALTER SESSION SET \"_ORACLE_SCRIPT\"=true");
            } catch(Exception e) {}
            try {
                s.execute("DROP USER myDB CASCADE");
            } catch(Exception e) {}
            s.execute("CREATE USER myDB IDENTIFIED BY \"Aa@102030\"");
            s.execute("GRANT ALL PRIVILEGES TO myDB");
            System.out.println("User/Schema myDB created successfully!");
            c.close();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}
