package net.tmclean.hunahview.lib.data.model;

import java.util.List;

import com.google.common.base.Preconditions;

public class Beer 
{	
	private String beer = null;
	private List<String> breweries = null;
	private String beerNotes = null;
	private String breweryLocation = null;
	
	public Beer() {}
	
	public Beer( Beer beer )
	{
		Preconditions.checkNotNull( beer );
		
		this.beer = beer.getBeer();
		this.breweries = beer.getBreweries();
		this.beerNotes = beer.getBeerNotes();
		this.breweryLocation = beer.getBreweryLocation();
	}


	public String getBeer() { return beer; }
	public void setBeer(String beer) { this.beer = beer; }
	
	public List<String> getBreweries() { return breweries; }
	public void setBreweries(List<String> breweries) { this.breweries = breweries; }

	public String getBeerNotes() { return beerNotes; }
	public void setBeerNotes(String beerNotes) { this.beerNotes = beerNotes; }

	public String getBreweryLocation() { return breweryLocation; }
	public void setBreweryLocation(String breweryLocation) { this.breweryLocation = breweryLocation; }
}
