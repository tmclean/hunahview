package net.tmclean.hunahview.lib;

import java.util.List;
import java.util.Properties;

import net.tmclean.hunahview.lib.data.model.Beer;
import net.tmclean.hunahview.lib.data.source.BeerDataSource;
import net.tmclean.hunahview.lib.data.source.hunahpu2014.Hunahpu2014BeerDataSource;
import net.tmclean.hunahview.lib.data.source.polling.PollingBeerDataSource;
import net.tmclean.hunahview.lib.data.storage.memory.InMemoryBeerStorage;

public class Driver 
{
	private static final String APP_NAME         = "Hunahview/0.0.1";
	private static final String CLIENT_ID        = "350171207459-2lnm99l9cpanbfhjh07glcuk41hgcnff@developer.gserviceaccount.com";
	private static final String PKCS12_FILE_PATH = "/home/tom/Development/google/1fed1d1cc20d2ea0fa42d06cc41eaa5f30416ca1-privatekey.p12";
	
	public static void main( String[] args ) throws Throwable 
	{	
		Properties properties = new Properties();
		
		properties.put( PollingBeerDataSource.DATA_SOURCE_CLASS, Hunahpu2014BeerDataSource.class.getName() );
		properties.put( PollingBeerDataSource.DATA_STORAGE_CLASS, InMemoryBeerStorage.class.getName() );
		properties.put( PollingBeerDataSource.DATA_POLL_DELAY, "10000" );
		properties.put( Hunahpu2014BeerDataSource.GOOGLE_APP_NAME, APP_NAME );
		properties.put( Hunahpu2014BeerDataSource.GOOGLE_CERT_FILE, PKCS12_FILE_PATH );
		properties.put( Hunahpu2014BeerDataSource.GOOGLE_CLIENT_ID, CLIENT_ID );
		
		BeerDataSource source = new PollingBeerDataSource();
		source.configure( properties );
		
		try
		{
			while( true )
			{
				List<Beer> beers = source.get();
				System.out.println( "Found " + beers.size() + " beers" );
				Thread.sleep( 10000 );
			}
		}
		finally
		{
			source.shutdown();
		}
	}
}
