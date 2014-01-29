package net.tmclean.hunahview.lib;

public enum ExportFormats 
{
	CSV( "csv" ),
	PDF( "pdf" );
	
	private String fmt = null;
	
	private ExportFormats( String fmt )
	{
		this.fmt = fmt;
	}
	
	@Override
	public String toString() { return fmt; }
}
