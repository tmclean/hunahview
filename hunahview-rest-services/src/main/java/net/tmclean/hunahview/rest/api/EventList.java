package net.tmclean.hunahview.rest.api;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import net.tmclean.hunahview.lib.data.model.Event;

@Path( "events" )
public interface EventList 
{
	@GET
	@Produces( MediaType.APPLICATION_JSON )
	public List<Event> getEvents();
}
