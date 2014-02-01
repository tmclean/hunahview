package net.tmclean.hunahview.lib;

import java.util.List;
import java.util.Properties;

import net.tmclean.hunahview.lib.data.model.Beer;
import net.tmclean.hunahview.lib.data.source.hunahpu2014.Hunahpu2014BeerDataSource;
import net.tmclean.hunahview.lib.data.source.polling.PollingBeerDataSource;
import net.tmclean.hunahview.lib.data.storage.memory.InMemoryBeerStorage;
import net.tmclean.hunahview.lib.event.Event;
import net.tmclean.hunahview.lib.event.EventRegistry;

public class Driver 
{
	private static final String APP_NAME         = "Hunahview/0.0.1";
	private static final String CLIENT_ID        = "350171207459-2lnm99l9cpanbfhjh07glcuk41hgcnff@developer.gserviceaccount.com";
	private static final String PKCS12_FILE_PATH = "/home/tom/Development/google/1fed1d1cc20d2ea0fa42d06cc41eaa5f30416ca1-privatekey.p12";
	
	public static void main( String[] args ) throws Throwable 
	{	
		Properties properties = new Properties();
		properties.put( EventRegistry.EVENT_LIST_KEY, "hunahpu2014" );
		properties.put( "hunahpu2014." + Event.DATA_SRC_CLASS_KEY, PollingBeerDataSource.class.getName() );
		properties.put( "hunahpu2014." + PollingBeerDataSource.DATA_SOURCE_CLASS, Hunahpu2014BeerDataSource.class.getName() );
		properties.put( "hunahpu2014." + PollingBeerDataSource.DATA_STORAGE_CLASS, InMemoryBeerStorage.class.getName() );
		properties.put( "hunahpu2014." + PollingBeerDataSource.DATA_POLL_DELAY, "10000" );
		properties.put( "hunahpu2014." + Hunahpu2014BeerDataSource.GOOGLE_APP_NAME, APP_NAME );
		properties.put( "hunahpu2014." + Hunahpu2014BeerDataSource.GOOGLE_CERT_FILE, PKCS12_FILE_PATH );
		properties.put( "hunahpu2014." + Hunahpu2014BeerDataSource.GOOGLE_CLIENT_ID, CLIENT_ID );
		
		EventRegistry registry = new EventRegistry( properties );
		
		while( true )
		{
			List<Beer> beers = registry.getEvent( "hunahpu2014" ).getDataSource().get();
			System.out.println( "Found " + beers.size() + " beers" );
			Thread.sleep( 10000 );
		}
	}
}
