package com.mr.clock.dao;

import com.mr.clock.dao.DAO;
import com.mr.clock.dao.DAOMysqlImpl;
import com.mr.clock.pojo.WorkLocation;
import org.junit.Assert;
import org.junit.Test;

public class testDAO {

    @Test
    public void testGetLocation() {
        DAO dao = new DAOMysqlImpl();

        WorkLocation setWorkLocation = new WorkLocation("河北省","邯郸市");
        dao.updateWorkLocation(setWorkLocation);

        WorkLocation workLocation = dao.getLocation();
        Assert.assertEquals("河北省", workLocation.getProvince());
        Assert.assertEquals("邯郸市", workLocation.getCity());
    }

    @Test
    public void testUpdateLocation() {
        DAO dao = new DAOMysqlImpl();
        WorkLocation workLocation = new WorkLocation("黑龙江省","哈尔滨市");
        dao.updateWorkLocation(workLocation);
        WorkLocation newWorkLocation = dao.getLocation();
        Assert.assertEquals("黑龙江省", newWorkLocation.getProvince());
        Assert.assertEquals("哈尔滨市", newWorkLocation.getCity());
    }
}
