package net.tmclean.hunahview.rest.impl;

import java.util.List;

import com.google.api.client.util.Strings;
import com.google.common.collect.Lists;

import net.tmclean.hunahview.lib.data.model.Beer;
import net.tmclean.hunahview.lib.data.model.Event;
import net.tmclean.hunahview.rest.api.HunahviewAPI;

public class HunahviewAPIServerImpl implements HunahviewAPI 
{
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
		List<Beer> beers = Lists.newArrayListWithCapacity( 1 );
		beers.add( new Beer() );
		
		if( Strings.isNullOrEmpty( brewery ) )
			beers.add( new Beer() );
		
		return beers;
	}

	@Override
	public List<String> getEventBreweries( String eventName )
	{
		List<String> breweries = Lists.newArrayListWithCapacity( 1 );
		breweries.add( "Blah" );
		return breweries;
	}
}
