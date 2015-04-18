package edu.sjsu.cmpe275.lab3.config;

import java.net.UnknownHostException;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;

public class SpringMongoConfig {
    
    public MongoClient getMongoClient() {
    	MongoClient mongoClient=null ;
    	String link ="mongodb://Surabhi:m0ngodbPa$$@ds061371.mongolab.com:61371/gaming";
    	MongoClientURI uri =new MongoClientURI(link);
    	
    	try{
    		mongoClient = new MongoClient(uri);
    	}catch(UnknownHostException ex){
    		ex.printStackTrace();
    	}
		return mongoClient;
    }

}
