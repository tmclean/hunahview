package net.tmclean.hunahview.lib.data.source.hunahpu2014;

import com.google.api.client.util.Strings;
import com.google.common.base.Preconditions;

import net.tmclean.hunahview.lib.data.model.Beer;

public class Hunahpu2014Beer extends Beer
{
	private String New = null;
	private String brewery = null;
	private String breweryLocation = null;

	public Hunahpu2014Beer() {}
	
	public Hunahpu2014Beer( Hunahpu2014Beer beer )
	{
		Preconditions.checkNotNull( beer );
		
		this.New = beer.getNew();
		this.brewery = beer.getBrewery();
		this.breweryLocation = beer.getBreweryLocation();
	}
	
	public boolean checkNew() { return !Strings.isNullOrEmpty( New ); }
	
	public String getNew() { return New; }
	public void setNew(String new1) { New = new1; }

	public String getBrewery() { return brewery; }
	public void setBrewery( String brewery ) { this.brewery = brewery; }

	public String getBreweryLocation() { return breweryLocation; }
	public void setBreweryLocation(String breweryLocation) { this.breweryLocation = breweryLocation; }
}