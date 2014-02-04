package net.tmclean.hunahview.lib.event;

import net.tmclean.hunahview.lib.config.ContextAwareProperties;
import net.tmclean.hunahview.lib.data.source.BeerDataFeed;
import net.tmclean.hunahview.lib.data.source.BeerDataSourceException;

public class Event 
{
	public static final String DATA_SRC_CLASS_KEY = "data.source.class";
	
	private BeerDataFeed ds = null;
	
	public Event( ContextAwareProperties properties )
	{
		try
		{
			String className = properties.getProperty( DATA_SRC_CLASS_KEY );
			this.ds = (BeerDataFeed)Class.forName( className ).newInstance();
			this.ds.configure( properties );
		}
		catch( BeerDataSourceException | InstantiationException | IllegalAccessException | ClassNotFoundException e ) 
		{
			e.printStackTrace();
		}
	}
	
	public BeerDataFeed getDataSource()
	{
		return this.ds;
	}
	
	public void shutdown()
	{
		this.ds.shutdown();
	}
}
