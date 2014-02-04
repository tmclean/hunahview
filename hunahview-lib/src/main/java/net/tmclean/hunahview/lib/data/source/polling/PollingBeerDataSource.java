package net.tmclean.hunahview.lib.data.source.polling;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import com.google.api.client.util.Strings;
import com.google.common.base.Preconditions;

import net.tmclean.hunahview.lib.config.ContextAwareProperties;
import net.tmclean.hunahview.lib.data.model.Beer;
import net.tmclean.hunahview.lib.data.source.BeerDataSource;
import net.tmclean.hunahview.lib.data.source.BeerDataSourceException;
import net.tmclean.hunahview.lib.data.storage.BeerStorage;
import net.tmclean.hunahview.lib.data.storage.BeerStorageException;

public class PollingBeerDataSource implements BeerDataSource
{
	public static final String BEER_POLLING_SOURCE_PREFIX = BEER_SOURCE_PREFIX + ".polling";
	
	public static final String DATA_SOURCE_CLASS  = BEER_POLLING_SOURCE_PREFIX + ".source.class";
	public static final String DATA_STORAGE_CLASS = BEER_POLLING_SOURCE_PREFIX + ".storage.class";
	public static final String DATA_POLL_DELAY    = BEER_POLLING_SOURCE_PREFIX + ".delay";
	
	private Timer timer = new Timer();
	private BeerDataSource source = null;
	private BeerStorage storage = null;

	@Override
	public void configure( ContextAwareProperties properties ) throws BeerDataSourceException 
	{
		String sourceClassName = properties.getProperty( DATA_SOURCE_CLASS );
		String storageClassName = properties.getProperty( DATA_STORAGE_CLASS );
		String delayString = properties.getProperty( DATA_POLL_DELAY );

		Preconditions.checkArgument( !Strings.isNullOrEmpty( sourceClassName ) );
		Preconditions.checkArgument( !Strings.isNullOrEmpty( storageClassName ) );
		Preconditions.checkArgument( !Strings.isNullOrEmpty( delayString ) );
		
		try
		{
			source = (BeerDataSource)Class.forName( sourceClassName ).newInstance();
			source.configure( properties );
			
			storage = (BeerStorage)Class.forName( storageClassName ).newInstance();
			storage.configure( properties );
			
			PollingBeerDataSourceTimerTask task = new PollingBeerDataSourceTimerTask( source, storage );
			timer.scheduleAtFixedRate( task, 0, Long.parseLong( delayString ) );
		} 
		catch( InstantiationException | IllegalAccessException | ClassNotFoundException | BeerStorageException e ) 
		{
			throw new BeerDataSourceException( "Error configuring " + this.getClass().getName(), e );
		}
	}
	
	@Override
	public List<Beer> get() throws BeerDataSourceException 
	{
		return storage.getBeers();
	}
	
	@Override
	public void shutdown() 
	{
		timer.cancel();
		source.shutdown();
	}
	
	public static class PollingBeerDataSourceTimerTask extends TimerTask
	{	
		private BeerDataSource source = null;
		private BeerStorage storage = null;
		private ClassLoader classloader = null;

		public PollingBeerDataSourceTimerTask( BeerDataSource source, BeerStorage storage )
		{
			this.classloader = this.getClass().getClassLoader();
			
			this.source = source;
			this.storage = storage;
		}
		
		@Override
		public void run() 
		{
			Thread.currentThread().setContextClassLoader( classloader );
			
			synchronized( BEER_SOURCE_PREFIX ) 
			{
				try 
				{
					List<Beer> newBeerList = source.get();
					storage.pushNewBeerList( newBeerList );
				}
				catch( BeerDataSourceException e ) 
				{
					e.printStackTrace();
				}
			}
		}
	}
}
