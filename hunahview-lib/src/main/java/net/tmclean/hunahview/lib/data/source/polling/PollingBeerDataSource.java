package net.tmclean.hunahview.lib.data.source.polling;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import com.google.common.base.Preconditions;

import net.tmclean.hunahview.lib.data.model.Beer;
import net.tmclean.hunahview.lib.data.source.BeerDataSource;
import net.tmclean.hunahview.lib.data.source.BeerDataSourceException;
import net.tmclean.hunahview.lib.data.storage.BeerStorage;

public class PollingBeerDataSource implements BeerDataSource
{
	private Timer timer = new Timer();
	private BeerDataSource source = null;
	
	public PollingBeerDataSource( BeerDataSource source, BeerStorage storage, long delay, TimeUnit unit )
	{
		Preconditions.checkNotNull( source );
		Preconditions.checkNotNull( storage );
		Preconditions.checkNotNull( unit );
		Preconditions.checkArgument( delay > 0 );
		
		PollingBeerDataSourceTimerTask task = new PollingBeerDataSourceTimerTask( source, storage );
		timer.schedule( task, unit.toMillis( delay ) );
	}
	
	@Override
	public List<Beer> read() throws BeerDataSourceException 
	{
		return source.read();
	}
	
	@Override
	public void shutdown() 
	{
		timer.cancel();
	}
	
	private static class PollingBeerDataSourceTimerTask extends TimerTask
	{
		private BeerDataSource source = null;
		private BeerStorage storage = null;
		
		public PollingBeerDataSourceTimerTask( BeerDataSource source, BeerStorage storage )
		{
			Preconditions.checkNotNull( source );
			Preconditions.checkNotNull( storage );
			
			this.source = source;
			this.storage = storage;
		}
		
		@Override
		public void run() 
		{
			try 
			{
				System.out.println( "Reading beer" );
				List<Beer> newBeerList = source.read();
				System.out.println( "Read " + newBeerList.size() + " beers" );
				storage.pushNewBeerList( newBeerList );
			}
			catch( BeerDataSourceException e ) 
			{
				e.printStackTrace();
			}
		}
	}
}
