package com.project.cassandra.database_classes;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Session;
import com.project.cassandra.connect.CassandraInitialConnection;
import com.project.cassandra.connect.Program_Beginning;

public class CreateTable {
	
	static Cluster cluster = CassandraInitialConnection.getClusterConnection(Program_Beginning.SERVER_IP);
	static Session session = CassandraInitialConnection.getKeyspaceConnection(cluster, Program_Beginning.PROGRAM_KEYSPACE);
	
	public static void createTable() {

	    // Create new table
		
		String cqlStatement = "CREATE TYPE IF NOT EXISTS user_full_name (" +
				"user_first_name text," + 
				"user_last_name text" + 
				");";

        session.execute(cqlStatement);
        
        System.out.println("New user type created created");

        /*
         * Initial structure of DB
        cqlStatement = "CREATE TABLE IF NOT EXISTS \"Users_by_Documents\" (" +
                " user_login FROZEN<Set<text>>, " +
                " user_password List<text>, " +
                " user_name List<FROZEN<user_full_name>>, " +
                " user_birthday List<date>, " +
                " user_email List<text>, " +
                " document_id FROZEN<Set<int>>, " +
                " document_title List<text>, " +
                " document_content List<text>, " +
                " document_creation_date List<date>, " +
                " document_author List<text>, " +
                " PRIMARY KEY (user_login, document_id) " +
                ");";
         */
        
        // Second edition structure
        cqlStatement = "CREATE TABLE IF NOT EXISTS \"Users_by_Documents\" (" +
                " user_login text, " +
                " user_password text STATIC, " +
                " user_name FROZEN<user_full_name> STATIC, " +
                " user_birthday date STATIC, " +
                " user_email text STATIC, " +
                " document_id int, " +
                " document_title text, " +
                " document_content text, " +
                " document_creation_date date, " +
                " document_author text, " +
                " PRIMARY KEY (user_login, document_id) " +
                ");";

        session.execute(cqlStatement);

        System.out.println("New table created");
        
        /*
         * Initial structure of DB
        cqlStatement = "CREATE TABLE IF NOT EXISTS \"Documents_by_Users\" (" +
                " user_login FROZEN<Set<text>>, " +
                " user_password List<text>, " +
                " user_name List<FROZEN<user_full_name>>, " +
                " user_birthday List<date>, " +
                " user_email List<text>, " +
                " document_id FROZEN<Set<int>>, " +
                " document_title List<text>, " +
                " document_content List<text>, " +
                " document_creation_date List<date>, " +
                " document_author List<text>, " +
                " PRIMARY KEY (document_id, user_login) " +
                ");";
        */
        
     // Second edition structure
        cqlStatement = "CREATE TABLE IF NOT EXISTS \"Documents_by_Users\" (" +
                " user_login text, " +
                " user_password text, " +
                " user_name FROZEN<user_full_name>, " +
                " user_birthday date, " +
                " user_email text, " +
                " document_id int, " +
                " document_title text STATIC, " +
                " document_content text STATIC, " +
                " document_creation_date date STATIC, " +
                " document_author text STATIC, " +
                " PRIMARY KEY (document_id, user_login) " +
                ");";

        session.execute(cqlStatement);
        // do not know if needed
        session.close();

        System.out.println("New table created");
	    
		}

}
