package net.tmclean.hunahview.rest.impl;

import java.util.List;

import javax.servlet.ServletContext;
import javax.ws.rs.core.Context;

import org.mongodb.morphia.Morphia;

import com.google.api.client.util.Strings;
import com.google.common.collect.Lists;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;

import net.tmclean.hunahview.lib.data.model.Beer;
import net.tmclean.hunahview.lib.data.model.BeerDirectory;
import net.tmclean.hunahview.lib.data.model.Brewery;
import net.tmclean.hunahview.lib.data.model.Checkin;
import net.tmclean.hunahview.lib.data.model.CurrentBeerDirectory;
import net.tmclean.hunahview.lib.data.model.Location;
import net.tmclean.hunahview.rest.api.HunahviewAPI;
import net.tmclean.hunahview.servlet.HunahViewDataSourceServletListner;

public class HunahviewAPIServerImpl implements HunahviewAPI 
{
	@Context ServletContext ctx;
	
	private List<Beer> getEventBeers( String eventId )
	{
		DB mongoDB = (DB)ctx.getAttribute( HunahViewDataSourceServletListner.DB_KEY );
		Morphia morphia = new Morphia();
		morphia.map( BeerDirectory.class );
		
		DBCollection directories = mongoDB.getCollection( "hunahview_directories" );
		
		BasicDBObject findObj = new BasicDBObject( "dirId", eventId );
		DBObject dirObj = directories.findOne( findObj );
		
		BeerDirectory currentDir = morphia.fromDBObject( BeerDirectory.class, dirObj );
		return currentDir.getBeers();
	}
	
	@Override
	public List<String> getEvents() 
	{
		DB mongoDB = (DB)ctx.getAttribute( HunahViewDataSourceServletListner.DB_KEY );
		Morphia morphia = new Morphia();
		morphia.map( CurrentBeerDirectory.class );
		
		DBCollection index = mongoDB.getCollection( "hunahview_index" );
		
		DBObject obj = index.findOne();
		
		CurrentBeerDirectory dir = morphia.fromDBObject( CurrentBeerDirectory.class, obj );
		
		List<String> ids = Lists.newArrayListWithCapacity( 1 );
		ids.add( dir.getDirId() );
		return ids;
	}
	
	@Override
	public List<Location> getEventLocations( String eventName ) 
	{
		List<Location> locations = Lists.newArrayListWithCapacity( 0 );
		
		List<Beer> beers = getEventBeers( eventName );
		
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
		
		return locations;
	}
	
	@Override
	public List<Beer> getEventBeersByLocation( String eventName, String location ) 
	{
		List<Beer> beers = Lists.newArrayList();
		
		List<Beer> sourceBeers = getEventBeers( eventName );
		
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
		
		return beers;
	}

	@Override
	public List<Beer> getEventBeers( String eventName, String brewery ) 
	{
		List<Beer> beers = Lists.newArrayList();
		
		List<Beer> sourceBeers = getEventBeers( eventName );
		
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
		
		return beers;
	}

	@Override
	public List<Brewery> getEventBreweries( String eventName )
	{
		List<Brewery> breweries = Lists.newArrayList();
		
		List<Beer> sourceBeers = getEventBeers( eventName );

		for( Beer beer : sourceBeers )
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
		
		return breweries;
	}
	
	@Override
	public Checkin checkin( String eventName, String beerId, String username ) 
	{
		List<Checkin> myCheckins = myCheckins( eventName, username );
		
		for( Checkin myCheckin : myCheckins )
		{
			if( myCheckin.getBeerId().equals( beerId ) )
			{
				return myCheckin;
			}
		}
		
		DB mongoDB = (DB)ctx.getAttribute( HunahViewDataSourceServletListner.DB_KEY );
		DBCollection hunahviewCheckins = mongoDB.getCollection( "hunahview_checkins" );
		
		Morphia morphia = new Morphia();
		morphia.map( Checkin.class );
		
		Checkin checkin = new Checkin();
		checkin.setEventId( eventName );
		checkin.setBeerId( beerId );
		checkin.setUsername( username );
		
		DBObject obj = morphia.toDBObject( checkin );
		
		hunahviewCheckins.insert( obj );
		
		return checkin;
	}
	
	@Override
	public List<Checkin> myCheckins( String eventName, String username ) 
	{
		DB mongoDB = (DB)ctx.getAttribute( HunahViewDataSourceServletListner.DB_KEY );
		DBCollection hunahviewCheckins = mongoDB.getCollection( "hunahview_checkins" );
		
		Morphia morphia = new Morphia();
		morphia.map( Checkin.class );
		
		BasicDBObject obj = new BasicDBObject( "eventId", eventName );
		obj.put( "username", username );
		DBCursor cursor = hunahviewCheckins.find( obj );
		
		List<Checkin> checkins = Lists.newArrayListWithCapacity( 0 );
		while( cursor.hasNext() )
		{
			DBObject checkinObj = cursor.next();
			Checkin checkin = morphia.fromDBObject( Checkin.class, checkinObj );
			checkins.add( checkin );
		}
		
		return checkins;
	}
}
