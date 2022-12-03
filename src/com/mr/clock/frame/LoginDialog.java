package com.mr.clock.frame;

import java.awt.Container;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import com.mr.clock.service.HRService;

/**
 * 登录对话框
 * 
 * @author mingrisoft
 *
 */
public class LoginDialog extends JDialog {
    private JTextField usernameField = null;// 用户名文本框
    private JPasswordField passwordField = null;// 密码输入框
    private JButton loginBtn = null;// 登录按钮
    private JButton cancelBtn = null;// 取消按钮
    private final int WIDTH = 300, HEIGHT = 150;// 对话框的宽高

    public LoginDialog(Frame owner) {
        super(owner, "管理员登录", true);// 阻塞主窗体
        setSize(WIDTH, HEIGHT);// 设置宽高
        // 在主窗体中央显示
        setLocation(owner.getX() + (owner.getWidth() - WIDTH) / 2, owner.getY() + (owner.getHeight() - HEIGHT) / 2);
        init();// 组件初始化
        addListener();// 为组件添加监听
    }

    /**
     * 组件初始化
     */
    private void init() {
        JLabel usernameLabel = new JLabel("用 户 名 ：", JLabel.CENTER);
        JLabel passwordLabel = new JLabel("密    码 ：", JLabel.CENTER);
        usernameField = new JTextField();
        passwordField = new JPasswordField();
        loginBtn = new JButton("登 录");
        cancelBtn = new JButton("取 消");

        Container c = getContentPane();
        c.setLayout(new GridLayout(3, 2));// 3行2列的网格布局
        c.add(usernameLabel);
        c.add(usernameField);
        c.add(passwordLabel);
        c.add(passwordField);
        c.add(loginBtn);
        c.add(cancelBtn);
    }

    /**
     * 为组件添加监听
     */
    private void addListener() {
        // 取消按钮的事件
        cancelBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                LoginDialog.this.dispose();// 销毁登录对话框
            }
        });
        // 登录按钮的事件
        loginBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 获取用户输入的用户名
                String username = usernameField.getText().trim();
                // 获取用户输入的密码
                String password = new String(passwordField.getPassword());
                // 检查用户名和密码是否正确
                boolean result = HRService.userLogin(username, password);
                if (result) {// 如果正确
                    LoginDialog.this.dispose();// 销毁登录对话框
                } else {
                    // 提示用户名、密码错误
                    JOptionPane.showMessageDialog(LoginDialog.this, "用户名或密码有误！");
                }
            }
        });
        // 密码输入框敲击回车的事件
        passwordField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loginBtn.doClick();// 触发登录按钮点击事件
            }
        });
        // 用户名文本框框敲击回车的事件
        usernameField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                passwordField.grabFocus();// 密码输入框获取光标
            }
        });
    }
}
