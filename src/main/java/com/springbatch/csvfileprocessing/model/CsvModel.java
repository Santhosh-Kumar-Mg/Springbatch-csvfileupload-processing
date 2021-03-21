package com.springbatch.csvfileprocessing.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = { "anzsic06", "area", "year", "geo_count", "ec_count" })
@XmlRootElement(name = "GeographicUnitsIndustry")
public class CsvModel {
	
	@XmlElement(name = "anzsic06", required = true)
	private String anzsic06;
	@XmlElement(name = "area", required = true)
	private String area;
	@XmlElement(name = "year", required = true)
	private String year;
	@XmlElement(name = "geo_count", required = true)
	private String geo_count;
	@XmlElement(name = "ec_count", required = true)
	private String ec_count;
	
	public String getAnzsic06() {
		return anzsic06;
	}
	public void setAnzsic06(String anzsic06) {
		this.anzsic06 = anzsic06;
	}
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public String getGeo_count() {
		return geo_count;
	}
	public void setGeo_count(String geo_count) {
		this.geo_count = geo_count;
	}
	public String getEc_count() {
		return ec_count;
	}
	public void setEc_count(String ec_count) {
		this.ec_count = ec_count;
	}
	
	
}
