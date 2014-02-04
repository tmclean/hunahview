package net.tmclean.hunahview.lib.data.storage.memory;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;

import net.tmclean.hunahview.lib.config.ContextAwareProperties;
import net.tmclean.hunahview.lib.data.model.Beer;
import net.tmclean.hunahview.lib.data.storage.BeerStorage;
import net.tmclean.hunahview.lib.data.storage.BeerStorageException;

public class InMemoryBeerStorage implements BeerStorage
{
	private Object lock = new Object();
	private List<Beer> cache = Lists.newArrayListWithCapacity( 0 );
	
	@Override
	public void configure( ContextAwareProperties properties ) throws BeerStorageException {}
	
	private Logger logger = LoggerFactory.getLogger( this.getClass() );
	
	@Override
	public List<Beer> getBeers() 
	{
		logger.debug( "Getting beers" );
		synchronized( lock ) 
		{
			ImmutableList.Builder<Beer> safeBeersBuilder = ImmutableList.builder();
			
			for( Beer beer : cache )
				safeBeersBuilder.add( new Beer( beer ) );
			
			return safeBeersBuilder.build();
		}
	}

	@Override
	public void pushNewBeerList( List<Beer> beers ) 
	{	
		logger.debug( "Pushing new beer list" );
		
		synchronized( lock ) 
		{
			this.cache = Lists.newArrayListWithCapacity( beers.size() );
			this.cache.addAll( beers );
		}
	}
}
