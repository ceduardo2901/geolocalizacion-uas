package com.uas.gsiam.web;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class AircraftType {

	public String type;
    public double length;
    public int seatingCapacity;

    public AircraftType(){}

    public AircraftType(String type, double length, int seatingCapacity) {
        this.type = type;
        this.length = length;
        this.seatingCapacity = seatingCapacity;
    }
}
