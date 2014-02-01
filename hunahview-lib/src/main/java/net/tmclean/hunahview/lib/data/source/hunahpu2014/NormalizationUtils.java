package net.tmclean.hunahview.lib.data.source.hunahpu2014;

import java.util.List;

import com.google.api.client.util.Strings;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;

import net.tmclean.hunahview.lib.data.model.Beer;
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
		populateLocations( beer, targetBeer );
		
		return targetBeer;
	}
	
	private static void populateBreweries( Hunahpu2014Beer beer, Beer targetBeer )
	{
		targetBeer.setBreweries( applyBrewerySubstitutions( normalizeSlashDelimiter( beer.getBrewery() ) ) );
	}
	
	private static void populateLocations( Hunahpu2014Beer beer, Beer targetBeer )
	{
		List<String> locationStrs = normalizeSlashDelimiter( beer.getBreweryLocation() );
		
		List<Location> locations = Lists.newArrayListWithCapacity( locationStrs.size() );
		
		for( String locStr : locationStrs )
		{
			String[] toks = locStr.split( "," );
			
			if( toks.length == 2 )
			{
				USLocation location = new USLocation();
				location.setCity( toks[0].trim() );
				location.setState( toks[1].trim() );
				locations.add( location );
			}
			else if( toks.length > 0 )
			{
				NonUSLocation location = new NonUSLocation();
				location.setCountry( "Unknown" );
				location.setLocation( toks[0].trim() );
				locations.add( location );
			}
			
			targetBeer.setBreweryLocations( locations );
		}
	}

	private static List<String> normalizeSlashDelimiter( String str )
	{
		List<String> strList = Lists.newArrayListWithCapacity( 0 );
		
		if( Strings.isNullOrEmpty( str ) )
			return strList;
		
		if( !Strings.isNullOrEmpty( str ) && str.contains( "/" ) )
		{
			String[] toks = str.split( "/" );
			for( String tok : toks )
				strList.add( tok );
		}
		else
		{
			strList.add( str );
		}
		
		return strList;
	}
	
	private static List<String> applyBrewerySubstitutions( List<String> breweries )
	{
		List<String> strListFinal = Lists.newArrayListWithCapacity( breweries.size() );
		
		for( String s : breweries )
		{
			if( s.equalsIgnoreCase( "CCB" ) )
				strListFinal.add( "Cigar City Brewing" );
			else
				strListFinal.add( s );
		}
		
		return strListFinal;
	}
}
