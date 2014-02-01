package net.tmclean.hunahview.lib.data.source.google.spreadsheets;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.Set;

import net.tmclean.hunahview.lib.data.source.hunahpu2014.ExportFormats;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import com.google.common.io.ByteStreams;
import com.google.common.io.Closer;

public class GoogleSpreadsheetsExporter 
{
	private static final Set<String> CLIENT_SCOPES = Collections.singleton( DriveScopes.DRIVE_FILE );
	private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();

	private static final String GOOGLE_SPREADSHEETS_EXPORT_BASE_URL = "https://spreadsheets.google.com/feeds/download/spreadsheets/Export";
	private static final String SPREADSHEET_EXPORT_FMT = GOOGLE_SPREADSHEETS_EXPORT_BASE_URL + "?key=%s&exportFormat=%s";

	private static HttpTransport httpTransport = null;
	private java.io.File pkcs12File = null;
	private String clientId = null;
	
	private Drive drive = null;
	
	public GoogleSpreadsheetsExporter( java.io.File pkcs12File, String clientId, String appName ) throws GeneralSecurityException, IOException  
	{
		this.pkcs12File = pkcs12File;
		this.clientId = clientId;
		
		httpTransport = GoogleNetHttpTransport.newTrustedTransport();
		
		Credential credential = authorizeService();

		drive = new Drive.Builder( httpTransport, JSON_FACTORY, credential ).setApplicationName( appName ).build();
	}
	
	public java.io.File exportToFile( String id, ExportFormats format, java.io.File file ) throws IOException 
	{
		InputStream is = downloadTapList( id, format );
		return dumpStreamToFile( is, file );
	}
	
	public InputStream exportToStream( String id, ExportFormats format ) throws IOException 
	{
		return downloadTapList( id, format );
	}
	
	private Credential authorizeService() throws GeneralSecurityException, IOException  
	{
		return new GoogleCredential.Builder()
								   .setTransport( httpTransport )
								   .setJsonFactory( JSON_FACTORY )
								   .setServiceAccountId( this.clientId )
								   .setServiceAccountScopes( CLIENT_SCOPES )
								   .setServiceAccountPrivateKeyFromP12File( this.pkcs12File )
								   .build();
	}
	
	private InputStream downloadTapList( String id, ExportFormats format ) throws IOException 
	{
		String exportUrl = String.format( SPREADSHEET_EXPORT_FMT, id, format );
		
		HttpRequest dlReq = drive.getRequestFactory().buildGetRequest( new GenericUrl( exportUrl ) );
		com.google.api.client.http.HttpResponse response = dlReq.execute();

		return new ByteArrayInputStream( dumpStreamToByteArray( response.getContent() ) );
	}
	
	private static java.io.File dumpStreamToFile( InputStream is, java.io.File file ) throws IOException
	{
		Closer closer = Closer.create();
		try
		{
			closer.register( is );
			OutputStream os = closer.register( new FileOutputStream( file ) );
			
			ByteStreams.copy( is, os );
			
			return file;
		}
		finally
		{
			closer.close();
		}
	}
	
	private static byte[] dumpStreamToByteArray( InputStream is ) throws IOException
	{
		Closer closer = Closer.create();
		try
		{
			closer.register( is );
			ByteArrayOutputStream os = closer.register( new ByteArrayOutputStream( is.available() ) );
			
			ByteStreams.copy( is, os );
			
			return os.toByteArray();
		}
		finally
		{
			closer.close();
		}
	}
}
