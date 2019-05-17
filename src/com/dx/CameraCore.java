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
import com.dx.util.CameraUtil;
import com.dx.util.FileUtil;

import lombok.Getter;
import lombok.Setter;


@Getter @Setter
public class CameraCore extends JPanel {
	
	static{System.loadLibrary(Core.NATIVE_LIBRARY_NAME);}
	static JLabel label;
	static boolean flag=false;
	//使用opencv，打开电脑摄像头发现人头拍照并保存
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
	public CameraCore() {
		label=new JLabel("");
		label.setBounds(50, 50, 640, 480);
		add(label);
		//操作
		//打开相机
		camera=CameraUtil.getVideoCapture();		
		try {
			 mainThread = new Thread(new Runnable() {
				@Override
				public void run() {
						Mat temp=new Mat();
						while(true){							
							camera.read(mat);
							Imgproc.cvtColor(mat, temp, Imgproc.COLOR_RGB2BGR);
							Mat detectFace = detectFace(mat);						
                            if(detectFace != null && flag == true){//检测到人脸,只拍一张暂停循环
								mImg=CameraUtil.mat2BI(detectFace);
								repaint();//panel.repaint(1000);重绘组件
								flag = false ;
							}else {
								mImg=CameraUtil.mat2BI(CameraUtil.detectFace(mat));
								repaint();
							}							
						}
				}
			});
			 mainThread.setDaemon(true);
			 mainThread.setName("cameraThread");
			 mainThread.start();
		}catch(Exception e){  
			e.printStackTrace();
			System.out.println("例外:" + e);  
		}finally{  
			System.out.println("--done--");  
		}

	}
	public void paintComponent(Graphics g){  
		if(mImg!=null){  
			g.drawImage(mImg, 0, 0, mImg.getWidth(),mImg.getHeight(),this);  
		}  
	} 
	
