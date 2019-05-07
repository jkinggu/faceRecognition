package com.dx;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JPanel;

import com.dx.pojo.PersonCard;
import com.dx.util.FileUtil;
import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.ptr.IntByReference;

public class Person extends JPanel {
	private boolean ischange = false;//默认不没改变，
	private PersonCard card1 = new PersonCard();
	private PersonCard card2 = new PersonCard();
//	private Thread pthread = null ;
	private Integer flag = 0;
	
	public Integer getFlag() {
		return flag;
	}
	public void setFlag(Integer flag) {
		this.flag = flag;
	}
	public PersonCard getCard1() {
		return card1;
	}
	public void setCard1(PersonCard card1) {
		this.card1 = card1;
	}
	public PersonCard getCard2() {
		return card2;
	}
	public void setCard2(PersonCard card2) {
		this.card2 = card2;
	}
	public boolean isIschange() {
		return ischange;
	}
	
//	public Thread getPthread() {
//		return pthread;
//	}
//	public void setPthread(Thread pthread) {
//		this.pthread = pthread;
//	}
	public void setIschange(boolean ischange) {
		boolean oldchange = this.ischange;
		this.ischange = ischange;
		firePropertyChange("ischange", oldchange, ischange);  //监听该属性是否发生改变
	}
	public Person(String path) {
		
	}
	public Person() {
		flag = initPerson();//初始化端口
		if(checkFlag(flag)) {
//			if(pthread == null) {
//				pthread = new Thread(new Runnable() {
//					@Override
//					public void run() {
//						while(true) {//循环读取身份证
//							try {
//								Thread.sleep(1000);
//							} catch (InterruptedException e) {
//								System.exit(0);
//							}
//							setPersonCards();
//						}
//					}
//				});
////				pthread.setDaemon(true);
//				pthread.setName("personThread");
//				pthread.start();
//			}else {
//				pthread.notify();
//			}
			Timer timer = new Timer();
			timer.scheduleAtFixedRate(new TimerTask() {
				public void run() {
					setPersonCards();
					if(flag == -10) {
						timer.cancel();
					}
				}
			}, 1000, 2000);
		}else {
			System.out.println("身份证阅读机启动失败");
			closePerson();
			new Person();
		}
	}
	public interface RdcardDll extends Library {
		File f = new File("");
		
		RdcardDll bell = (RdcardDll) Native.loadLibrary(f.getAbsolutePath()+"\\RdCard.dll", RdcardDll.class);
		//RdcardDll bell = (RdcardDll) Native.loadLibrary("D:\\eclipse\\workspace\\testthread\\RdCard.dll", RdcardDll.class);
		int UCommand1(String pCmd, IntByReference prag0, IntByReference prag1,
				byte[] prag2);

		int GetName(byte[] buf);

		int GetSexGB(byte[] buf);

		int GetFolkGB(byte[] buf);

		int GetAddr(byte[] buf);

		int GetIDNum(byte[] buf);

		int GetDep(byte[] buf);

		int GetBirth(byte[] buf);

		int GetBegin(byte[] buf);

		int GetEnd(byte[] buf);

		int GetBmpPath(byte[] buf);
		
		int FID_GetEnName (byte[] buf); //读取英文姓名
		int FID_GetChName (byte[] buf); //读取中文姓名
		int FID_GetNationality (byte[] buf); //读取国籍
		int FID_GetSex (byte[] buf); //读取性别编码
		int FID_GetSexGB (byte[] buf); //读取性别
		int FID_GetBirth (byte[] buf); //读取出生
		int FID_GetIDNum (byte[] buf); //读取公民身份号码
		int FID_GetDep (byte[] buf); //读取签发机关
		int FID_GetBegin (byte[] buf); //读取有效期起
		int FID_GetEnd (byte[] buf); //读取有效期止
		int FID_GetVersion (byte[] buf); //读取证件版本号
		int FID_GetBmpPath (byte[] buf); //读取头像图片路径
	}

	//1.初始化端口
	private int initPerson() {
		String Pcmd = String.format("%c", 0x41);
		IntByReference prag0 = new IntByReference();
		prag0.setValue(0);
		IntByReference prag1 = new IntByReference();
		prag1.setValue(8811);
		byte[] prag2 = { 0x02, 0x27, 0x00, 0x00};
		int flag =  RdcardDll.bell.UCommand1(Pcmd, prag0, prag1, prag2);
	//	System.out.println(flag);
		return flag;
	}
	
	
	//2.验证卡
	private int verfiyCard() {
		String Pcmd = String.format("%c", 0x43);
		IntByReference prag0 = new IntByReference();
		prag0.setValue(0);
		IntByReference prag1 = new IntByReference();
		prag1.setValue(8811);
		byte[] prag2 = { 0x02, 0x27, 0x00, 0x00};
		int flag =  RdcardDll.bell.UCommand1(Pcmd, prag0, prag1, prag2);
	//	System.out.println(flag);
		return flag;
	}
	
