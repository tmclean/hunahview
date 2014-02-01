package net.tmclean.hunahview.rest.impl;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;

import com.google.api.client.util.Strings;
import com.google.common.collect.Lists;

import net.tmclean.hunahview.lib.data.model.Beer;
import net.tmclean.hunahview.lib.data.model.Location;
import net.tmclean.hunahview.lib.data.source.BeerDataSource;
import net.tmclean.hunahview.lib.data.source.BeerDataSourceException;
import net.tmclean.hunahview.lib.event.EventRegistry;
import net.tmclean.hunahview.rest.api.HunahviewAPI;
import net.tmclean.hunahview.servlet.HunahViewDataSourceServletListner;

public class HunahviewAPIServerImpl implements HunahviewAPI 
{
	@Context HttpServletRequest httpRequest;
	
	private EventRegistry getEventRegistry()
	{
		return (EventRegistry)httpRequest.getSession().getServletContext().getAttribute( HunahViewDataSourceServletListner.EVENT_REGISTRY_KEY );
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
			BeerDataSource source = getEventRegistry().getEvent( eventName ).getDataSource();
			List<Beer> beers = source.get();
			
			for( Beer beer : beers )
			{
				if( beer.getBreweryLocations() != null )
				{
					for( Location location : beer.getBreweryLocations() )
					{
						if( !locations.contains( location ) )
							locations.add( location );
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
	public List<Beer> getEventTaps( String eventName, String brewery ) 
	{
		try 
		{
			BeerDataSource source = getEventRegistry().getEvent( eventName ).getDataSource();
			List<Beer> beers = source.get();
			
			if( !Strings.isNullOrEmpty( brewery ) )
			{
				List<Beer> filteredBeers = Lists.newArrayList();
				
				for( Beer beer : beers )
				{
					if( beer.getBreweries() != null )
					{
						for( String b : beer.getBreweries() )
						{
							if( b.equalsIgnoreCase( brewery ) )
							{
								System.out.println( "Matched beer" );
								filteredBeers.add( beer );
							}
						}
					}
				}
				
				return filteredBeers;
			}
			else
			{
				return beers;
			}
		}
		catch( BeerDataSourceException e ) 
		{
			e.printStackTrace();
		}
		
		return new ArrayList<Beer>();
	}

	@Override
	public List<String> getEventBreweries( String eventName )
	{
		try
		{
			BeerDataSource source = getEventRegistry().getEvent( eventName ).getDataSource();
			List<Beer> beers = source.get();

			List<String> breweries = Lists.newArrayList();
			for( Beer beer : beers )
			{
				if( beer.getBreweries() != null )
				{
					for( String b : beer.getBreweries() )
					{
						if( !breweries.contains( b ) )
							breweries.add( b );
					}
				}
			}
			
			return breweries;
		}
		catch( BeerDataSourceException e ) 
		{
			e.printStackTrace();
		}
		
		return new ArrayList<String>();
	}
}
