package RestAssuredJava.RestAssured;

import static io.restassured.RestAssured.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;

import POJO.Api;
import POJO.GetCourse;
import POJO.WebAutomation;
import io.restassured.parsing.Parser;
import io.restassured.path.json.JsonPath;

public class oAuthPojoTest {
	public static void main(String[] args) throws InterruptedException {
		// TODO Auto-generated method stub
		String[] courseTitles = { "Selenium Webdriver Java", "Cypress", "Protractor" };

		// 1st step:

		String url = "https://rahulshettyacademy.com/getCourse.php?code=4%2F1wGpv2iUn_QOcRabyXT0475Qs5ymzSwUOecEDfmZ7_WqEMup1GVj_KCAQAM8hqOnsSElhpHJGaPQ1KBzyeYzta8&scope=email+https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fuserinfo.email+openid&authuser=0&prompt=none";
		System.out.println(url);
		String partialcode = url.split("code=")[1];
		System.out.println(partialcode);
		String code = partialcode.split("&scope=")[0];
		System.out.println(code);

		// 2nd step
		// here url encoding is used to tell rest assured not to do url encoding for
		// special characters
		String accessTokenResponse = given().urlEncodingEnabled(false).queryParams("code", code)
				.queryParams("client_id", "692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com")
				.queryParams("client_secret", "erZOWM9g3UtwNRj340YYaK_W")
				.queryParams("redirect_uri", "https://rahulshettyacademy.com/getCourse.php")
				.queryParams("grant_type", "authorization_code").when().log().all()
				.post("https://www.googleapis.com/oauth2/v4/token").asString();
		System.out.println(accessTokenResponse);
		JsonPath Js = new JsonPath(accessTokenResponse);
		String accessToken = Js.getString("access_token");
		System.out.println(accessToken);

		// 3rd step

		// bottom down approach you should follow=Step1, Short cut to get the response
		// without using then.
		GetCourse gc = given().queryParam("access_token", accessToken).expect().defaultParser(Parser.JSON).when()
				.get("https://rahulshettyacademy.com/getCourse.php").as(GetCourse.class);
		System.out.println(gc.getLinkedIn());
		System.out.println(gc.getCourses().getApi().get(1).getCourseTitle());

		List<Api> apicourses = gc.getCourses().getApi();
		for (int i = 0; i < apicourses.size(); i++) {
			if (apicourses.get(i).getCourseTitle().equalsIgnoreCase("SoapUI Webservices testing")) {
				System.out.println(apicourses.get(i).getPrice());
			}
		}
		// get the list of all the course tile to the web automation
		List<WebAutomation> w = gc.getCourses().getWebAutomation();
		ArrayList<String> a = new ArrayList<String>();
		for (int j = 0; j < w.size(); j++) {
			System.out.println(w.get(j).getCourseTitle());
		}
		// get the list and compare it is working as per the expectation or not
		for (int j = 0; j < w.size(); j++) {
			a.add(w.get(j).getCourseTitle());
		}
		List<String> expectedresults = Arrays.asList(courseTitles);
		Assert.assertEquals(a, expectedresults);

	}

}
