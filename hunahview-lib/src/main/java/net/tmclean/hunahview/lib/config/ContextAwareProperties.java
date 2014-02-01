package net.tmclean.hunahview.lib.config;

import java.util.Properties;

public class ContextAwareProperties
{
	private Properties properties = null;
	private String context = null;
	
	public ContextAwareProperties( Properties properties, String context )
	{
		this.properties = properties;
		this.context = context;
	}
	
	public String getProperty( String key ) 
	{
		key = buildKey( key );
		return properties.getProperty( key );
	}
	
	public String getProperty( String key, String defaultValue ) 
	{
		key = buildKey( key );
		return properties.getProperty( key, defaultValue );
	}
	
	private String buildKey( String key )
	{
		if( key.startsWith( "." ) )
			return context + key;
		
		return context + "." + key;
	}
}
