package com.mr.clock.session;

import java.awt.image.BufferedImage;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import com.arcsoft.face.FaceFeature;
import com.mr.clock.pojo.Employee;
import com.mr.clock.pojo.User;
import com.mr.clock.pojo.WorkLocation;
import com.mr.clock.pojo.WorkTime;
import com.mr.clock.service.*;
import com.mr.clock.util.JDBCUtil;

/**
 * ȫ�ֻỰ����������ȫ������
 */
public class Session {

    /**
     * ��ʱӦ�ڵ�λ��
     */
    public static WorkLocation workLocation = null;

    /**
     * ��ǰ��¼����Ա
     */
    public static User user = null;
    /**
     * ��ǰ��Ϣʱ��
     */
    public static WorkTime worktime = null;
    /**
     * ȫ��Ա��
     */
    public static final HashSet<Employee> EMP_SET = new HashSet<>();
    /**
     * ȫ����������
     */
    public static final HashMap<String, FaceFeature> FACE_FEATURE_MAP = new HashMap<>();
    /**
     * ȫ������ͼ��
     */
    public static final HashMap<String, BufferedImage> IMAGE_MAP = new HashMap<>();
    /**
     * ȫ���򿨼�¼
     */
    public static final HashMap<Integer, Set<Date>> RECORD_MAP = new HashMap<>();

    /**
     * ��ʼ��ȫ����Դ
     */
    public static void init() {
        ImageService.loadAllImage();// ������������ͼ���ļ�
        LocationService.loadLocation();
        HRService.loadWorkTime();// ������Ϣʱ��
        HRService.loadAllEmp();// ��������Ա��
        HRService.loadAllClockInRecord();// �������д򿨼�¼
        FaceEngineService.loadAllFaceFeature();// ����������������
    }

    /**
     * �ͷ�ȫ����Դ
     */
    public static void dispose() {
        FaceEngineService.dispost();// �ͷ�����ʶ������
        CameraService.releaseCamera();// �ͷ�����ͷ
        JDBCUtil.closeConnection();// �ر����ݿ�����
    }
}
