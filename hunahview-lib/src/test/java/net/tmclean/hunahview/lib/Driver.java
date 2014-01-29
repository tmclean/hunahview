package net.tmclean.hunahview.lib;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.security.GeneralSecurityException;
import java.util.List;

public class Driver 
{
	private static final String CLIENT_ID           = "350171207459-2lnm99l9cpanbfhjh07glcuk41hgcnff@developer.gserviceaccount.com";
	private static final String PKCS12_FILE_PATH    = "/home/tom/Development/google/1fed1d1cc20d2ea0fa42d06cc41eaa5f30416ca1-privatekey.p12";
	private static final File   PKCS12_FILE         = new File( PKCS12_FILE_PATH );
	private static final String HUNAHPU_TAP_LIST_ID = "0Auhv_-iTf6vpdFA2bUxNbkFjMi1Bem5raEd6Y1dYVGc";
	
	public static void main( String[] args ) throws IOException, GeneralSecurityException 
	{	
		GoogleSpreadsheetsExporter exporter = new GoogleSpreadsheetsExporter( PKCS12_FILE, CLIENT_ID );
		
		InputStream is = exporter.exportToStream( HUNAHPU_TAP_LIST_ID, ExportFormats.CSV );
		
		List<Beer> beers = CSVBeerReader.readFromInputStream( is );
		
		for( Beer b : beers )
			System.out.println( b.isNew() + "---" + b.getBrewery() + " --- " + b.getBeer() );
		
		System.out.println( "Found " + beers.size() + " beers" );
	}
}
