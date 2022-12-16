package com.mr.clock.pojo;

import java.util.Objects;

public class WorkLocation {

    String province;
    String city;

    public WorkLocation() {
        super();
    }

    public WorkLocation(String province, String city) {
        super();
        this.province = province;
        this.city = city;
    }

    public String getProvince() {
        return province;
    }

    public String getCity() {
        return city;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof WorkLocation)) return false;
        WorkLocation that = (WorkLocation) o;
        return Objects.equals(getProvince(), that.getProvince()) && Objects.equals(getCity(), that.getCity());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getProvince(), getCity());
    }

    @Override
    public String toString() {
        return "WorkLocation [" +
                "province='" + province + '\'' +
                ", city='" + city + '\'' +
                ']';
    }
}
