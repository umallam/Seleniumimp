
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelFile {

	private XSSFWorkbook wb;
	private XSSFSheet sheet;
	private XSSFRow row;
	private XSSFCell cell;
	private String filename;
	
	public ExcelFile(String filename){
		try{					 			//System.out.println("TRY to open existing file");
			this.filename=filename;
			InputStream Exlfile = new FileInputStream(this.filename);
			 wb = new XSSFWorkbook(Exlfile);
			 sheet = wb.getSheetAt(0);
		}
		catch (Exception e){			//System.out.println("catch to open new file");
			System.out.println("File not found new file opened");
			String sheetName = "Sheet1";//name of sheet
			wb = new XSSFWorkbook();
			sheet = wb.createSheet(sheetName);
		}
	}
	
	public Integer sheetlength (String sheetname){
		sheet= wb.getSheet(sheetname);
		int rowcount=sheet.getPhysicalNumberOfRows();
		return rowcount;
	}
	
		
	public String[][] readrowdata(String sheetname,int colnumber,String colvalue){
	
		int sheetwidth =sheet.getRow(0).getLastCellNum();
		int sheetlength=sheet.getLastRowNum();
		sheet =wb.getSheet(sheetname);
		ArrayList<Integer> mainrows= new ArrayList<Integer> ();

		for (int row=1;row<sheetlength;row++){
			//if (colvalue.equalsIgnoreCase(readcelldata(sheetname,row,colnumber))){
			//	mainrows.add(row);
			//}

			if ((readcelldata(sheetname,row,colnumber).toLowerCase()).matches((colvalue.toLowerCase())+".*")){
				mainrows.add(row);
			}
		}
		
		String[] [] sheetrowdata= new String[mainrows.size()][sheet.getRow(0).getPhysicalNumberOfCells()];
		
		for (int i=0;i<mainrows.size();i++){
			for (int col=0;col<sheetwidth;col++){
			 sheetrowdata[i][col]=(readcelldata(sheetname,mainrows.get(i),col));		 
			}
		}
		return sheetrowdata;
	}
	
	public String[][] readrowdata(String sheetname,int colnumber,int colvalue){
		
		String strcolvalue=String.valueOf(colvalue);
		return readrowdata(sheetname,colnumber,strcolvalue);
	}
	
	public String[][] readrowdata(String sheetname,String colname,String colvalue){
		int colnumber=0;
		ArrayList<String> sheetheaddata =readsheethead(sheetname);
		for (int i=0;i<sheetheaddata.size();i++){
			if (colname.equalsIgnoreCase(sheetheaddata.get(i))){
				colnumber=i;
			}
		}
		return readrowdata(sheetname,colnumber,colvalue);
	}
		
	public String readcelldata(String sheetname,int rownum,int colnum){
		
		sheet=wb.getSheet(sheetname);
		row =sheet.getRow(rownum);
		cell= row.getCell(colnum);
			
		String cellvalue=null;
		if (cell.getCellType() == XSSFCell.CELL_TYPE_STRING) 	{
			cellvalue=cell.getStringCellValue();
		}
		else if(cell.getCellType() == XSSFCell.CELL_TYPE_NUMERIC) {
			cellvalue=String.valueOf(cell.getNumericCellValue());    
		}
		else 	{
			//U Can Handel Boolean, Formula, Errors
		}
		return cellvalue;
	}
		
	public ArrayList <String> readsheethead(String sheetname){	
		sheet=wb.getSheet(sheetname);
		int cellcount= sheet.getRow(0).getPhysicalNumberOfCells();
		ArrayList<String> cellvalues= new ArrayList <String>();
		
		for (int i=0; i<cellcount;i++){
			cellvalues.add(readcelldata(sheetname,0,i));
		}
		return cellvalues;
	}
	
public void writecelldata(String sheetname,int rownum,int colnum){
		
		sheet=wb.getSheet(sheetname);
		row=sheet.getRow(rownum);
		cell= row.getCell(colnum);
		
		//XSSFSheet sheet = wb.createSheet(sheetname) ;

		cell.setCellValue(50000);
		
		try{
		FileOutputStream fileOut = new FileOutputStream(filename);
		//write this workbook to an Outputstream.
				wb.write(fileOut);
				fileOut.flush();
				fileOut.close();
		}
		catch (Exception e){
			System.out.println("File not Present");
		}
	}

	public String[][] readdata(String sheetname){

		sheet = wb.getSheet(sheetname);
		int rowcount=(sheet.getPhysicalNumberOfRows()-1);
		int colcount = sheet.getRow(0).getLastCellNum();
			
		String[] [] filedata= new String[rowcount][colcount];
			
		for (int r=0;r<(rowcount);r++){
			for (int c=0;c<colcount;c++){
				filedata[r][c]=readcelldata(sheetname,(r+1),c);
			}
		}
		return filedata;
	}

	/*
	public void print(String sheetname){
		
		String[] [] sheetdata= readdata(sheetname);
		for (int r=0;r<(sheetdata.length);r++){
			for (int c=0;c<sheetdata[0].length;c++){
			 System.out.print(readcelldata(sheetname,(r+1),c)+ " ");
			}
			 System.out.println();
		}
	}
	*/
	public void print(String[][] data){
		
		for (int r=0;r<(data.length);r++){
			for (int c=0;c<data[r].length;c++){
			 System.out.print((data[r][c])+ " ");
			}
			 System.out.println();
		}
	}
	
	/*
	private String[][] readdata(String sheetname,int no_of_cols_toread){

		sheet = wb.getSheet(sheetname);
		int rowcount=(sheet.getPhysicalNumberOfRows()-1);
		int colcount = sheet.getRow(0).getPhysicalNumberOfCells();
		if (no_of_cols_toread<colcount){
			colcount=no_of_cols_toread;
		}
			
		String[] [] filedata= new String[rowcount][colcount];
			
		for (int r=0;r<(rowcount);r++){
			for (int c=0;c<colcount;c++){
				filedata[r][c]=readcelldata(sheetname,(r+1),c);
			}
		}
		return filedata;
	}
	*/
	
	public static void main (String[] args){
		

		//Excelfile xlfile = new Excelfile(System.getProperty("user.dir")+"//src//data//data.xlsx");
		
		//ArrayList <String> shthead = xlfile.readsheethead("sheet1");
		//for (int i=0; i<shthead.size();i++)
		//System.out.println(shthead.get(i));
		//String abc=xlfile.readcelldata("Sheet1",1,1);
		//System.out.println(abc);
		//xlfile.writecelldata("Sheet1",2,2);
		//xlfile.preparedata();
		/*
		String[][] rowdata =xlfile.readrowdata("Sheet1",0,"Date Listed");
		//String[][] rowdata =xlfile.readdata("sheet2");
		for (int i=0; i< rowdata.length; i++ ){
			for (int j=0;j<rowdata[0].length;j++){
				System.out.print(rowdata[i][j]+" ");
			}
			System.out.println();
		}
		*/
		
		ExcelFile xlfile = new ExcelFile("F://Testing_data//test4.xlsx");
		xlfile.print(xlfile.readdata("Sheet1"));
	}
	
}
