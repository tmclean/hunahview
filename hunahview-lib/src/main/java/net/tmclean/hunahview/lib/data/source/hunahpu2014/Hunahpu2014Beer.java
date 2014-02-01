package net.tmclean.hunahview.lib.data.source.hunahpu2014;

import java.util.List;

import com.google.api.client.util.Strings;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;

import net.tmclean.hunahview.lib.data.model.Beer;

public class Hunahpu2014Beer extends Beer
{
	private String New = null;
	private String brewery = null;

	public Hunahpu2014Beer() {}
	
	public Hunahpu2014Beer( Hunahpu2014Beer beer )
	{
		Preconditions.checkNotNull( beer );
		
		this.New = beer.getNew();
		this.brewery = beer.getBrewery();
	}
	
	public boolean checkNew() { return !Strings.isNullOrEmpty( New ); }
	
	public String getNew() { return New; }
	public void setNew(String new1) { New = new1; }

	public String getBrewery() { return brewery; }
	public void setBrewery( String brewery ) 
	{ 
		List<String> breweriesTmp = Lists.newArrayListWithCapacity( 0 );
		
		if( !Strings.isNullOrEmpty( brewery ) && brewery.contains( "/" ) )
		{
			String[] breweriesTok = brewery.split( "/" );
			for( String breweryTok : breweriesTok )
				breweriesTmp.add( breweryTok );
		}
		else
		{
			breweriesTmp.add( brewery );
		}
		
		List<String> breweriesFinal = Lists.newArrayListWithCapacity( breweriesTmp.size() );
		for( String b : breweriesTmp )
		{
			if( b.equalsIgnoreCase( "CCB" ) )
				breweriesFinal.add( "Cigar City Brewing" );
			else
				breweriesFinal.add( b );
		}
		
		this.setBreweries( breweriesFinal );
		
		this.brewery = brewery; 
	}
}
