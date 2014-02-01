package net.tmclean.hunahview.lib.event;

import java.util.List;
import java.util.Map;
import java.util.Properties;

import net.tmclean.hunahview.lib.config.ContextAwareProperties;

import com.google.api.client.util.Strings;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class EventRegistry 
{
	public static final String EVENT_LIST_KEY = "net.tmclean.hunahview.events";
	
	private Map<String, Event> registry = Maps.newHashMap();
	
	public EventRegistry( Properties properties )
	{
		Preconditions.checkNotNull( properties );

		String eventsStr = properties.getProperty( EVENT_LIST_KEY );
		
		Preconditions.checkArgument( !Strings.isNullOrEmpty( eventsStr ) );

		for( String eventName : eventsStr.split( "," ) )
			registry.put( eventName, new Event( new ContextAwareProperties( properties, eventName ) ) );
	}
	
	public List<String> getEventNames()
	{
		List<String> names = Lists.newArrayList();
		for( String name : registry.keySet() )
			names.add( name );
		return names;
	}
	
	public Event getEvent( String eventName )
	{
		return this.registry.get( eventName );
	}
	
	public void shutdown()
	{
		for( String eventName : registry.keySet() )
			registry.get( eventName ).shutdown();
	}
}
