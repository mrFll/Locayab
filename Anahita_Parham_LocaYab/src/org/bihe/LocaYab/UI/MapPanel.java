package org.bihe.LocaYab.UI;

import javax.swing.JOptionPane;
import com.teamdev.jxmaps.DirectionsRequest;
import com.teamdev.jxmaps.DirectionsResult;
import com.teamdev.jxmaps.DirectionsRouteCallback;
import com.teamdev.jxmaps.DirectionsStatus;
import com.teamdev.jxmaps.DirectionsStep;
import com.teamdev.jxmaps.GeocoderCallback;
import com.teamdev.jxmaps.GeocoderRequest;
import com.teamdev.jxmaps.GeocoderResult;
import com.teamdev.jxmaps.GeocoderStatus;
import com.teamdev.jxmaps.Icon;
import com.teamdev.jxmaps.LatLng;
import com.teamdev.jxmaps.Map;
import com.teamdev.jxmaps.MapMouseEvent;
import com.teamdev.jxmaps.MapOptions;
import com.teamdev.jxmaps.MapReadyHandler;
import com.teamdev.jxmaps.MapStatus;
import com.teamdev.jxmaps.MapTypeControlOptions;
import com.teamdev.jxmaps.MapViewOptions;
import com.teamdev.jxmaps.Marker;
import com.teamdev.jxmaps.MouseEvent;
import com.teamdev.jxmaps.PlaceNearbySearchCallback;
import com.teamdev.jxmaps.PlaceResult;
import com.teamdev.jxmaps.PlaceSearchPagination;
import com.teamdev.jxmaps.PlaceSearchRequest;
import com.teamdev.jxmaps.PlacesServiceStatus;
import com.teamdev.jxmaps.TrafficLayer;
import com.teamdev.jxmaps.TravelMode;

public class MapPanel extends com.teamdev.jxmaps.swing.MapView {
	/**
		 * 
		 */
	private static final long serialVersionUID = 1L;

	private static Map map;
	public String location = "ایران";
	public LatLng[] propertiesLocation;
	public Marker[] propertiesPin;
	public Marker pinLocation;
	public Marker inDetailLocation;
	public boolean pinUsage = false;
	private boolean set;
	public Marker[] requestedNearby;
	public double distance;
	public double ZOOM = 15.0;
	static final MapViewOptions mapOptions;

	private DirectionsRequest request;

	private TrafficLayer traffic;
	static {// enable the usage of places library
		mapOptions = new MapViewOptions();
		mapOptions.importPlaces();
	}

	// --------------------------------------------//Getters&Setters//------------------------------------------------
	// properties
	public LatLng[] getPropertiesLocation() {
		return propertiesLocation;
	}

	public void setPropertiesLocation(LatLng[] propertiesLocation) {
		this.propertiesLocation = propertiesLocation;
	}

	// Nearby Markers
	public Marker[] getRequestedNearby() {
		return requestedNearby;
	}

	public void setRequestedNearby(Marker[] requestedNearby) {
		this.requestedNearby = requestedNearby;
	}

	// location
	public void setLocation(String location) {
		this.location = location;
	}

	// pin location
	public Marker getPinLocation() {
		return pinLocation;
	}

	public void setPinLocation(Marker pinLocation) {
		this.pinLocation = pinLocation;
	}

	// pin usage
	public void setPinUsage(boolean pinUsage) {
		this.pinUsage = pinUsage;
	}

	// markers Number
	public boolean getSet() {
		return set;
	}

	public void setSet(boolean set) {
		this.set = set;
	}

	// in detail Location
	public Marker getInDetailLocation() {
		return inDetailLocation;
	}

	public void setInDetailLocation(Marker inDetailLocation) {
		this.inDetailLocation = inDetailLocation;
	}

	// distance
	public double getDistance() {
		return distance;
	}

	public void setDistance(double distance) {
		this.distance = distance;
	}

