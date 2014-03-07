package net.tmclean.hunahview.lib.data.model;

import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;


@Entity( "hunahview_directories" )
public class CurrentBeerDirectory 
{
	@Id
	private String objId = null;
	
	private String dirId = null;

	public String getObjId() { return objId; }
	public void setObjId(String objId) { this.objId = objId; }
	
	public String getDirId() { return dirId; }
	public void setDirId(String dirId) { this.dirId = dirId; }	
}
