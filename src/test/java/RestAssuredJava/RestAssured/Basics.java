package RestAssuredJava.RestAssured;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import org.testng.Assert;

import Files.ReusableMethods;
import Files.payload;

public class Basics {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		RestAssured.baseURI = "https://rahulshettyacademy.com";
		String response = given().log().all().queryParam("key", "qaclick123").header("Content-Type", "application/json")
				.body(payload.addplace()).when().post("maps/api/place/add/json").then().log().all().assertThat()
				.statusCode(200).body("scope", equalTo("APP")).header("server", "Apache/2.4.18 (Ubuntu)").extract()
				.response().asString();
		System.out.println(response);
		JsonPath js = new JsonPath(response);
		String placeid = js.get("place_id");
		System.out.println(placeid);

		// add a place->update a place->check it new place is successfully added or not

		// Update Place
		String newaddress = "sri varicharam apartment, munnekolala";

		given().log().all().queryParam("key", "qaclick123").header("Content-Type", "application/json")
				.body("{\r\n" + "\"place_id\":\"" + placeid + "\",\r\n" + "\"address\":\"" + newaddress + "\",\r\n"
						+ "\"key\":\"qaclick123\"\r\n" + "}")
				.when().put("maps/api/place/update/json").then().assertThat().log().all().statusCode(200)
				.body("msg", equalTo("Address successfully updated"));

		// get Place

		String getPlaceResponse = given().log().all().queryParam("key", "qaclick123").queryParam("place_id", placeid)
				.when().get("maps/api/place/get/json").then().assertThat().log().all().statusCode(200).extract()
				.response().asString();
		JsonPath js1 = ReusableMethods.rawToJson(getPlaceResponse);
		// JsonPath js1=new JsonPath(getPlaceResponse); //this is the simplier way
		String actualaddress = js1.getString("address");
		System.out.println(actualaddress);
		Assert.assertEquals(actualaddress, newaddress);

	}

}
