package RestAssuredJava.RestAssured;

import io.restassured.RestAssured;
import io.restassured.filter.session.SessionFilter;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;

import java.io.File;

import org.testng.Assert;


public class JiraTest {
	
public static void main(String[] args) {
	RestAssured.baseURI = "http://localhost:8080";

	// login scenario
	SessionFilter session = new SessionFilter();

	String response = given().header("Content-Type", "application/json")
			.body("{ \r\n" + "\"username\": \"rupomroy\", \r\n" + "\"password\": \"rupom123\" \r\n" + "	\r\n" + "}")
			.log().all().filter(session).when().post("/rest/auth/1/session").then().log().all().extract().response().asString();
	
			
	// adding a comment in the JIRA
	String expectedMessage ="this is my revision on JIRA?";
String addCommentResponse=given().pathParam("id", "10002").log().all().header("Content-Type", "application/json").body("{\r\n"
			+ "    \"body\": \""+expectedMessage+"\",\r\n"
			+ "    \"visibility\": {\r\n" + "        \"type\": \"role\",\r\n"
			+ "        \"value\": \"Administrators\"\r\n" + "    }\r\n" + "}").filter(session).when()
			.post("/rest/api/2/issue/{id}/comment").then().log().all().assertThat().statusCode(201).extract().response().asString();
JsonPath js=new JsonPath(addCommentResponse);
String commentId    = js.getString("id");
System.out.println(commentId);
		
		//adding attachment to the JIRA
given().header("X-Atlassian-Token", "no-check").filter

(session).pathParam("key", "10002")

		.header("Content-Type", "multipart/form-data")

		.multiPart("file", new File("Jira.txt")).when().

		post("rest/api/2/issue/{key}/attachments").then().log().all

		().assertThat().statusCode(200);

//Get Issue
//here we are using fields to take which response we want from the total details

		String issueDetails=given().filter(session).pathParam("key", "10002").queryParam("fields", "comment").queryParam("fields", "comment")

		.log().all().when().get("/rest/api/2/issue/{key}").then()

		.log().all().extract().response().asString();

		System.out.println(issueDetails);
		
		//here we are getting the details of the comment and matching it with that.
		
		JsonPath js1=new JsonPath(issueDetails);
		int commentscount       =  js1.getInt("fields.comment.comments.size()");
		for(int i=0;i<commentscount;i++)
		{
			String commentissue  = js1.get("fields.comment.comments["+i+"].id").toString();
			//System.out.println(commentissue);
			if(commentissue.equalsIgnoreCase(commentId))
			{
			String message	=js1.get("fields.comment.comments["+i+"].body").toString();
			System.out.println(message);
			Assert.assertEquals(message, expectedMessage);
		
		
	}
		}}
}
