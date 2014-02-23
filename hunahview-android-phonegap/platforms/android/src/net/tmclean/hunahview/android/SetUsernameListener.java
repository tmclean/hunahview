package net.tmclean.hunahview.android;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.widget.EditText;

public class SetUsernameListener implements DialogInterface.OnClickListener
{
	private Hunahview hv = null;
	private EditText input = null;
	
	public SetUsernameListener( Hunahview hv, EditText input ) 
	{
		this.hv = hv;
		this.input = input;
	}

	@Override
	public void onClick( DialogInterface dialog, int which ) 
	{
		FileOutputStream fout = null;
		
		try 
		{
			String username = input.getText().toString();
			
			if( username == null || username.isEmpty() )
			{
				AlertDialog.Builder builder = new AlertDialog.Builder( hv );
				builder.setMessage( "Must select a user name" );
				
				
				builder.setNegativeButton( "Dismiss",  new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick( DialogInterface dialog, int which ) 
					{
						hv.askForUsername();
						dialog.cancel();
					}
				});
				
				AlertDialog erroDialog = builder.create();
				erroDialog.show();
				return;
			}
			else
			{
				hv.deleteFile( Hunahview.USERNAME_FILE );
				
				fout = hv.openFileOutput( Hunahview.USERNAME_FILE, Context.MODE_PRIVATE );
				fout.write( input.getText().toString().getBytes( Charset.forName( "UTF-8" ) ) );
				fout.close();
				
				hv.setUsername( username );
				
				input.getText().clear();
				
				hv.reloadApp();
			}
		}
		catch( Throwable t ) 
		{
			t.printStackTrace();
		}
		finally
		{
			try 
			{
				if( fout != null )
					fout.close();
			}
			catch( IOException e ) 
			{
				e.printStackTrace();
			}
		}
	}
}
