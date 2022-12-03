package com.mr.clock.service;

import java.awt.image.BufferedImage;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import com.mr.clock.dao.DAO;
import com.mr.clock.dao.DAOFactory;
import com.mr.clock.pojo.Employee;
import com.mr.clock.pojo.User;
import com.mr.clock.pojo.WorkTime;
import com.mr.clock.session.Session;
import com.mr.clock.util.DateTimeUtil;

/**
 * 人事服务
 * 
 * @author mingrisoft
 *
 */
public class HRService {
    private static final String CLOCK_IN = "I";// 正常上班打卡标记
    private static final String CLOCK_OUT = "O";// 正常下班打卡标记
    private static final String LATE = "L";// 迟到标记
    private static final String LEFT_EARLY = "E";// 早退标记
    private static final String ABSENT = "A";// 缺席标记

    private static DAO dao = DAOFactory.getDAO();// 数据库接口

    /**
     * 加载所有员工
     */
    public static void loadAllEmp() {
        Session.EMP_SET.clear();// 全局会话清空所有员工
        // 重新从数据库中加载所有员工的对象集合
        Session.EMP_SET.addAll(dao.getALLEmp());
    }

    /**
     * 管理员用户登录
     * 
     * @param username 用户名
     * @param password 密码
     * @return 登录是否成功
     */
    public static boolean userLogin(String username, String password) {
        User user = new User(username, password);// 管理员对象
        if (dao.userLogin(user)) {// 如果数据库中可以查到相关管理员用户名和密码
            Session.user = user;// 将登录的管理员设为全局会话中的管理员
            return true;// 登录成功
        } else {
            return false;// 登录失败
        }
    }

    /**
     * 添加新员工
     * 
     * @param name 员工姓名
     * @param face 员工人脸照片
     * @return 添加成功的员工对象
     */
    public static Employee addEmp(String name, BufferedImage face) {
        // 通过UUID随机生成该员工的特征码
        String code = UUID.randomUUID().toString().replace("-", "");
        Employee e = new Employee(null, name, code);// 创建新员工对象
        dao.addEmp(e);// 向数据库插入该员工数据
        e = dao.getEmp(code);// 重新获取已分配员工编号的员工对象
        Session.EMP_SET.add(e);// 新员工加入到全局员工列表中
        return e;// 返回新员工对象
    }

    /**
     * 删除员工
     * 
     * @param id 员工编号
     */
    public static void deleteEmp(int id) {
        Employee e = getEmp(id);// 根据编号获取该员工对象
        if (e != null) {// 如果存在该员工
            Session.EMP_SET.remove(e);// 从员工列表中删除
        }
        dao.deleteEmp(id);// 在数据库中删除该员工信息
        dao.deleteClockInRecord(id);// 在数据库中删除该员工所有打卡记录
        ImageService.deleteFaceImage(e.getCode());// 删除该员工人脸照片文件
        Session.FACE_FEATURE_MAP.remove(e.getCode());// 删除该员工人脸特征
        Session.RECORD_MAP.remove(e.getId());// 删除该员工打卡记录
    }

    /**
     * 获取员工对象
     * 
     * @param id 员工编号
     * @return
     */
    public static Employee getEmp(int id) {
        for (Employee e : Session.EMP_SET) {// 遍历所有员工
            if (e.getId().equals(id)) {// 如果编号是一样的
                return e;// 返回该员工
            }
        }
        return null;// 没找到返回null
    }

    /**
     * 获取员工对象
     * 
     * @param code 员工特征码
     * @return
     */
    public static Employee getEmp(String code) {
        for (Employee e : Session.EMP_SET) {// 遍历所有员工
            if (e.getCode().equals(code)) {// 如果特征码是一样的
                return e;// 返回该员工
            }
        }
        return null;// 没找到返回null
    }

    /**
     * 添加打卡记录
     * 
     * @param e
     */
    public static void addClockInRecord(Employee e) {
        Date now = new Date();// 当前时间
        // 为该员工添加当前时间的打卡记录
        dao.addCLockInRecord(e.getId(), now);
        // 如果全局会话中没有该员工的打卡记录
        if (!Session.RECORD_MAP.containsKey(e.getId())) {
            // 为该员工添加空记录
            Session.RECORD_MAP.put(e.getId(), new HashSet<>());
        }
        // 在该员工的打卡记录中添加新的打卡时间
        Session.RECORD_MAP.get(e.getId()).add(now);
    }

