package net.tmclean.hunahview.lib.data.storage;

import java.util.List;

import net.tmclean.hunahview.lib.config.ContextAwareProperties;
import net.tmclean.hunahview.lib.data.model.Beer;

public interface BeerStorage 
{
	public static final String BEER_STORAGE_PREFIX = "data.storage";

	public void configure( ContextAwareProperties properties ) throws BeerStorageException;
	public List<Beer> getBeers();
	public void pushNewBeerList( List<Beer> beers );
}
