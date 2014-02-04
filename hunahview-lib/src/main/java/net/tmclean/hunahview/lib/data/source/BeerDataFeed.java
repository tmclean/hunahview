package net.tmclean.hunahview.lib.data.source;

import java.util.List;

import net.tmclean.hunahview.lib.config.ContextAwareProperties;
import net.tmclean.hunahview.lib.data.model.Beer;

public interface BeerDataFeed 
{
	public static final String BEER_SOURCE_PREFIX = "data.source";
	
	public void configure( ContextAwareProperties properties ) throws BeerDataSourceException;
	public List<Beer> get() throws BeerDataSourceException;
	public void shutdown();
}
