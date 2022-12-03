package com.mr.clock.dao;

/**
 * DAO工厂类
 * 
 * @author mingrisoft
 *
 */
public class DAOFactory {
    /**
     * 获取DAO对象
     * 
     * @return
     */
    public static DAO getDAO() {
        return new DAOMysqlImpl();// 返回基于Mysql的实现类
//         return new DAOSqliteImpl();// 返回基于Sqlite的实现类
    }
}
