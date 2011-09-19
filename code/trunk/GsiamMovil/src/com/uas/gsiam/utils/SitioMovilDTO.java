package com.uas.gsiam.utils;

import java.io.Serializable;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

import android.os.Parcel;
import android.os.Parcelable;

public class SitioMovilDTO implements Parcelable {

	public String idSitio;

	public String nombre;

	public String direccion;

	public String lon;

	public String lat;

	public String getLon() {
		return lon;
	}

	public void setLon(String lon) {
		this.lon = lon;
	}

	public String getLat() {
		return lat;
	}

	public void setLat(String lat) {
		this.lat = lat;
	}

	public SitioMovilDTO() {

	}

	public String getIdSitio() {
		return idSitio;
	}

	public void setIdSitio(String idSitio) {
		this.idSitio = idSitio;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(idSitio);
		dest.writeString(nombre);
		dest.writeString(direccion);
		dest.writeString(lon);
		dest.writeString(lat);

	}

	public static final Parcelable.Creator<SitioMovilDTO> CREATOR = new Parcelable.Creator<SitioMovilDTO>() {
		public SitioMovilDTO createFromParcel(Parcel in) {
			return new SitioMovilDTO(in);
		}

		public SitioMovilDTO[] newArray(int size) {
			return new SitioMovilDTO[size];
		}
	};

	private SitioMovilDTO(Parcel in) {
		idSitio = in.readString();
		nombre = in.readString();
		direccion = in.readString();
		lon = in.readString();
		lat = in.readString();
		
	}

}
