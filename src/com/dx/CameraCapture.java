package com.dx;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import javax.imageio.ImageIO;
import javax.swing.JLabel;
import javax.swing.JPanel;
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
import com.dx.pojo.PersonCard;
import com.dx.pojo.Zkzdata;
import com.dx.service.FaceLogsImpl;
import com.dx.service.FaceadminImpl;
import com.dx.service.ParamSetupImpl;
import com.dx.service.ZkzInterImpl;
import com.dx.util.AFRTest;
import com.dx.util.FileUtil;



public class CameraCapture extends JPanel {
	
	static{System.loadLibrary(Core.NATIVE_LIBRARY_NAME);}
	static JLabel label;
	static boolean flag=false;
	//ʹ��opencv���򿪵�������ͷ������ͷ���ղ�����
	
	private static final long serialVersionUID = 1L;
	private static  BufferedImage mImg;  
	private Thread mainThread = null;
	Mat mat=new Mat();
	private  File f = null ;
	private String filepath  ; 
	static String upersonnum = "" ;
	private String shibieleixing = "" ;
	private VideoCapture  camera = null;
	private Zkzdata zkzdata = null ;
	private FaceadminInteraface faceservice = new FaceadminImpl();
	private FaceLogsInterface impl = new FaceLogsImpl();
	private CascadeClassifier faceDetector = new CascadeClassifier("D:\\faceimages\\opencv\\sources\\data\\haarcascades_cuda/haarcascade_frontalface_alt_tree.xml");//_tree
	static PersonCard card = null ;
	private boolean types = false ;
	public CameraCapture() {
		label=new JLabel("");
		label.setBounds(50, 50, 640, 480);
		add(label);
		//����
		camera=new VideoCapture();
		camera.open(1);//0�򿪱���Ĭ������ͷ
		//camera.release();�ر�����ͷ
		if(!camera.isOpened()){
			System.out.println("Camera Error");
		} else{
			/**
			 * CV_CAP_PROP_FRAME_WIDTH ��Ƶ��֡�Ŀ���/һ֡ͼ��Ŀ���
CV_CAP_PROP_FRAME_HEIGHT ��Ƶ��֡�ĸ�./һ֡ͼ��ĸ߶�
CV_CAP_PROP_FPS ֡��.
CV_CAP_PROP_FRAME_COUNT ��Ƶ�ļ���֡��.
CV_CAP_PROP_BRIGHTNESS ͼ������ (ֻ������ͷ).
CV_CAP_PROP_CONTRAST ͼ��Աȶ� (ֻ������ͷ).
CV_CAP_PROP_SATURATION ͼ�񱥺Ͷ� (ֻ������ͷ).
CV_CAP_PROP_HUE ɫ�� (ֻ������ͷ).
CV_CAP_PROP_GAIN ����(ֻ������ͷ).
CV_CAP_PROP_EXPOSURE �ع�(ֻ������ͷ).
*/
			camera.set(Videoio.CV_CAP_PROP_FRAME_WIDTH,800);
			camera.set(Videoio.CV_CAP_PROP_FRAME_HEIGHT,600);
			camera.set(Videoio.CV_CAP_PROP_FPS,60);//֡��
			//camera.set(Videoio.CV_CAP_PROP_BRIGHTNESS,40);
			//camera.set(Videoio.CV_CAP_PROP_CONTRAST,120);
			//camera.set(Videoio.CV_CAP_PROP_SATURATION,80);
			//camera.set(Videoio.CV_CAP_PROP_HUE,120);
			//camera.set(Videoio.CV_CAP_PROP_GAIN,80);
			//camera.set(Videoio.CV_CAP_PROP_EXPOSURE,120);
		}	
		try {
			 mainThread = new Thread(new Runnable() {
				@Override
				public void run() {
						Mat temp=new Mat();
						while(true){
							camera.read(mat);
							Imgproc.cvtColor(mat, temp, Imgproc.COLOR_RGB2BGR555);
							Mat detectFace = detectFace(mat);
							//System.out.println(mat+"-------"+detectFace+"======"+flag);
							if(detectFace == null && flag == true ) {//δ��⵽����������֤
								mImg=mat2BI(mat);
								repaint();
							}else if(detectFace== null&&flag == false) {//δ��⵽��������������֤
								mImg=mat2BI(mat);
								repaint();
							}else if(detectFace!= null&&flag == false) {//δ��⵽��������������֤
								mImg=mat2BI(mat);
								repaint();
							}else if(detectFace != null && flag == true){//��⵽����,ֻ��һ����ͣѭ��
								mImg=mat2BI(detectFace);
								repaint();//panel.repaint(1000);�ػ����
								flag = false ;
							}
							/*try {
								Thread.sleep(100);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}*/
						}
				}
			});
			 mainThread.setDaemon(true);
			 mainThread.setName("cameraThread");
			 mainThread.start();
		}catch(Exception e){  
			e.printStackTrace();
			System.out.println("����:" + e);  
		}finally{  
			System.out.println("--done--");  
		}

	}
	public BufferedImage mat2BI(Mat mat){  
		int dataSize =mat.cols()*mat.rows()*(int)mat.elemSize();  
		byte[] data=new byte[dataSize];  
		mat.get(0, 0,data);  
		int type=mat.channels()==1?	BufferedImage.TYPE_BYTE_GRAY:BufferedImage.TYPE_3BYTE_BGR;  
		
		if(type==BufferedImage.TYPE_3BYTE_BGR){  
			for(int i=0;i<dataSize;i+=3){  
				byte blue=data[i+0];  
				data[i+0]=data[i+2];  
				data[i+2]=blue;  
			}  
		}  
		BufferedImage image=new BufferedImage(mat.cols(),mat.rows(),type);  
		image.getRaster().setDataElements(0, 0, mat.cols(), mat.rows(), data);  
		return image;  
	}  
	
	
	public void paintComponent(Graphics g){  
		if(mImg!=null){  
			g.drawImage(mImg, 0, 0, mImg.getWidth(),mImg.getHeight(),this);  
		}  
	} 
	
