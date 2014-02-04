package net.tmclean.hunahview.lib.data.source.hunahpu2014;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import net.tmclean.hunahview.lib.data.model.Beer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.supercsv.io.CsvBeanReader;
import org.supercsv.prefs.CsvPreference;

import com.google.common.collect.Lists;

public final class CSVBeerReader 
{
	private CSVBeerReader(){}
	
	private static final Logger logger = LoggerFactory.getLogger( CSVBeerReader.class );
	
	public static final List<Beer> readFromInputStream( InputStream is ) throws IOException
	{
		logger.debug( "Parsing Hunahpu 2014 CSV data" );
		
		CsvPreference preferences = CsvPreference.STANDARD_PREFERENCE;
		
		CSVHeaderSkipper tokenizer = new CSVHeaderSkipper( new InputStreamReader( is ), preferences );
		
		@SuppressWarnings("resource")
		CsvBeanReader reader = new CsvBeanReader( tokenizer, preferences );

		logger.debug( "Consuming headers" );
		
		String[] header = null;
		do
		{
			header = reader.getHeader( false );
		}
		while( header == null );
		
		List<Beer> beers = Lists.newArrayList();
		
		Hunahpu2014Beer beer = null;
		do
		{
			beer = reader.read( Hunahpu2014Beer.class, "New", "Brewery", "Beer", "BeerNotes", "BreweryLocation" );

			if( beer != null )
			{
				logger.debug( "Read beer data {}", beer.toString() );
				beers.add( NormalizationUtils.noramlize( beer ) );
			}
		}
		while( beer != null );
		
		logger.debug( "Read {} beers", beers.size() );
		
		return beers;
	}
}
