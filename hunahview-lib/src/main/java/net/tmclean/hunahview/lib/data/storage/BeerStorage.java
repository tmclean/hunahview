package net.tmclean.hunahview.lib.data.storage;

import java.util.List;
import java.util.Properties;

import net.tmclean.hunahview.lib.data.model.Beer;

public interface BeerStorage 
{
	public static final String BEER_STORAGE_PREFIX = "net.tmclean.hunahview.lib.data.storage.";

	public void configure( Properties properties ) throws BeerStorageException;
	public List<Beer> getBeers();
	public void pushNewBeerList( List<Beer> beers );
}
