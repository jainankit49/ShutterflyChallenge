package com.shutterfly.model;


public class SiteVisit extends Events{


private String customerId;
private Tags tags;


public String getCustomerId() {
return customerId;
}

public void setCustomerId(String customerId) {
this.customerId = customerId;
}

public Tags getTags() {
return tags;
}

public void setTags(Tags tags) {
this.tags = tags;
}

}