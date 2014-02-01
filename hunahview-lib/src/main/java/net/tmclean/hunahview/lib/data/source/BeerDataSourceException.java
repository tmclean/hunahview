package net.tmclean.hunahview.lib.data.source;

public class BeerDataSourceException extends Exception 
{
	private static final long serialVersionUID = 8875337963350606124L;

	public BeerDataSourceException() {}
	
	public BeerDataSourceException( Throwable t )
	{
		super( t );
	}
	
	public BeerDataSourceException( String message )
	{
		super( message );
	}
	
	public BeerDataSourceException( String message, Throwable t )
	{
		super( message, t );
	}
}
