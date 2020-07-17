package RestAssuredJava.RestAssured;

import java.io.IOException;
import java.util.ArrayList;

public class ExcelDriven {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		DataDrivenExtended d = new DataDrivenExtended();
		//ArrayList data = d.getdata("very good");
		ArrayList data = d.getdata("very good","test data");
		System.out.println(data.get(0));
		System.out.println(data.get(1));
		System.out.println(data.get(2));
		System.out.println(data.get(3));

	}

}
