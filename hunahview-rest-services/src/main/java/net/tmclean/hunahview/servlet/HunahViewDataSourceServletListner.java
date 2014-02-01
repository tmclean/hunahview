package net.tmclean.hunahview.servlet;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import net.tmclean.hunahview.lib.data.source.BeerDataSource;
import net.tmclean.hunahview.lib.data.source.BeerDataSourceException;

import com.google.api.client.util.Strings;
import com.google.common.base.Preconditions;

public class HunahViewDataSourceServletListner implements ServletContextListener
{
	public static final String CONFIG_FILE_KEY = "net.tmclean.hunahview.server.app.config";
	private static final String DATA_SRC_CLASS_KEY = "net.tmclean.hunahview.server.source.class";
	public static final String DATA_SRC_KEY = "net.tmclean.hunahview.server.source";
	
	private BeerDataSource ds = null;

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
				
				ds = (BeerDataSource)Class.forName( properties.getProperty( DATA_SRC_CLASS_KEY ) ).newInstance();
				ds.configure( properties );
				
				event.getServletContext().setAttribute( DATA_SRC_KEY, ds );
			} 
			catch( InstantiationException | IllegalAccessException | ClassNotFoundException | IOException | BeerDataSourceException e ) 
			{
				e.printStackTrace();
			}
		}
	}
	
	@Override
	public void contextDestroyed( ServletContextEvent event ) 
	{
		if( ds != null )
			ds.shutdown();
	}
}
