package com.project.cassandra.database_classes;

import com.datastax.driver.core.Session;

public class CreateKeyspace {
	
	public static void createKeyspace(Session session, String keyspace) {

    // Create new keyspace
    String cqlStatement = "CREATE KEYSPACE IF NOT EXISTS " + keyspace + " WITH " +
            "replication = {'class':'SimpleStrategy','replication_factor':1}";

    session.execute(cqlStatement);
    session.close();

    System.out.println("Connected successfully");
    
	}
}
