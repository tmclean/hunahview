package net.tmclean.hunahview.lib.data.source.hunahpu2014;

import java.io.InputStream;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Preconditions;

import net.tmclean.hunahview.lib.config.ContextAwareProperties;
import net.tmclean.hunahview.lib.data.model.Beer;
import net.tmclean.hunahview.lib.data.source.BeerDataSource;
import net.tmclean.hunahview.lib.data.source.BeerDataSourceException;
import net.tmclean.hunahview.lib.data.source.google.spreadsheets.GoogleException;
import net.tmclean.hunahview.lib.data.source.google.spreadsheets.GoogleSpreadsheetsExporter;

public class Hunahpu2014BeerDataSource extends GoogleSpreadsheetsExporter implements BeerDataSource
{	
	private static final String HUNAHPU_TAP_LIST_ID = "0Auhv_-iTf6vpdFA2bUxNbkFjMi1Bem5raEd6Y1dYVGc";
	
	private Logger logger = LoggerFactory.getLogger( this.getClass() );
	
	@Override
	public void configure( ContextAwareProperties properties ) throws BeerDataSourceException 
	{
		logger.info( "Configuring" );
		
		Preconditions.checkNotNull( properties );
		
		try 
		{
			super.configure( properties );
		} 
		catch( GoogleException e )
		{
			throw new BeerDataSourceException( "Error configuring Hunapu's Day 2014 Beer Data Source", e );
		}
	}
	
	@Override
	public List<Beer> get() throws BeerDataSourceException
	{
		logger.info( "Getting tap list" );
		
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
