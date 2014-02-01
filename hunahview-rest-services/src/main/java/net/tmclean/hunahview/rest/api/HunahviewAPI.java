package net.tmclean.hunahview.rest.api;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;

import net.tmclean.hunahview.lib.data.model.Beer;
import net.tmclean.hunahview.lib.data.model.Brewery;
import net.tmclean.hunahview.lib.data.model.Location;

@Path( "/api" )
@Api(value = "/api", description = "API for event tap lists and breweries" )
public interface HunahviewAPI
{
	@GET
	@Path( "/events" )
	@Produces( MediaType.APPLICATION_JSON )
	@ApiOperation( "/events" )
	public List<String> getEvents();

	@GET
	@Path( "/{eventName}/locations")
	@Produces( MediaType.APPLICATION_JSON )
	@ApiOperation( "/{eventName}/locations/" )
	public List<Location> getEventLocations( @PathParam( "eventName" ) String eventName );
	
	@GET
	@Path( "/{eventName}/beers/{location}")
	@Produces( MediaType.APPLICATION_JSON )
	@ApiOperation( "/{eventName}/beers" )
	public List<Beer> getEventBeersByLocation( @PathParam( "eventName" ) String eventName,
											   @PathParam( "location" ) String location );
	
	@GET
	@Path( "/{eventName}/beers")
	@Produces( MediaType.APPLICATION_JSON )
	@ApiOperation( "/{eventName}/beers" )
	public List<Beer> getEventBeers( @PathParam( "eventName" ) String eventName,
									 @QueryParam( "brewery" ) String brewery );
	
	@GET
	@Path( "/{eventName}/breweries")
	@Produces( MediaType.APPLICATION_JSON )
	@ApiOperation( "/{eventName}/breweries/" )
	public List<Brewery> getEventBreweries( @PathParam( "eventName" ) String eventName );
}
