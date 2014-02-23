/*
       Licensed to the Apache Software Foundation (ASF) under one
       or more contributor license agreements.  See the NOTICE file
       distributed with this work for additional information
       regarding copyright ownership.  The ASF licenses this file
       to you under the Apache License, Version 2.0 (the
       "License"); you may not use this file except in compliance
       with the License.  You may obtain a copy of the License at

         http://www.apache.org/licenses/LICENSE-2.0

       Unless required by applicable law or agreed to in writing,
       software distributed under the License is distributed on an
       "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
       KIND, either express or implied.  See the License for the
       specific language governing permissions and limitations
       under the License.
 */

package net.tmclean.hunahview.android;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;

import org.apache.cordova.*;

import static net.tmclean.hunahview.android.Hunahview.MODE.*;

public class Hunahview extends CordovaActivity 
{	
	static final String USERNAME_FILE = "username.dat";
	
	private AlertDialog setUsernameDialog = null;
	
	protected static enum MODE
	{
		DEV( "http://localhost:8080?username=%s" ),
		PROD( "http://havok2905.github.io/hunahview-front-end?username=%s" );
		
		private String url = null;
		
		private MODE( String url )
		{
			this.url = url;
		}
		
		public String getUrl() { return this.url; }
	}
	
	private MODE mode = PROD;
	private String username = null;
	
	public Hunahview() {}

	@Override
    @SuppressWarnings("deprecation")
    public void onCreate( Bundle savedInstanceState )
    {
    	super.setIntegerProperty( "splashscreen", R.drawable.screen );
    	
        super.onCreate( savedInstanceState );
        super.init();
        
		String devMode = System.getProperty( "DEVELOPMENT_MODE" );
		
		if( devMode != null && devMode.equalsIgnoreCase( "true" ) )
		{
			mode = DEV;
		}
		
		try { username = readUsername(); } catch( IOException e ) {}
		
		AlertDialog.Builder builder = new AlertDialog.Builder( this );
		final EditText usernameInput = new EditText( this );
		usernameInput.getText().insert( 0, username == null ? "" : username );
		
		builder.setTitle( "Set User Name" );
		builder.setMessage( "Set current user name: " );
		builder.setView( usernameInput );
		builder.setPositiveButton( "Set", new SetUsernameListener( this, usernameInput ) );
		builder.setNegativeButton( "Cancel", new CancelListener() );
		setUsernameDialog = builder.create();

		ensureUsername();
    }
    
    private void ensureUsername()
    {
		try
		{
			username = readUsername();
	        reloadApp();
		}
		catch( IOException e )
		{
			setUsernameDialog.show();
		}
    }
    
    public String readUsername() throws IOException
    {
		FileInputStream fin = openFileInput( USERNAME_FILE );
		
		BufferedReader reader = new BufferedReader( new InputStreamReader( fin ) );
		this.username = reader.readLine();
		
    	return this.username;
    }
    
    @Override
    public boolean onCreateOptionsMenu( Menu menu )
    {
    	MenuInflater inflater = this.getMenuInflater();
    	inflater.inflate( R.xml.menu, menu );
    	return true;
    }
    
    @Override
    public boolean onOptionsItemSelected( MenuItem item ) 
    {
    	if( item.getItemId() == R.id.refresh )
    	{
    		reloadApp();
    	}
    	else if( item.getItemId() == R.id.setName )
    	{
			setUsernameDialog.show();	
    	}
    		
    	return super.onOptionsItemSelected( item );
    }
    
    public void reloadApp()
    {
        super.loadUrl( String.format( mode.url, this.username ) );
    }
    
    public void setUsername( String username )
    {
    	this.username = username;
    }
    
    public void askForUsername()
    {
    	this.setUsernameDialog.show();
    }
}
