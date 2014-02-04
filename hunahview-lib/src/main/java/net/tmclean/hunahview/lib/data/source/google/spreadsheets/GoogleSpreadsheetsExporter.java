package net.tmclean.hunahview.lib.data.source.google.spreadsheets;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.tmclean.hunahview.lib.config.ContextAwareProperties;
import net.tmclean.hunahview.lib.data.source.BeerDataSourceException;
import net.tmclean.hunahview.lib.data.source.hunahpu2014.ExportFormats;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.Strings;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import com.google.common.base.Preconditions;
import com.google.common.io.ByteStreams;
import com.google.common.io.Closer;

public class GoogleSpreadsheetsExporter 
{
	public static final String GOOGLE_APP_NAME  = "data.source.google.spreadsheets.appName";
	public static final String GOOGLE_CLIENT_ID = "data.source.google.spreadsheets.clientId";
	public static final String GOOGLE_CERT_FILE = "data.source.google.spreadsheets.certFile";
	
	private static final Set<String> CLIENT_SCOPES = Collections.singleton( DriveScopes.DRIVE_FILE );
	private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();

	private static final String GOOGLE_SPREADSHEETS_EXPORT_BASE_URL = "https://spreadsheets.google.com/feeds/download/spreadsheets/Export";
	private static final String SPREADSHEET_EXPORT_FMT = GOOGLE_SPREADSHEETS_EXPORT_BASE_URL + "?key=%s&exportFormat=%s";

	private static HttpTransport httpTransport = null;
	
	private Logger logger = LoggerFactory.getLogger( this.getClass() );
	
	private Drive drive = null;
	
	public void configure( ContextAwareProperties properties ) throws BeerDataSourceException, GoogleException 
	{
		Preconditions.checkNotNull( properties );
		
		String appName  = properties.getProperty( GOOGLE_APP_NAME );
		String clientId = properties.getProperty( GOOGLE_CLIENT_ID );
		String certFile = properties.getProperty( GOOGLE_CERT_FILE );
		
		logger.info( "Configuring {}: appName:  {}",  properties.getContext(), appName  );
		logger.info( "Configuring {}: clientId: {}", properties.getContext(), clientId );
		logger.info( "Configuring {}: certFile: {}", properties.getContext(), certFile );
		
		Preconditions.checkArgument( !Strings.isNullOrEmpty( appName ) );
		Preconditions.checkArgument( !Strings.isNullOrEmpty( clientId ) );
		Preconditions.checkArgument( !Strings.isNullOrEmpty( certFile ) );
		
		File cert = new File( certFile );
		
		Preconditions.checkArgument( cert.exists() );
		logger.info( "{} certFile {} exists", properties.getContext(), certFile );
		
		Preconditions.checkArgument( cert.canRead() );
		logger.info( "{} certFile {} is readable", properties.getContext(), certFile );
		
		try
		{
			httpTransport = GoogleNetHttpTransport.newTrustedTransport();
			
			Credential credential = authorizeService( clientId, cert );
	
			drive = new Drive.Builder( httpTransport, JSON_FACTORY, credential ).setApplicationName( appName ).build();
		} 
		catch( GeneralSecurityException | IOException e ) 
		{
			throw new GoogleException( "Error configuring Google Spreadsheets Exporter", e );
		}
	}
	
	public File exportToFile( String id, ExportFormats format, File file ) throws IOException 
	{
		logger.debug( "Exporting spreadsheet {} to {}", id, file.getAbsolutePath() );
		
		InputStream is = download( id, format );
		
		return dumpStreamToFile( is, file );
	}
	
	public InputStream exportToStream( String id, ExportFormats format ) throws IOException 
	{
		logger.debug( "Exporting spreadsheet {} to stream", id );
		return download( id, format );
	}
	
	private Credential authorizeService( String clientId, File cert ) throws GeneralSecurityException, IOException  
	{
		return new GoogleCredential.Builder()
								   .setTransport( httpTransport )
								   .setJsonFactory( JSON_FACTORY )
								   .setServiceAccountId( clientId )
								   .setServiceAccountScopes( CLIENT_SCOPES )
								   .setServiceAccountPrivateKeyFromP12File( cert )
								   .build();
	}
	
	private InputStream download( String id, ExportFormats format ) throws IOException 
	{
		logger.debug( "Downloading spreadsheet {} as {}", id, format.toString() );
		
		String exportUrl = String.format( SPREADSHEET_EXPORT_FMT, id, format );
		
		HttpRequest dlReq = drive.getRequestFactory().buildGetRequest( new GenericUrl( exportUrl ) );
		com.google.api.client.http.HttpResponse response = dlReq.execute();

		return new ByteArrayInputStream( dumpStreamToByteArray( response.getContent() ) );
	}
	
	private static File dumpStreamToFile( InputStream is, File file ) throws IOException
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
