package com.canchita.model.location;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * 
 * @author Pablo Federico Abramowicz
 * @author Martín Esteban Cabral
 * @author Darío Maximiliano Gomez Vidal
 * @author Juan Ignacio Goñi
 * @author Martín Palombo
 * @author Carlos Manuel Sessa
 *
 */
public class Place {

	private String address;
	private String zipCode;
	private String neighbourhood;
	private String town;
	private String state;
	private String country;
	private String latitude;
	private String longitude;
	private List<String> telephones;
	
	public Place(String address, List<String> telephones, String zipCode,
			String neighbourhood, String town, String state, String country,
			String latitude, String longitude) {
		this.address = address;
		this.zipCode = zipCode;
		this.neighbourhood = neighbourhood;
		this.town = town;
		this.state = state;
		this.country = country;
		this.latitude = latitude;
		this.longitude = longitude;

		this.telephones = new ArrayList<String>();
		for (Iterator<String> i = telephones.iterator(); i.hasNext(); ) {
			this.telephones.add(i.next());
		}
	}

	public List<String> getTelephones() {
		
		List<String> ret = new ArrayList<String>();
		
		for (Iterator<String> i = telephones.iterator(); i.hasNext(); ) {
			ret.add(i.next());
		}
		
		return ret;
	}
	
	public boolean addTelephone (String telephone) {
		return telephones.add(telephone);
	}
	
	public boolean removeTelephone (String telephone) {
		return telephones.remove(telephone);
	}
	
	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getZipCode() {
		return this.zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public String getNeighbourhood() {
		return this.neighbourhood;
	}

	public void setNeighbourhood(String neighbourhood) {
		this.neighbourhood = neighbourhood;
	}

	public String getTown() {
		return this.town;
	}

	public void setTown(String town) {
		this.town = town;
	}

	public String getState() {
		return this.state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCountry() {
		return this.country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getLatitude() {
		return this.latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getLongitude() {
		return this.longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((address == null) ? 0 : address.hashCode());
		result = prime * result + ((country == null) ? 0 : country.hashCode());
		result = prime * result
				+ ((latitude == null) ? 0 : latitude.hashCode());
		result = prime * result
				+ ((longitude == null) ? 0 : longitude.hashCode());
		result = prime * result
				+ ((neighbourhood == null) ? 0 : neighbourhood.hashCode());
		result = prime * result + ((state == null) ? 0 : state.hashCode());
		result = prime * result
				+ ((telephones == null) ? 0 : telephones.hashCode());
		result = prime * result + ((town == null) ? 0 : town.hashCode());
		result = prime * result + ((zipCode == null) ? 0 : zipCode.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Place))
			return false;
		Place other = (Place) obj;
		if (address == null) {
			if (other.address != null)
				return false;
		} else if (!address.equals(other.address))
			return false;
		if (country == null) {
			if (other.country != null)
				return false;
		} else if (!country.equals(other.country))
			return false;
		if (latitude == null) {
			if (other.latitude != null)
				return false;
		} else if (!latitude.equals(other.latitude))
			return false;
		if (longitude == null) {
			if (other.longitude != null)
				return false;
		} else if (!longitude.equals(other.longitude))
			return false;
		if (neighbourhood == null) {
			if (other.neighbourhood != null)
				return false;
		} else if (!neighbourhood.equals(other.neighbourhood))
			return false;
		if (state == null) {
			if (other.state != null)
				return false;
		} else if (!state.equals(other.state))
			return false;
		if (telephones == null) {
			if (other.telephones != null)
				return false;
		} else if (!telephones.equals(other.telephones))
			return false;
		if (town == null) {
			if (other.town != null)
				return false;
		} else if (!town.equals(other.town))
			return false;
		if (zipCode == null) {
			if (other.zipCode != null)
				return false;
		} else if (!zipCode.equals(other.zipCode))
			return false;
		return true;
	}

	@Override
	public String toString() {
		StringBuffer ret = new StringBuffer();
		
		ret.append(address).append(", ").append(town).append(", ").append(state);
		ret.append(", ").append(country).append(".\n CP: ").append(zipCode).append(", ");
		ret.append(neighbourhood).append(", ").append(latitude).append(", ").append(longitude);
		if (!telephones.isEmpty()) {
			ret.append(".\n Telefonos: ").append(telephones.toString());
		}
		return ret.toString();
	}
	
	
	
}
