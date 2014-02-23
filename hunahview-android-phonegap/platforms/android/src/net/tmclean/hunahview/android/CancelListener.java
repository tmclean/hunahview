package net.tmclean.hunahview.android;

import android.content.DialogInterface;

public class CancelListener implements DialogInterface.OnClickListener
{
	@Override
	public void onClick( DialogInterface dialog, int which ) 
	{
		dialog.cancel();
	}
}
