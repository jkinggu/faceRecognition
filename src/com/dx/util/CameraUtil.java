package com.dx.util;

import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfRect;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;
import org.opencv.videoio.VideoCapture;
import org.opencv.videoio.Videoio;

import com.dx.inter.FaceLogsInterface;
import com.dx.inter.FaceadminInteraface;
import com.dx.inter.ParamSetupInterface;
import com.dx.inter.ZkzInterface;
import com.dx.pojo.FaceLog;
import com.dx.pojo.Faceadmin;
import com.dx.pojo.Zkzdata;
import com.dx.service.FaceLogsImpl;
import com.dx.service.FaceadminImpl;
import com.dx.service.ParamSetupImpl;
import com.dx.service.ZkzInterImpl;

/**
 * @author fang
 *
 * @Date 2019��5��17��
 *
 *       ��Ŀ�� FaceRecongnition
 *
 * @version 1.0
 */
public class CameraUtil {
	static {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
	}

	public static void paintComponent(Graphics g, BufferedImage mImg, ImageObserver observer) {
		if (mImg != null) {
			g.drawImage(mImg, 0, 0, mImg.getWidth(), mImg.getHeight(), observer);
		}
	}

	private static void saveImage(Mat bigImg, Mat iconImg) {
		if (!bigImg.empty()) {
			String name = UUID.randomUUID().toString().replaceAll("-", "");
			String filename = "D:\\faceimages/faceimage/" + name + ".jpg";
			Imgcodecs.imwrite(filename, bigImg);// ����ԭͼ
			String filename2 = "D:\\faceimages/faceimage/" + name + ".png";
			Imgcodecs.imwrite(filename2, iconImg);// ��������ͼ
		}
	}

	public static BufferedImage mat2BI(Mat mat) {
		int dataSize = mat.cols() * mat.rows() * (int) mat.elemSize();
		byte[] data = new byte[dataSize];
		mat.get(0, 0, data);
		int type = mat.channels() == 1 ? BufferedImage.TYPE_BYTE_GRAY : BufferedImage.TYPE_3BYTE_BGR;

		if (type == BufferedImage.TYPE_3BYTE_BGR) {
			for (int i = 0; i < dataSize; i += 3) {
				byte blue = data[i + 0];
				data[i + 0] = data[i + 2];
				data[i + 2] = blue;
			}
		}
		BufferedImage image = new BufferedImage(mat.cols(), mat.rows(), type);
		image.getRaster().setDataElements(0, 0, mat.cols(), mat.rows(), data);
		return image;
	}

