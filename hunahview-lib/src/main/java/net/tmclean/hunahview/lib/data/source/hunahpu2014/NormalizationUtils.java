package net.tmclean.hunahview.lib.data.source.hunahpu2014;

import java.util.List;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;

import net.tmclean.hunahview.lib.data.model.Beer;
import net.tmclean.hunahview.lib.data.model.Brewery;
import net.tmclean.hunahview.lib.data.model.Location;
import net.tmclean.hunahview.lib.data.model.NonUSLocation;
import net.tmclean.hunahview.lib.data.model.USLocation;

public class NormalizationUtils 
{
	public static final List<Beer> normalize( List<Hunahpu2014Beer> beers )
	{
		Preconditions.checkNotNull( beers );

		List<Beer> finalBeers = Lists.newArrayListWithCapacity( beers.size() );
		
		for( Hunahpu2014Beer beer : beers )
			finalBeers.add( noramlize( beer ) );
		
		return finalBeers;
	}
	
	public static final Beer noramlize( Hunahpu2014Beer beer )
	{
		Beer targetBeer = new Beer( beer );
		
		populateBreweries( beer, targetBeer );
		
		return targetBeer;
	}
	
	private static void populateBreweries( Hunahpu2014Beer beer, Beer targetBeer )
	{
		if( !Strings.isNullOrEmpty( beer.getBreweryLocation() ) && 
			!Strings.isNullOrEmpty( beer.getBrewery() ))
		{
			String[] breweyStrs = beer.getBrewery().split( "/" );
			String[] breweryLocStrs = beer.getBreweryLocation().split( "/" );
			
			if( breweyStrs.length != breweryLocStrs.length ) return;
			
			List<Brewery> breweries = Lists.newArrayListWithCapacity( breweyStrs.length );
			
			for( int i=0; i<breweyStrs.length; i++ )
			{
				String breweryStr = breweyStrs[i];
				String breweryLocStr = breweryLocStrs[i];
				
				String[] locationToks = breweryLocStr.split( "," );
				
				Location location = null;
				
				if( locationToks.length == 2 )
				{
					USLocation l = new USLocation();
					l.setCity( locationToks[0].trim() );
					l.setState( locationToks[1].trim() );
					location = l;
				}
				else if( locationToks.length > 0 )
				{
					NonUSLocation l = new NonUSLocation();
					l.setCountry( "Unknown" );
					l.setLocation( locationToks[0].trim() );
					location = l;
				}
				else
				{
					return;
				}
				
				Brewery brewery = new Brewery();
				brewery.setLocation( location );
				brewery.setName( filterBreweryName( breweryStr ) );
				
				breweries.add( brewery );
			}
			
			targetBeer.setBreweries( breweries );
		}
	}
	
	private static String filterBreweryName( String brewery )
	{
		return brewery.equalsIgnoreCase( "CCB" ) ? "Cigar City Brewing" : brewery;
	}
}
