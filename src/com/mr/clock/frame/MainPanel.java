package com.mr.clock.frame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JToggleButton;

import com.mr.clock.pojo.Employee;
import com.mr.clock.service.CameraService;
import com.mr.clock.service.FaceEngineService;
import com.mr.clock.service.HRService;
import com.mr.clock.session.Session;
import com.mr.clock.util.DateTimeUtil;

/**
 * 主面板
 * 
 * @author mingrisoft
 *
 */
public class MainPanel extends JPanel {
    private MainFrame parent;// 主窗体
    private JToggleButton daka;// 打卡按钮
    private JButton kaoqin;// 考勤按钮
    private JButton yuangong;// 员工按钮
    private JTextArea area;// 提示信息文本域
    private DetectFaceThread dft;// 人脸识别线程
    private JPanel center;// 中部面板

    public MainPanel(MainFrame parent) {
        this.parent = parent;
        init();// 组件初始化
        addListener();// 为组件添加监听
    }

    /**
     * 组件初始化
     */
    private void init() {
        parent.setTitle("MR人脸识别打卡系统");

        center = new JPanel();// 中部面板
        center.setLayout(null);// 采用绝对布局

        area = new JTextArea();
        area.setEditable(false);// 文本域不可编辑
        area.setFont(new Font("黑体", Font.BOLD, 18));// 字体
        JScrollPane scroll = new JScrollPane(area);// 文本域放入滚动面板
        scroll.setBounds(0, 0, 275, 380);// 滚动面板的坐标与宽高
        center.add(scroll);

        daka = new JToggleButton("打  卡");
        daka.setFont(new Font("黑体", Font.BOLD, 40));// 字体
        daka.setBounds(330, 300, 240, 70);// 打卡按钮的坐标与宽高
        center.add(daka);

        JPanel blakPanel = new JPanel();// 纯黑面板
        blakPanel.setBounds(286, 16, 320, 240);// 黑色面板的坐标与宽高
        blakPanel.setBackground(Color.BLACK);// 黑色背景
        center.add(blakPanel);

        setLayout(new BorderLayout());// 主面板采用边界布局
        add(center, BorderLayout.CENTER);

        JPanel bottom = new JPanel();// 底部面板
        kaoqin = new JButton("考勤报表");
        yuangong = new JButton("员工管理");
        bottom.add(kaoqin);// 添加底部组件
        bottom.add(yuangong);
        add(bottom, BorderLayout.SOUTH);
    }

    /**
     * 为组件添加监听
     */
    private void addListener() {
        // 考勤按钮的事件
        kaoqin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (Session.user == null) {// 如果没有管理员登录
                    // 创建登录对话框
                    LoginDialog ld = new LoginDialog(parent);
                    ld.setVisible(true);// 展示登录对话框
                }
                if (Session.user != null) {// 如果管理员已登录
                    // 创建考勤报表面板
                    AttendanceManagementPanel amp = new AttendanceManagementPanel(parent);
                    parent.setPanel(amp);// 主窗体切换至考勤面板
                    releaseCamera();// 释放摄像头
                }
            }
        });
        // 员工按钮的事件
        yuangong.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (Session.user == null) {// 如果没有管理员登录
                    // 创建登录对话框
                    LoginDialog ld = new LoginDialog(parent);
                    ld.setVisible(true);// 展示登录对话框
                }
                if (Session.user != null) {// 如果管理员已登录
                    // 创建员工管理面板
                    EmployeeManagementPanel emp = new EmployeeManagementPanel(parent);
                    parent.setPanel(emp);// 主窗体切换至考勤面板
                    releaseCamera();// 释放摄像头资源
                }
            }
        });
        // 打卡按钮的事件
        daka.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (daka.isSelected()) {// 如果打卡按钮是选中状态
                    // 文本域添加提示信息
                    area.append("正在开启摄像头，请稍后.......\n");
                    daka.setEnabled(false);// 打卡按钮不可用
                    daka.setText("关闭摄像头");// 更改打卡按钮的文本
                    // 创建启动摄像头的临时线程
                    Thread cameraThread = new Thread() {
                        public void run() {
                            // 如果摄像头可以正常开启
                            if (CameraService.startCamera()) {
                                area.append("请面向摄像头打卡。\n");// 添加提示
                                daka.setEnabled(true);// 打卡按钮可用
                                // 获取摄像头画面面板
                                JPanel cameraPanel = CameraService.getCameraPanel();
                                // 设置面板的坐标与宽高
                                cameraPanel.setBounds(286, 16, 320, 240);
                                center.add(cameraPanel);// 放到中部面板当中
                            } else {
                                // 弹出提示
                                JOptionPane.showMessageDialog(parent, "未检测到摄像头！");
                                releaseCamera();// 释放摄像头资源
                                return;// 停止方法
                            }
                        }
                    };
                    cameraThread.start();// 启动临时线程
                    dft = new DetectFaceThread();// 创建人脸识别线程
                    dft.start();// 启动人脸识别线程
                } else {// 如果打卡按钮不是选中状态
                    releaseCamera();// 释放摄像头资源
                }
            }
        });
    }

    /**
     * 释放摄像头及面板中的一些列资源
     */
    private void releaseCamera() {
        CameraService.releaseCamera();// 释放摄像头
        area.append("摄像头已关闭。\n");// 添加提示信息
        if (dft != null) {// 如果人脸识别线程被创建
            dft.stopThread();// 停止线程
        }
        daka.setText("打  卡");// 更改打卡按钮的文本
        daka.setSelected(false);// 打卡按钮变为未选中状态
        daka.setEnabled(true);// 打卡按钮可用
    }

    /**
     * 人脸识别线程
     * 
     * @author mingrisoft
     *
     */
    private class DetectFaceThread extends Thread {
        boolean work = true;// 人脸识别线程是否继续扫描image

        @Override
        public void run() {
            while (work) {
                // 如果摄像头已开启
                if (CameraService.cameraIsOpen()) {
                    // 获取摄像头的当前帧
                    BufferedImage frame = CameraService.getCameraFrame();
                    if (frame != null) {// 如果可以获得到有效帧
                        // 获取当前帧中出现的人脸对应的特征码
                        String code = FaceEngineService.detectFace(FaceEngineService.getFaceFeature(frame));
                        if (code != null) {// 如果特征码不为null，表明画面中存在某员工的人脸
                            Employee e = HRService.getEmp(code);// 根据特征码获取员工对象
                            HRService.addClockInRecord(e);// 为此员工添加打卡记录
                            // 文本域添加提示信息
                            area.append("\n" + DateTimeUtil.dateTimeNow() + " \n");
                            area.append(e.getName() + " 打卡成功。\n\n");
                            releaseCamera();// 释放摄像头
                        }
                    }
                }
            }
        }

        public synchronized void stopThread() {// 停止人脸识别线程
            work = false;
        }
    }
}
