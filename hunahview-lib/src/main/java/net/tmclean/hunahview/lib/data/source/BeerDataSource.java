package net.tmclean.hunahview.lib.data.source;

import java.util.List;

import net.tmclean.hunahview.lib.data.model.Beer;

public interface BeerDataSource 
{
	public List<Beer> read() throws BeerDataSourceException;
	public void shutdown();
}