	// ------------------------------------------//Constructor//--------------------------------------------------
	public MapPanel() {
		super(mapOptions);
		setOnMapReadyHandler(new MapReadyHandler() {

			@Override
			public void onMapReady(MapStatus e) {
				if (e == MapStatus.MAP_STATUS_OK) {
					map = getMap();
					//
					MapOptions options = new MapOptions();
					//
					MapTypeControlOptions controlOptions = new MapTypeControlOptions();
					// controlOptions.setPosition(ControlPosition.BOTTOM_RIGHT);
					options.setMapTypeControlOptions(controlOptions);
					// Setting map options
					map.setOptions(options);
					finder(location);
					map.setZoom(ZOOM);
					addNewPin();
				}
			}
		});
	}

	// ---------------------------------------------//nearbyFinder//-------------------------------------------------
	// Find and mark requested places
	public void nearbyFinder(String requestType, Icon icon, boolean setMarker) {
		markersCleaner(getRequestedNearby());
		PlaceSearchRequest nearbySearching = new PlaceSearchRequest();
		nearbySearching.setLocation(getInDetailLocation().getPosition());
		// set a limit to check the requested option
		nearbySearching.setRadius(500.0);
		// what to find in nearby
		nearbySearching.setTypes(new String[] { requestType });
		try {
			getServices().getPlacesService().nearbySearch(nearbySearching, new PlaceNearbySearchCallback(map) {
				@Override
				public void onComplete(PlaceResult[] results, PlacesServiceStatus status,
						PlaceSearchPagination pagination) {
					// Checking operation status
					if (status == PlacesServiceStatus.OK) {
						markersCleaner(getRequestedNearby());
						Marker[] requestedNearby = new Marker[results.length];
						map = getMap();

						for (int i = 0; i < results.length; ++i) {
							PlaceResult result = results[i];
							//
							requestedNearby[i] = new Marker(map);
							requestedNearby[i].setIcon(icon);

							if (result.getGeometry() != null) {
								LatLng location = result.getGeometry().getLocation();
								if (location != null) {
									requestedNearby[i].setPosition(location);
									requestedNearby[i].setVisible(false);
								}
							}
						}
						setRequestedNearby(requestedNearby);
						if (setMarker) {
							markerShow(getRequestedNearby());
						} else if (!setMarker && requestedNearby.length != 0) {
							distanceCalculator(inDetailLocation.getPosition(), requestedNearby[0].getPosition(),
									TravelMode.WALKING, false);
						}

						//
					} else {
						setDistance(-1);
						JOptionPane.showMessageDialog(null, "موردی در شعاع 500 متری یافت نشد!");
					}
				}
			});
		} catch (NullPointerException e) {
		}
	}

	// ----------------------------------------//MarkerCreator//-----------------------------------------------------
	// put new markers
	public void markerCreator(LatLng[] location) {
		Marker[] marker = new Marker[location.length];
		if (map != null) {
			for (int i = 0; i < location.length; i++) {
				marker[i] = new Marker(map);
				marker[i].setPosition(location[i]);
				marker[i].setVisible(false);
			}
			this.propertiesPin = marker;
		}
	}

	public Marker markerCreator(LatLng location) {
		Marker marker = null;
		if (map != null) {
			marker = new Marker(map);
		} else {
			JOptionPane.showMessageDialog(null, " اتصال برقرار نیست!مجددا تلاش کنید");
		}
		return marker;
	}

	// ----------------------------------------//PutMarker//-----------------------------------------------------
	// put new markers
	public void markerShow(Marker markers[]) {
		if (markers != null) {
			for (int i = 0; i < markers.length; i++) {
				if (markers[i] != null) {
					markers[i].setVisible(true);
				}
			}
		}
	}

	// ----------------------------------------//MarkerCleaner//-----------------------------------------------------
	// clear previous markers
	public void markersCleaner(Marker markers[]) {
		if (markers != null) {
			for (int i = 0; i < markers.length; i++) {
				if (markers[i] != null) {
					markers[i].setVisible(false);
					markers[i].remove();
				}
			}
		}
	}

	public void markersCleaner(Marker marker) {
		if (marker != null) {
			marker.setVisible(false);
			marker.remove();
		}
	}

