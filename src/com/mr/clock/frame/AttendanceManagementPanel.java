package com.mr.clock.frame;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.table.DefaultTableModel;

import com.mr.clock.pojo.WorkTime;
import com.mr.clock.service.HRService;
import com.mr.clock.session.Session;
import com.mr.clock.util.DateTimeUtil;

/**
 * 考勤报表面板
 * 
 * @author mingrisoft
 *
 */
public class AttendanceManagementPanel extends JPanel {
    private MainFrame parent;// 主窗体

    private JToggleButton dayRecordBtn; // 日报按钮
    private JToggleButton monthRecordBtn;// 月报按钮
    private JToggleButton worktimeBtn;// 作息时间设置按钮
    private JButton back;// 返回按钮
    private JButton flushD, flushM;// 分别在日报和月报面板中的刷新按钮
    private JPanel centerdPanel; // 中央面板
    private CardLayout card;// 中央面板使用的卡片布局

    private JPanel dayRecordPanel;// 日报面板
    private JTextArea area;// 日报面板里的文本域
    // 日报面板里的年、月、日下拉列表
    private JComboBox<Integer> yearComboBoxD, monthComboBoxD, dayComboBoxD;
    // 年、月、日下拉列表使用的数据模型
    private DefaultComboBoxModel<Integer> yearModelD, monthModelD, dayModelD;

    private JPanel monthRecordPanel;// 月报面板
    private JTable table;// 月报面板里的表格
    private DefaultTableModel model;// 表格的数据模型
    // 月报面板里的年、月下拉列表
    private JComboBox<Integer> yearComboBoxM, monthComboBoxM;
    // 年、月下拉列表使用的数据模型
    private DefaultComboBoxModel<Integer> yearModelM, monthModelM;

    private JPanel worktimePanel;// 作息时间面板
    // 上班时间的时、分、秒文本框
    private JTextField hourS, minuteS, secondS;
    // 下班时间的时、分、秒文本框
    private JTextField hourE, minuteE, secondE;
    private JButton updateWorktime;// 替换作息时间按钮

    public AttendanceManagementPanel(MainFrame parent) {
        this.parent = parent;
        init();// 组件初始化
        addListener();// 为组件添加监听
    }

    /**
     * 组件初始化
     */
    private void init() {
        WorkTime worktime = Session.worktime;// 获取当前作息时间
        // 修改主窗体标题
        parent.setTitle("考勤报表 (上班时间：" + worktime.getStart() + ",下班时间：" + worktime.getEnd() + ")");
        dayRecordBtn = new JToggleButton("日报");
        dayRecordBtn.setSelected(true);// 日报按钮处于选中状态
        monthRecordBtn = new JToggleButton("月报");
        worktimeBtn = new JToggleButton("作息时间设置");
        // 按钮组，保证三个按钮中只有一个按钮处于选中状态
        ButtonGroup group = new ButtonGroup();
        group.add(dayRecordBtn);
        group.add(monthRecordBtn);
        group.add(worktimeBtn);

        back = new JButton("返回");
        flushD = new JButton("刷新报表");
        flushM = new JButton("刷新报表");

        ComboBoInit();// 下拉列表初始化
        dayRecordInit();// 日报面板初始化
        MonthRecordInit();// 月报面板初始化
        worktimeInit();// 作息时间面板初始化

        card = new CardLayout();// 卡片布局
        centerdPanel = new JPanel(card);// 中部面板采用卡片布局
        // day标签为日报面板
        centerdPanel.add("day", dayRecordPanel);
        // month标签为月报面板
        centerdPanel.add("month", monthRecordPanel);
        // worktime标签为作息时间面板
        centerdPanel.add("worktime", worktimePanel);

        JPanel bottom = new JPanel();// 底部面板
        bottom.add(dayRecordBtn);// 添加底部的组件
        bottom.add(monthRecordBtn);
        bottom.add(worktimeBtn);
        bottom.add(back);

        setLayout(new BorderLayout());// 采用边界布局
        add(centerdPanel, BorderLayout.CENTER);
        add(bottom, BorderLayout.SOUTH);//
    }

