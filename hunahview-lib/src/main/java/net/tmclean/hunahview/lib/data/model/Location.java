package net.tmclean.hunahview.lib.data.model;

import com.google.common.base.Objects;
import com.google.common.base.Preconditions;

public abstract class Location 
{
	private String country = null;
	
	public Location() {}
	
	public Location( Location loc )
	{
		Preconditions.checkNotNull( loc );
		
		this.country = loc.getCountry();
	}
	
	
	public String getCountry() { return country; }
	public void setCountry(String country) { this.country = country; }
	
	public boolean match( String str )
	{
		return str.equalsIgnoreCase( country );
	}
	
	@Override
	public boolean equals( Object obj ) 
	{
		if( obj == null ) return false;
		if( obj.getClass() != this.getClass() ) return false;
		
		Location other = (Location)obj;
		
		return Objects.equal( this.country, other.country ); 
	}
}