	/**
	 * opencvʵ������ʶ��
	 * @param img
	 */
	public   Mat detectFace(Mat img) {
		// �������ļ�lbpcascade_frontalface.xml�д���һ������ʶ���������ļ�λ��opencv��װĿ¼��
		try {
			
			if(flag == true) {//��⵽���µ�����֤
				setTypes(flag);
				setShibieleixing("");
				
				MatOfRect faceDetections = new MatOfRect();
				faceDetector.detectMultiScale(img, faceDetections);
				Rect[] rects = faceDetections.toArray();
				
			
			
				//CascadeClassifier faceDetector = new CascadeClassifier("D:\\faceimages\\opencv\\sources\\data\\lbpcascades/lbpcascade_profileface.xml");
				//����opencv�İ�װ·�����ҵ�sources\\data\\haarcascades\\haarcascade_frontalface_alt.xml�ļ�
				//CascadeClassifier faceDetector = new CascadeClassifier("D:\\faceimages\\opencv\\sources\\data\\haarcascades_cuda/haarcascade_frontalface_alt.xml");
				// ��ͼƬ�м������
				
				Mat image2 = null ;
				if(rects != null && rects.length == 1 && flag == true){ 
				//	System.out.println(rects.length+"----rects.length");
					
					BufferedImage newface = null ;
					//��������� ,����������֤��ִ�������
					for (Rect rect : rects) {  //����
						Imgproc.rectangle(img, new Point(rect.x, rect.y), new Point(rect.x + rect.width, rect.y + rect.height),  
								new Scalar(0, 255, 245), 2);  
						//ͼƬ�и����������Ƭ
						Rect rect2 = new Rect(rect.x,rect.y,rect.width,rect.height);  
						Mat roi_img = new Mat(img,rect2); 
						image2 = new Mat();
						roi_img.copyTo(image2);
						
					} 
					//showSheng("Please keep still");
					//����
					//SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
					//String name = sdf.format(new Date());//�����ֲ������ظ�����ʱ�䲻���ʣ����ܻ��ظ�
					String name = UUID.randomUUID().toString().replaceAll("-",""); 
					//��ȡ����·��
					//File path = FileSystemView.getFileSystemView().getHomeDirectory();
	//				File path = new File("D:\\faceimages");
					String fpath = "D:\\faceimages/faceimage";
					File path = new File(fpath);
					FileUtil.createFile(fpath);
					//System.out.println(path);
					//�����ȫ�ļ�
					String format = "jpg";
					String format2 = "png";
					f = new File(path + File.separator + name + "." + format);//�ļ�ȫ��
					try {
						ImageIO.write(mImg, format, f);//����������ͼƬ
					} catch (IOException e) {
						e.printStackTrace();
					}
	//				setFilepath(path + File.separator + name + "." + format);��ʾ������ ͼƬ
					//������,��ַ��Ҫ�浽���ݿ��
					String filename = "D:\\faceimages/faceimage/"+name+".png";
					Imgcodecs.imwrite(filename, image2);
					setFilepath(path + File.separator + name + "." + format2);//��ʾ��
					//�ļ���ַ���Ʊ������ݿ�
					System.out.println("upersonnum---"+upersonnum);
					if(!"".equals(upersonnum)) {
						String sbleix = this.getStrByAllpho(upersonnum,filename);
						setShibieleixing(sbleix);
					}
				}else if(rects != null && rects.length > 1 && flag == true){
					System.out.println("�ж�� ����~~~~~~");
					if("".equals(upersonnum)) {
						setShibieleixing("�ж�� ���������޹���Ավ������ͷλ��");
					}
				}else {//û������
					img = null ;
					if("".equals(upersonnum)) {
						setShibieleixing("�뱣�ֲ�Ҫ�һ�~~~~");
					}
				}
			}else {
//				for (Rect rect : rects) {  //����
//					Imgproc.rectangle(img, new Point(rect.x, rect.y), new Point(rect.x + rect.width, rect.y + rect.height),  
//							new Scalar(0, 255, 245), 2);  
//				} 
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return img;
	}
//	//��������
//	public static void showSheng(String str) {
//		ActiveXComponent sap = new ActiveXComponent("Sapi.SpVoice");
//		// Dispatch����ʲô�ģ�
//		Dispatch sapo = sap.getObject();
//		try {
//			
//			// ���� 0-100
//			sap.setProperty("Volume", new Variant(100));
//			// �����ʶ��ٶ� -10 �� +10
//			sap.setProperty("Rate", new Variant(4));
//			
//			Variant defalutVoice = sap.getProperty("Voice");
//			
//			Dispatch dispdefaultVoice = defalutVoice.toDispatch();
//			Variant allVoices = Dispatch.call(sapo, "GetVoices");
//			Dispatch dispVoices = allVoices.toDispatch();
//			
//			Dispatch setvoice = Dispatch.call(dispVoices, "Item", new Variant(1)).toDispatch();
//			ActiveXComponent voiceActivex = new ActiveXComponent(dispdefaultVoice);
//			ActiveXComponent setvoiceActivex = new ActiveXComponent(setvoice);
//			
//			Variant item = Dispatch.call(setvoiceActivex, "GetDescription");
//			// ִ���ʶ�
//			Dispatch.call(sapo, "Speak", new Variant(str));//"��С��С���ѣ����Ϻ�"
//			
//		} catch (Exception e) {
//			e.printStackTrace();
//		} finally {
//			sapo.safeRelease();
//			sap.safeRelease();
//		}
//	}


	public VideoCapture getCamera() {
		return camera;
	}
	public void setCamera(VideoCapture camera) {
		this.camera = camera;
	}
	public static BufferedImage getmImg() {
		return mImg;
	}
	public static void setmImg(BufferedImage mImg) {
		CameraCapture.mImg = mImg;
	}
	public Thread getMainThread() {
		return mainThread;
	}
	public void setMainThread(Thread mainThread) {
		this.mainThread = mainThread;
	}
	public  String getShibieleixing() {
		return shibieleixing;
	}
	public  void setShibieleixing(String shibieleixing) {
		//showSheng(shibieleixing);
		String oldshibieleixing = this.shibieleixing ;
		this.shibieleixing = shibieleixing;
		firePropertyChange("shibieleixing", oldshibieleixing, shibieleixing);//�����������Ƿ����ı�
		
	}

	public String getFilepath() {
		return filepath;
	}
	public void setFilepath(String filepath) {
		String oldpath = this.filepath ;
		this.filepath = filepath;
		firePropertyChange("filepath", oldpath, filepath);  //�����������Ƿ����ı�
	}

	//ͼƬ�и�
	public BufferedImage SplitImage(BufferedImage img, int x, int y, 
			int width, int height) {
		if(x+width >= img.getWidth() || y+height >= img.getHeight()) {
			return null;
		}else {
			BufferedImage newImg = new BufferedImage(width, height, 
					BufferedImage.TYPE_INT_ARGB);
			for(int i=x;i<x+width;i++) {
				for(int j=y;j<y+height;j++) {
					newImg.setRGB(i-x, j-y, img.getRGB(i, j));
				}
			}
			return newImg;
		}
	}
	/**
	 * //ȡ׼��֤�ϵ���Ƭ
	 * ��Ƭ��ַ�̶�
	 * (����֤��Ƭ��D:\\faceimages\\411534199563559387\\411534199563559387zp.bmp���ֳ�������D:\\faceimages/ʱ���.png��׼��֤��Ƭ��D:\faceimages\zkzpho\411534199563559387.JPG)
	 * sfzpho����֤��Ƭ
	 * filename�ֳ�ͼƬ��ַ�����ݿ�
	 * zkzpho׼��֤��Ƭ
	 * */
	private String getStrByAllpho(String upersonnum, String filename) {
		String str = "" ;
		String strint = "" ;
		String xingming = "" ;
		String xingbie = "" ;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date date = new Date();
			
			
			String sfzpho = "D:\\faceimages\\faceimage\\"+upersonnum+"/"+upersonnum+"zp.bmp" ;
			String filepath = "D:\\faceimages\\faceimage\\"+upersonnum+"/"+upersonnum+"" ;
//			
//			ImageLoader il = new ImageLoader();
//	    	il.reader(sfzpho,filepath);//��λͼ��Ϊjpg��ʽ��ͼƬ
//			
//	    	sfzpho = "D:\\faceimages\\"+upersonnum+"/"+upersonnum+".jpg" ;
			
			AFRTest af = new AFRTest();
			//System.out.println(filename+"----------"+sfzpho);
	    	Float xsd = af.compareImage(filename, sfzpho);
	    	xingming=card.getPname() ;
	    	xingbie = card.getPsex() ;
			System.out.println("���ص����ƶ�----"+xsd+"--------"+xingming+"--------"+xingbie);
			if(xsd<50) {
				str = "�Ǳ�������֤" ;
				strint = "1" ;
			}else {
				//��ѯ������֤�ŵ�׼��֤����Ϣ
				ZkzInterface zkzimpl = new ZkzInterImpl();
				Zkzdata zkz = zkzimpl.findByPersonnum(upersonnum);
				if(zkz == null) {
					str= "�ǿ���" ;//��ע����Ա
					strint = "2" ;
				}else {
					xingming = zkz.getXingming() ;
					xingbie = zkz.getXingbie() ;
					String sj = zkz.getSj1().substring(0, 10);//��-��-��
					String dd = zkz.getDd1() ;//�ص�
					String kc = zkz.getKc1() ;//����
//					System.out.println(sj+"----"+dd+"----"+kc);
					//�ж��Ƿ�Ϊ���ù���ʱ��Ŀ���
					ParamSetupInterface paramimpl = new  ParamSetupImpl();
					Integer paramc = paramimpl.getParamSetupBySj(sj);
					if(paramc!=0) {//���ڶ�
						paramc = paramimpl.getParamSetupByDd(sj,dd);
						if(paramc != 0) {
							paramc = paramimpl.getParamSetupByKc(sj,dd,kc);
							if(paramc != 0) {
								//������Ҫ��ʾ׼��֤��Ϣ��
								strint = "7" ;//ͨ��
								str = "ͨ��";
								//str = zkz.toString();//׼��֤��Ϣ��ʾ
								//zkzdata = zkz ;
								setZkzdata(zkz);
							}else {
								str= "��������ȷ" ;
								strint = "6" ;
							}
						}else {
							str= "���Եص㲻��ȷ" ;
							strint = "5" ;
						}
					}else {
						str= "����ʱ�䲻��ȷ" ;
						strint = "3" ;
					}
					
					
				}
				
				
			}
			
			
			
			FaceLog faceLog = new FaceLog();
			faceLog.setRenlianphoto(filename);
			faceLog.setSfz(upersonnum);
			faceLog.setRemarks(xsd+"");//���ƶ�
			faceLog.setSfzphoto(sfzpho);
			faceLog.setShijian(sdf.format(date));
			if(strint.equals("7")) {
				faceLog.setShibieleixing("ͨ��");
			}else {
				faceLog.setShibieleixing(str);
			}
			faceLog.setShibieleixingint(strint);
			faceLog.setXingming(xingming);
			faceLog.setXingbie(xingbie);
			//��ȡ�û���½���Σ���ȷ��Ϊ�ڼ������ԣ������ѯʹ��
			Faceadmin admin = faceservice.getFaceadmin();
			//System.out.println("admin------"+admin);
			if(admin != null) {
				faceLog.setDenglumana(admin.getBmname());
				faceLog.setChangci(admin.getCurchangci());
				//���˸ó��ε����һ������
				FaceLog lastfaceLog = impl.getLastFaceLog(upersonnum,admin.getCurchangci());
				if(lastfaceLog == null) {
					faceLog.setRenzcount("1");
				}else {
					String coun = lastfaceLog.getRenzcount() ;
					String shibieint = lastfaceLog.getShibieleixingint() ;
					//System.out.println("coun------"+coun+"-------"+shibieint);
					if(coun!=null&&!"".equals(coun) ) {
						if(shibieint.equals("7")) {
							//ͨ�������ٱ���renzcount,ֻ�ǵ���������һ������������ܽ�����
						}else {
							Integer cc = Integer.parseInt(coun)+1;
							faceLog.setRenzcount(cc+"");
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
	public Zkzdata getZkzdata() {
		return zkzdata;
	}
	public void setZkzdata(Zkzdata zkzdata) {
		Zkzdata oldzkzdata = this.zkzdata ;
		this.zkzdata = zkzdata;
		firePropertyChange("zkzdata", oldzkzdata, zkzdata);
		
	}
	//����Ҳ������
	public boolean isTypes() {
		return types;
	}
	public void setTypes(boolean types) {
		boolean oldtype = this.types ;
		this.types = types;
		firePropertyChange("types", oldtype, types);
		this.types = false ;
	}
	
	
	
	
	
	
	
}