package net.tmclean.hunahview.lib.data.model;

import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

@Entity( "hunahview_checkins" )
public class Checkin 
{
	@Id
	private String objId = null;
	private String eventId = null;
	private String username = null;
	private String beerId = null;
	
	public String getObjId() { return objId; }
	public void setObjId(String objId) { this.objId = objId; }
	
	public String getEventId() { return eventId; }
	public void setEventId(String eventId) { this.eventId = eventId; }
	
	public String getUsername() { return username; }
	public void setUsername(String username) { this.username = username; }
	
	public String getBeerId() { return beerId; }
	public void setBeerId(String beerId) { this.beerId = beerId; }	
}
