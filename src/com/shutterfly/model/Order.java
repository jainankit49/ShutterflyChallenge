package com.shutterfly.model;

public class Order extends Events{


private String customerId;
private String totalAmount;


public String getCustomerId() {
return customerId;
}

public void setCustomerId(String customerId) {
this.customerId = customerId;
}

public String getTotalAmount() {
return totalAmount;
}

public void setTotalAmount(String totalAmount) {
this.totalAmount = totalAmount;
}

}