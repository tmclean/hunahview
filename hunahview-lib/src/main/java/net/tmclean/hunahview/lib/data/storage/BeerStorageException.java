package net.tmclean.hunahview.lib.data.storage;

public class BeerStorageException extends Exception
{
	private static final long serialVersionUID = 7415004995015288988L;

	public BeerStorageException() {}
	
	public BeerStorageException( Throwable t )
	{
		super( t );
	}
	
	public BeerStorageException( String message )
	{
		super( message );
	}
	
	public BeerStorageException( String message, Throwable t )
	{
		super( message, t );
	}
}
