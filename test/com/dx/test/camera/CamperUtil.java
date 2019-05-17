package com.dx.test.camera;

import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.File;
import java.util.List;
import java.util.UUID;

import javax.imageio.ImageIO;
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

import com.dx.util.FileUtil;

/**
 * @author fang
 *
 * @Date 2019年5月16日
 *
 *       项目名 FaceRecongnition
 *
 * @version 1.0
 */
public class CamperUtil {
	static {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
	}

	public static void paintComponent(Graphics g, BufferedImage mImg, ImageObserver observer) {
		if (mImg != null) {
			g.drawImage(mImg, 0, 0, mImg.getWidth(), mImg.getHeight(), observer);
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
				saveImage(img, iconImg);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return img;
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
}
