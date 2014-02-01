package net.tmclean.hunahview.lib.data.model;

import com.google.api.client.repackaged.com.google.common.base.Objects;
import com.google.common.base.Preconditions;

public class Brewery 
{
	private String name = null;
	private Location location = null;
	
	public Brewery() {}
	
	public Brewery( Brewery brewery )
	{
		Preconditions.checkNotNull( brewery );
		
		name = brewery.getName();
		location = brewery.getLocation();
	}
	
	public String getName() { return name; }
	public void setName(String name) { this.name = name; }

	public Location getLocation() { return location; }
	public void setLocation(Location location) { this.location = location; }
	
	@Override
	public boolean equals( Object obj ) 
	{
		if( obj == null ) return false;
		if( obj.getClass() != this.getClass() ) return false;
		
		Brewery other = (Brewery)obj;
		
		return Objects.equal( this.name, other.name ) && 
			   Objects.equal( location, other.location ); 
	}
}
