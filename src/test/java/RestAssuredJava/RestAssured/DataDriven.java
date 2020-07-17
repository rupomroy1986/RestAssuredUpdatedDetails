package RestAssuredJava.RestAssured;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class DataDriven {

	public static void main(String[] args) throws IOException {

		FileInputStream fis = new FileInputStream(
				"C:\\REST ASSURED API TESTING FINAL REVISION\\Excel\\PractiseExcel.xlsx");
		;
		XSSFWorkbook workbook = new XSSFWorkbook(fis);
		int sheets = workbook.getNumberOfSheets();
		// System.out.println(sheets);
		// now to get the access of the sheet
		for (int i = 0; i < sheets; i++) {
			if (workbook.getSheetName(i).equalsIgnoreCase("test data")) {
				XSSFSheet sheet = workbook.getSheetAt(i);

				// Identify Testcases coloum by scanning the entire 1st row
				Iterator<Row> rows = sheet.iterator();

				Row firstrow = rows.next();
				Iterator<Cell> ce = firstrow.cellIterator();
				int k = 0;
				int coloumn = 0;
				while (ce.hasNext()) {
					Cell value = ce.next();
					if (value.getStringCellValue().equalsIgnoreCase("Data3")) {
						coloumn = k;
					}
					k++;

				}
				System.out.println(coloumn);
				/*
				 * testcase coloum to identify purcjhase testcase row //after you grab purchase
				 * testcase row = pull all the data of that row and feed into test
				 */

				while (rows.hasNext()) {
					Row r = rows.next();
					if (r.getCell(coloumn).getStringCellValue().equalsIgnoreCase("silchar")) {
						Iterator<Cell> cv = r.cellIterator();
						while (cv.hasNext()) {
							System.out.println(cv.next().getRichStringCellValue());
						}

					}
				}
			}

		}

	}

}