	/**
	 * opencv实现人脸识别
	 * @param img
	 */
	public   Mat detectFace(Mat img) {
		// 从配置文件lbpcascade_frontalface.xml中创建一个人脸识别器，该文件位于opencv安装目录下
		try {
			
			if(flag == true) {//检测到有新的身份证
				setTypes(flag);
				setShibieleixing("");
				
				MatOfRect faceDetections = new MatOfRect();
				faceDetector.detectMultiScale(img, faceDetections);
				Rect[] rects = faceDetections.toArray();
				
			
			
				//CascadeClassifier faceDetector = new CascadeClassifier("D:\\faceimages\\opencv\\sources\\data\\lbpcascades/lbpcascade_profileface.xml");
				//这是opencv的安装路径下找到sources\\data\\haarcascades\\haarcascade_frontalface_alt.xml文件
				//CascadeClassifier faceDetector = new CascadeClassifier("D:\\faceimages\\opencv\\sources\\data\\haarcascades_cuda/haarcascade_frontalface_alt.xml");
				// 在图片中检测人脸
				
				Mat image2 = null ;
				if(rects != null && rects.length == 1 && flag == true){ 
				//	System.out.println(rects.length+"----rects.length");
					
					BufferedImage newface = null ;
					//如果有人脸 ,并且有身份证在执行下面的
					for (Rect rect : rects) {  //画框
						Imgproc.rectangle(img, new Point(rect.x, rect.y), new Point(rect.x + rect.width, rect.y + rect.height),  
								new Scalar(0, 255, 245), 2);  
						//图片切割，保存人脸照片
						Rect rect2 = new Rect(rect.x,rect.y,rect.width,rect.height);  
						Mat roi_img = new Mat(img,rect2); 
						image2 = new Mat();
						roi_img.copyTo(image2);
						
					} 
					//showSheng("Please keep still");
					//拍照
					//SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
					//String name = sdf.format(new Date());//改名字不允许重复，用时间不合适，可能会重复
					String name = UUID.randomUUID().toString().replaceAll("-",""); 
					//获取桌面路径
					//File path = FileSystemView.getFileSystemView().getHomeDirectory();
				   //File path = new File("D:\\faceimages");
					String fpath = "D:\\faceimages/faceimage";
					File path = new File(fpath);
					FileUtil.createFile(fpath);
					//System.out.println(path);
					//保存的全文件
					String format = "jpg";
					String format2 = "png";
					f = new File(path + File.separator + name + "." + format);//文件全名
					try {
						ImageIO.write(mImg, format, f);//保存完整的图片
					} catch (IOException e) {
						e.printStackTrace();
					}
	//				setFilepath(path + File.separator + name + "." + format);显示完整的 图片
					//存人脸,地址需要存到数据库的
					String filename = "D:\\faceimages/faceimage/"+name+".png";
					Imgcodecs.imwrite(filename, image2);
					setFilepath(path + File.separator + name + "." + format2);//显示脸
					//文件地址名称保存数据库
					System.out.println("upersonnum---"+upersonnum);
					if(!"".equals(upersonnum)) {
						String sbleix = this.getStrByAllpho(upersonnum,filename);
						setShibieleixing(sbleix);
					}
				}else if(rects != null && rects.length > 1 && flag == true){
					System.out.println("有多个 人脸~~~~~~");
					if("".equals(upersonnum)) {
						setShibieleixing("有多个 人脸，请无关人员站离摄像头位置");
					}
				}else {//没有人脸
					img = null ;
					if("".equals(upersonnum)) {
						setShibieleixing("请保持不要乱晃~~~~");
					}
				}
			}else {
//				for (Rect rect : rects) {  //画框
//					Imgproc.rectangle(img, new Point(rect.x, rect.y), new Point(rect.x + rect.width, rect.y + rect.height),  
//							new Scalar(0, 255, 245), 2);  
//				} 
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return img;
	}
	public  void setShibieleixing(String shibieleixing) {
		//showSheng(shibieleixing);
		String oldshibieleixing = this.shibieleixing ;
		this.shibieleixing = shibieleixing;
		firePropertyChange("shibieleixing", oldshibieleixing, shibieleixing);//监听该属性是否发生改变
		
	}

	public String getFilepath() {
		return filepath;
	}
	public void setFilepath(String filepath) {
		String oldpath = this.filepath ;
		this.filepath = filepath;
		firePropertyChange("filepath", oldpath, filepath);  //监听该属性是否发生改变
	}

	//图片切割
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
	 * //取准考证上的照片
	 * 照片地址固定
	 * (身份证照片：D:\\faceimages\\411534199563559387\\411534199563559387zp.bmp，现场人脸：D:\\faceimages/时间戳.png，准考证照片：D:\faceimages\zkzpho\411534199563559387.JPG)
	 * sfzpho身份证照片
	 * filename现场图片地址存数据库
	 * zkzpho准考证照片
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
			AFRTest af = new AFRTest();
			//System.out.println(filename+"----------"+sfzpho);
	    	Float xsd = af.compareImage(filename, sfzpho);
	    	xingming=card.getPname() ;
	    	xingbie = card.getPsex() ;
			System.out.println("返回的相似度----"+xsd+"--------"+xingming+"--------"+xingbie);
			if(xsd<50) {
				str = "非本人身份证" ;
				strint = "1" ;
			}else {
				//查询该身份证号的准考证的信息
				ZkzInterface zkzimpl = new ZkzInterImpl();
				Zkzdata zkz = zkzimpl.findByPersonnum(upersonnum);
				if(zkz == null) {
					str= "非考生" ;//非注册人员
					strint = "2" ;
				}else {
					xingming = zkz.getXingming() ;
					xingbie = zkz.getXingbie() ;
					String sj = zkz.getSj1().substring(0, 10);//年-月-日
					String dd = zkz.getDd1() ;//地点
					String kc = zkz.getKc1() ;//考场
//					System.out.println(sj+"----"+dd+"----"+kc);
					//判断是否为设置过的时间的考生
					ParamSetupInterface paramimpl = new  ParamSetupImpl();
					Integer paramc = paramimpl.getParamSetupBySj(sj);
					if(paramc!=0) {//日期对
						paramc = paramimpl.getParamSetupByDd(sj,dd);
						if(paramc != 0) {
							paramc = paramimpl.getParamSetupByKc(sj,dd,kc);
							if(paramc != 0) {
								//这里需要显示准考证信息了
								strint = "7" ;//通过
								str = "通过";
								//str = zkz.toString();//准考证信息显示
								//zkzdata = zkz ;
								setZkzdata(zkz);
							}else {
								str= "考场不正确" ;
								strint = "6" ;
							}
						}else {
							str= "考试地点不正确" ;
							strint = "5" ;
						}
					}else {
						str= "考试时间不正确" ;
						strint = "3" ;
					}
					
					
				}
				
				
			}
			
			
			
			FaceLog faceLog = new FaceLog();
			faceLog.setRenlianphoto(filename);
			faceLog.setSfz(upersonnum);
			faceLog.setRemarks(xsd+"");//相似度
			faceLog.setSfzphoto(sfzpho);
			faceLog.setShijian(sdf.format(date));
			if(strint.equals("7")) {
				faceLog.setShibieleixing("通过");
			}else {
				faceLog.setShibieleixing(str);
			}
			faceLog.setShibieleixingint(strint);
			faceLog.setXingming(xingming);
			faceLog.setXingbie(xingbie);
			//获取用户登陆场次，来确定为第几场考试，方便查询使用
			Faceadmin admin = faceservice.getFaceadmin();
			//System.out.println("admin------"+admin);
			if(admin != null) {
				faceLog.setDenglumana(admin.getBmname());
				faceLog.setChangci(admin.getCurchangci());
				//该人该场次的最后一条数据
				FaceLog lastfaceLog = impl.getLastFaceLog(upersonnum,admin.getCurchangci());
				if(lastfaceLog == null) {
					faceLog.setRenzcount("1");
				}else {
					String coun = lastfaceLog.getRenzcount() ;
					String shibieint = lastfaceLog.getShibieleixingint() ;
					//System.out.println("coun------"+coun+"-------"+shibieint);
					if(coun!=null&&!"".equals(coun) ) {
						if(shibieint.equals("7")) {
							//通过，不再保存renzcount,只是单纯的添加一条正常结果不管结果如何
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
	public void setZkzdata(Zkzdata zkzdata) {
		Zkzdata oldzkzdata = this.zkzdata ;
		this.zkzdata = zkzdata;
		firePropertyChange("zkzdata", oldzkzdata, zkzdata);
		
	}
	//这里也不合适
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