package com.dx.util;

import java.awt.Toolkit;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfRect;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;
import org.opencv.videoio.VideoCapture;
import org.opencv.videoio.Videoio;

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
    //当Mat为空时，出现静态边框，去除静态边框使用
	public static Mat detectFace(Mat img) {
		try {
			CascadeClassifier faceDetector = new CascadeClassifier("D:\\faceimages\\opencv\\sources\\data\\haarcascades_cuda/haarcascade_frontalface_alt_tree.xml");// _	     
			MatOfRect faceDetections = new MatOfRect();
			faceDetector.detectMultiScale(img, faceDetections);
			Rect[] rects = faceDetections.toArray();
			if (rects != null) {
				// 如果有人脸 ,并且有身份证在执行下面的 
				for (Rect rect : rects) { // 画框
					Imgproc.rectangle(img, new Point(rect.x, rect.y),
							new Point(rect.x + rect.width, rect.y + rect.height), new Scalar(0, 255, 245), 2);					
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return img;
	}
	public static VideoCapture getVideoCapture() {
		VideoCapture capture = new VideoCapture();
		capture.open(0);
		if (!capture.isOpened()) {
			throw new RuntimeException("相机未打开");
		}
		capture.set(Videoio.CV_CAP_PROP_FRAME_WIDTH, Const.CV_CAP_PROP_FRAME_WIDTH);
		capture.set(Videoio.CV_CAP_PROP_FRAME_HEIGHT, Const.CV_CAP_PROP_FRAME_HEIGHT);
		capture.set(Videoio.CV_CAP_PROP_FPS,Const.CV_CAP_PROP_FPS);// 帧率
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
}
