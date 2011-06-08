package com.uas.gsiam.web;

import java.util.LinkedList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;


@Path("/aircrafts")
public class AircraftTypeList {

	 static final List<AircraftType> aircraftTypes = new LinkedList<AircraftType>();

	    static {
	        aircraftTypes.add(new AircraftType("B737", 42.1, 204));
	        aircraftTypes.add(new AircraftType("A330", 58.8, 253));
	    }

	    @GET @Produces({MediaType.APPLICATION_JSON})
	    public List<AircraftType> getAircraftTypes() {
	        return aircraftTypes;
	    }
  

}
