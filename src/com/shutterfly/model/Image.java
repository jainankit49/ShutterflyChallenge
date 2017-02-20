package com.shutterfly.model;


public class Image extends Events{


private String customerId;
private String cameraMake;
private String cameraModel;


public String getCustomerId() {
return customerId;
}

public void setCustomerId(String customerId) {
this.customerId = customerId;
}

public String getCameraMake() {
return cameraMake;
}

public void setCameraMake(String cameraMake) {
this.cameraMake = cameraMake;
}

public String getCameraModel() {
return cameraModel;
}

public void setCameraModel(String cameraModel) {
this.cameraModel = cameraModel;
}

}