package com.uas.gsiam.web;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class AircraftTypes {

	public String type;
    public double length;
    public int seatingCapacity;

    public AircraftTypes(){}

    public AircraftTypes(String type, double length, int seatingCapacity) {
        this.type = type;
        this.length = length;
        this.seatingCapacity = seatingCapacity;
    }
}
