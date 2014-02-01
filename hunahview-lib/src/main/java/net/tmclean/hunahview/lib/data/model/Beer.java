package net.tmclean.hunahview.lib.data.model;

import com.google.api.client.util.Strings;
import com.google.common.base.Preconditions;

public class Beer 
{	
	private String New = null;
	private String beer = null;
	private String brewery = null;
	private String beerNotes = null;
	private String breweryLocation = null;
	
	public Beer() {}
	
	public Beer( Beer beer )
	{
		Preconditions.checkNotNull( beer );
		
		this.New = beer.getNew();
		this.beer = beer.getBeer();
		this.brewery = beer.getBrewery();
		this.beerNotes = beer.getBeerNotes();
		this.breweryLocation = beer.getBreweryLocation();
	}

	public boolean checkNew() { return !Strings.isNullOrEmpty( New ); }
	
	public String getNew() { return New; }
	public void setNew(String new1) { New = new1; }

	public String getBeer() { return beer; }
	public void setBeer(String beer) { this.beer = beer; }

	public String getBrewery() { return brewery; }
	public void setBrewery(String brewery) { this.brewery = brewery; }
	
	public String getBeerNotes() { return beerNotes; }
	public void setBeerNotes(String beerNotes) { this.beerNotes = beerNotes; }

	public String getBreweryLocation() { return breweryLocation; }
	public void setBreweryLocation(String breweryLocation) { this.breweryLocation = breweryLocation; }
}