    /**
     * 加载所有人的打卡记录
     */
    public static void loadAllClockInRecord() {
        // 获取打卡记录数据
        String record[][] = dao.getAllClockInRecord();
        if (record == null) {// 如果数据库中不存在打卡数据
            System.err.println("表中无打卡数据");
            return;
        }
        // 遍历所有打卡记录
        for (int i = 0, length = record.length; i < length; i++) {
            String r[] = record[i];// 获取第i行记录
            Integer id = Integer.valueOf(r[0]);// 获取员工编号
            // 如果全局会话中没有该员工的打卡记录
            if (!Session.RECORD_MAP.containsKey(id)) {
                // 为该员工添加空记录
                Session.RECORD_MAP.put(id, new HashSet<>());
            }
            try {
                // 日期时间字符串转为日期对象
                Date recodeDate = DateTimeUtil.dateOf(r[1]);
                // 在该员工的打卡记录中添加新的打卡时间
                Session.RECORD_MAP.get(id).add(recodeDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 加载作息时间
     */
    public static void loadWorkTime() {
        // 从数据库中获取作息时间，并赋值给全局回话
        Session.worktime = dao.getWorkTime();
    }

    /**
     * 更新作息时间
     * 
     * @param time 新的作息时间
     */
    public static void updateWorkTime(WorkTime time) {
        dao.updateWorkTime(time);// 更新数据库中的作息时间
        Session.worktime = time;// 更新全局会话中的作息时间
    }

    /**
     * 获取某一天所有员工的打卡数据， 键为员工对象，值为员工考勤标记。打卡记录用拼接起来的标志符描述打卡情况。
     * 
     * @param year  年
     * @param month 月
     * @param day   日
     * @return 键为员工对象，值为员工考勤标记
     */
    private static Map<Employee, String> getOneDayRecordData(int year, int month, int day) {
        Map<Employee, String> record = new HashMap<>();// 键为员工对象，值为考勤标记
        // 时间点
        Date zeroTime = null, noonTime = null, lastTime = null, workTime = null, closingTime = null;
        try {
            // 零点
            zeroTime = DateTimeUtil.dateOf(year, month, day, "00:00:00");
            // 中午12点
            noonTime = DateTimeUtil.dateOf(year, month, day, "12:00:00");
            // 一天中最后一秒
            lastTime = DateTimeUtil.dateOf(year, month, day, "23:59:59");
            WorkTime wt = Session.worktime;
            // 上班时间
            workTime = DateTimeUtil.dateOf(year, month, day, wt.getStart());
            // 下班时间
            closingTime = DateTimeUtil.dateOf(year, month, day, wt.getEnd());
        } catch (ParseException e1) {
            e1.printStackTrace();
        }

        for (Employee e : Session.EMP_SET) {// 遍历所有员工
            String report = "";// 员工打卡记录，初始为空
            // 如果打卡记录中存在该员工的记录
            if (Session.RECORD_MAP.containsKey(e.getId())) {
                boolean isAbsent = true;// 默认为缺席状态
                // 获取该员工的所有打卡记录
                Set<Date> lockinSet = Session.RECORD_MAP.get(e.getId());
                for (Date r : lockinSet) {// 遍历所有打卡记录
                    // 如果员工在此日期内有打卡记录
                    if (r.after(zeroTime) && r.before(lastTime)) {
                        isAbsent = false;// 不缺席
                        // 上班前打卡
                        if (r.before(workTime) || r.equals(workTime)) {
                            report += CLOCK_IN;// 追加上班正常打卡标记
                        }
                        // 下班后打卡
                        if (r.after(closingTime) || r.equals(closingTime)) {
                            report += CLOCK_OUT;// 追加下班正常打卡标记
                        }
                        // 上班后，中午前打卡
                        if (r.after(workTime) && r.before(noonTime)) {
                            report += LATE;// 追加迟到标记
                        }
                        // 中午后，下班前打卡
                        if (r.after(noonTime) && r.before(closingTime)) {
                            report += LEFT_EARLY;// 追加早退标记
                        }
                    }
                }
                if (isAbsent) {// 此人在此日期没有打卡记录
                    report = ABSENT;// 指定为缺席标记
                }
            } else {// 如果打卡记录里没有此人记录
                report = ABSENT;// 指定为缺席标记
            }
            record.put(e, report);// 保存该员工的打卡记录
        }
        return record;
    }

    /**
     * 获取日报报表
     * 
     * @param year  年
     * @param month 月
     * @param day   日
     * @return 完整的日报字符串
     */
    public static String getDayReport(int year, int month, int day) {
        Set<String> lateSet = new HashSet<>();// 迟到名单
        Set<String> leftSet = new HashSet<>();// 早退名单
        Set<String> absentSet = new HashSet<>();// 缺席名单
        // 获取这一天所有人的打卡数据
        Map<Employee, String> record = HRService.getOneDayRecordData(year, month, day);
        for (Employee e : record.keySet()) {// 遍历每一个员工
            String oneRecord = record.get(e);// 获取该员工的考勤标记
            // 如果有迟到标记，并且没有正常上班打卡标记
            if (oneRecord.contains(LATE) && !oneRecord.contains(CLOCK_IN)) {
                lateSet.add(e.getName());// 添加到迟到名单
            }
            // 如果有早退标记，并且没有正常下班打卡标记
            if (oneRecord.contains(LEFT_EARLY) && !oneRecord.contains(CLOCK_OUT)) {
                leftSet.add(e.getName());// 添加到早退名单
            }
            // 如果有缺席标记
            if (oneRecord.contains(ABSENT)) {
                absentSet.add(e.getName());// 添加到缺席名单
            }
        }

        StringBuilder report = new StringBuilder();// 报表字符串
        int count = Session.EMP_SET.size();// 获取员工人数
        // 拼接报表内容
        report.append("-----  " + year + "年" + month + "月" + day + "日  -----\n");
        report.append("应到人数：" + count + "\n");

        report.append("缺席人数：" + absentSet.size() + "\n");
        report.append("缺席名单：");
        if (absentSet.isEmpty()) {// 如果缺席名单是空的
            report.append("（空）\n");
        } else {
            // 创建缺席名单的遍历对象
            Iterator<String> it = absentSet.iterator();
            while (it.hasNext()) {// 遍历名单
                // 在报表中添加缺席员工的名字
                report.append(it.next() + " ");
            }
            report.append("\n");
        }

        report.append("迟到人数：" + lateSet.size() + "\n");
        report.append("迟到名单：");
        if (lateSet.isEmpty()) {// 如果迟到名单是空的
            report.append("（空）\n");
        } else {
            // 创建迟到名单的遍历对象
            Iterator<String> it = lateSet.iterator();
            while (it.hasNext()) {// 遍历名单
                // 在报表中添加迟到员工的名字
                report.append(it.next() + " ");
            }
            report.append("\n");
        }

        report.append("早退人数：" + leftSet.size() + "\n");
        report.append("早退名单：");
        if (leftSet.isEmpty()) {// 如果早退名单是空的
            report.append("（空）\n");
        } else {
            // 创建早退名单的遍历对象
            Iterator<String> it = leftSet.iterator();
            while (it.hasNext()) {// 遍历名单
                // 在报表中添加早退员工的名字
                report.append(it.next() + " ");
            }
            report.append("\n");
        }
        return report.toString();
    }

    /**
     * 获取月报数据。二维数组第一列为员工名称，第二列值最后一列为year年month月1日至最后一日的打卡情况
     * 
     * @param year  年
     * @param month 月
     * @return
     */
    public static String[][] getMonthReport(int year, int month) {
        int lastDay = DateTimeUtil.getLastDay(year, month);// 此月最大天数
        int count = Session.EMP_SET.size();// 总人数
        // 报表数据键值对，键为员工对象，值为该员工从第一天日至最后一天的考勤标记列表
        Map<Employee, ArrayList<String>> reportCollectioin = new HashMap<>();
        for (int day = 1; day <= lastDay; day++) {// 从第一天遍历至最后一天
            // 获取这一天所有人的打卡数据
            Map<Employee, String> recordOneDay = HRService.getOneDayRecordData(year, month, day);
            for (Employee e : recordOneDay.keySet()) {// 遍历每一个人
                // 如果报表中没有此员工的记录
                if (!reportCollectioin.containsKey(e)) {
                    // 为该员工添加空列表，列表长度为最大天数
                    reportCollectioin.put(e, new ArrayList<>(lastDay));
                }
                // 向该员工的打卡记录列表中添加这一天的考勤标记
                reportCollectioin.get(e).add(recordOneDay.get(e));
            }
        }

        // 报表数据数组，行数为员工人数，列数为最大天数 + 1（姓名列）
        String report[][] = new String[count][lastDay + 1];
        int row = 0;// 行索引，从第一行开始遍历
        // 遍历报表数据键值对中的每一个员工
        for (Employee e : reportCollectioin.keySet()) {
            report[row][0] = e.getName();// 第一列为员工名
            // 获取该员工考勤标记列表
            ArrayList<String> list = reportCollectioin.get(e);
            // 遍历每一个考勤标记
            for (int i = 0, length = list.size(); i < length; i++) {
                report[row][i + 1] = "";// 从第二列开始，默认值为空字符串
                String record = list.get(i);// 获取此列的考勤标记
                if (record.contains(ABSENT)) {// 如果存在缺席标记
                    report[row][i + 1] = "【缺席】";// 该列标记为缺席
                }
                // 如果存在正常上班打卡记录和正常下班打卡记录
                else if (record.contains(CLOCK_IN) && record.contains(CLOCK_OUT)) {// 如果是全勤
                    report[row][i + 1] = "";// 该列标记为空字符串
                } else {
                    // 如果有迟到记录，并且无正常上班打卡记录
                    if (record.contains(LATE) && !record.contains(CLOCK_IN)) {
                        report[row][i + 1] += "【迟到】";// 该列标记为迟到
                    }
                    // 如果有早退记录，并且无下班打卡记录
                    if (record.contains(LEFT_EARLY) && !record.contains(CLOCK_OUT)) {
                        report[row][i + 1] += "【早退】";// 该列标记为早退
                    }

                    // 如果无迟到记录，并且无上班打卡记录
                    if (!record.contains(LATE) && !record.contains(CLOCK_IN)) {
                        report[row][i + 1] += "【上班未打卡】";// 该列标记为上班未打卡
                    }
                    // 如果无早退记录，并且无下班打卡记录
                    if (!record.contains(LEFT_EARLY) && !record.contains(CLOCK_OUT)) {
                        report[row][i + 1] += "【下班未打卡】";// 该列标记为下班未打卡
                    }
                }
            }
            row++;// 行索引递增
        }
        return report;
    }
}