    /**
     * 为组件添加监听
     */
    private void addListener() {
        // 日报按钮的事件
        dayRecordBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 卡片布局切换至日报面板
                card.show(centerdPanel, "day");
            }
        });
        // 月报按钮的事件
        monthRecordBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 卡片布局切换至月报面板
                card.show(centerdPanel, "month");
            }
        });
        // 作息时间设置按钮的事件
        worktimeBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 卡片布局切换至作息时间面板
                card.show(centerdPanel, "worktime");
            }
        });
        
        // 返回按钮的事件
        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                parent.setPanel(new MainPanel(parent)); // 主窗体切换到主面板
            }
        });
        // 日报面板刷新按钮的事件
        flushD.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateDayRecord();// 更新日报
            }
        });
        // 月报面板刷新按钮的事件
        flushM.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateMonthRecord();// 更新月报
            }
        });
        // 替换作息时间按钮的事件
        updateWorktime.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String hs = hourS.getText().trim();// 上班的小时
                String ms = minuteS.getText().trim();// 上班的分钟
                String ss = secondS.getText().trim();// 上班的秒
                String he = hourE.getText().trim();// 下班的小时
                String me = minuteE.getText().trim();// 下班的分钟
                String se = secondE.getText().trim();// 下班的秒

                boolean check = true;// 时间校验成功标志
                String startInput = hs + ":" + ms + ":" + ss;// 拼接上班时间
                String endInput = he + ":" + me + ":" + se;// 拼接下班时间
                // 如果上班时间不是正确的时间格式
                if (!DateTimeUtil.checkTimeStr(startInput)) {
                    check = false;// 校验失败
                    // 弹出提示
                    JOptionPane.showMessageDialog(parent, "上班时间的格式不正确");
                }
                // 如果下班时间不是正确的时间格式
                if (!DateTimeUtil.checkTimeStr(endInput)) {
                    check = false;// 校验失败
                    // 弹出提示
                    JOptionPane.showMessageDialog(parent, "下班时间的格式不正确");
                }

                if (check) {// 如果校验通过
                    // 弹出选择对话框，并记录用户选择
                    int confirmation = JOptionPane.showConfirmDialog(parent,
                            "确定做出以下设置？\n上班时间：" + startInput + "\n下班时间：" + endInput, "提示！", JOptionPane.YES_NO_OPTION);
                    if (confirmation == JOptionPane.YES_OPTION) {// 如果用户选择确定
                        WorkTime input = new WorkTime(startInput, endInput);
                        HRService.updateWorkTime(input);// 更新作息时间
                        // 修改标题
                        parent.setTitle("考勤报表 (上班时间：" + startInput + ",下班时间：" + endInput + ")");
                    }
                }
            }
        });

        // 日报面板中的日期下拉列表使用的监听对象
        ActionListener dayD_Listener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateDayRecord();// 更新日报
            }
        };
        dayComboBoxD.addActionListener(dayD_Listener);// 添加监听

        // 日报面板中的年份、月份下拉列表使用的监听对象
        ActionListener yearD_monthD_Listener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 删除日期下拉列表使用的监听对象，防止日期改变后自动触发此监听
                dayComboBoxD.removeActionListener(dayD_Listener);
                updateDayModel();// 更新日下拉列表中的天数
                updateDayRecord();// 更新日报
                // 重新为日期下拉列表添加监听对象
                dayComboBoxD.addActionListener(dayD_Listener);
            }
        };

        yearComboBoxD.addActionListener(yearD_monthD_Listener);// 添加监听
        monthComboBoxD.addActionListener(yearD_monthD_Listener);

        // 月报面板中的年份、月份下拉列表使用的监听对象
        ActionListener yearM_monthM_Listener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateMonthRecord();// 更新月报
            }
        };

        yearComboBoxM.addActionListener(yearM_monthM_Listener);// 添加监听
        monthComboBoxM.addActionListener(yearM_monthM_Listener);
    }

    /**
     * 作息时间面板初始化
     */
    private void worktimeInit() {
        WorkTime worktime = Session.worktime;// 获取当前的作息时间
        // 将上班时间和下班时间分割成时、分、秒数组
        String startTime[] = worktime.getStart().split(":");
        String endTime[] = worktime.getEnd().split(":");

        Font labelFont = new Font("黑体", Font.BOLD, 20);// 字体

        JPanel top = new JPanel();// 顶部面板

        JLabel startLabel = new JLabel("上班时间：");// 文本标签
        startLabel.setFont(labelFont);// 使用指定字体
        top.add(startLabel);

        hourS = new JTextField(3);// 上班时间的时输入框，长度为3
        hourS.setText(startTime[0]);// 默认值为当前上班时间的小时
        top.add(hourS);

        JLabel colon1 = new JLabel(":");
        colon1.setFont(labelFont);
        top.add(colon1);

        minuteS = new JTextField(3);// 上班时间的分输入框
        minuteS.setText(startTime[1]);// 默认值为当前上班时间的分钟
        top.add(minuteS);

        JLabel colon2 = new JLabel(":");
        colon2.setFont(labelFont);
        top.add(colon2);

        secondS = new JTextField(3);// 上班时间的秒输入框
        secondS.setText(startTime[2]);// 默认值为当前上班时间的秒
        top.add(secondS);

        JPanel bottom = new JPanel();// 底部面板

        JLabel endLabel = new JLabel("下班时间：");
        endLabel.setFont(labelFont);
        bottom.add(endLabel);

        hourE = new JTextField(3);// 下班时间的时输入框
        hourE.setText(endTime[0]);// 默认值为当前下班时间的小时
        bottom.add(hourE);

        JLabel colon3 = new JLabel(":");
        colon3.setFont(labelFont);
        bottom.add(colon3);

        minuteE = new JTextField(3);// 下班时间的分输入框
        minuteE.setText(endTime[1]);// 默认值为当前下班时间的分钟
        bottom.add(minuteE);

        JLabel colon4 = new JLabel(":");
        colon4.setFont(labelFont);
        bottom.add(colon4);

        secondE = new JTextField(3);// 下班时间的秒输入框
        secondE.setText(endTime[2]);// 默认值为当前下班时间的秒
        bottom.add(secondE);

        worktimePanel = new JPanel();
        worktimePanel.setLayout(null);// 作息时间面板采用绝对布局

        JPanel center = new JPanel();// 作息面板中央显示的面板
        center.setLayout(new GridLayout(2, 1));// 采用2行1列的网格布局
        center.add(top);// 第1行放顶部面板
        center.add(bottom);// 第2行放底部面板

        center.setBounds(100, 60, 400, 150);// 设置面板的坐标和宽高
        worktimePanel.add(center);

        updateWorktime = new JButton("替换作息时间");
        updateWorktime.setFont(new Font("黑体", Font.BOLD, 20));
        updateWorktime.setBounds(220, 235, 170, 55);// 按钮的坐标和宽高
        worktimePanel.add(updateWorktime);
    }

    /**
     * 日报面板初始化
     */
    private void dayRecordInit() {
        area = new JTextArea();
        area.setEditable(false);// 文本域不可编辑
        area.setFont(new Font("宋体", Font.BOLD, 24));
        JScrollPane scroll = new JScrollPane(area);// 文本域放到滚动面板中

        dayRecordPanel = new JPanel();
        dayRecordPanel.setLayout(new BorderLayout());// 日报面板采用边界布局
        dayRecordPanel.add(scroll, BorderLayout.CENTER);// 滚动面板在中部显示

        JPanel top = new JPanel();// 顶部面板
        top.setLayout(new FlowLayout());// 采用流布局
        top.add(yearComboBoxD);// 年下拉列表
        top.add(new JLabel("年"));// 文本标签
        top.add(monthComboBoxD);// 月下拉列表
        top.add(new JLabel("月"));
        top.add(dayComboBoxD);// 日下拉列表
        top.add(new JLabel("日"));
        top.add(flushD);// 日报面板的刷新按钮
        dayRecordPanel.add(top, BorderLayout.NORTH);

        updateDayRecord();// 更新日报
    }

    /**
     * 月报面板初始化
     */
    private void MonthRecordInit() {
        JPanel top = new JPanel();// 顶部面板
        top.add(yearComboBoxM);// 年下拉列表
        top.add(new JLabel("年"));
        top.add(monthComboBoxM);// 年下拉列表
        top.add(new JLabel("月"));
        top.add(flushM);// 月报面板的刷新按钮

        monthRecordPanel = new JPanel();
        monthRecordPanel.setLayout(new BorderLayout());// 月报面板采用边界布局
        monthRecordPanel.add(top, BorderLayout.NORTH);

        model = new DefaultTableModel();// 表格数据模型
        table = new JTable(model);// 表格采用数模型
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);// 关闭自动调整宽度
        JScrollPane tableScroll = new JScrollPane(table);// 表格放入滚动面板
        monthRecordPanel.add(tableScroll, BorderLayout.CENTER);

        updateMonthRecord();// 更新月报
    }

    /**
     * 下拉列表初始化
     */
    private void ComboBoInit() {
        // 下拉列表的数据模型初始化
        yearModelD = new DefaultComboBoxModel<>();
        monthModelD = new DefaultComboBoxModel<>();
        dayModelD = new DefaultComboBoxModel<>();
        yearModelM = new DefaultComboBoxModel<>();
        monthModelM = new DefaultComboBoxModel<>();

        // 获取当前时间的年、月、日、时、分、秒数组
        Integer now[] = DateTimeUtil.now();

        // 获取当前时间前后十年的年份，添加到年下拉列表的数据模型中
        for (int i = now[0] - 10; i <= now[0] + 10; i++) {
            yearModelD.addElement(i);
            yearModelM.addElement(i);
        }
        yearComboBoxD = new JComboBox<>(yearModelD);// 日报的年下拉列表
        yearComboBoxD.setSelectedItem(now[0]);// 默认选中今年
        yearComboBoxM = new JComboBox<>(yearModelM);// 月报的年下拉列表
        yearComboBoxM.setSelectedItem(now[0]);// 默认选中今年

        // 遍历12个月,，添加到月下拉列表的数据模型中
        for (int i = 1; i <= 12; i++) {
            monthModelD.addElement(i);
            monthModelM.addElement(i);
        }
        monthComboBoxD = new JComboBox<>(monthModelD);// 日报的月下拉列表
        monthComboBoxD.setSelectedItem(now[1]);// 默认选中本月
        monthComboBoxM = new JComboBox<>(monthModelM);// 日报的月下拉列表
        monthComboBoxM.setSelectedItem(now[1]);// 默认选中本月

        updateDayModel();// 更新日下拉列表中的天数
        dayComboBoxD = new JComboBox<>(dayModelD);// 日报的日下拉列表
        dayComboBoxD.setSelectedItem(now[2]);// 默认选中今天
    }

    /**
     * 更新日下拉列表中的天数
     */
    private void updateDayModel() {
        // 获取年下拉列表选中的值
        int year = (int) yearComboBoxD.getSelectedItem();
        // 获取月下拉列表选中的值
        int month = (int) monthComboBoxD.getSelectedItem();
        // 获取选中月份的最大天数
        int lastDay = DateTimeUtil.getLastDay(year, month);
        dayModelD.removeAllElements();// 清除已有元素
        for (int i = 1; i <= lastDay; i++) {
            dayModelD.addElement(i);// 将每一天都添加到日下拉列表数据模型中
        }
    }

    /**
     * 更新日报
     */
    private void updateDayRecord() {
        // 获取日报面板中选中的年、月、日
        int year = (int) yearComboBoxD.getSelectedItem();
        int month = (int) monthComboBoxD.getSelectedItem();
        int day = (int) dayComboBoxD.getSelectedItem();
        // 获取日报报表
        String report = HRService.getDayReport(year, month, day);
        area.setText(report);// 日报报表覆盖到文本域中
    }

    /**
     * 更新月报
     */
    private void updateMonthRecord() {
        // 获取月报面板中选中的年、月
        int year = (int) yearComboBoxM.getSelectedItem();
        int month = (int) monthComboBoxM.getSelectedItem();

        int lastDay = DateTimeUtil.getLastDay(year, month);// 此月最大天数

        String tatle[] = new String[lastDay + 1];// 表格列头
        tatle[0] = "员工姓名";// 第一列是员工姓名
        // 后面n为选中月份的每一天日期
        for (int day = 1; day <= lastDay; day++) {
            tatle[day] = year + "年" + month + "月" + day + "日";
        }
        // 获取月报数据
        String values[][] = HRService.getMonthReport(year, month);
        model.setDataVector(values, tatle);// 将数据和列头放入表格数据模型中
        int columnCount = table.getColumnCount();// 获取表格中的所有列数
        for (int i = 1; i < columnCount; i++) {// 遍历每一列
            // 从第2列开始，没一列都设为100宽度
            table.getColumnModel().getColumn(i).setPreferredWidth(100);
        }
    }
}