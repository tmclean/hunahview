package net.tmclean.hunahview.rest.impl;

import java.util.List;

import com.google.common.collect.Lists;

import net.tmclean.hunahview.lib.data.model.Event;
import net.tmclean.hunahview.rest.api.EventList;

public class EventListImpl implements EventList 
{
	public List<Event> getEvents() 
	{
		List<Event> events = Lists.newArrayListWithCapacity( 1 );
		events.add( new Event() );
		return events;
	}
}
