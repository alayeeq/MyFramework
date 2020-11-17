package com.MyFramework_1.pages;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.NumberToTextConverter;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

import com.MyFramework_1baseclasses.TestBase;

public class inputConsolPOM extends TestBase{

	public inputConsolPOM(WebDriver d) {
		
		 driver = d;
		 PageFactory.initElements(driver, this);
		}

	String absPath=null;
	String ipPath=null;
	String archievePath=null;
	String errPath=null;
	String tdPath=null;
	String archieveTD=null;
	ProductPagePOM p = new ProductPagePOM(driver);
	
	public void initPath()
	{
		logger.info("Folder Path initialized");
	      String currentDirectory = System.getProperty("user.dir");
	      System.out.println("The current working directory is " + currentDirectory);
	      absPath=currentDirectory;
	      ipPath=absPath+"\\TestData\\Input";
	  	  archievePath=absPath+"\\TestData\\Archieve";
	  	  errPath=absPath+"\\TestData\\Error";
	  	  tdPath=absPath+"\\TestData\\TD";
	  	  archieveTD=absPath+"\\TestData\\ArchieveTD";
	}
	
	
	@SuppressWarnings("deprecation")
	public String readCell(XSSFCell cel)
	{
		String cellValue=null;
		try {
            CellType type = cel.getCellType();
			
            if ((type == CellType.STRING) ||(type == CellType.BLANK))
            {					
            	cellValue=cel.getStringCellValue();
            	//System.out.println(type);
            }
            else if (type == CellType.NUMERIC)  
            {
            	cellValue=NumberToTextConverter.toText(cel.getNumericCellValue());
            	//System.out.println(type);
            	
            }
            else  if (HSSFDateUtil.isCellDateFormatted(cel))
            {
				Date date=cel.getDateCellValue();
				//System.out.println(date);
				DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");  
				cellValue=dateFormat.format(date);
            	//System.out.println(type);	
            }
            else 
            {
            	cellValue=cel.getStringCellValue();
            	//System.out.println(type);
            }
			}catch(NullPointerException e) 
        { 
            //System.out.print("NullPointerException Caught"); 
         	//td_Set[row][col]=cl.getStringCellValue();
        } 
		
		cellValue.trim();
		return cellValue;
		
	}
	
