package net.tmclean.hunahview.lib.data.model;

import java.util.List;
import java.util.UUID;

import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

import com.google.common.collect.Lists;

@Entity( "hunahview_directories" )
public class BeerDirectory 
{
	@Id
	private String objId = null;
	
	private String dirId = UUID.randomUUID().toString();
	private List<Beer> beers = Lists.newArrayListWithCapacity( 0 );
	
	public String getObjId() { return objId; }
	public void setObjId(String objId) { this.objId = objId; }
	
	public String getDirId() { return dirId; }
	public void setDirId(String dirId) { this.dirId = dirId; }
	
	public List<Beer> getBeers() { return beers; }
	public void setBeers(List<Beer> beers) { this.beers = beers; }
}
