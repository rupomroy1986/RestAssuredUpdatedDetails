package RestAssuredJava.RestAssured;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import org.testng.annotations.Test;

import Files.ReusableMethods;
import Files.payload;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import static io.restassured.RestAssured.*;


public class ExcelDrivenExtended {

	
	@Test
	public void addBook() throws IOException {
		DataDrivenExtended d = new DataDrivenExtended();
		ArrayList data = d.getdata("AddBook", "REST ASSURED");
		
		HashMap<String,Object> map=new HashMap<String,Object>();
		map.put("name", data.get(1));
		map.put("isbn", data.get(2));
		map.put("aisle", data.get(3));
		map.put("author", data.get(4));
		
		
         //her we are putting the value directly in the body by passing the map reference
		/*RestAssured.baseURI = "http://216.10.245.166";
		Response response = given().log().all().header("Content-Type", "application/json").body(map).when()
				.post("/Library/Addbook.php").then().log().all().assertThat().statusCode(200).extract().response();
		System.out.println(response);
		JsonPath js = ReusableMethods.rawToJson(response);
		String id = js.get("ID");
		System.out.println(id);*/
		
		//another way to add the book and delete the book
		
		RestAssured.baseURI = "http://216.10.245.166";
		Response response = given().log().all().header("Content-Type", "application/json").body(payload.Body(data.get(1), data.get(2), data.get(3), data.get(4))).when()
				.post("/Library/Addbook.php").then().log().all().assertThat().statusCode(200).extract().response();
		System.out.println(response);
		JsonPath js = ReusableMethods.rawToJson(response);
		String id = js.get("ID");
		System.out.println(id);
		

	}
	
	//deletebook
	@Test
	public void deleteBook() throws IOException
	{
		DataDrivenExtended d = new DataDrivenExtended();
		ArrayList<String> data = d.getdata("AddBook", "REST ASSURED");
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("name", data.get(1));
		map.put("isbn", data.get(2));
		map.put("aisle", data.get(3));
		map.put("author", data.get(4));

		         
		RestAssured.baseURI = "http://216.10.245.166";
		Response response = given().log().all().header("Content-Type", "application/json").body(payload.DeleteBody(data.get(2), data.get(3))).when()
				.post("/Library/DeleteBook.php").then().log().all().assertThat().statusCode(200).extract().response();
		System.out.println(response);
		
}
	

}
