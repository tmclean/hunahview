package net.tmclean.hunahview.lib.data.model;

import java.util.List;

import com.google.common.base.Preconditions;

public class Beer 
{	
	private String beer = null;
	private List<Brewery> breweries = null;
	private String beerNotes = null;
	
	public Beer() {}
	
	public Beer( Beer beer )
	{
		Preconditions.checkNotNull( beer );
		
		this.beer = beer.getBeer();
		this.breweries = beer.getBreweries();
		this.beerNotes = beer.getBeerNotes();
	}

	public String getBeer() { return beer; }
	public void setBeer(String beer) { this.beer = beer; }
	
	public List<Brewery> getBreweries() { return breweries; }
	public void setBreweries(List<Brewery> breweries) { this.breweries = breweries; }

	public String getBeerNotes() { return beerNotes; }
	public void setBeerNotes(String beerNotes) { this.beerNotes = beerNotes; }
}
