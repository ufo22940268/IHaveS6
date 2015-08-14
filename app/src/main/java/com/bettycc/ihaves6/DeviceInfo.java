package com.bettycc.ihaves6;

/**
 * Created by cc on 8/10/15.
 */
public class DeviceInfo {

    private String mBrand;
    private String mModel;

    public DeviceInfo(String brand, String model) {
        setBrand(brand);
        setModel(model);
    }

    public String getBrand() {
        return mBrand;
    }

    public void setBrand(String brand) {
        mBrand = brand;
    }

    public String getModel() {
        return mModel;
    }

    public void setModel(String model) {
        mModel = model;
    }
}
