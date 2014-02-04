package net.tmclean.hunahview.rest.impl;

import java.util.List;

import javax.servlet.ServletContext;
import javax.ws.rs.core.Context;

import com.google.api.client.util.Strings;
import com.google.common.collect.Lists;

import net.tmclean.hunahview.lib.data.model.Beer;
import net.tmclean.hunahview.lib.data.model.Brewery;
import net.tmclean.hunahview.lib.data.model.Location;
import net.tmclean.hunahview.lib.data.source.BeerDataFeed;
import net.tmclean.hunahview.lib.data.source.BeerDataSourceException;
import net.tmclean.hunahview.lib.event.EventRegistry;
import net.tmclean.hunahview.rest.api.HunahviewAPI;
import net.tmclean.hunahview.servlet.HunahViewDataSourceServletListner;

public class HunahviewAPIServerImpl implements HunahviewAPI 
{
	@Context ServletContext ctx;
	
	private EventRegistry getEventRegistry()
	{
		return (EventRegistry)ctx.getAttribute( HunahViewDataSourceServletListner.EVENT_REGISTRY_KEY );
	}
	
	@Override
	public List<String> getEvents() 
	{
		return getEventRegistry().getEventNames();
	}
	
	@Override
	public List<Location> getEventLocations( String eventName ) 
	{
		List<Location> locations = Lists.newArrayListWithCapacity( 0 );
		
		try 
		{
			BeerDataFeed source = getEventRegistry().getEvent( eventName ).getDataSource();
			List<Beer> beers = source.get();
			
			for( Beer beer : beers )
			{
				if( beer.getBreweries() != null )
				{
					for( Brewery brewery : beer.getBreweries() )
					{
						if( !locations.contains( brewery.getLocation() ) )
							locations.add( brewery.getLocation() );
					}
				}
			}
		}
		catch( BeerDataSourceException e ) 
		{
			e.printStackTrace();
		}
		
		return locations;
	}
	
	@Override
	public List<Beer> getEventBeersByLocation( String eventName, String location ) 
	{
		List<Beer> beers = Lists.newArrayList();
		
		try 
		{
			BeerDataFeed source = getEventRegistry().getEvent( eventName ).getDataSource();
			List<Beer> sourceBeers = source.get();
			
			for( Beer beer : sourceBeers )
			{
				if( beer.getBreweries() != null )
				{
					for( Brewery brewery : beer.getBreweries() )
					{
						if( brewery.getLocation() != null && brewery.getLocation().match( location ) )
						{
							beers.add( beer );
							break;
						}
					}
				}
			}
		}
		catch( BeerDataSourceException e ) 
		{
			e.printStackTrace();
		}
		
		return beers;
	}

	@Override
	public List<Beer> getEventBeers( String eventName, String brewery ) 
	{
		List<Beer> beers = Lists.newArrayList();
		
		try 
		{
			BeerDataFeed source = getEventRegistry().getEvent( eventName ).getDataSource();
			List<Beer> sourceBeers = source.get();
			
			if( !Strings.isNullOrEmpty( brewery ) )
			{
				List<Beer> filteredBeers = Lists.newArrayList();
				
				for( Beer beer : sourceBeers )
				{
					if( beer.getBreweries() != null )
					{
						for( Brewery b : beer.getBreweries() )
						{
							if( b.getName().equalsIgnoreCase( brewery ) )
							{
								filteredBeers.add( beer );
								break;
							}
						}
					}
				}
				
				beers = filteredBeers;
			}
			else
			{
				beers = sourceBeers;
			}
		}
		catch( BeerDataSourceException e ) 
		{
			e.printStackTrace();
		}
		
		return beers;
	}

	@Override
	public List<Brewery> getEventBreweries( String eventName )
	{
		List<Brewery> breweries = Lists.newArrayList();
		
		try
		{
			BeerDataFeed source = getEventRegistry().getEvent( eventName ).getDataSource();
			List<Beer> beers = source.get();

			for( Beer beer : beers )
			{
				if( beer.getBreweries() != null )
				{
					for( Brewery b : beer.getBreweries() )
					{
						if( !breweries.contains( b ) )
							breweries.add( b );
					}
				}
			}
		}
		catch( BeerDataSourceException e ) 
		{
			e.printStackTrace();
		}
		
		return breweries;
	}
}
