package net.tmclean.hunahview.lib.data.source.google.spreadsheets;

public class GoogleException extends Exception 
{
	private static final long serialVersionUID = -5176153769361706436L;

	public GoogleException() {}
	
	public GoogleException( Throwable t )
	{
		super( t );
	}
	
	public GoogleException( String message )
	{
		super( message );
	}
	
	public GoogleException( String message, Throwable t )
	{
		super( message, t );
	}
}
