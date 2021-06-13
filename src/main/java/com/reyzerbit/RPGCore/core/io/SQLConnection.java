package com.reyzerbit.RPGCore.core.io;

import java.sql.DriverManager;
import java.sql.SQLException;

import com.reyzerbit.RPGCore.RPGCore;

public class SQLConnection {

	@SuppressWarnings("unused")
	private static void sqlConnect() throws SQLException, ClassNotFoundException {
		
		if(RPGCore.sqlConnection != null && !RPGCore.sqlConnection.isClosed()) {
			
			return;
			
		}
		
		RPGCore.sqlConnection = DriverManager.getConnection("jdbc:mysql://"
	            + RPGCore.sqlName + ":" + RPGCore.sqlPort + "/" + RPGCore.sqlDatabase,
	            RPGCore.sqlUser, RPGCore.sqlPassword);
		
	}

}
