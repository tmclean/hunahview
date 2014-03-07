package net.tmclean.hunahview.lib.data.model;

import com.google.common.base.Objects;
import com.google.common.base.Preconditions;

public class NonUSLocation extends Location 
{
	private String location = null;
	
	public NonUSLocation() {}
	
	public NonUSLocation( NonUSLocation loc )
	{
		super( loc );
		
		this.location = loc.getLocation();
	}

	public String getLocation() { return location; }
	public void setLocation(String location) { this.location = location; }

	@Override
	public boolean match( String str ) 
	{
		Preconditions.checkNotNull( str );
		
		return super.match( str ) || str.equalsIgnoreCase( location );
	}
	
	@Override
	public boolean equals( Object obj ) 
	{
		if( obj == null ) return false;
		if( obj.getClass() != this.getClass() ) return false;
		if( !super.equals( obj ) ) return false;
		
		NonUSLocation other = (NonUSLocation)obj;
		
		return Objects.equal( this.location, other.location ); 
	}
}
