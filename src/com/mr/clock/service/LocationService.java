package com.mr.clock.service;

import com.mr.clock.dao.DAO;
import com.mr.clock.dao.DAOFactory;
import com.mr.clock.pojo.WorkLocation;
import com.mr.clock.session.Session;
import com.sun.corba.se.spi.orbutil.threadpool.Work;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;

import java.io.UnsupportedEncodingException;
import javax.net.ssl.HttpsURLConnection;
import java.util.Enumeration;


public class LocationService {
    static DAO dao = DAOFactory.getDAO();
    public static WorkLocation getLocation() {
        try {
            Enumeration<NetworkInterface> enumeration = NetworkInterface.getNetworkInterfaces();
            InetAddress ip = null;
            while (enumeration.hasMoreElements()) {
                NetworkInterface ni = enumeration.nextElement();
                //过滤掉虚拟机、未开启、回环接口
                if (ni.isVirtual() || !ni.isUp() || ni.isLoopback()) {
                    //System.out.println("虚拟机接口或接口未启动");
                    continue;
                    //根据接口名称进一步过滤：WiFi驱动一般名称中包含“Intel”，有线连接包含"Realtek"
                } else if (ni.getDisplayName().contains("Intel") || ni.getDisplayName().contains("Realtek") || ni.getDisplayName().contains("Wi")) {
                    Enumeration<InetAddress> address = ni.getInetAddresses();
                    while (address.hasMoreElements()) {
                        ip = address.nextElement();
                        if (ip instanceof Inet4Address) {
//                            System.out.println(ip.getHostAddress());


                            String IP = getNowIPv4();
//                            System.out.println(IP);
//                            System.exit(0);
                            String key = "981118347c34901d75d295b638022ded";
                            String url_head = "https://restapi.amap.com/v3/ip?output=json&key=";
                            String Url_str = url_head + key + "&ip=" + IP;
//                            System.out.println(Url_str);
//                            System.exit(123);
                            URL url = new URL(Url_str);
                            HttpsURLConnection urlcon = (HttpsURLConnection) url.openConnection();
                            urlcon.connect();
                            BufferedReader br = new BufferedReader(
                                    new InputStreamReader(urlcon.getInputStream(), "utf-8"));
                            StringBuffer sb = new StringBuffer();
                            String content = null;
                            while ((content = br.readLine()) != null) {
                                sb.append(content);
                            }
                            //去除首位大括号用
                            String contont_src_tem = sb.toString();
//                            System.out.println(contont_src_tem);
//                            System.exit(-10);
                            String contont_src = sb.substring(1, contont_src_tem.length() - 1);
                            //System.out.println(contont_src);
                            //将经纬度与前面的信息分开
                            String[] s = contont_src.split("\"rectangle\"");
                            String s_a = s[0];
                            String s_b = "\"rectangle\"" + s[1];
                            //跟前半部分切片
                            String[] before_split = s_a.split(",");

                            String province = before_split[3];
                            String city = before_split[4];
                            province = province.split("\"")[3];
                            city = city.split("\"")[3];
//                            System.out.println(province);
//                            System.out.println(city);
//                            System.exit(-1);
//                            String[] place = {province, city};
//                            return place;
                            return new WorkLocation(province, city);
                        }

                    }
                }
            }
            return new WorkLocation("wrong", "wrong");
        } catch (SocketException e) {
            throw new RuntimeException(e);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void updateWorkLocation(WorkLocation workLocation) {
        dao.updateWorkLocation(workLocation);// 更新数据库中的作息时间
        Session.workLocation = workLocation;// 更新全局会话中的作息时间
    }

    public static void loadLocation() {
        Session.workLocation = dao.getLocation();
    }

    private static String getNowIPv4() throws IOException {
        String ip = null;
        BufferedReader br = null;
        try {
            URL url = new URL("https://v6r.ipip.net/?format=callback");
            br = new BufferedReader(new InputStreamReader(url.openStream()));
            String s = "";
            StringBuffer sb = new StringBuffer("");
            String webContent = "";
            while ((s = br.readLine()) != null) {
                sb.append(s + "\r\n");
            }
            webContent = sb.toString();
            int start = webContent.indexOf("(") + 2;
            int end = webContent.indexOf(")") - 1;
            webContent = webContent.substring(start, end);
            ip = webContent;
        } finally {
            if (br != null)
                br.close();
        }
        return ip;
    }

}
