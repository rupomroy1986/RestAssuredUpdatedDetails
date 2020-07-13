package RestAssuredJava.RestAssured;
import static io.restassured.RestAssured.*;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import io.restassured.path.json.JsonPath;
public class oAuthTest {
public static void main(String[] args) throws InterruptedException {
		// TODO Auto-generated method stub

		// 1st step:

		String url = "https://rahulshettyacademy.com/getCourse.php?code=4%2F1wGITLSeXOI2Wq4BC8FGAmqrgBtNSGBKPPiY1YNqs6CP8r9q_ipWw5K8BFyLeZFbN7JAWBdJf3dtjfTqvQSmdcA&scope=email+openid+https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fuserinfo.email&authuser=0&prompt=none";
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
		String response = given().queryParam("access_token", accessToken).when().log().all()
				.get("https://rahulshettyacademy.com/getCourse.php").asString();
		System.out.println(response);

	}

}
