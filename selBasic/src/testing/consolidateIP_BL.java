package testing;

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

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.NumberToTextConverter;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class consolidateIP_BL {
	

	String absPath=null;
	String ipPath=null;
	String archievePath=null;
	String errPath=null;
	String tdPath=null;
	
	public void initPath()
	{
	      String currentDirectory = System.getProperty("user.dir");
	      System.out.println("The current working directory is " + currentDirectory);
	      absPath=currentDirectory;
	      ipPath=absPath+"\\TestData\\Input";
	  	  archievePath=absPath+"\\TestData\\Archieve";
	  	  errPath=absPath+"\\TestData\\Error";
	  	  tdPath=absPath+"\\TestData\\TD";
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
		int ip_Home_address_Row1 = 5;
		int ip_Email_Address_Row = 6;
		int ip_Cell_No_Row = 7;
		int ip_Manager_Name_Row = 8;
		int ip_Manager_Email_Row = 9;
		int ip_Prod_1_Row=11;

		String[] ip_val=new String[19];

		try {
			File fil=new File(ipPathAbs);
			FileInputStream fis;
			fis = new FileInputStream(fil);
			
			XSSFWorkbook wb = new XSSFWorkbook(fis);
			XSSFSheet sh=wb.getSheet("Requistion");
			
			//ip_Date
			{
				XSSFRow rw=sh.getRow(ip_Date_Row);
				XSSFCell cl=rw.getCell(1);
				
				Date date=cl.getDateCellValue();
				System.out.println(date);
				DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");  
				ip_val[0]=dateFormat.format(date);
				//[1]=cl.getStringCellValue();
				System.out.println(ip_val[0]);
			}
			
			//ip_Emp_No
			{
				XSSFRow rw=sh.getRow(ip_Emp_No_Row);
				XSSFCell cl=rw.getCell(1);
				ip_val[1]=cl.getStringCellValue();
				System.out.println(ip_val[1]);
			}
			
			//ip_Emp_Name
			{
				XSSFRow rw=sh.getRow(ip_Emp_Name_Row);
				XSSFCell cl=rw.getCell(1);
				ip_val[2]=cl.getStringCellValue();
				System.out.println(ip_val[2]);
			}
			
			//ip_Home_address
			{
				XSSFRow rw=sh.getRow(ip_Home_address_Row);
				XSSFCell cl=rw.getCell(1);
				ip_val[3]=cl.getStringCellValue();
				System.out.println(ip_val[3]);
			}
			
			//ip_Home_address1
			{
				XSSFRow rw=sh.getRow(ip_Home_address_Row1);
				XSSFCell cl=rw.getCell(1);
				ip_val[4]=cl.getStringCellValue();
				System.out.println(ip_val[4]);
			}
			
			//ip_Email_Address
			{
				XSSFRow rw=sh.getRow(ip_Email_Address_Row);
				XSSFCell cl=rw.getCell(1);
				ip_val[5]=cl.getStringCellValue();
				System.out.println(ip_val[5]);
			}
			
			//ip_Cell_No
			{
				XSSFRow rw=sh.getRow(ip_Cell_No_Row);
				XSSFCell cl=rw.getCell(1);
				String str = NumberToTextConverter.toText(cl.getNumericCellValue());
				ip_val[6]=str;
				System.out.println(ip_val[6]);
			}
			
			//ip_Manager_Name
			{
				XSSFRow rw=sh.getRow(ip_Manager_Name_Row);
				XSSFCell cl=rw.getCell(1);
				ip_val[7]=cl.getStringCellValue();
				System.out.println(ip_val[7]);
			}
			
			//ip_Manager_Email
			{
				XSSFRow rw=sh.getRow(ip_Manager_Email_Row);
				XSSFCell cl=rw.getCell(1);
				ip_val[8]=cl.getStringCellValue();
				System.out.println(ip_val[8]);
			}
			
			
			//Product details
			String tmp=null;
			int counter1=ip_Prod_1_Row;
			int counter2=9;
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
				System.out.println(ip_val[counter2]);
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
			
			fis.close();
			wb.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		
		}
		
		
		return ip_val;
		
	}
	
	public void xlwrite()
	{
		String tdPathAbs=tdPath+"\\TD.xlsx";
		//String[] ipClnFils=this.getIPFiles();
		System.out.println(tdPathAbs);
		
		int seq_Col = 0;
		int Date_Col = 2;
		int Emp_No_Col = 3;
		int Emp_Name_Col = 4;
		int Home_address_Col = 5;
		int Home_address1_Col = 6;
		int Email_Address_Col = 7;
		int Cell_No_Col = 8;
		int Manager_Name_Col = 9;
		int Manager_Email_Col = 10;
		int Prod1_Col = 11;
		int Prod2_Col = 12;
		int Prod3_Col = 13;
		int Prod4_Col = 14;
		int Prod5_Col = 15;
		int Prod6_Col = 16;
		int Prod7_Col = 17;
		int Prod8_Col = 18;
		int Prod9_Col = 19;
		int Prod10_Col = 20;
		int Process_DT_Col = 21;
		int fileName_Col = 23;

		//Get Date
		   DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm:ss");  
		   LocalDateTime now = LocalDateTime.now();  
		   String timeStamp=dtf.format(now);
		   System.out.println(timeStamp);  
		
		
		
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
				System.out.println(rowCount);
				++rowCount;

				
				String[] ipVal=this.ipRead(p);
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
				
				Cell cel_Home_address1=rw.createCell(Home_address1_Col);
				cel_Home_address1.setCellValue(ipVal[4]);
				
				Cell cel_Email_Address=rw.createCell(Email_Address_Col);
				cel_Email_Address.setCellValue(ipVal[5]);				
				
				Cell cel_Cell_No=rw.createCell(Cell_No_Col);
				cel_Cell_No.setCellValue(ipVal[6]);
				
				Cell cel_Manager_Name=rw.createCell(Manager_Name_Col);
				cel_Manager_Name.setCellValue(ipVal[7]);
				
				Cell cel_Manager_Email=rw.createCell(Manager_Email_Col);
				cel_Manager_Email.setCellValue(ipVal[8]);
				
				Cell cel_Prod1=rw.createCell(Prod1_Col);
				cel_Prod1.setCellValue(ipVal[9]);
				
				Cell cel_Prod2=rw.createCell(Prod2_Col);
				cel_Prod2.setCellValue(ipVal[10]);
				
				Cell cel_Prod3=rw.createCell(Prod3_Col);
				cel_Prod3.setCellValue(ipVal[11]);
				
				Cell cel_Prod4=rw.createCell(Prod4_Col);
				cel_Prod4.setCellValue(ipVal[12]);
				
				Cell cel_Prod5=rw.createCell(Prod5_Col);
				cel_Prod5.setCellValue(ipVal[13]);
				
				Cell cel_Prod6=rw.createCell(Prod6_Col);
				cel_Prod6.setCellValue(ipVal[14]);
				
				Cell cel_Prod7=rw.createCell(Prod7_Col);
				cel_Prod7.setCellValue(ipVal[15]);
				
				Cell cel_Prod8=rw.createCell(Prod8_Col);
				cel_Prod8.setCellValue(ipVal[16]);
				
				Cell cel_Prod9=rw.createCell(Prod9_Col);
				cel_Prod9.setCellValue(ipVal[17]);
				
				Cell cel_Prod10=rw.createCell(Prod10_Col);
				cel_Prod10.setCellValue(ipVal[18]);
				
				Cell cel_Process_DT=rw.createCell(Process_DT_Col);
				cel_Process_DT.setCellValue(timeStamp);
				
				Cell cel_fileName=rw.createCell(fileName_Col);
				cel_fileName.setCellValue(p);
				
				fis.close();
				FileOutputStream fos=new FileOutputStream(fil);
				wb.write(fos);
				fos.close();
				wb.close();
				
				this.archieve(p);
				
			}
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	

	public void WeedOut(String[] ipFiles)
	{

		for(String f:ipFiles)
			{
			int dotPos=f.indexOf(".");
			String tmp=f.substring(dotPos);
			System.out.println(tmp);
			if(!tmp.equalsIgnoreCase(".xlsx"))
			{
				String ipPathAbs=ipPath+"\\"+f;
				System.out.println(ipPath);
				System.out.println(ipPathAbs);
				String errPathAbs=errPath+"\\"+f;
				System.out.println(errPathAbs);
				
				this.moveFile(ipPathAbs, errPathAbs);
			}
		}
	}
	
	public void archieve(String file)
	{
		String ipPathAbs=ipPath+"\\"+file;
		System.out.println(ipPath);
		System.out.println(ipPathAbs);
		String arrPathAbs=archievePath+"\\"+file;
		System.out.println(arrPathAbs);
		
		this.moveFile(ipPathAbs, arrPathAbs);
	}
	
	   private void moveFile(String src, String dest ) {
		   
		      Path result = null;
		      try {
		         result = Files.move(Paths.get(src), Paths.get(dest));
		      } catch (IOException e) {
		         System.out.println("Exception while moving file: " + e.getMessage());
		      }
		      if(result != null) {
		         System.out.println("File moved successfully." + dest);
		      }else{
		         System.out.println("File movement failed." + dest);
		      }
	   }
	
	public String[] getIPFiles ()
	{
	// TODO Auto-generated method stub

		String[] filNames;

      System.out.println(ipPath);
      File fil=new File(ipPath);
      filNames=fil.list();
      
      for(String p:filNames)
      {
    	  System.out.println(p);
      }
	return filNames;
      
	}
}
