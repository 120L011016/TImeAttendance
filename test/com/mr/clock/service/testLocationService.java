package com.mr.clock.service;

import com.mr.clock.pojo.WorkLocation;
import com.sun.corba.se.spi.orbutil.threadpool.Work;
import org.junit.Assert;
import org.junit.Test;

public class testLocationService {
    @Test
    public void testGetLocation() {
        WorkLocation workLocation = LocationService.getLocation();
        Assert.assertEquals("河北省", workLocation.getProvince());
        Assert.assertEquals("邯郸市", workLocation.getCity());
    }
}
