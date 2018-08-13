package ChatClient;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.nio.file.FileSystem;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.nio.file.WatchEvent.Kind;
import java.nio.file.WatchEvent.Modifier;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;

public class ClientDatabase {
	
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	static final String DB_URL = "jdbc:derby:./ClientDatabase";
	static Connection conn = null;
	static Statement stmt = null;
	static final String usernameForDB = "ChatprogramUserAccount";
	static final String passwordForDB = "ChatprogramUserAccountPassword";
	private ClientDatabase() {
		// TODO Auto-generated constructor stub
	}
	public static boolean CheckIfDBExist() {
		return new File("Database").exists();
	}
	public static void CreateDatabase() {
    	File dir = new File("Database");
    	dir.mkdir();
    	try {
			// STEP 1: Register JDBC driver
			Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
			// STEP 2: Open a connection
			System.out.println("Connecting to a selected database...");
			conn = DriverManager.getConnection(DB_URL + ";create=true", usernameForDB, passwordForDB);
			System.out.println("Connected database successfully...");

			// STEP 3: Execute a query
			System.out.println("Query gets executed");
			stmt = conn.createStatement();
			ArrayList<String> sqlCommands = new ArrayList<>();
			sqlCommands.add("CREATE TABLE ChatProgramClientDB.Message ("
					+ "mesId INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), \r\n"
					+ "userId_chaId INTEGER NOT NULL,\r\n"
					+ "mesText Varchar(2000) NOT NULL,\r\n"
					+ "PRIMARY KEY (mesId)\r\n)");
			for (String sqlCommand : sqlCommands) {
				stmt.executeUpdate(sqlCommand);
				System.out.println(sqlCommand + "Worked");
			}
			ResultSet resultSet = stmt.executeQuery("select userName from sys.sysusers");
			ResultSetMetaData rsmd = resultSet.getMetaData();
			int columnsNumber = rsmd.getColumnCount();
			while (resultSet.next()) {
				for (int i = 1; i <= columnsNumber; i++) {
					if (i > 1)
						System.out.print(",  ");
					String columnValue = resultSet.getString(i);
					System.out.print(columnValue + " " + rsmd.getColumnName(i));
				}
				System.out.println("");
			}
		} catch (SQLException se) {
			// Handle errors for JDBC
			se.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			// finally block used to close resources
			try {
				if (conn != null)
					conn.close();
				else
					System.out.println("Connection is null");
			} catch (SQLException se) {
				System.out.println("Connection could not be closed");
			}
		}
	}
}
