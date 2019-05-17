package com.dx.test.camera;

import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfDouble;
import org.opencv.core.MatOfRect;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;
import org.opencv.objdetect.HOGDescriptor;
import org.opencv.videoio.VideoCapture;
import org.opencv.videoio.Videoio;

/**
 * @author fang
 *
 
 *
 * @version .0
 */
public class CameraTest extends JPanel {
	private  BufferedImage mImage;
	private BufferedImage mat2BI(Mat mat) {
		int dataSize=mat.cols()*mat.rows()*(int)mat.elemSize();
		byte[] data=new byte[dataSize];
		mat.get(0, 0,data);
		int type=mat.channels()==1?BufferedImage.TYPE_BYTE_GRAY:BufferedImage.TYPE_3BYTE_BGR;
	    if(type==BufferedImage.TYPE_3BYTE_BGR) {
	    	for(int i=0;i<dataSize;i+=3) {
	    		byte blue=data[i+0];
	    		data[i+0]=data[i+2];
	    		data[i+2]=blue;
	    	}
	    }
	    BufferedImage image=new BufferedImage(mat.cols(),mat.rows(),type);
	    image.getRaster().setDataElements(0, 0,mat.cols(), mat.rows(),data);
	    return image;
	}

	public void paintComponent(Graphics g) {
		if (mImage != null) {
			g.drawImage(mImage, 0, 0, mImage.getWidth(), mImage.getHeight(), this);

		}
	}

	// 瀹炵幇浜鸿劯璇嗗埆
	public static Mat detectFace(Mat img) throws Exception{
		System.out.println("Running DetectFace...");
		MatOfRect faceDetections=new MatOfRect();
	    CascadeClassifier faceDetector=new CascadeClassifier("D:/Program Files (x86)/opencv/opencv/sources/data/haarcascades_cuda/haarcascade_frontalface_default.xml");
		faceDetector.detectMultiScale(img, faceDetections); 
		Rect[] rects=faceDetections.toArray();
		if(rects!=null&&rects.length>=1) {
			for(Rect rect:rects) {
				Imgproc.rectangle(img, new Point(rect.x,rect.y),new Point(rect.x+rect.width,rect.y+rect.height),new Scalar(0,0,255),2);				
			}
		}
		return img;
	}

	// opencv瀹炵幇浜哄瀷璇嗗埆锛宧og榛樿鍒嗙被鍣ㄣ�傛晥鏋滀笉濂�

	public static Mat detectPeople(Mat img) {
		if (img.empty()) {
			System.out.println("image is exist");
		}
		HOGDescriptor hog = new HOGDescriptor();
		hog.setSVMDetector(HOGDescriptor.getDefaultPeopleDetector());
		System.out.println(HOGDescriptor.getDefaultPeopleDetector());
		MatOfRect regions = new MatOfRect();
		MatOfDouble foundWeights = new MatOfDouble();

		hog.detectMultiScale(img, regions, foundWeights);

		for (Rect rect : regions.toArray()) {
			Imgproc.rectangle(img, new Point(rect.x, rect.y), new Point(rect.x + rect.width, rect.y + rect.width),
					new Scalar(0, 0, 255), 2);
		}
		return img;

	}

	public static void main(String[] args) {
		try {
			System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
			Mat capImg = new Mat();
			VideoCapture capture = new VideoCapture();
            capture.open(0);
            capture.set(Videoio.CV_CAP_PROP_FRAME_WIDTH,800);
            capture.set(Videoio.CV_CAP_PROP_FRAME_HEIGHT,600);
            capture.set(Videoio.CV_CAP_PROP_FPS,60);//甯х巼
			int height = (int) capture.get(Videoio.CAP_PROP_FRAME_HEIGHT);
			int width = (int) capture.get(Videoio.CAP_PROP_FRAME_WIDTH);
			if (height == 0 || width == 0) {
				throw new Exception("camera not found!");
			}
			JFrame frame = new JFrame("camera");
			frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
			CameraTest panel = new CameraTest();
		   // panel.setSize(0,0);
			panel.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					// TODO Auto-generated method stub
					super.mouseClicked(e);
					System.out.println("click");
				}

				@Override
				public void mouseMoved(MouseEvent e) {
					// TODO Auto-generated method stub
					super.mouseMoved(e);
					System.out.println("move");
				}

				@Override
				public void mouseReleased(MouseEvent arg0) {
					System.out.println("mouseReleased");
				}

				@Override
				public void mousePressed(MouseEvent arg0) {
					System.out.println("mousePressed");
				}

				@Override
				public void mouseExited(MouseEvent arg0) {
					System.out.println("mouseExited");
					// System.out.println(arg0.toString());
				}

				@Override
				public void mouseDragged(MouseEvent arg0) {
					System.out.println("mouseDragged");
					// System.out.println(arg0.toString());
				}

			});
			
			frame.setContentPane(panel);
			Toolkit toolkit = Toolkit.getDefaultToolkit();
			int x = (int)toolkit.getScreenSize().getWidth();
			int y = (int)toolkit.getScreenSize().getHeight();
			frame.setLocation(x/2-700, y/2-384);
			frame.setSize(1400, 768);
			frame.setVisible(true);
			
			//frame.setSize(width+frame.getInsets().left+frame.getInsets().right,height+frame.getInsets().top+frame.getInsets().bottom);
            int n=0;
            Mat temp=new Mat();
            
            while(frame.isShowing()&&n<500) {
            	capture.read(capImg);
            	Imgproc.cvtColor(capImg, temp,Imgproc.COLOR_RGB2BGR);
            	panel.mImage=panel.mat2BI(detectFace(capImg));
            	panel.repaint();      	
            }
            capture.release();
            frame.dispose();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

}
