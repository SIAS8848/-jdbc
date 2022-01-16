package 西二三轮;

//ORM思想
public class city {
	private String id;
	private String lat;
	private String lon;
	private String name;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getLat() {
		return lat;
	}
	public void setLat(String lat) {
		this.lat = lat;
	}
	public String getLon() {
		return lon;
	}
	public void setLon(String lon) {
		this.lon = lon;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@Override
	public String toString() {
		return "city [id=" + id + ", lat=" + lat + ", lon=" + lon + ", name=" + name + "]";
	}
	public city(String id, String lat, String lon, String name) {
		super();
		this.id = id;
		this.lat = lat;
		this.lon = lon;
		this.name = name;
	}
	public city() {
		super();
	}
	
	
	

}
