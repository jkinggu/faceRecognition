package com.dx.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileFilter;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

import com.dx.pojo.Belongfor;
import com.dx.pojo.FaceLog;
import com.dx.pojo.ParamSetup;
import com.dx.pojo.Zkzdata;
import com.fr.third.org.apache.poi.hssf.usermodel.HSSFCell;
import com.fr.third.org.apache.poi.hssf.usermodel.HSSFRow;
import com.fr.third.org.apache.poi.hssf.usermodel.HSSFSheet;
import com.fr.third.org.apache.poi.hssf.usermodel.HSSFWorkbook;

public class ExportFileService {  
    private static final String SAVEPATH = "D:\\";   
    private static final String BUTTONTEXT = "导出";    
    private static final String  TITLE = "导出数据";    
    public ExportFileService(){  
          
    }  
    /** 
     *  
     * @param filters  可选择的导出类型 
     * @return file[0] 文件全路径；file[1] 导出类型 
     */  
    public static String[] exportFile(List<FileFilter> filters){    
        JFileChooser fileChooser = initEmportFile(filters);  
        int result = fileChooser.showOpenDialog(null);  
        String type = fileChooser.getFileFilter().getDescription();  
        if (result == JFileChooser.APPROVE_OPTION) {  
            File file = fileChooser.getSelectedFile();  
            String fn = file.getPath();  
            String[] fileAndType = new String[2];  
            fileAndType[0]=fn;  
            fileAndType[1]=type;  
            return fileAndType;  
        }  
        return null;  
    }  
    /** 
     * 文件对话框中判断是否覆盖已存在文件，加入带导入类型 
     * @param filters 可选择的导入类型 
     * @return 文件选择框 
     */  
    private static JFileChooser initEmportFile(List<FileFilter> filters) {  
        JFileChooser fileChooser = new JFileChooser(SAVEPATH) {  
            private static final long serialVersionUID = 1L;  
  
            public void approveSelection() {  
                File file;  
                String fileName = this.getSelectedFile().getAbsolutePath();  
  
                file = new File(fileName);  
                if (file.exists()) {  
                    int copy = JOptionPane.showConfirmDialog(null,  
                            "是否要覆盖当前文件？", "保存", JOptionPane.YES_NO_OPTION,  
                            JOptionPane.QUESTION_MESSAGE);  
                    if (copy == JOptionPane.YES_OPTION) {  
                        super.setSelectedFile(file);  
                        super.approveSelection();  
                    }  
                } else  
                    super.approveSelection();  
            }  
        };  
        fileChooser.setAcceptAllFileFilterUsed(false);  
        fileChooser.setApproveButtonText(BUTTONTEXT);  
        fileChooser.setDialogTitle(TITLE);  
        for(FileFilter one :filters){  
            fileChooser.addChoosableFileFilter( one);  
        }  
        return fileChooser;  
    }  
    /** 
     * 创建新的Excel 工作簿 
     * @param filename 待导出文件名称的全路径，不带后缀 
     * @param data 待导出数据，首个list<Object>是字段信息 
     * @param sheetName 表单名称 
     * @return true 导出成功；false 导出失败 
     */  
    @SuppressWarnings("deprecation")  
    public static boolean createExcel(String filename, List<List<Object>> data,  
            String sheetName) {   
        HSSFWorkbook workbook = null;  
        HSSFSheet sheet = null;  
        HSSFRow[] rows = null;  
        HSSFCell[][] cells = null;  
        workbook = new HSSFWorkbook();  
        if (sheetName.trim().equals("")) {  
            sheet = workbook.createSheet("表");  
        } else {  
            sheet = workbook.createSheet(sheetName);  
        }  
        try {  
            int row = 0;  
            int col = 0;  
            if (data.size() > 0) {  
                row = data.size();  
                col = data.get(0).size();  
            }  
            rows = new HSSFRow[row];  
            cells = new HSSFCell[row][col];  
            for (int i = 0; i < data.size(); i++) {  
                rows[i] = sheet.createRow((short) i);  
                for (int j = 0; j < data.get(i).size(); j++) {  
                    cells[i][j] = rows[i].createCell((short) j);  
                    cells[i][j].setCellType(HSSFCell.CELL_TYPE_STRING);  
                    if (data.get(i).get(j) != null  
                            && !data.get(i).get(j).toString().trim().equals("")) {  
                        cells[i][j].setCellValue(data.get(i).get(j).toString()  
                                .trim());  
                    } else {  
                        cells[i][j].setCellValue("");  
                    }  
                }  
            }  
            FileOutputStream fOut = new FileOutputStream(filename);  
            // 把相应的Excel 工作簿存盘  
            workbook.write(fOut);  
            fOut.flush();  
            // 操作结束，关闭文件  
            fOut.close();  
            return true;  
        } catch (Exception e) {  
            return false;  
        }  
    }  
    /** 
     * 生成XML文件 
     * @param filename 待导出文件名称的全路径，不带后缀 
     * @param data 待导出数据,首个list<Object>是字段信息 
     * @return true 导出成功；false 导出失败 
     */  
    public static boolean createXML(String filename, List list) {  
    	try {  
    		Document doc = DocumentHelper.createDocument();  
    		Element root = doc.addElement("allinfo");  
    		FaceLog logs = null ;
    		for(int i=0; i<list.size();i++) {
    			logs = (FaceLog) list.get(i);
    			Element row = root.addElement("FaceLog");
    			//root.addAttribute("name", "facelog");
    			createXmlfacelog(logs, row);
    			
    		}
            FileOutputStream fOut = new FileOutputStream(filename);  
            OutputFormat format = new OutputFormat("", true);  
            format.setEncoding("utf-8");  
            // 可以把System.out改为你要的流。  
            XMLWriter xmlWriter = new XMLWriter(fOut, format);  
            xmlWriter.write(doc);  
            xmlWriter.close();  
            return true;  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
        return false;  
    }
	public static boolean createXMLAllData(String filename, List<Belongfor> beflist, List<FaceLog> loglist,
			List<ParamSetup> parsetlist, List<Zkzdata> zkzlist) {
		try {
			Document doc = DocumentHelper.createDocument();  
    		Element root = doc.addElement("allinfo");  
    		FaceLog logs = null ;
    		Belongfor bef = null ;
    		ParamSetup paraset = null ;
    		Zkzdata zkz = null ;
    		
    		for(int i=0; i<loglist.size();i++) {
    			logs = loglist.get(i);
    			Element row = root.addElement("FaceLog");
    			createXmlfacelog(logs, row);
    			
    		}
    		for(int i=0; i<beflist.size();i++) {
    			bef = beflist.get(i);
    			Element row = root.addElement("Belongfor");
    			createXmlbelongfor(bef, row);
    		}
    		for(int i=0; i<parsetlist.size();i++) {
    			paraset = parsetlist.get(i);
    			Element row = root.addElement("ParamSetup");
    			createXmlparamset(paraset, row);
    		}
    		for(int i=0; i<zkzlist.size();i++) {
    			zkz = zkzlist.get(i);
    			Element row = root.addElement("Zkzdata");
    			createXmlzkzdata(zkz, row);
    		}
			
			FileOutputStream fOut = new FileOutputStream(filename);  
            OutputFormat format = new OutputFormat("", true);  
            format.setEncoding("utf-8");  
            // 可以把System.out改为你要的流。  
            XMLWriter xmlWriter = new XMLWriter(fOut, format);  
            xmlWriter.write(doc);  
            xmlWriter.close();  
            return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return false;
	}
	private static void createXmlzkzdata(Zkzdata zkz, Element row) {
		Element id = row.addElement("id");
		id.setText(zkz.getId()+"");
		id.addAttribute("name","id");
		
		Element xingming = row.addElement("xingming");
		xingming.setText(zkz.getXingming()+"");
		xingming.addAttribute("name","xingming");
		
		Element xingbie = row.addElement("xingbie");
		xingbie.setText(zkz.getXingbie()+"");
		xingbie.addAttribute("name","xingbie");
		
		Element nianlin = row.addElement("nianlin");
		nianlin.setText(zkz.getNianlin()+"");
		nianlin.addAttribute("name","nianlin");
		
		Element zkznum = row.addElement("zkznum");
		zkznum.setText(zkz.getZkznum()+"");
		zkznum.addAttribute("name","zkznum");
		
		Element upersonnum = row.addElement("upersonnum");
		upersonnum.setText(zkz.getUpersonnum()+"");
		upersonnum.addAttribute("name","upersonnum");
		
		Element danweiid = row.addElement("danweiid");
		danweiid.setText(zkz.getDanweiid()+"");
		danweiid.addAttribute("name","danweiid");
		
		Element danweiname = row.addElement("danweiname");
		danweiname.setText(zkz.getDanweiname()+"");
		danweiname.addAttribute("name","danweiname");
		
		Element baokaocode = row.addElement("baokaocode");
		baokaocode.setText(zkz.getBaokaocode()+"");
		baokaocode.addAttribute("name","baokaocode");
		
		Element baokaoname = row.addElement("baokaoname");
		baokaoname.setText(zkz.getBaokaoname()+"");
		baokaoname.addAttribute("name","baokaoname");
		
		Element jbcode = row.addElement("jbcode");
		jbcode.setText(zkz.getJbcode()+"");
		jbcode.addAttribute("name","jbcode");
		
		Element jbname = row.addElement("jbname");
		jbname.setText(zkz.getJbname()+"");
		jbname.addAttribute("name","jbname");
		
		Element kd1 = row.addElement("kd1");
		kd1.setText(zkz.getKd1()+"");
		kd1.addAttribute("name","kd1");
		
		Element kc1 = row.addElement("kc1");
		kc1.setText(zkz.getKc1()+"");
		kc1.addAttribute("name","kc1");
		
		Element zh1 = row.addElement("zh1");
		zh1.setText(zkz.getZh1()+"");
		zh1.addAttribute("name","zh1");
		
		Element sj1 = row.addElement("sj1");
		sj1.setText(zkz.getSj1()+"");
		sj1.addAttribute("name","sj1");
		
		Element dd1 = row.addElement("dd1");
		dd1.setText(zkz.getDd1()+"");
		dd1.addAttribute("name","dd1");
		
		Element kd2 = row.addElement("kd2");
		kd2.setText(zkz.getKd2());
		kd2.addAttribute("name","kd2");
		
		Element kc2 = row.addElement("kc2");
		kc2.setText(zkz.getKc2()+"");
		kc2.addAttribute("name","kc2");
		
		Element zh2 = row.addElement("zh2");
		zh2.setText(zkz.getZh2()+"");
		zh2.addAttribute("name","zh2");
		
		Element sj2 = row.addElement("sj2");
		sj2.setText(zkz.getSj2()+"");
		sj2.addAttribute("name","sj2");
		
		Element dd2 = row.addElement("dd2");
		dd2.setText(zkz.getDd2()+"");
		dd2.addAttribute("name","dd2");
		
		Element kd3 = row.addElement("kd3");
		kd3.setText(zkz.getKd3()+"");
		kd3.addAttribute("name","kd3");
		
		Element kc3 = row.addElement("kc3");
		kc3.setText(zkz.getKc3()+"");
		kc3.addAttribute("name","kc3");
		
		Element zh3 = row.addElement("zh3");
		zh3.setText(zkz.getZh3()+"");
		zh3.addAttribute("name","zh3");
		
		Element sj3 = row.addElement("sj3");
		sj3.setText(zkz.getSj3()+"");
		sj3.addAttribute("name","sj3");
		
		Element dd3 = row.addElement("dd3");
		dd3.setText(zkz.getDd3()+"");
		dd3.addAttribute("name","dd3");
		
		Element zmarks = row.addElement("zmarks");
		zmarks.setText(zkz.getZmarks()+"");
		zmarks.addAttribute("name","zmarks");
		
		Element dishiname = row.addElement("dishiname");
		dishiname.setText(zkz.getDishiname()+"");
		dishiname.addAttribute("name","dishiname");
		
	}
	private static void createXmlparamset(ParamSetup paraset, Element row) {
		Element pid = row.addElement("pid");
		pid.setText(paraset.getPid()+"");
		pid.addAttribute("name", "pid");
		
		Element dishi = row.addElement("dishi");
		dishi.setText(paraset.getDishi()+"");
		dishi.addAttribute("name", "dishi");
		
		Element dishiname = row.addElement("dishiname");
		dishiname.setText(paraset.getDishiname()+"");
		dishiname.addAttribute("name", "dishiname");
		
		Element kaodianname = row.addElement("kaodianname");
		kaodianname.setText(paraset.getKaodianname()+"");
		kaodianname.addAttribute("name", "kaodianname");
		
		Element kaochang = row.addElement("kaochang");
		kaochang.setText(paraset.getKaochang()+"");
		kaochang.addAttribute("name", "kaochang");
		
		Element starttime = row.addElement("starttime");
		starttime.setText(paraset.getStarttime()+"");
		starttime.addAttribute("name", "starttime");
		
		Element endtime = row.addElement("endtime");
		endtime.setText(paraset.getEndtime()+"");
		endtime.addAttribute("name", "endtime");
		
		Element remarks = row.addElement("remarks");
		remarks.setText(paraset.getRemarks()+"");
		remarks.addAttribute("name", "remarks");
		
		Element adminname = row.addElement("adminname");
		adminname.setText(paraset.getAdminname()+"");
		adminname.addAttribute("name", "adminname");
		
		Element adminid = row.addElement("adminid");
		adminid.setText(paraset.getAdminid()+"");
		adminid.addAttribute("name", "adminid");
		
		Element didian = row.addElement("didian");
		didian.setText(paraset.getDidian()+"");
		didian.addAttribute("name", "didian");
		
		Element ustatu = row.addElement("ustatu");
		ustatu.setText(paraset.getUstatu()+"");
		ustatu.addAttribute("name", "ustatu");
		
	}
	private static void createXmlbelongfor(Belongfor bef, Element row) {
		Element allcode = row.addElement("allcode");
		allcode.setText(bef.getAllcode()+"");
		allcode.addAttribute("name", "allcode");
		
		Element no1code = row.addElement("no1code");
		no1code.setText(bef.getNo1code()+"");
		no1code.addAttribute("name", "no1code");
		
		Element no1name = row.addElement("no1name");
		no1name.setText(bef.getNo1name()+"");
		no1name.addAttribute("name", "no1name");
		
		Element no2code = row.addElement("no2code");
		no2code.setText(bef.getNo2code()+"");
		no2code.addAttribute("name", "no2code");
		
		Element no2name = row.addElement("no2name");
		no2name.setText(bef.getNo2name());
		no2name.addAttribute("name", "no2name");
		
		Element no3code = row.addElement("no3code");
		no3code.setText(bef.getNo3code());
		no3code.addAttribute("name", "no3code");
		
		Element no3name = row.addElement("no3name");
		no3name.setText(bef.getNo3name());
		no3name.addAttribute("name", "no3name");
		
		Element no4code = row.addElement("no4code");
		no4code.setText(bef.getNo4code());
		no4code.addAttribute("name", "no4code");
		
		Element no4name = row.addElement("no4name");
		no4name.setText(bef.getNo4name());
		no4name.addAttribute("name", "no4name");
		
		Element id = row.addElement("id");
		id.setText(bef.getId());
		id.addAttribute("name", "id");
		
		Element leibie = row.addElement("leibie");
		leibie.setText(bef.getLeibie()+"");
		leibie.addAttribute("name", "leibie");
		
		
		
		
	}
	private static void createXmlfacelog(FaceLog logs, Element row) {
		Element id = row.addElement("id"); 
		id.setText(logs.getId()+"");
		id.addAttribute("name", "id");  
		
		Element sfz = row.addElement("sfz"); 
		sfz.setText(logs.getSfz()+"");
		sfz.addAttribute("name", "sfz"); 
		
		Element xingming = row.addElement("xingming"); 
		xingming.setText(logs.getXingming()+"");
		xingming.addAttribute("name", "xingming"); 
		
		Element xingbie = row.addElement("xingbie"); 
		xingbie.setText(logs.getXingbie()+"");
		xingbie.addAttribute("name", "xingbie"); 
		
		Element shibieleixing = row.addElement("shibieleixing"); 
		shibieleixing.setText(logs.getShibieleixing()+"");
		shibieleixing.addAttribute("name", "shibieleixing"); 
		
		Element shibieleixingint = row.addElement("shibieleixingint"); 
		shibieleixingint.setText(logs.getShibieleixingint()+"");
		shibieleixingint.addAttribute("name", "shibieleixingint"); 
		
		Element shijian = row.addElement("shijian"); 
		shijian.setText(logs.getShijian()+"");
		shijian.addAttribute("name", "shijian"); 
		
		Element renlianphoto = row.addElement("renlianphoto"); 
		renlianphoto.setText(logs.getRenlianphoto()+"");
		renlianphoto.addAttribute("name", "renlianphoto"); 
		
		Element remarks = row.addElement("remarks"); 
		remarks.setText(logs.getRemarks()+"");
		remarks.addAttribute("name", "remarks"); 
		
		Element sfzphoto = row.addElement("sfzphoto"); 
		sfzphoto.setText(logs.getSfzphoto()+"");
		sfzphoto.addAttribute("name", "sfzphoto");
		
		Element changci = row.addElement("changci"); 
		changci.setText(logs.getChangci()+"");
		changci.addAttribute("name", "changci");
		
		Element denglumana = row.addElement("denglumana"); 
		denglumana.setText(logs.getDenglumana()+"");
		denglumana.addAttribute("name", "denglumana");
		
		Element renzcount = row.addElement("renzcount"); 
		renzcount.setText(logs.getRenzcount()+"");
		renzcount.addAttribute("name", "renzcount");
	}  
}
