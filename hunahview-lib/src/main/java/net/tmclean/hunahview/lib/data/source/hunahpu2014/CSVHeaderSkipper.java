package net.tmclean.hunahview.lib.data.source.hunahpu2014;

import java.io.IOException;
import java.io.Reader;
import java.util.List;

import org.supercsv.io.Tokenizer;
import org.supercsv.prefs.CsvPreference;

import com.google.api.client.util.Strings;

public class CSVHeaderSkipper extends Tokenizer
{
    public CSVHeaderSkipper( Reader reader, CsvPreference preferences ) 
    {
        super( reader, preferences );
    }

    @Override
    public boolean readColumns( List<String> columns ) throws IOException 
    {
        boolean moreInput = super.readColumns( columns );

        while (moreInput && (columns.size() == 0 || columns.size() == 1 && columns.get(0).trim().isEmpty()))
        {
            moreInput = super.readColumns(columns);
        }
        
        if( columns.size() < 2 )
        	return false;
        
        if( Strings.isNullOrEmpty( columns.get( 1 ) ) ) return false;
        if( Strings.isNullOrEmpty( columns.get( 2 ) ) ) return false;
        
        return true;
    }
}
