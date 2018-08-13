package ch.nyp.chatprogram.common;

import java.awt.Checkbox;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Random;

public class DataConnection {
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	static final String DB_URL = "jdbc:derby:./Database";
	static Connection conn = null;
	static Statement stmt = null;
	static String token = "";
	static UserApplication user;
	private static boolean isTesting = true; // Falls 'true' wird die Authentifikation nicht ben�tigt
	
	private static ResultSet SelectQuery(String query, boolean needsAuthentification) {
		if(!isTesting)
			if(needsAuthentification)
				if(!IsUserAuthorizied()) {
					System.out.println("User is not authorized");
					return null;
				}
		System.out.println("Connecting to a selected database...");
		try {
			conn = DriverManager.getConnection(DB_URL, "ChatProgramInitAdmin", "ChatProgramInitAdmin");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println("Connected database successfully...");

		// STEP 2: Execute a query
		System.out.println("Query gets executed");
		try {
			stmt = conn.createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println("Connecting to a selected database...");
		ArrayList<UserSession> allSessions = new ArrayList<UserSession>();
		try {
			conn = DriverManager.getConnection(DB_URL, "ChatProgramInitAdmin", "ChatProgramInitAdmin");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println("Connected database successfully...");
		ResultSet resultSet = null;
		try {
			resultSet = stmt.executeQuery(query);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			if(conn != null)
				conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return resultSet;
	}
	private static void ExcecuteUpdateQuery(String command, Boolean needsAuthentification) {
		if(!isTesting)
			if(needsAuthentification)
				if(!IsUserAuthorizied()) {
					System.out.println("User is not authorized");
					return;
				}
		System.out.println("Connecting to a selected database...");
		try {
			conn = DriverManager.getConnection(DB_URL, "ChatProgramInitAdmin", "ChatProgramInitAdmin");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println("Connected database successfully...");

		// STEP 2: Execute a query
		System.out.println("Query gets executed");
		try {
			stmt = conn.createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			stmt.executeUpdate(command);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		// STEP 3: Close Connection
		try {
			if (conn != null)
				conn.close();
			else
				System.out.println("Connection is null");
		} catch (SQLException se) {
			System.out.println("Connection could not be closed");
		}
	}
	
	
	public static void CreateDatabase(String username, String password) {
		try {
			// STEP 1: Register JDBC driver
			Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
			// STEP 2: Open a connection
			System.out.println("Connecting to a selected database...");
			conn = DriverManager.getConnection(DB_URL + ";create=true", username, password);
			System.out.println("Connected database successfully...");

			// STEP 3: Execute a query
			System.out.println("Query gets executed");
			stmt = conn.createStatement();
			ArrayList<String> sqlCommands = new ArrayList<>();
			sqlCommands.add("CREATE TABLE ChatProgramTui.UserApplication (\r\n"
					+ "useId INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),\r\n"
					+ "ususername varchar(50) NOT NULL,\r\n" 
					+ "uspassword char(32) NOT NULL,\r\n"
					+ "usdisplayName VARCHAR(50),\r\n" 
					+ "usisAdmin BOOLEAN,\r\n" 
					+ "PRIMARY KEY (useId)\r\n" + ")");
			sqlCommands
					.add("insert into CHATPROGRAMTUI.USERAPPLICATION (USUSERNAME, USPASSWORD) values ('test1234', 'test1234')");
			sqlCommands.add("CREATE TABLE ChatProgramTui.Session ("
					+ "sesId INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), \r\n"
					+ "useId INTEGER,\r\n"
					+ "sesIP VARCHAR(50), \r\n"
					+ "sesLastPing TIMESTAMP NOT NULL, \r\n"
					+ "sesToken CHAR(64) NOT NULL, \r\n" 
					+ "PRIMARY KEY (sesId)\r\n" + ")");
			sqlCommands.add("CREATE TABLE ChatProgramTui.Chat ("
					+ "chaId INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), \r\n"
					+ "chaName varchar(50),\r\n"
					+ "chaDescription VARCHAR(200),\r\n"
					+ "PRIMARY KEY (chaId)\r\n)");
			sqlCommands.add("CREATE TABLE ChatProgramTui.UserChat ("
					+ "userIdChaId INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), \r\n"
					+ "userId INTEGER NOT NULL,\r\n"
					+ "chaId INTEGER NOT NULL,\r\n"
					+ "PRIMARY KEY (userIdChaId)\r\n)");
			/*
			 * sqlCommands.
			 * add("CALL SYSCS_UTIL.SYSCS_SET_DATABASE_PROPERTY('derby.connection.requireAuthentication','true')"
			 * ); sqlCommands.
			 * add("CALL SYSCS_UTIL.SYSCS_SET_DATABASE_PROPERTY('derby.user.ChatProgramInitAdmin', 'ChatProgramInitAdmin')"
			 * ); sqlCommands.
			 * add("CALL SYSCS_UTIL.SYSCS_SET_DATABASE_PROPERTY('derby.database.defaultConnectionMode', 'noAccess')"
			 * ); sqlCommands.
			 * add("CALL SYSCS_UTIL.SYSCS_SET_DATABASE_PROPERTY('derby.database.fullAccessUsers', 'ChatProgramInitAdmin')"
			 * );
			 */
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

		} // end try
	}// end main

	public static Boolean DoesDatabaseExist() {
		Boolean databaseFound;
		try {
			conn = DriverManager.getConnection(DB_URL, "test123", "test123");
			conn.close();
			databaseFound = true;
		} catch (SQLException e) {
			databaseFound = false;
		}
		return databaseFound;
	}

	public static ArrayList<UserApplication> GetAllAccounts() {
		ArrayList<UserApplication> allAccounts = new ArrayList<UserApplication>();
		ResultSet resultSet = SelectQuery("select * from CHATPROGRAMTUI.USERAPPLICATION", true);
		try {
			while (resultSet.next()) {
				UserApplication selectedUserApplication = new UserApplication();
				selectedUserApplication.SetUserId(resultSet.getInt("USEID"));
				selectedUserApplication.SetUsername(resultSet.getString("USUSERNAME"));
				selectedUserApplication.SetPassword(resultSet.getString("USPASSWORD"));
				selectedUserApplication.SetDisplayName(resultSet.getString("USDISPLAYNAME"));
				selectedUserApplication.SetIsAdmin(resultSet.getBoolean("USISADMIN"));
				allAccounts.add(selectedUserApplication);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			resultSet.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return allAccounts;
	}
	public static UserApplication GetUserByUsernamePassword(String username, String password) {
		UserApplication userAccount = new UserApplication();
		UserApplication matchingUserAccount = null;
		ResultSet resultSet = SelectQuery("select * from CHATPROGRAMTUI.USERAPPLICATION where USUSERNAME = " + username, true);
		try {
			while(resultSet.next()) {
				userAccount.SetUserId(resultSet.getInt("USEID"));
				userAccount.SetUsername(resultSet.getString("USUSERNAME"));
				userAccount.SetPassword(resultSet.getString("USPASSWORD"));
				userAccount.SetDisplayName(resultSet.getString("USDISPLAYNAME"));
				userAccount.SetIsAdmin(resultSet.getBoolean("USISADMIN"));
				if(userAccount.GetPassword().trim().equals(password))
					matchingUserAccount = userAccount;
			} 
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			resultSet.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return matchingUserAccount;
	}
	
	public static UserApplication GetUserById(int userId){

		UserApplication userAccount = new UserApplication();
		ResultSet resultSet = SelectQuery("select * from CHATPROGRAMTUI.USERAPPLICATION where USEID = " + userId, true);
		try {
			if(resultSet.next()) {
				userAccount.SetUserId(resultSet.getInt("USEID"));
				userAccount.SetUsername(resultSet.getString("USUSERNAME"));
				userAccount.SetPassword(resultSet.getString("USPASSWORD"));
				userAccount.SetDisplayName(resultSet.getString("USDISPLAYNAME"));
				userAccount.SetIsAdmin(resultSet.getBoolean("USISADMIN"));
			} 
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			resultSet.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return userAccount;
	}
	public static void InsertUserApplication(UserApplication user) {
		ExcecuteUpdateQuery("insert into CHATPROGRAMTUI.USERAPPLICATION (USUSERNAME, USPASSWORD, USDISPLAYNAME, USISADMIN) "
					+ "values ('" + user.GetUsername() + "', '" + user.GetPassword() + "', '" + user.GetdisplayName() + "', '" + user.GetIsAdmin() + "')", true);
	}
	public static void UpdateUser(UserApplication userApplication) {
		ExcecuteUpdateQuery("UPDATE CHATPROGRAMTUI.USERAPPLICATION SET USUSERNAME='"+ userApplication.GetUsername() +"', USPASSWORD = '"+ userApplication.GetPassword() +"', USDISPLAYNAME = '"+ userApplication.GetdisplayName() +"', USISADMIN = " + userApplication.GetIsAdmin() + " WHERE useID="+ userApplication.GetUserId() , true);
	}
	public static void DeleteAccountById(int accountId) {
		ExcecuteUpdateQuery("DELETE FROM CHATPROGRAMTUI.UserApplication WHERE useId = "+ accountId, true);
	}
	
	
	public static ArrayList<UserSession> GetAllSessions(){
		ArrayList<UserSession> allSessions = new ArrayList<UserSession>();
		ResultSet resultSet = SelectQuery("select * from CHATPROGRAMTUI.SESSION", true);
		try {
			while (resultSet.next()) {
				UserSession selectedUserSession = new UserSession();
				selectedUserSession.SetSessionId(resultSet.getInt("SESID"));
				selectedUserSession.SetUser(UserApplication.GetUserById(resultSet.getInt("USEID")));
				selectedUserSession.SetSessionIP(resultSet.getString("SESIP"));
				selectedUserSession.SetLastPing(resultSet.getTimestamp("SESLASTPING").toLocalDateTime());
				selectedUserSession.SetToken(resultSet.getString("SESTOKEN"));
				allSessions.add(selectedUserSession);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			try {
				resultSet.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return allSessions;
	}
	public static void DestroySession(int sessionId) {
		ExcecuteUpdateQuery("DELETE FROM CHATPROGRAMTUI.SESSION WHERE sesID = "+ sessionId, true);
	}
	public static UserSession GetSessionBySessionId(int sessionId) {
		UserSession userSession = new UserSession();
		ResultSet resultSet = SelectQuery("select * from CHATPROGRAMTUI.USERAPPLICATION where SESID = " + sessionId, true);
		try {
			resultSet.next();
			userSession.SetSessionId(resultSet.getInt("SESID"));
			userSession.SetUser(UserApplication.GetUserById(resultSet.getInt("USEID")));
			userSession.SetSessionIP(resultSet.getString("SESIP"));
			userSession.SetLastPing(resultSet.getTimestamp("SESLASTPING").toLocalDateTime());
			userSession.SetToken(resultSet.getString("SESTOKEN"));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			try {
				resultSet.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return userSession;
	}
	public static boolean IsUserAuthorizied() {
		ResultSet resultSet = SelectQuery("select * from CHATPROGRAMTUI.SESSION where USEID =" + user.GetUserId() + " AND SESTOKEN = " + token, true);
		Boolean userExist = false;
		try {
			userExist =resultSet.next(); 
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			resultSet.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return userExist;
	}

	/*public static void CreateNewSession(UserSession userSession) {
		ExcecuteUpdateQuery("insert into CHATPROGRAMTUI.SESSION (useId, sesIP, sesLastPing, sesToken) "
					+ "values (" + userSession.GetUser().GetUserId() + ", '" + userSession.GetSessionIP() + "', '" + Timestamp.valueOf(userSession.GetLastPing()) + "', '" + userSession.GetToken() + "')", true);
	}*/
	/*public static UserApplication TryLogin(String username, String password) {
		UserApplication userAccount = new UserApplication();
		ResultSet resultSet = SelectQuery("select * from CHATPROGRAMTUI.USERAPPLICATION where USUSERNAME = " + username, false);
		try {
			while(resultSet.next()) {
				userAccount.SetUserId(resultSet.getInt("USEID"));
				userAccount.SetUsername(resultSet.getString("USUSERNAME"));
				userAccount.SetPassword(resultSet.getString("USPASSWORD"));
				userAccount.SetDisplayName(resultSet.getString("USDISPLAYNAME"));
				userAccount.SetIsAdmin(resultSet.getBoolean("USISADMIN"));
				if(userAccount.GetPassword().trim().equals(password)) {
					user = userAccount;							
					return userAccount;
				}
			} 
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	private static String GenerateRandomString(int length) {
		String allowedChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder builder = new StringBuilder();
        Random rnd = new Random();
        while (builder.length() < length) { // length of the random string.
            int index = (int) (rnd.nextFloat() * allowedChars.length());
            builder.append(allowedChars.charAt(index));
        }
        String saltStr = builder.toString();
        return saltStr;
	}
	private static UserSession CreateSession() {
		UserSession session = new UserSession();
		session.SetLastPing(lastPing);
		session.SetSessionIP(sessionIP);
		token = GenerateRandomString(64);
		session.SetToken(token);
		session.SetUser(user);
	}*/
}
