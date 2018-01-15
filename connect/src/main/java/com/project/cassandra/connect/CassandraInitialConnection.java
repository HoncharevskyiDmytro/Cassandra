package com.project.cassandra.connect;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.ConsistencyLevel;
import com.datastax.driver.core.QueryOptions;
import com.datastax.driver.core.Session;

public class CassandraInitialConnection {

	public static Cluster getClusterConnection(String serverIP) {
		
		QueryOptions options = new QueryOptions();
		options.setConsistencyLevel(ConsistencyLevel.ALL);

		Cluster cluster = Cluster.builder()
				.withQueryOptions(options)
				.addContactPoints(serverIP)
				.build();
		
		return cluster;
	
	}
	
	public static Session getKeyspaceConnection(Cluster cluster, String keyspace) {
		
		Session session = cluster.connect(keyspace);
		return session;
	
	}
}
