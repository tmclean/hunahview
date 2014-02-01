package net.tmclean.hunahview.lib.data.storage.memory;

import java.util.List;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;

import net.tmclean.hunahview.lib.data.model.Beer;
import net.tmclean.hunahview.lib.data.storage.BeerStorage;

public class InMemoryBeerStorage implements BeerStorage
{
	private Object lock = new Object();
	private List<Beer> cache = Lists.newArrayListWithCapacity( 0 );
	
	@Override
	public List<Beer> getBeers() 
	{
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
		synchronized( lock ) 
		{
			this.cache = Lists.newArrayListWithCapacity( beers.size() );
			this.cache.addAll( beers );
		}
	}
}
