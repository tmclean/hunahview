package net.tmclean.hunahview.servlet;

import java.net.UnknownHostException;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.mongodb.DB;
import com.mongodb.Mongo;
import com.mongodb.MongoURI;

public class HunahViewDataSourceServletListner implements ServletContextListener
{
	private static final String MONGO_URI = "mongodb://ds033639.mongolab.com:33639/hunahview";
	
	public static final String EVENT_REGISTRY_KEY = "net.tmclean.hunahview.server.event.registry";
	
	public static final String CONFIG_FILE_KEY = "net.tmclean.hunahview.server.app.config";
	
	public static final String DB_KEY = "net.tmclean.hunahview.server.db";

	private Mongo mongo = null;
	
	@SuppressWarnings("deprecation")
	@Override
	public void contextInitialized( ServletContextEvent event ) 
	{
		try 
		{
			mongo = new Mongo( new MongoURI( MONGO_URI ) );
			DB mongoDB = mongo.getDB( "hunahview" );
			mongoDB.authenticate( System.getProperty( "mongoUser" ), System.getProperty( "mongoPass" ).toCharArray() );
			
			event.getServletContext().setAttribute( DB_KEY, mongoDB );
		}
		catch( UnknownHostException e ) 
		{
			e.printStackTrace();
		}
	}
	
	@Override
	public void contextDestroyed( ServletContextEvent event )  
	{
		mongo.close();
	}
}
