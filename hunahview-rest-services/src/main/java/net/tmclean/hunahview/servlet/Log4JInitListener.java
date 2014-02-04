package net.tmclean.hunahview.servlet;

import java.io.File;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.PropertyConfigurator;

public class Log4JInitListener implements ServletContextListener 
{
	@Override
	public void contextInitialized( ServletContextEvent event )
	{
		String log4jLocation = event.getServletContext().getInitParameter("log4j-properties-location");

		ServletContext sc = event.getServletContext();

		if( log4jLocation == null ) 
		{
			System.err.println("*** No log4j-properties-location init param, so initializing log4j with BasicConfigurator");
			BasicConfigurator.configure();
		} 
		else 
		{
			String webAppPath = sc.getRealPath( "/" );
			String log4jProp = webAppPath + log4jLocation;
			
			File log4jConf = new File( log4jProp );
			
			if( log4jConf.exists() ) 
			{
				PropertyConfigurator.configure( log4jProp );
			} 
			else 
			{
				BasicConfigurator.configure();
			}
		}
	}
	
	@Override
	public void contextDestroyed( ServletContextEvent event )
	{
		
	}
}
