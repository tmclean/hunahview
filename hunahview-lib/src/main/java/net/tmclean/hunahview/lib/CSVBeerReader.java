package net.tmclean.hunahview.lib;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import org.supercsv.io.CsvBeanReader;
import org.supercsv.prefs.CsvPreference;

import com.google.common.collect.Lists;

public class CSVBeerReader 
{
	public static final List<Beer> readFromInputStream( InputStream is ) throws IOException
	{
		CsvPreference preferences = CsvPreference.STANDARD_PREFERENCE;
		
		CSVHeaderSkipper tokenizer = new CSVHeaderSkipper( new InputStreamReader( is ), preferences );
		
		@SuppressWarnings("resource")
		CsvBeanReader reader = new CsvBeanReader( tokenizer, preferences );

		String[] header = null;
		do
		{
			header = reader.getHeader( false );
		}
		while( header == null );
		
		List<Beer> beers = Lists.newArrayList();
		
		Beer beer = null;
		do
		{
			beer = reader.read( Beer.class, "New", "Brewery", "Beer", "BeerNotes", "BreweryLocation" );
			
			if( beer != null )
				beers.add( beer );
		}
		while( beer != null );
		
		return beers;
	}
}
