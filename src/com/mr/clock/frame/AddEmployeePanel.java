package com.mr.clock.frame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.arcsoft.face.FaceFeature;
import com.mr.clock.pojo.Employee;
import com.mr.clock.service.CameraService;
import com.mr.clock.service.FaceEngineService;
import com.mr.clock.service.HRService;
import com.mr.clock.service.ImageService;
import com.mr.clock.session.Session;

/**
 * 添加新员工面板
 * 
 * @author mingrisoft
 *
 */
public class AddEmployeePanel extends JPanel {
    private MainFrame parent;// 主窗体
    private JLabel message;// 提示
    private JTextField nameField;// 姓名文本框
    private JButton submit;// 提交按钮
    private JButton back;// 返回按钮
    private JPanel center;// 中部面板

    public AddEmployeePanel(MainFrame parent) {
        this.parent = parent;
        init();// 组件初始化
        addListener();// 为组件添加监听
    }

    /**
     * 组件初始化
     */
    private void init() {
        parent.setTitle("录入新员工");// 修改主窗体标题
        JLabel nameLabel = new JLabel("员工名称:", JLabel.RIGHT);
        nameField = new JTextField(15);// 文本框宽度为15
        submit = new JButton("拍照并录入");
        back = new JButton("返回");

        setLayout(new BorderLayout());// 采用边界布局

        JPanel bottom = new JPanel();// 底部面板
        bottom.add(nameLabel);// 添加底部的组件
        bottom.add(nameField);
        bottom.add(submit);
        bottom.add(back);
        add(bottom, BorderLayout.SOUTH);

        center = new JPanel();// 中部面板
        center.setLayout(null);// 中部面板采用绝对布局

        message = new JLabel("正在打开摄像头......");
        message.setFont(new Font("黑体", Font.BOLD, 40));// 设置字体
        message.setBounds((640 - 400) / 2, 20, 400, 50);// 设置组件的坐标和宽高
        center.add(message);

        JPanel blackPanel = new JPanel();// 黑色面板
        blackPanel.setBackground(Color.BLACK);// 背景色为黑色
        blackPanel.setBounds(150, 75, 320, 240);// 设置面板的坐标和宽高
        center.add(blackPanel);

        add(center, BorderLayout.CENTER);

        // 摄像头启动线程
        Thread cameraThread = new Thread() {
            public void run() {
                if (CameraService.startCamera()) {// 如果摄像头成功开启
                    message.setText("请正面面向摄像头");// 更换提示信息
                    // 获取摄像头画面面板
                    JPanel cameraPanel = CameraService.getCameraPanel();
                    // 设置面板的坐标和宽高
                    cameraPanel.setBounds(150, 75, 320, 240);
                    center.add(cameraPanel);// 放到中部面板
                } else {
                    // 弹出提示
                    JOptionPane.showMessageDialog(parent, "未检测到摄像头！");
                    back.doClick();// 出发返回按钮的点击事件
                }
            }
        };
        cameraThread.start();// 开启线程
    }

    /**
     * 为组件添加监听
     */
    private void addListener() {
        // 提交按钮的事件
        submit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e1) {
                String name = nameField.getText().trim();// 获取文本框里的名字
                if (name == null || "".equals(name)) {// 如果是空内容
                    JOptionPane.showMessageDialog(parent, "名字不能为空！");
                    return;// 中断方法
                }
                if (!CameraService.cameraIsOpen()) {// 如果摄像头未开启
                    JOptionPane.showMessageDialog(parent, "摄像头尚未开启，请稍后。");
                    return;
                }
                // 获取当前摄像头捕捉的帧
                BufferedImage image = CameraService.getCameraFrame();
                // 获取此图像中人脸的面部特征
                FaceFeature ff = FaceEngineService.getFaceFeature(image);
                if (ff == null) {// 如果不存在面部特征
                    JOptionPane.showMessageDialog(parent, "未检测到有效人脸信息");
                    return;
                }
                Employee e = HRService.addEmp(name, image);// 添加新员工
                ImageService.saveFaceImage(image, e.getCode());// 保存员工照片文件
                Session.FACE_FEATURE_MAP.put(e.getCode(), ff);// 全局回话记录此人面部特征
                JOptionPane.showMessageDialog(parent, "员工添加成功！");// 弹出提示框
                back.doClick();// 出发返回按钮的点击事件
            }
        });
        // 返回按钮的事件
        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CameraService.releaseCamera();// 释放摄像头
                // 主窗体切换到员工管理面板
                parent.setPanel(new EmployeeManagementPanel(parent));
            }
        });
    }
}