	// -------------------------------------//AddMarker&Remove//------------------------------------------
	/**
	 * pin a location in map and edit it
	 * 
	 * @param mouseEvent
	 */
	public void addNewPin() {
		// you can only put 1 marker in the map
		map.addEventListener("click", new MapMouseEvent() {
			@Override
			public void onEvent(MouseEvent mouseEvent) {
				if (pinUsage) {
					if (!set) {// not more than one pin
						set = true;
						pinLocation = new Marker(map);
						pinLocation.setPosition(mouseEvent.latLng());
						pinLocation.addEventListener("click", new MapMouseEvent() {
							@Override
							public void onEvent(MouseEvent mouseEvent) {
								// remove the pin by click on it
								set = false;
								pinLocation.remove();
								pinLocation = null;
							}
						});
					}
				}
			}
		});
	}

	// -------------------------------------//findLocationByString//------------------------------------------
	/**
	 * find location by name
	 * 
	 * @param location
	 */
	public void finder(String location) {
		@SuppressWarnings("deprecation")
		GeocoderRequest request = new GeocoderRequest(map);
		request.setAddress(location + "ایران,");
		try {
			getServices().getGeocoder().geocode(request, new GeocoderCallback(map) {
				@Override
				public void onComplete(GeocoderResult[] result, GeocoderStatus status) {
					if (status == GeocoderStatus.OK) {
						map = getMap();
						map.setZoom(ZOOM);
						map.setCenter(result[0].getGeometry().getLocation());
						if (set == true) {
							inDetailLocation = new Marker(map);
							inDetailLocation.setPosition(result[0].getGeometry().getLocation());
						}
					}
				}
			});
		} catch (NullPointerException e) {
		}
	}

	// -------------------------------------//findLocationByLocation//------------------------------------------
	/**
	 * find location by location
	 * 
	 * @param location
	 */
	public void finder(LatLng location, boolean detail) {
		try {
			map = getMap();
			map.setCenter(location);
			map.setZoom(ZOOM);
			if (detail) {
				if (getInDetailLocation() != null) {
					inDetailLocation = new Marker(map);
					inDetailLocation.setPosition(location);
				}
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "لطفا تا باز شدن کامل نقشه تأمل فرمایید");
		}

	}

	// -------------------------------------//DistanceCalculator//------------------------------------------
	public void distanceCalculator(LatLng orgin, LatLng destination, TravelMode trvaelMode, boolean showDirection) {
		request = new DirectionsRequest();
		request.setOrigin(orgin);
		request.setDestination(destination);
		request.getTransitOptions().getDepartureTime();
		// Setting of the travel mode
		request.setTravelMode(trvaelMode);

		getServices().getDirectionService().route(request, new DirectionsRouteCallback(map) {

			@Override
			public void onRoute(DirectionsResult result, DirectionsStatus status) {
				// Checking of the operation status
				if (status == DirectionsStatus.OK) {
					DirectionsStep[] step = result.getRoutes()[0].getLegs()[0].getSteps();
					double distance = 0;
					for (int i = 0; i < step.length; i++) {
						distance += step[i].getDistance().getValue();
					}
					setDistance(distance);
					if (showDirection) {
						map.getDirectionsRenderer().setDirections(result);

					}
				} else {
					JOptionPane.showMessageDialog(null,
							"Error. Route cannot be calculated.\nPlease correct input data.");
				}
			}
		});
	}

	// -------------------------------------//Traffic//------------------------------------------
	public void traffic(boolean show) {

		if (show) {
			traffic = new TrafficLayer(map);
		} else if (traffic != null) {
			traffic.remove();
		}

	}

	// -------------------------------------//DistanceCalculator//------------------------------------------
	public void mapRemover(boolean pinUsage) {
		setPinUsage(pinUsage);
		markersCleaner(getRequestedNearby());
		markersCleaner(getInDetailLocation());
		markersCleaner(getPinLocation());
		markersCleaner(propertiesPin);
		//
		setSet(false);
		setLocation("ایران");
		finder(location);
		ZOOM = 5.0;
	}

	// -------------------------------------//PathRemover//------------------------------------------
	@SuppressWarnings("deprecation")
	public void PathRemover() {
		setSet(false);
		markersCleaner(new Marker[] { pinLocation });
		if (map != null) {
			pinLocation = new Marker(map);
			map.getDirectionsRenderer().setDirections(new DirectionsResult(map));
		}

	}
}
