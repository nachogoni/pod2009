package com.canchita.model.location;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
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
	
	public Place(Builder builder) {
		this.address = builder.address;
		this.zipCode = builder.zipCode;
		this.neighbourhood = builder.neighbourhood;
		this.town = builder.town;
		this.state = builder.state;
		this.country = builder.country;
		this.latitude = builder.latitude;
		this.longitude = builder.longitude;
		this.telephones = builder.telephones;
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
		
		ret.append(address).append(", ").append(neighbourhood);
		
		return ret.toString();
	}
	
	public static class Builder {
		
		//Required parameters
		private final String address;
		private final String neighbourhood;
		
		//Optional parameters
		private String zipCode = null;
		private String town = null;
		private String state = null;
		private String country = null;
		private String latitude = null;
		private String longitude = null;
		private List<String> telephones = new LinkedList<String>();
		
		public Builder(String address, String neighbourhood) {
			this.address = address;
			this.neighbourhood = neighbourhood;
		}

		public Builder zipCode(String zipCode) {
			this.zipCode = zipCode;
			return this;
		}

		public Builder town(String town) {
			this.town = town;
			return this;
		}

		public Builder state(String state) {
			this.state = state;
			return this;
		}

		public Builder country(String country) {
			this.country = country;
			return this;
		}

		public Builder latitude(String latitude) {
			this.latitude = latitude;
			return this;
		}

		public Builder longitude(String longitude) {
			this.longitude = longitude;
			return this;
		}

		public Builder telephone(String telephone) {
			this.telephones.add(telephone);
			return this;
		}
		
		public Place build() {
			return new Place(this);
		}
		
	}
	
}