	public static Mat detectFace(Mat img) {
		try {
			CascadeClassifier faceDetector = new CascadeClassifier(
					"D:\\faceimages\\opencv\\sources\\data\\haarcascades_cuda/haarcascade_frontalface_alt_tree.xml");// _
			MatOfRect faceDetections = new MatOfRect();
			faceDetector.detectMultiScale(img, faceDetections);
			Rect[] rects = faceDetections.toArray();
			Mat iconImg = new Mat();
			if (rects != null && rects.length == 1) {
				// ��������� ,���������֤��ִ�������
				for (Rect rect : rects) { // ����
					Imgproc.rectangle(img, new Point(rect.x, rect.y),
							new Point(rect.x + rect.width, rect.y + rect.height), new Scalar(0, 255, 245), 2);
					// ͼƬ�и����������Ƭ
					Rect rect2 = new Rect(rect.x, rect.y, rect.width, rect.height);
					Mat roi_img = new Mat(img, rect2);
					roi_img.copyTo(iconImg);
				}

				String name = UUID.randomUUID().toString().replaceAll("-", "");
				String filename = "D:\\faceimages/faceimage/" + name + ".jpg";
				Imgcodecs.imwrite(filename, img);// ����ԭͼ
				String filename2 = "D:\\faceimages/faceimage/" + name + ".png";
				Imgcodecs.imwrite(filename2, iconImg);// ��������ͼ

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return img;
	}
   private static void  saveImagesAndPath() {
	   
	   
	   
   }
	public static VideoCapture getVideoCapture() {
		VideoCapture capture = new VideoCapture();
		capture.open(0);
		if (!capture.isOpened()) {
			throw new RuntimeException("���δ��");
		}
		capture.set(Videoio.CV_CAP_PROP_FRAME_WIDTH, 800);
		capture.set(Videoio.CV_CAP_PROP_FRAME_HEIGHT, 600);
		capture.set(Videoio.CV_CAP_PROP_FPS, 60);// ֡��
		return capture;
	}

	// �����÷���
	public static JFrame getJFrame(JPanel panel) {
		JFrame frame = new JFrame("camera");

		frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		int x = (int) toolkit.getScreenSize().getWidth();
		int y = (int) toolkit.getScreenSize().getHeight();
		frame.setLocation(x / 2 - 700, y / 2 - 384);
		frame.setSize(1400, 768);
		frame.setVisible(true);
		frame.setContentPane(panel);
		return frame;

	}

	/**
	 * //ȡ׼��֤�ϵ���Ƭ ��Ƭ��ַ�̶�
	 * (���֤��Ƭ��D:\\faceimages\\411534199563559387\\411534199563559387zp.bmp���ֳ�������D:\\faceimages/ʱ���.png��׼��֤��Ƭ��D:\faceimages\zkzpho\411534199563559387.JPG)
	 * sfzpho���֤��Ƭ filename�ֳ�ͼƬ��ַ�����ݿ� zkzpho׼��֤��Ƭ
	 */
	public static String getStrByAllpho(String upersonnum, String filename) {
		String str = "";
		String strint = "";
		String xingming = "";
		String xingbie = "";
		FaceLogsInterface impl = new FaceLogsImpl();
		FaceadminInteraface faceservice = new FaceadminImpl();
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date date = new Date();
			String sfzpho = "D:\\faceimages\\faceimage\\" + upersonnum + "/" + upersonnum + "zp.bmp";
			String filepath = "D:\\faceimages\\faceimage\\" + upersonnum + "/" + upersonnum + "";
			AFRTest af = new AFRTest();
			Float xsd = af.compareImage(filename, sfzpho);
			if (xsd < 50) {
				str = "�Ǳ������֤";
				strint = "1";
			} else {
				// ��ѯ�����֤�ŵ�׼��֤����Ϣ
				ZkzInterface zkzimpl = new ZkzInterImpl();
				Zkzdata zkz = zkzimpl.findByPersonnum(upersonnum);
				if (zkz == null) {
					str = "�ǿ���";// ��ע����Ա
					strint = "2";
				} else {
					xingming = zkz.getXingming();
					xingbie = zkz.getXingbie();
					String sj = zkz.getSj1().substring(0, 10);// ��-��-��
					String dd = zkz.getDd1();// �ص�
					String kc = zkz.getKc1();// ����
					// System.out.println(sj+"----"+dd+"----"+kc);
					// �ж��Ƿ�Ϊ���ù���ʱ��Ŀ���
					ParamSetupInterface paramimpl = new ParamSetupImpl();
					Integer paramc = paramimpl.getParamSetupBySj(sj);
					if (paramc != 0) {// ���ڶ�
						paramc = paramimpl.getParamSetupByDd(sj, dd);
						if (paramc != 0) {
							paramc = paramimpl.getParamSetupByKc(sj, dd, kc);
							if (paramc != 0) {
								// ������Ҫ��ʾ׼��֤��Ϣ��
								strint = "7";// ͨ��
								str = "ͨ��";
								// str = zkz.toString();//׼��֤��Ϣ��ʾ
								// zkzdata = zkz ;
								// setZkzdata(zkz);
							} else {
								str = "��������ȷ";
								strint = "6";
							}
						} else {
							str = "���Եص㲻��ȷ";
							strint = "5";
						}
					} else {
						str = "����ʱ�䲻��ȷ";
						strint = "3";
					}

				}
			}
			FaceLog faceLog = new FaceLog();
			faceLog.setRenlianphoto(filename);
			faceLog.setSfz(upersonnum);
			faceLog.setRemarks(xsd + "");// ���ƶ�
			faceLog.setSfzphoto(sfzpho);
			faceLog.setShijian(sdf.format(date));
			if (strint.equals("7")) {
				faceLog.setShibieleixing("ͨ��");
			} else {
				faceLog.setShibieleixing(str);
			}
			faceLog.setShibieleixingint(strint);
			faceLog.setXingming(xingming);
			faceLog.setXingbie(xingbie);
			// ��ȡ�û���½���Σ���ȷ��Ϊ�ڼ������ԣ������ѯʹ��
			Faceadmin admin = faceservice.getFaceadmin();
			// System.out.println("admin------"+admin);
			if (admin != null) {
				faceLog.setDenglumana(admin.getBmname());
				faceLog.setChangci(admin.getCurchangci());
				// ���˸ó��ε����һ������
				FaceLog lastfaceLog = impl.getLastFaceLog(upersonnum, admin.getCurchangci());
				if (lastfaceLog == null) {
					faceLog.setRenzcount("1");
				} else {
					String coun = lastfaceLog.getRenzcount();
					String shibieint = lastfaceLog.getShibieleixingint();
					// System.out.println("coun------"+coun+"-------"+shibieint);
					if (coun != null && !"".equals(coun)) {
						if (shibieint.equals("7")) {
							// ͨ�������ٱ���renzcount,ֻ�ǵ��������һ������������ܽ�����
						} else {
							Integer cc = Integer.parseInt(coun) + 1;
							faceLog.setRenzcount(cc + "");
						}
					}
				}
			}
			impl.insertFaceLogs(faceLog);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return str;
	}

}
