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
 * @Date 2019年5月17日
 *
 *       项目名 FaceRecongnition
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
			Imgcodecs.imwrite(filename, bigImg);// 保存原图
			String filename2 = "D:\\faceimages/faceimage/" + name + ".png";
			Imgcodecs.imwrite(filename2, iconImg);// 保存缩略图
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
				// 如果有人脸 ,并且有身份证在执行下面的
				for (Rect rect : rects) { // 画框
					Imgproc.rectangle(img, new Point(rect.x, rect.y),
							new Point(rect.x + rect.width, rect.y + rect.height), new Scalar(0, 255, 245), 2);
					// 图片切割，保存人脸照片
					Rect rect2 = new Rect(rect.x, rect.y, rect.width, rect.height);
					Mat roi_img = new Mat(img, rect2);
					roi_img.copyTo(iconImg);
				}

				String name = UUID.randomUUID().toString().replaceAll("-", "");
				String filename = "D:\\faceimages/faceimage/" + name + ".jpg";
				Imgcodecs.imwrite(filename, img);// 保存原图
				String filename2 = "D:\\faceimages/faceimage/" + name + ".png";
				Imgcodecs.imwrite(filename2, iconImg);// 保存缩略图

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
			throw new RuntimeException("相机未打开");
		}
		capture.set(Videoio.CV_CAP_PROP_FRAME_WIDTH, 800);
		capture.set(Videoio.CV_CAP_PROP_FRAME_HEIGHT, 600);
		capture.set(Videoio.CV_CAP_PROP_FPS, 60);// 帧率
		return capture;
	}

	// 测试用方法
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
	 * //取准考证上的照片 照片地址固定
	 * (身份证照片：D:\\faceimages\\411534199563559387\\411534199563559387zp.bmp，现场人脸：D:\\faceimages/时间戳.png，准考证照片：D:\faceimages\zkzpho\411534199563559387.JPG)
	 * sfzpho身份证照片 filename现场图片地址存数据库 zkzpho准考证照片
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
				str = "非本人身份证";
				strint = "1";
			} else {
				// 查询该身份证号的准考证的信息
				ZkzInterface zkzimpl = new ZkzInterImpl();
				Zkzdata zkz = zkzimpl.findByPersonnum(upersonnum);
				if (zkz == null) {
					str = "非考生";// 非注册人员
					strint = "2";
				} else {
					xingming = zkz.getXingming();
					xingbie = zkz.getXingbie();
					String sj = zkz.getSj1().substring(0, 10);// 年-月-日
					String dd = zkz.getDd1();// 地点
					String kc = zkz.getKc1();// 考场
					// System.out.println(sj+"----"+dd+"----"+kc);
					// 判断是否为设置过的时间的考生
					ParamSetupInterface paramimpl = new ParamSetupImpl();
					Integer paramc = paramimpl.getParamSetupBySj(sj);
					if (paramc != 0) {// 日期对
						paramc = paramimpl.getParamSetupByDd(sj, dd);
						if (paramc != 0) {
							paramc = paramimpl.getParamSetupByKc(sj, dd, kc);
							if (paramc != 0) {
								// 这里需要显示准考证信息了
								strint = "7";// 通过
								str = "通过";
								// str = zkz.toString();//准考证信息显示
								// zkzdata = zkz ;
								// setZkzdata(zkz);
							} else {
								str = "考场不正确";
								strint = "6";
							}
						} else {
							str = "考试地点不正确";
							strint = "5";
						}
					} else {
						str = "考试时间不正确";
						strint = "3";
					}

				}
			}
			FaceLog faceLog = new FaceLog();
			faceLog.setRenlianphoto(filename);
			faceLog.setSfz(upersonnum);
			faceLog.setRemarks(xsd + "");// 相似度
			faceLog.setSfzphoto(sfzpho);
			faceLog.setShijian(sdf.format(date));
			if (strint.equals("7")) {
				faceLog.setShibieleixing("通过");
			} else {
				faceLog.setShibieleixing(str);
			}
			faceLog.setShibieleixingint(strint);
			faceLog.setXingming(xingming);
			faceLog.setXingbie(xingbie);
			// 获取用户登陆场次，来确定为第几场考试，方便查询使用
			Faceadmin admin = faceservice.getFaceadmin();
			// System.out.println("admin------"+admin);
			if (admin != null) {
				faceLog.setDenglumana(admin.getBmname());
				faceLog.setChangci(admin.getCurchangci());
				// 该人该场次的最后一条数据
				FaceLog lastfaceLog = impl.getLastFaceLog(upersonnum, admin.getCurchangci());
				if (lastfaceLog == null) {
					faceLog.setRenzcount("1");
				} else {
					String coun = lastfaceLog.getRenzcount();
					String shibieint = lastfaceLog.getShibieleixingint();
					// System.out.println("coun------"+coun+"-------"+shibieint);
					if (coun != null && !"".equals(coun)) {
						if (shibieint.equals("7")) {
							// 通过，不再保存renzcount,只是单纯的添加一条正常结果不管结果如何
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
