package com.mr.clock.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import com.mr.clock.pojo.Employee;
import com.mr.clock.pojo.User;
import com.mr.clock.pojo.WorkTime;
import com.mr.clock.util.JDBCUtil;

/**
 * 基于MySQL的DAO实现类
 * 
 * @author mingrisoft
 *
 */
public class DAOMysqlImpl implements DAO {
    Connection con = null;
    Statement stmt = null;
    PreparedStatement ps = null;
    ResultSet rs = null;

    @Override
    public Set<Employee> getALLEmp() {
        Set<Employee> set = new HashSet<Employee>();// 全体员工集合
        String sql = "select id, name,code from t_emp ";
        con = JDBCUtil.getConnection();
        try {
            stmt = con.createStatement();
            rs = stmt.executeQuery(sql);
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String code = rs.getString("code");
                Employee e = new Employee(id, name, code);
                set.add(e);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCUtil.close(stmt, ps, rs);// 关闭数据库接口对象
        }
        return set;
    }

    @Override
    public Employee getEmp(int id) {
        String sql = "select name,code from t_emp where id = ?";// 待执行的SQL语句
        con = JDBCUtil.getConnection();// 获数据库连接
        try {
            ps = con.prepareStatement(sql);// 创建执行SQL语句的接口
            ps.setInt(1, id);// 将SQL语句中第一个?改为员工编号的值
            rs = ps.executeQuery();// 执行SQL语句
            if (rs.next()) {// 如果有查询结果
                String name = rs.getString("name");// 获取name字段的值
                String code = rs.getString("code");// 获取code字段的值
                // 将id，name，code这三个值封装成员工对象
                Employee e = new Employee(id, name, code);
                return e;// 返回此员工对象
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCUtil.close(stmt, ps, rs);// 关闭数据库接口对象
        }
        return null;// 无此员工则返回null
    }

    @Override
    public Employee getEmp(String code) {
        String sql = "select id,name from t_emp where code = ?";
        con = JDBCUtil.getConnection();
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, code);
            rs = ps.executeQuery();
            if (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                Employee e = new Employee(id, name, code);
                return e;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCUtil.close(stmt, ps, rs);// 关闭数据库接口对象
        }
        return null;// 无此员工则返回null
    }

    @Override
    public void addEmp(Employee e) {
        String sql = "insert into t_emp(name,code) values(?,?)";
        con = JDBCUtil.getConnection();
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, e.getName());
            ps.setString(2, e.getCode());
            ps.executeUpdate();
        } catch (SQLException e1) {
            e1.printStackTrace();
        } finally {
            JDBCUtil.close(stmt, ps, rs);// 关闭数据库接口对象
        }
    }

    @Override
    public void deleteEmp(Integer id) {
        String sql = "delete from t_emp where id = ?";
        con = JDBCUtil.getConnection();
        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e1) {
            e1.printStackTrace();
        } finally {
            JDBCUtil.close(stmt, ps, rs);// 关闭数据库接口对象
        }
    }

    @Override
    public WorkTime getWorkTime() {
        String sql = "select start,end from t_work_time ";
        con = JDBCUtil.getConnection();
        try {
            stmt = con.createStatement();
            rs = stmt.executeQuery(sql);
            if (rs.next()) {
                String start = rs.getString("start");
                String end = rs.getString("end");
                return new WorkTime(start, end);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCUtil.close(stmt, ps, rs);// 关闭数据库接口对象
        }
        return null;// 无记录则返回null
    }

    @Override
    public void updateWorkTime(WorkTime time) {
        String sql = "update t_work_time set start = ?, end = ? ";
        con = JDBCUtil.getConnection();
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, time.getStart());
            ps.setString(2, time.getEnd());
            ps.executeUpdate();
        } catch (SQLException e1) {
            e1.printStackTrace();
        } finally {
            JDBCUtil.close(stmt, ps, rs);// 关闭数据库接口对象
        }
    }

    @Override
    public void addCLockInRecord(int empID, Date now) {
        // 日期格式化
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = sdf.format(now);// 将日期转为字符串
        String sql = "insert into t_lock_in_record(emp_id,lock_in_time) values(?,?)";
        con = JDBCUtil.getConnection();
        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, empID);
            ps.setString(2, time);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCUtil.close(stmt, ps, rs);// 关闭数据库接口对象
        }
    }

    @Override
    public void deleteClockInRecord(int empID) {
        String sql = "delete from t_lock_in_record where emp_id = ?";
        con = JDBCUtil.getConnection();
        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, empID);
            ps.executeUpdate();
        } catch (SQLException e1) {
            e1.printStackTrace();
        } finally {
            JDBCUtil.close(stmt, ps, rs);// 关闭数据库接口对象
        }
    }

    @Override
    public boolean userLogin(User user) {
        String sql = "select id from t_user where username = ? and password = ? ";
        con = JDBCUtil.getConnection();
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getPassword());
            rs = ps.executeQuery();
            return rs.next();// 如果有查询结果则返回true
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCUtil.close(stmt, ps, rs);// 关闭数据库接口对象
        }
        return false;// 无查询结果则返回false
    }

    @Override
    public String[][] getAllClockInRecord() {
        // 保存查询数据的集合。因为不确定行数，所以使用集合而不是二维数组。
        HashSet<String[]> set = new HashSet<>();
        String sql = "select emp_id, lock_in_time from t_lock_in_record ";
        con = JDBCUtil.getConnection();
        try {
            stmt = con.createStatement();
            rs = stmt.executeQuery(sql);
            while (rs.next()) {
                String emp_id = rs.getString("emp_id");
                String lock_in_time = rs.getString("lock_in_time");
                // 直接将查询的两个结果以字符串数组的形式放到集合中
                set.add(new String[] { emp_id, lock_in_time });
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCUtil.close(stmt, ps, rs);// 关闭数据库接口对象
        }
        if (set.isEmpty()) {// 如果集合是空的，表示表中没有任何打卡数据
            return null;
        } else {// 如果存在打卡数据
            //创建二维数组作为返回结果，数组行数为集合元素个数，列数为2
            String result[][] = new String[set.size()][2];
            Iterator<String[]> it = set.iterator();//创建集合迭代器
            for (int i = 0; it.hasNext(); i++) {//迭代集合，同时让i递增
                result[i] = it.next();//集合中的每一个元素都作为数组的每一行数据
            }
            return result;
        }
    }
}
