package net.tmclean.hunahview.lib;

import com.google.api.client.util.Strings;

public class Beer 
{	
	private String New = null;
	private String beer = null;
	private String brewery = null;
	private String beerNotes = null;
	private String breweryLocation = null;
	
	public boolean isNew() { return !Strings.isNullOrEmpty( New ); }
	
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
