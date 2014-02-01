package net.tmclean.hunahview.lib.data.storage;

import java.util.List;

import net.tmclean.hunahview.lib.data.model.Beer;

public interface BeerStorage 
{
	public List<Beer> getBeers();
	public void pushNewBeerList( List<Beer> beers );
}
