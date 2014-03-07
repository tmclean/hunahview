package net.tmclean.hunahview.lib.data.source.hunahpu2014;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.tmclean.hunahview.lib.config.ContextAwareProperties;
import net.tmclean.hunahview.lib.data.model.Beer;
import net.tmclean.hunahview.lib.data.source.BeerDataFeed;
import net.tmclean.hunahview.lib.data.source.BeerDataSourceException;

public class Hunahpu2014BeerDataSource implements BeerDataFeed
{	
	private Logger logger = LoggerFactory.getLogger( this.getClass() );
	
	@Override
	public void configure( ContextAwareProperties properties ) throws BeerDataSourceException 
	{
		logger.info( "Configuring" );
	}
	
	@Override
	public List<Beer> get() throws BeerDataSourceException
	{
		return new ArrayList<>();
	}
	
	@Override
	public void shutdown() {}
}
