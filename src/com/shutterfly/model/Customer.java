package com.shutterfly.model;

public class Customer extends Events{


private String lastName;
private String adrCity;
private String adrState;
private Double customerLTV;


public String getLastName() {
return lastName;
}

public void setLastName(String lastName) {
this.lastName = lastName;
}

public String getAdrCity() {
return adrCity;
}

public void setAdrCity(String adrCity) {
this.adrCity = adrCity;
}

public String getAdrState() {
return adrState;
}

public void setAdrState(String adrState) {
this.adrState = adrState;
}

public Double getCustomerLTV() {
	return customerLTV;
}

public void setCustomerLTV(double customerLTV) {
	this.customerLTV = customerLTV;
}
}
