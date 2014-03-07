package net.tmclean.hunahview.lib.data.model;

import java.util.Date;
import java.util.UUID;

import javax.xml.bind.annotation.XmlTransient;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

@Entity( "hunahview_checkins" )
public class Checkin 
{
	@Id
	@JsonIgnore
	private String objId = null;
	private String checkinId = UUID.randomUUID().toString();
	private Date   checkinTime = null;
	private String eventId = null;
	private String username = null;
	private String beerId = null;

	@XmlTransient
	public String getObjId() { return objId; }
	public void setObjId(String objId) { this.objId = objId; }
	
	public String getCheckinId() { return checkinId; }
	public void setCheckinId(String checkinId) { this.checkinId = checkinId; }
	
	public Date getCheckinTime() { return checkinTime; }
	public void setCheckinTime(Date checkinTime) { this.checkinTime = checkinTime; }
	
	public String getEventId() { return eventId; }
	public void setEventId(String eventId) { this.eventId = eventId; }
	
	public String getUsername() { return username; }
	public void setUsername(String username) { this.username = username; }
	
	public String getBeerId() { return beerId; }
	public void setBeerId(String beerId) { this.beerId = beerId; }	
}