	public String[] ipRead(String ipFilName)
	{
		String ipPathAbs=ipPath+"\\"+ipFilName;
		//String[] ipClnFils=this.getIPFiles();
		System.out.println(ipPathAbs);
		
		int ip_Date_Row = 1;
		int ip_Emp_No_Row = 2;
		int ip_Emp_Name_Row = 3;
		int ip_Home_address_Row = 4;
		//int ip_Home_address_Row1 = 5;
		
		int ip_City = 5;
		int ip_State = 6;
		int ip_Zip = 7;
		
		int ip_Email_Address_Row = 8;
		int ip_Cell_No_Row = 9;
		int ip_Manager_Name_Row = 10;
		int ip_Manager_Email_Row = 11;
		int ip_Prod_1_Row=13;
		boolean shtChck=true;
		//boolean contentChck=true;
		
		String sheetName="Requistion";
		String[] ip_val=new String[22];
		
		FileInputStream fis = null;
		XSSFWorkbook wb=null;
		XSSFSheet sh=null;
		
		try {
			File fil=new File(ipPathAbs);
			fis = new FileInputStream(fil);
			wb = new XSSFWorkbook(fis);
			sh=wb.getSheet(sheetName);
				
			//Requistion sheet check 
			String dummy=sh.getSheetName();
			String cel_Type=null;
			//ip_Date
			{
				XSSFRow rw=sh.getRow(ip_Date_Row);
				XSSFCell cl=rw.getCell(1);

				CellType type = cl.getCellType();
				
	            if ((type == CellType.STRING) ||(type == CellType.BLANK))
	            {					
	            	String cellValue=cl.getStringCellValue();
	            	ip_val[0]=cellValue;
	            
	            }
	            else if (type == CellType.NUMERIC)  
	            {
					Date date=cl.getDateCellValue();
					DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");  
					ip_val[0]=dateFormat.format(date);
	            }
				
				
				logger.info(ip_val[0]);
				//logger.info("experiment");
				//logger.info(this.readCell(cl));
			}
			
			//ip_Emp_No
			{
				XSSFRow rw=sh.getRow(ip_Emp_No_Row);
				XSSFCell cl=rw.getCell(1);
				ip_val[1]=this.readCell(cl);
				logger.info(ip_val[1]);
			}
			
			//ip_Emp_Name
			{
				XSSFRow rw=sh.getRow(ip_Emp_Name_Row);
				XSSFCell cl=rw.getCell(1);
				ip_val[2]=this.readCell(cl);
				logger.info(ip_val[2]);
			}
			
			//ip_Home_address
			{
				XSSFRow rw=sh.getRow(ip_Home_address_Row);
				XSSFCell cl=rw.getCell(1);
				ip_val[3]=this.readCell(cl);
				logger.info(ip_val[3]);
			}
			
			
			//City
			{
				XSSFRow rw=sh.getRow(ip_City);
				XSSFCell cl=rw.getCell(1);
				ip_val[4]=this.readCell(cl);
				logger.info(ip_val[4]);
			}
			
			//State
			{
				XSSFRow rw=sh.getRow(ip_State);
				XSSFCell cl=rw.getCell(1);
				ip_val[5]=this.readCell(cl);
				logger.info(ip_val[5]);
			}
			
			//Zip
			{
				XSSFRow rw=sh.getRow(ip_Zip);
				XSSFCell cl=rw.getCell(1);
				ip_val[6]=this.readCell(cl);
				logger.info(ip_val[6]);
			}
			
			/*//ip_Home_address1
			{
				String[] clnAddr=new String[3];
				XSSFRow rw=sh.getRow(ip_Home_address_Row1);
				XSSFCell cl=rw.getCell(1);
				ip_val[4]=this.readCell(cl);
				clnAddr=this.addrSplitCity(ip_val[4]);
				logger.info(ip_val[4]);
				
				ip_val[4]=clnAddr[0]+","+clnAddr[2]+","+clnAddr[1];
				ip_val[19]=clnAddr[0];
				ip_val[20]=clnAddr[2];
				ip_val[21]=clnAddr[1];
				logger.info(ip_val[4]);
			}*/
			
			//ip_Email_Address
			{
				XSSFRow rw=sh.getRow(ip_Email_Address_Row);
				XSSFCell cl=rw.getCell(1);
				ip_val[7]=this.readCell(cl);
				logger.info(ip_val[7]);
			}
			
			//ip_Cell_No
			{
				XSSFRow rw=sh.getRow(ip_Cell_No_Row);
				XSSFCell cl=rw.getCell(1);
				ip_val[8]=this.readCell(cl);
				logger.info(ip_val[8]);
			}
			
			//ip_Manager_Name
			{
				XSSFRow rw=sh.getRow(ip_Manager_Name_Row);
				XSSFCell cl=rw.getCell(1);
				ip_val[9]=this.readCell(cl);
				logger.info(ip_val[9]);
			}
			
			//ip_Manager_Email
			{
				XSSFRow rw=sh.getRow(ip_Manager_Email_Row);
				XSSFCell cl=rw.getCell(1);
				ip_val[10]=this.readCell(cl);
				logger.info(ip_val[10]);
			}
			
			
			//Product details
			String tmp=null;
			int counter1=ip_Prod_1_Row;
			int counter2=11;
			
			boolean flag=false;
			{
			XSSFRow rw=sh.getRow(counter1);
			XSSFCell cl=rw.getCell(1);
			tmp=cl.getStringCellValue();
			}
			
			if(!((tmp.equalsIgnoreCase("TOTAL"))||(tmp.isEmpty())))
			{
			flag=true;
			}
			
			
			while(flag)
			{
				ip_val[counter2]=tmp;
				logger.info(ip_val[counter2]);
				counter1++;
				counter2++;
				{
					XSSFRow rw=sh.getRow(counter1);
					XSSFCell cl=rw.getCell(1);
					tmp=cl.getStringCellValue();
				}
				if(((tmp.equalsIgnoreCase("TOTAL"))||(tmp.isEmpty())||(counter2>18)))
				{
					flag=false;
				}
			}
			
		} catch(NullPointerException e){
			logger.info("Sheet --> Requistion tab is missing" );
			ip_val[0]="false";
		}catch (java.lang.IllegalStateException e) {
			e.printStackTrace();
			logger.info("Sheet --> Data is inconsistent" );
			ip_val[0]="false";
		}catch (java.lang.StringIndexOutOfBoundsException e) {
			/*logger.info("Sheet --> Address is not in expected sandard" );*/
			e.printStackTrace();
			logger.info("Sheet --> Data is inconsistent" );
			ip_val[0]="false";
		}catch (IOException e) {
			e.printStackTrace();
			logger.info("Sheet --> Data is inconsistent" );
			ip_val[0]="false";
		}
		
		try {
		fis.close();
		wb.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
		return ip_val;
		
	}
	
	public String[] addrSplitCity(String ip_val)
	{
		String[] retArr=new String[3];
	String City=null;
	String finalAddr=null;
	ip_val=ip_val.trim();
	
	//---------
	int commaSep=ip_val.indexOf(",");
	int blankSep=ip_val.indexOf(" ");
	int sep=0;
	
	if((commaSep>0)&&(commaSep<blankSep))
	{
		sep=commaSep;
	}
	else 
	{
		sep=blankSep;
	}
	int cityEndPos=sep;
	//System.out.println("City seperator "+sep+"city end pos :"+cityEndPos);
	City=ip_val.substring(0, cityEndPos);
	City=City.trim();
	retArr[0]=City;
	logger.info(City);
	//----------
	
	String Zip1=null;
	String Zip2=null;
	String Zip=null;
	boolean extZip=true;
	int zipStartPos=0;
	
	int ipValLen=ip_val.length();
	int zipSep=ip_val.indexOf("-");
	//System.out.println("Zip Sep index"+zipSep);
	
	if(!(zipSep<0))
	{
		zipStartPos=zipSep-5;
		//System.out.println("Zip Start index " +zipStartPos);
		Zip1=ip_val.substring(zipStartPos,zipSep);
		Zip1=Zip1.trim();
		
		Zip2=ip_val.substring(zipSep+1);
		Zip2=Zip2.trim();
		//System.out.println("Zip1 :"+Zip1+"Zip2 :"+Zip2);
		Zip=Zip1+"-"+Zip2;
		logger.info(Zip);
	}
	else
	{
		zipStartPos=ipValLen-5;
		//System.out.println("Zip Start index " +zipStartPos);
		Zip1=ip_val.substring(zipStartPos);
		Zip1=Zip1.trim();
		
		Zip=Zip1;
		logger.info(Zip1);
		extZip=false;
	}
	retArr[1]=Zip;
	
	//----------
	
	int stStartPos=cityEndPos+1;
	int stEndPos=0;
	String State=null;
	String tmp1=null;
	String tmp=ip_val.substring(cityEndPos+1);
	//System.out.println("tmp state string :"+tmp);
	
	int commaSep1=ip_val.indexOf(",",stStartPos);
	int sep_cntr=zipStartPos;
	int sep1=0;
	
	if(commaSep1>=0)
	{
		stEndPos=(commaSep1);
		State=ip_val.substring(stStartPos, stEndPos);
	}
	else 
	{
		stEndPos=(sep_cntr);
		State=ip_val.substring(stStartPos, stEndPos);
	}
	
	State=State.trim();
	retArr[2]=State;
	//System.out.println("state start "+stStartPos+" end pos "+stEndPos);
	logger.info("State "+State);
	
	finalAddr=City+","+State+","+Zip;
	return retArr;
	}
	
	public String statusChck(int row)
	{
		String tdPathAbs=tdPath+"\\TD.xlsx";
		int status_Col = 1;
		String msg = null;
		
		try {
			File fil=new File(tdPathAbs);
			FileInputStream fis=new FileInputStream(fil);
			XSSFWorkbook wb = new XSSFWorkbook(fis);
			XSSFSheet sh=wb.getSheet("Sheet1");

			XSSFRow rw=sh.getRow(row);
			XSSFCell cl=rw.getCell(status_Col);
			msg=this.readCell(cl);
			logger.info("Status check - "+msg);
			
			fis.close();
			FileOutputStream fos=new FileOutputStream(fil);
			wb.write(fos);
			fos.close();
			wb.close();
			
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		return msg;
	}
	
	public void statusUpdate(int row, String msg)
	{
		String tdPathAbs=tdPath+"\\TD.xlsx";
		int status_Col = 1;
		try {
			File fil=new File(tdPathAbs);
			FileInputStream fis=new FileInputStream(fil);
			XSSFWorkbook wb = new XSSFWorkbook(fis);
			XSSFSheet sh=wb.getSheet("Sheet1");

			Row rw=sh.getRow(row);
			Cell seq=rw.createCell(status_Col);
			seq.setCellValue(msg);
			
			fis.close();
			FileOutputStream fos=new FileOutputStream(fil);
			wb.write(fos);
			fos.close();
			wb.close();
			
		}catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	//Asif code for records processed
		public void nofRecords(int row, String msg)
		{
			String tdPathAbs=tdPath+"\\TD.xlsx";
			
			
			int a = p.numberOfCols();
			
			int status_Col = a+4;
			try {
				File fil=new File(tdPathAbs);
				FileInputStream fis=new FileInputStream(fil);
				XSSFWorkbook wb = new XSSFWorkbook(fis);
				XSSFSheet sh=wb.getSheet("Sheet1");

				Row rw=sh.getRow(row);
				
				
				Cell seq=rw.createCell(status_Col);
				seq.setCellValue(msg);
				
				fis.close();
				FileOutputStream fos=new FileOutputStream(fil);
				wb.write(fos);
				fos.close();
				wb.close();
				
			}catch(Exception e)
			{
				e.printStackTrace();
			}
		}
	
	public void xlwrite()
	{
		String tdPathAbs=tdPath+"\\TD.xlsx";
		//String[] ipClnFils=this.getIPFiles();
		logger.info(tdPathAbs);
		
		int seq_Col = 0;
		int Date_Col = 2;
		int Emp_No_Col = 3;
		int Emp_Name_Col = 4;
		int Home_address_Col = 5;
		//int Home_address1_Col = 6;
		
		int city_Col=6;
		int state_Col=7;
		int zip_Col=8;

		
		int Email_Address_Col = 9;
		int Cell_No_Col = 10;
		int Manager_Name_Col = 11;
		int Manager_Email_Col = 12;
		int Prod1_Col = 13;
		int Prod2_Col = 14;
		int Prod3_Col = 15;
		int Prod4_Col = 16;
		int Prod5_Col = 17;
		int Prod6_Col = 18;
		int Prod7_Col = 19;
		int Prod8_Col = 20;
		int Prod9_Col = 21;
		int Prod10_Col = 22;
		int Process_DT_Col = 23;
		int PRN_Col=24;
		int fileName_Col = 25;
		
		/*int city_Col=24;
		int state_Col=25;
		int zip_Col=26;
*/
		//Get Date
		   DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm:ss");  
		   LocalDateTime now = LocalDateTime.now();  
		   String timeStamp=dtf.format(now);
		   logger.info(timeStamp);  
		
		try {
			File fil=new File(tdPathAbs);
			String[] clnFiles=this.getIPFiles();

			/*for(String p:clnFiles)
			{
				System.out.println(p);
			}*/
			
			for(String p:clnFiles)
			{
				FileInputStream fis=new FileInputStream(fil);
				XSSFWorkbook wb = new XSSFWorkbook(fis);
				XSSFSheet sh=wb.getSheet("Sheet1");
				int rowCount = sh.getLastRowNum();
				logger.info("ROW COUNT "+rowCount);
				++rowCount;

				String[] ipVal=this.ipRead(p);
				if(!(ipVal[0].equalsIgnoreCase("false")))
				{
					Row rw=sh.createRow(rowCount);
					
					Cell seq=rw.createCell(seq_Col);
					seq.setCellValue(rowCount);
					
					Cell cel_Date=rw.createCell(Date_Col);
					cel_Date.setCellValue(ipVal[0]);
					
					Cell cel_Emp_No=rw.createCell(Emp_No_Col);
					cel_Emp_No.setCellValue(ipVal[1]);
					
					Cell cel_Emp_Name=rw.createCell(Emp_Name_Col);
					cel_Emp_Name.setCellValue(ipVal[2]);
					
					Cell cel_Home_address=rw.createCell(Home_address_Col);
					cel_Home_address.setCellValue(ipVal[3]);
					
/*					Cell cel_Home_address1=rw.createCell(Home_address1_Col);
					cel_Home_address1.setCellValue(ipVal[4]);*/
			
					//City, State & Zip changes
					Cell cel_City=rw.createCell(city_Col);
					cel_City.setCellValue(ipVal[4]);
			
					Cell cel_State=rw.createCell(state_Col);
					cel_State.setCellValue(ipVal[5]);
			
					Cell cel_Zip=rw.createCell(zip_Col);
					cel_Zip.setCellValue(ipVal[6]);
					
					//
					
					Cell cel_Email_Address=rw.createCell(Email_Address_Col);
					cel_Email_Address.setCellValue(ipVal[7]);				
					
					Cell cel_Cell_No=rw.createCell(Cell_No_Col);
					cel_Cell_No.setCellValue(ipVal[8]);
					
					Cell cel_Manager_Name=rw.createCell(Manager_Name_Col);
					cel_Manager_Name.setCellValue(ipVal[9]);
					
					Cell cel_Manager_Email=rw.createCell(Manager_Email_Col);
					cel_Manager_Email.setCellValue(ipVal[10]);
					
					Cell cel_Prod1=rw.createCell(Prod1_Col);
					cel_Prod1.setCellValue(ipVal[11]);
					
					Cell cel_Prod2=rw.createCell(Prod2_Col);
					cel_Prod2.setCellValue(ipVal[12]);
					
					Cell cel_Prod3=rw.createCell(Prod3_Col);
					cel_Prod3.setCellValue(ipVal[13]);
					
					Cell cel_Prod4=rw.createCell(Prod4_Col);
					cel_Prod4.setCellValue(ipVal[14]);
					
					Cell cel_Prod5=rw.createCell(Prod5_Col);
					cel_Prod5.setCellValue(ipVal[15]);
					
					Cell cel_Prod6=rw.createCell(Prod6_Col);
					cel_Prod6.setCellValue(ipVal[16]);
					
					Cell cel_Prod7=rw.createCell(Prod7_Col);
					cel_Prod7.setCellValue(ipVal[17]);
					
					Cell cel_Prod8=rw.createCell(Prod8_Col);
					cel_Prod8.setCellValue(ipVal[18]);
					
					Cell cel_Prod9=rw.createCell(Prod9_Col);
					cel_Prod9.setCellValue(ipVal[19]);
					
					Cell cel_Prod10=rw.createCell(Prod10_Col);
					cel_Prod10.setCellValue(ipVal[20]);
					
					Cell cel_Process_DT=rw.createCell(Process_DT_Col);
					cel_Process_DT.setCellValue(timeStamp);
					
					Cell cel_fileName=rw.createCell(fileName_Col);
					cel_fileName.setCellValue(p);
					
/*					Cell cel_City=rw.createCell(city_Col);
					cel_City.setCellValue(ipVal[19]);
					
					Cell cel_State=rw.createCell(state_Col);
					cel_State.setCellValue(ipVal[20]);
					
					Cell cel_Zip=rw.createCell(zip_Col);
					cel_Zip.setCellValue(ipVal[21]);
*/					
					this.archieve(p);
					fis.close();
					FileOutputStream fos=new FileOutputStream(fil);
					wb.write(fos);
					fos.close();
					wb.close();
				}
				else {
					//Incorrect data exist in input file, File to be moved to Error folder.
					fis.close();
					FileOutputStream fos=new FileOutputStream(fil);
					wb.write(fos);
					fos.close();
					wb.close();
					
					String ipPathAbs=ipPath+"\\"+p;
					logger.info(ipPath);
					logger.info("File moved to error for data inconisstency"+ipPathAbs);
					String errPathAbs=errPath+"\\"+p;
					logger.info(errPathAbs);
					
					this.moveFile(ipPathAbs, errPathAbs);
				}

			}
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	

	public void WeedOut(String[] ipFiles)
	{
		logger.info("Weed out method init ");
		for(String f:ipFiles)
			{
			int len=f.length();
			int dotPos=f.indexOf(".",(len-5));
			String tmp=f.substring(dotPos);
			System.out.println(tmp);
			if(!tmp.equalsIgnoreCase(".xlsx"))
			{
				String ipPathAbs=ipPath+"\\"+f;
				logger.info(ipPath);
				logger.info(ipPathAbs);
				String errPathAbs=errPath+"\\"+f;
				logger.info(errPathAbs);
				
				this.moveFile(ipPathAbs, errPathAbs);
			}
		}
	}
	
	public void archieve(String file)
	{
		String ipPathAbs=ipPath+"\\"+file;
		logger.info(ipPathAbs);
		String arrPathAbs=archievePath+"\\"+file;
		logger.info(arrPathAbs);
		
		this.moveFile(ipPathAbs, arrPathAbs);
	}
	
	   private void moveFile(String src, String dest ) {
		   
		      Path result = null;
		      try {
		         result = Files.move(Paths.get(src), Paths.get(dest));
		      } catch (IOException e) {
		         logger.info("Exception while moving file: " + e.getMessage());
		      }
		      if(result != null) {
		         logger.info("File moved successfully." + dest);
		      }else{
		         logger.info("File movement failed." + dest);
		      }
	   }
	
	   public void copyFile(String srcPth, String dstPth) 
	   {
		   logger.info("Copy File Method init");
		   File src=new File(srcPth);
		   File dst=new File(dstPth);
		   try {
			   if(src.exists())
			   {
				   if(!(dst.exists()))
				   {
					   Files.copy(src.toPath(), dst.toPath());   
					   logger.info("File Copy successfull Source "+srcPth+" destination :"+dstPth);
				   }
				   else
				   {
					   logger.info("Desitination file Already Exist- Delete and Retry :"+dstPth);
					   this.archieveTD();
					   Files.copy(src.toPath(), dst.toPath());   
					   logger.info("File Copy successfull Source "+srcPth+" destination :"+dstPth);
				   }
			   }
			   else {
				   logger.info("Source file for Copy is NOT avavilable :"+srcPth);
			   }
		} catch (IOException e) {
			e.printStackTrace();
		}
	   }
	   
	   
	   public void archieveTD() {
		   
		   logger.info("Archieve TD Method init");
		   //Get Date
		   DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MMddyyyyHHmm");  
		   LocalDateTime now = LocalDateTime.now();  
		   String timeStamp=dtf.format(now);
		   //logger.info(timeStamp);  
		   
		   String tdArchievePathAbs=archieveTD+"\\TD_TS"+timeStamp+".xlsx"; 
		   String tdPathAbs=tdPath+"\\TD.xlsx";
		
		   this.moveFile(tdPathAbs, tdArchievePathAbs);

	   }
	   
	   public void clearTD() {

		   logger.info("Test Data Template creation");
		   
		   String tdGCPathAbs=tdPath+"\\TD_GC.xlsx";
		   String tdPathAbs=tdPath+"\\TD.xlsx";
		   try {
			   this.copyFile(tdGCPathAbs, tdPathAbs);	
		   }catch(Exception e) {
			   e.printStackTrace();
		   }

	   }
	   
	   
		public String[] getIPFiles ()
		{
			logger.info("GET IP File method call init");
			String[] filNames;
			logger.info(ipPath);
			File fil=new File(ipPath);
			filNames=fil.list();
			
			try{
			  for(String p:filNames)
			  {
				  logger.info(p);
			  }
			}catch(NullPointerException e) {
			     logger.info("Input Folder does NOT Exist :"+ipPath);
			  }
			return filNames;
		}
	
}
