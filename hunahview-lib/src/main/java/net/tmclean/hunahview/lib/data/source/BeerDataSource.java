package net.tmclean.hunahview.lib.data.source;

import java.util.List;
import java.util.Properties;

import net.tmclean.hunahview.lib.data.model.Beer;

public interface BeerDataSource 
{
	public static final String BEER_SOURCE_PREFIX = "net.tmclean.hunahview.lib.data.source.";
	
	public void configure( Properties properties ) throws BeerDataSourceException;
	public List<Beer> get() throws BeerDataSourceException;
	public void shutdown();
}
