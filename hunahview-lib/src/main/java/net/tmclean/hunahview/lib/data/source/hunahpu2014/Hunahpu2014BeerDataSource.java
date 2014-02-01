package net.tmclean.hunahview.lib.data.source.hunahpu2014;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.security.GeneralSecurityException;
import java.util.List;

import net.tmclean.hunahview.lib.data.model.Beer;
import net.tmclean.hunahview.lib.data.source.BeerDataSource;
import net.tmclean.hunahview.lib.data.source.BeerDataSourceException;
import net.tmclean.hunahview.lib.data.source.google.spreadsheets.GoogleSpreadsheetsExporter;

public class Hunahpu2014BeerDataSource extends GoogleSpreadsheetsExporter implements BeerDataSource
{
	private static final String HUNAHPU_TAP_LIST_ID = "0Auhv_-iTf6vpdFA2bUxNbkFjMi1Bem5raEd6Y1dYVGc";
	
	public Hunahpu2014BeerDataSource( File pkcs12File, String clientId, String appName ) throws GeneralSecurityException, IOException 
	{
		super( pkcs12File, clientId, appName );
	}

	@Override
	public List<Beer> read() throws BeerDataSourceException
	{
		try
		{
			InputStream is = this.exportToStream( HUNAHPU_TAP_LIST_ID, ExportFormats.CSV );
			return CSVBeerReader.readFromInputStream( is );
		}
		catch( Exception e )
		{
			throw new BeerDataSourceException( "Error beer for Hunahpu's Day 2014", e );
		}
	}
	
	@Override
	public void shutdown() {}
}