	//3.读取信息
	private int readInfo(){
		String Pcmd = String.format("%c", 0x49);
		IntByReference prag0 = new IntByReference();
		prag0.setValue(0);
		IntByReference prag1 = new IntByReference();
		prag1.setValue(8811);
		String filepath = "D:\\faceimages\\facetemp\\face";
		FileUtil.createFile(filepath);
		String strPara2 = filepath+"\\face"+"\0";
		byte[] prag2 = null;
		int flag = 0;
		try {
			prag2 = strPara2.getBytes("GBK");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		flag =  RdcardDll.bell.UCommand1(Pcmd, prag0, prag1, prag2);
		return flag;
	}
	
	//4.显示信息
	private int showInfo() {
		String Pcmd = String.format("%c", 0x49);
		IntByReference prag0 = new IntByReference();
		prag0.setValue(0);
		IntByReference prag1 = new IntByReference();
		prag1.setValue(8811);
		String num = "";
		try {
			byte[] idNum = new byte[50];
			RdcardDll.bell.GetIDNum(idNum);
			num = new String(idNum, "GBK").trim();
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		String filepath = "D:\\faceimages\\faceimage\\"+num;
		FileUtil.createFile(filepath);
		String strPara2 = filepath+"\\"+num+"\0";
//		String strPara2 = "D:\\eclipse\\workspace\\FaceRecongnition\\img\\face"+"\0";
		int flag = 0;
		try {
			byte[] prag2 = strPara2.getBytes("GBK");
			flag = RdcardDll.bell.UCommand1(Pcmd, prag0, prag1, prag2);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	//	System.out.println(flag);
		return flag;
	}
	
	//关闭端口
	public int closePerson() {
		String Pcmd = String.format("%c", 0x42);
		IntByReference prag0 = new IntByReference();
		prag0.setValue(0);
		IntByReference prag1 = new IntByReference();
		prag1.setValue(8811);
		byte[] prag2 = { 0x02, 0x27, 0x00, 0x00};
		int flag = RdcardDll.bell.UCommand1(Pcmd, prag0, prag1, prag2);
		return -256;
	}
	
	
	//返回实体类
	private PersonCard getPersonCard() {
		PersonCard card = new PersonCard();
		try {
			//姓名
			byte[] name1 = new byte[30];
			RdcardDll.bell.GetName(name1);
			//String name = new String(name1, "GBK");
			card.setPname(new String(name1, "GBK").trim());
			//性别
			byte[] sex1 = new byte[20];
			RdcardDll.bell.GetSexGB(sex1);
			card.setPsex(new String(sex1, "GBK").trim());
			//名族
			byte[] min1 = new byte[20];
			RdcardDll.bell.GetFolkGB(min1);
			card.setPmin(new String(min1, "GBK").trim());
			//住址
			byte[] addr = new byte[70];
			RdcardDll.bell.GetAddr(addr);
			card.setPadd(new String(addr, "GBK").trim());
			//身份证号
			byte[] idNum = new byte[50];
			RdcardDll.bell.GetIDNum(idNum);
			card.setPnum(new String(idNum, "GBK").trim());
			//签发部门
			byte[] dep1 = new byte[50];
			RdcardDll.bell.GetDep(dep1);
			card.setPdept(new String(dep1, "GBK").trim());
			//出生年月
			byte[] bir1 = new byte[20];
			RdcardDll.bell.GetBirth(bir1);
			card.setPbirth(new String(bir1, "GBK").trim());
			//起始日期
			byte[] begin = new byte[20];
			RdcardDll.bell.GetBegin(begin);
			card.setPstart(new String(begin, "GBK").trim());
			//结束日期
			byte[] end1 = new byte[20];
			RdcardDll.bell.GetEnd(end1);
			card.setPend(new String(end1, "GBK").trim());
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return card;
	}
	private boolean checkFlag(int flag) {
		if(flag == 62171 || flag == 62172 || flag == 62173) {
			return true;
		}else {
			return false;
		}
	}
	
	public PersonCard doRead() {
//		try {
//			Thread.sleep(50);
//		} catch (InterruptedException e) {
//			System.exit(0);
//		}
//		int flag = initPerson();
//		if(checkFlag(flag)) {
		flag = verfiyCard();
		flag =readInfo();
			if(checkFlag(flag)) {
				flag =showInfo();
				if(checkFlag(flag)) {
					return getPersonCard();
				}else {
					System.out.println("保存照片失败");
					return null;
				}
			}else {
				System.out.println("读取身份证信息失败");
				return null;
			}
//		}else {
//			System.out.println("身份证阅读机启动失败");
//			return null;
//		}
	}
	
	public static void main(String[] args) {
		Person person = new Person();
		System.out.println(person.ischange);
//		if (person.ischange) {
//			person.getPthread().interrupt();
//		}
		
	}
	
	public void setPersonCards() {
//		try {
//			Thread.sleep(500);
//		} catch (InterruptedException e) {
//			System.exit(0);
//		}
		card2 = doRead();
		if(card1 == null && card2 == null) {//未放置身份证
		//	ischange = false;
			setIschange(false);
		}else if(card1 != null && card2 == null) {//身份证取走
		//	ischange = false;
			setIschange(false);
		}else if(card1 == null && card2 != null) {//放置身份证
		//	ischange = true;
			setIschange(true);
		}else if(card1 != null && card2 != null && card1.equals(card2)) {//同一张身份证
		//	ischange = false;
			setIschange(false);
		}else if(card1 != null && card2 != null && !card1.equals(card2)) {//切换身份证
		//	ischange = true;
			setIschange(true);
		}
		card1 = card2;
	}
	
//	关闭端口和线程
//	public void closeThread() {
//		try {
//			pthread.interrupt();//线程关闭
//			closePerson();//关闭端口
//		} catch (Exception e) {
//			System.out.println("阅读器线程中止报错");
//			e.printStackTrace();
//			pthread.interrupt();
//		}
//	}
	
}

