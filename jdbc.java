import java.sql.*;

class User {
    public static final String name = "system"; // SQL+ username
    public static final String password = "subin2172001"; // SQL+ password
}

class DatabaseInfo {
    public static final String SID = "orcl"; // "orcl" by default
}

class DB {
    public static void main(String[] args) {
        //select("event");
        insert(10);
        delete(10);
        insert(10);
        update(10, "CARDIO");
    }

    //SELECT
    public static void select(String Tname) {
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver"); // Specify the driver to be used
            Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:" + DatabaseInfo.SID, User.name, User.password); // Same username and password used in SQL+
                                                //  URL = jdbc:oracle:<driver_type>:[<username>/<password>]@<database_specifier>:<port_number>:<SID>
                                                // <database_specifier> is the SID of the
            Statement stmt = con.createStatement();

            try {
                //Performing a Query on EVENT table
                ResultSet resSet = stmt.executeQuery("SELECT * FROM " + Tname );

                System.out.println("Details of "+ Tname +" are:");
                while(resSet.next()) {
                    System.out.println("EID: " + resSet.getInt("EID")); // Can specify column number instead of name. Numbering starts from 1
                    System.out.println("EName: " + resSet.getString("ENAME"));
                    // System.out.println("Salary: " + resSet.getBigDecimal("Salary"));
                    // System.out.println("Department number: " + resSet.getInt("DepNo"));
                    System.out.println("EDate: " + resSet.getDate("EDATE") + "\n");
                }
            }
            catch (SQLException sqle) {
                    System.out.println(sqle);
                    System.out.println("Failed to execute SELECt * query");
            }
                
            stmt.close();
            con.close();
        }
        catch(ClassNotFoundException cnfe) {
            System.out.println(cnfe);
            System.out.println("Failed to load driver");
        }
        catch(SQLException sqle) {
            System.out.println(sqle);
        }
    }

    //INSERT
    public static void insert(int dno) {
        try {
            int rowsUpdated;
            Class.forName("oracle.jdbc.driver.OracleDriver"); // Specify the driver to be used
            Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:" + DatabaseInfo.SID, User.name, User.password); // Same username and password used in SQL+
                                                //  URL = jdbc:oracle:<driver_type>:[<username>/<password>]@<database_specifier>:<port_number>:<SID>
                                                // <database_specifier> is the SID of the
            Statement stmt = con.createStatement();

            try {  
                PreparedStatement prepStmt = con.prepareStatement("INSERT INTO DEPT (DNO, DNAME, ID) VALUES ( "+ dno +" , 'ORTHO' , 21 )");

                rowsUpdated = prepStmt.executeUpdate();
                if(rowsUpdated > 0)
                    System.out.println("Successfully inserted " + rowsUpdated + " row(s)");
                else
                    System.out.println("Failed to insert to table");
                
                prepStmt.close();
            }
            catch(SQLException sqle) {
                System.out.println(sqle);
                System.out.println("Failed to perform Insert using prepared statement");
            }

            stmt.close();
            con.close();
        }
        catch(ClassNotFoundException cnfe) {
            System.out.println(cnfe);
            System.out.println("Failed to load driver");
        }
        catch(SQLException sqle) {
            System.out.println(sqle);
        }
    }

    //DELETE    
    public static void delete(int dno) {
        try {
            int rowsUpdated;
            Class.forName("oracle.jdbc.driver.OracleDriver"); // Specify the driver to be used
            Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:" + DatabaseInfo.SID, User.name, User.password); // Same username and password used in SQL+
                                                //  URL = jdbc:oracle:<driver_type>:[<username>/<password>]@<database_specifier>:<port_number>:<SID>
                                                // <database_specifier> is the SID of the

            Statement stmt = con.createStatement();

            try {
                rowsUpdated = stmt.executeUpdate("DELETE FROM DEPT WHERE DNO = "+ dno +"");
                if(rowsUpdated > 0)
                    System.out.println("Successfully deleted " + rowsUpdated + " row(s)");
                else
                    System.out.println("Failed to delete from table");
            }
            catch(SQLException sqle) {
                System.out.println(sqle);
                System.out.println("Failed to perform Delete");
            }
        
            stmt.close();
            con.close();
        }
        catch(ClassNotFoundException cnfe) {
            System.out.println(cnfe);
            System.out.println("Failed to load driver");
        }
        catch(SQLException sqle) {
            System.out.println(sqle);
        }
    }

    //UPDATE
    public static void update(int dno, String name) {
        try {
            int rowsUpdated;
            Class.forName("oracle.jdbc.driver.OracleDriver"); // Specify the driver to be used
            Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:" + DatabaseInfo.SID, User.name, User.password); // Same username and password used in SQL+
                                                //  URL = jdbc:oracle:<driver_type>:[<username>/<password>]@<database_specifier>:<port_number>:<SID>
                                                // <database_specifier> is the SID of the

            Statement stmt = con.createStatement();

                //Updating the newly added row
            try {
                rowsUpdated = stmt.executeUpdate("UPDATE DEPT SET DNAME = '"+ name +"' WHERE DNO = "+ dno);
                if(rowsUpdated > 0)
                    System.out.println("Successfully updated " + rowsUpdated + " row(s)");
                else
                    System.out.println("Failed to update table");
            }
            catch(SQLException sqle) {
                System.out.println(sqle);
                System.out.println("Failed to perform Delete");
            }            

            stmt.close();
            con.close();
        }
        catch(ClassNotFoundException cnfe) {
            System.out.println(cnfe);
            System.out.println("Failed to load driver");
        }
        catch(SQLException sqle) {
            System.out.println(sqle);
        }
    }
}