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

        WorkLocation setWorkLocation = new WorkLocation("�ӱ�ʡ","������");
        dao.updateWorkLocation(setWorkLocation);

        WorkLocation workLocation = dao.getLocation();
        Assert.assertEquals("�ӱ�ʡ", workLocation.getProvince());
        Assert.assertEquals("������", workLocation.getCity());
    }

    @Test
    public void testUpdateLocation() {
        DAO dao = new DAOMysqlImpl();
        WorkLocation workLocation = new WorkLocation("������ʡ","��������");
        dao.updateWorkLocation(workLocation);
        WorkLocation newWorkLocation = dao.getLocation();
        Assert.assertEquals("������ʡ", newWorkLocation.getProvince());
        Assert.assertEquals("��������", newWorkLocation.getCity());
    }
}
