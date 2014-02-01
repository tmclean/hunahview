package net.tmclean.hunahview.servlet;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import net.tmclean.hunahview.lib.event.EventRegistry;

import com.google.api.client.util.Strings;
import com.google.common.base.Preconditions;

public class HunahViewDataSourceServletListner implements ServletContextListener
{
	public static final String EVENT_REGISTRY_KEY = "net.tmclean.hunahview.server.event.registry";
	
	public static final String CONFIG_FILE_KEY = "net.tmclean.hunahview.server.app.config";

	@Override
	public void contextInitialized( ServletContextEvent event ) 
	{
		String configFilePath = (String)event.getServletContext().getInitParameter( CONFIG_FILE_KEY );
		
		if( !Strings.isNullOrEmpty( configFilePath ) )
		{
			try 
			{
				File configFile = new File( event.getServletContext().getRealPath( configFilePath ) );
				Preconditions.checkArgument( configFile.exists() && configFile.canRead() );
				
				Properties properties = new Properties();
				properties.load( new FileReader( configFile ) );
				
				String eventsStr = properties.getProperty( "net.tmclean.hunahview.events" );
				
				Preconditions.checkArgument( !Strings.isNullOrEmpty( eventsStr ) );
				
				EventRegistry registry = new EventRegistry( properties );
				
				event.getServletContext().setAttribute( EVENT_REGISTRY_KEY, registry );
			} 
			catch( IOException e ) 
			{
				e.printStackTrace();
			}
		}
	}
	
	@Override
	public void contextDestroyed( ServletContextEvent event )  
	{
		EventRegistry registry = (EventRegistry)event.getServletContext().getAttribute( EVENT_REGISTRY_KEY ); 
		
		if( registry != null )
			registry.shutdown();
	}
}
