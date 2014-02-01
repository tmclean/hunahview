package net.tmclean.hunahview.lib;

import java.io.File;
import java.util.concurrent.TimeUnit;

import net.tmclean.hunahview.lib.data.source.hunahpu2014.Hunahpu2014BeerDataSource;
import net.tmclean.hunahview.lib.data.source.polling.PollingBeerDataSource;
import net.tmclean.hunahview.lib.data.storage.memory.InMemoryBeerStorage;

public class Driver 
{
	private static final String APP_NAME         = "Hunahview/0.0.1";
	private static final String CLIENT_ID        = "350171207459-2lnm99l9cpanbfhjh07glcuk41hgcnff@developer.gserviceaccount.com";
	private static final String PKCS12_FILE_PATH = "/home/tom/Development/google/1fed1d1cc20d2ea0fa42d06cc41eaa5f30416ca1-privatekey.p12";
	private static final File   PKCS12_FILE      = new File( PKCS12_FILE_PATH );
	
	public static void main( String[] args ) throws Throwable 
	{	
		Hunahpu2014BeerDataSource hunahpuSrc = new Hunahpu2014BeerDataSource( PKCS12_FILE, CLIENT_ID, APP_NAME );
		InMemoryBeerStorage storage          = new InMemoryBeerStorage();
		PollingBeerDataSource pollingSrc     = new PollingBeerDataSource( hunahpuSrc, storage, 10, TimeUnit.SECONDS );
		
		try
		{
			while( true )
			{
				System.out.println( "Found " + storage.getBeers().size() + " beers" );
				Thread.sleep( 10000 );
			}
		}
		finally
		{
			pollingSrc.shutdown();
		}
	}
}
