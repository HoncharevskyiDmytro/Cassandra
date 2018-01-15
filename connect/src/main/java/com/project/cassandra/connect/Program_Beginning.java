package com.project.cassandra.connect;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Session;
import com.project.cassandra.window_forms.Authorization;
import com.project.cassandra.database_classes.CreateKeyspace;
import com.project.cassandra.database_classes.CreateTable;

public class Program_Beginning {
	
	public static final String SERVER_IP = "127.0.0.1";
	public static final String SYSTEM_KEYSPACE = "system";
	public static final String PROGRAM_KEYSPACE = "user_document";
	private static Session session;

	public static void main(String[] args) {
			
		
		//!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
		Cluster cluster = CassandraInitialConnection.getClusterConnection(SERVER_IP);
		session = CassandraInitialConnection.getKeyspaceConnection(cluster, SYSTEM_KEYSPACE);
		
		// to Create keyspace here
		CreateKeyspace.createKeyspace(session, PROGRAM_KEYSPACE);
		
		// to Create table here
		CreateTable.createTable();
		
		//!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
		
		Authorization.main(null);
	}

	public static Session getSession() {
		return session;
	}

	public static void setSession(Session session) {
		Program_Beginning.session = session;
	}

}
