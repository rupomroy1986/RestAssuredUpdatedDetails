package RestAssuredJava.RestAssured;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.sl.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class ExcelWrite {

	public static void main(String[] args) throws EncryptedDocumentException, InvalidFormatException, IOException {
		// TODO Auto-generated method stub

		File f = new File("C:\\REST ASSURED API TESTING FINAL REVISION\\Excel\\PractiseExcel.xlsx");
		FileInputStream fis = new FileInputStream(f);
		Workbook wb = WorkbookFactory.create(fis);
		org.apache.poi.ss.usermodel.Sheet st = wb.getSheet("test data");
		Row r = st.getRow(1);
		Cell c = r.getCell(2);
		String data = c.toString();
		System.out.println(data);
		System.out.println("to write in the excel sheet");
		org.apache.poi.ss.usermodel.Sheet st1 = wb.getSheet("test data");
		Row r1 = st1.createRow(6);
		Cell c1 = r1.createCell(3);
		Cell c2 = r1.createCell(2);
		Cell c3 = r1.createCell(1);
		Cell c4 = r1.createCell(0);
		c1.setCellValue("very good");
		c2.setCellValue("is");
		c3.setCellValue("Learning");
		c4.setCellValue("java");

		FileOutputStream fos = new FileOutputStream(f);
		wb.write(fos);

	}

}
