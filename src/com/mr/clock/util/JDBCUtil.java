package com.mr.clock.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import javax.naming.ConfigurationException;

/**
 * 数据库连接工具类
 * 
 * @author mingrisoft
 *
 */
public class JDBCUtil {
    private static String driver_name;// 驱动类
    private static String username;// 账号
    private static String password;// 密码
    private static String url;// 数据库地址
    private static Connection con = null;// 数据库连接
    // 数据库配置文件地址
    private static final String CONFIG_FILE = "src/com/mr/clock/config/jdbc.properties";

    static {
        Properties pro = new Properties();// 配置文件解析类
        try {
            // 配置文件的文件对象
            File config = new File(CONFIG_FILE);
            if (!config.exists()) {// 如果配置文件不存在
                throw new FileNotFoundException("缺少文件：" + config.getAbsolutePath());
            }
            pro.load(new FileInputStream(config));// 加载配置文件
            driver_name = pro.getProperty("driver_name");// 获取指定字段值
            username = pro.getProperty("username", "");
            password = pro.getProperty("password", "");
            url = pro.getProperty("url");
            if (driver_name == null || url == null) {
                throw new ConfigurationException("jdbc.properties文件缺少配置信息");
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ConfigurationException e) {
            System.err.println("配置文件获取的内容:[driver_name=" + driver_name + "],[username=" + username + "],[password="
                    + password + "],[url=" + url + "]");
            e.printStackTrace();
        } 
    }

/**
 * 获取数据库连接。根据jdbc.properties中的配置信息返回对应的Connection对象。
 * 
 * @return 已连接数据库的Connection对象
 */
public static Connection getConnection() {
    try {
        if (con == null || con.isClosed()) {// 如果连接对象被关闭
            Class.forName(driver_name);// 加载驱动类
            // 根据URL、账号密码获取数据库连接
            con = DriverManager.getConnection(url, username, password);
        }
    } catch (ClassNotFoundException e) {
        e.printStackTrace();
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return con;
}

    /**
     * 关闭ResultSet
     * 
     * @param rs
     */
    public static void close(ResultSet rs) {
        try {
            if (rs != null)
                rs.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * 关闭Statement
     * 
     * @param stmt
     */
    public static void close(Statement stmt) {
        try {
            if (stmt != null)
                stmt.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * 关闭PreparedStatement
     * 
     * @param ps
     */
    public static void close(PreparedStatement ps) {
        try {
            if (ps != null)
                ps.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * 安全的关闭数据库接口
     * 
     * @param ps
     * @param rs
     */
    public static void close(Statement stmt, PreparedStatement ps, ResultSet rs) {
        close(rs);
        close(ps);
        close(stmt);
    }

    /**
     * 关闭Connection
     */
    public static void closeConnection() {
        if (con != null) {
            try {
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
