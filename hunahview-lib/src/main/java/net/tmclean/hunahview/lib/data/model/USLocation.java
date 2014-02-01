package net.tmclean.hunahview.lib.data.model;

import com.google.api.client.repackaged.com.google.common.base.Objects;
import com.google.common.base.Preconditions;

public class USLocation extends Location 
{
	private String city = null;
	private String state = null;
	
	public USLocation() 
	{
		this.setCountry( "USA" );
	}
	
	public USLocation( USLocation loc )
	{
		super( loc );
		
		this.setCountry( "USA" );
		this.city = loc.getCity();
		this.state = loc.getState();
	}

	public String getCity() { return city; }
	public void setCity(String city) { this.city = city; }
	
	public String getState() { return state; }
	public void setState(String state) { this.state = state; }
	
	@Override
	public boolean match( String str ) 
	{
		Preconditions.checkNotNull( str );
		
		return super.match( str ) || 
			   str.equalsIgnoreCase( city ) || 
			   str.equalsIgnoreCase( state );
	}
	
	@Override
	public boolean equals( Object obj ) 
	{
		if( obj == null ) return false;
		if( obj.getClass() != this.getClass() ) return false;
		if( !super.equals( obj ) ) return false;
		
		USLocation other = (USLocation)obj;
		
		return Objects.equal( this.city, other.city ) && 
			   Objects.equal( this.state, other.state ); 
	}
}
