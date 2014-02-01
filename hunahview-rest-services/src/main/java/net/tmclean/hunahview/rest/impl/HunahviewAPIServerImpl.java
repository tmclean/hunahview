package net.tmclean.hunahview.rest.impl;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;

import com.google.api.client.util.Strings;
import com.google.common.collect.Lists;

import net.tmclean.hunahview.lib.data.model.Beer;
import net.tmclean.hunahview.lib.data.model.Event;
import net.tmclean.hunahview.lib.data.source.BeerDataSource;
import net.tmclean.hunahview.lib.data.source.BeerDataSourceException;
import net.tmclean.hunahview.rest.api.HunahviewAPI;
import net.tmclean.hunahview.servlet.HunahViewDataSourceServletListner;

public class HunahviewAPIServerImpl implements HunahviewAPI 
{
	@Context HttpServletRequest httpRequest;
	
	@Override
	public List<Event> getEvents() 
	{
		List<Event> events = Lists.newArrayListWithCapacity( 1 );
		events.add( new Event() );
		return events;
	}

	@Override
	public List<Beer> getEventTaps( String eventName, String brewery ) 
	{
		try 
		{
			BeerDataSource source = (BeerDataSource)(httpRequest.getSession().getServletContext().getAttribute( HunahViewDataSourceServletListner.DATA_SRC_KEY ));
			List<Beer> beers = source.get();
			
			if( !Strings.isNullOrEmpty( brewery ) )
			{
				List<Beer> filteredBeers = Lists.newArrayList();
				
				for( Beer beer : beers )
				{
					if( beer.getBrewery() != null && beer.getBrewery().equalsIgnoreCase( brewery ) )
					{
						System.out.println( "Matched beer" );
						filteredBeers.add( beer );
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
			List<String> breweries = Lists.newArrayList();
			BeerDataSource source = (BeerDataSource)(httpRequest.getSession().getServletContext().getAttribute( HunahViewDataSourceServletListner.DATA_SRC_KEY ));
			List<Beer> beers = source.get();
			
			for( Beer beer : beers )
			{
				if( !breweries.contains( beer.getBrewery() ) )
					breweries.add( beer.getBrewery() );
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
