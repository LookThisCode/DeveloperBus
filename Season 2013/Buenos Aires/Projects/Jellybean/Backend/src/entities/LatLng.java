package entities;

public class LatLng {
	
	public LatLng(double latitude, double longitude, boolean flagAlert){
		lat = latitude;
		lng = longitude;		
		alert = flagAlert;
	}
	
	public double lat;
	
	public double lng;
	
	public boolean alert;
}
