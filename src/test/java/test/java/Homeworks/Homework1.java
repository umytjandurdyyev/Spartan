package test.java.Homeworks;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import test.java.utilities.ConfigurationReader;
import test.java.utilities.ExcelUtil;

import java.math.BigDecimal;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static org.testng.Assert.assertEquals;

public class Homework1 {

    //Optional homeworks
    //Homework-1
    //1-Create csv file from mackaroo website, which includes name,gender,phone
    //2-Download csv file
    //3-Using testng data provider and apache poi create data driven posting from spartan

    /*
    POST Body Response
    {
    "success": "A Spartan is Born!",
    "data": {
        "id": 110,
        "name": "Donald",
        "gender": "Male",
        "phone": 1234567891
    }

    POST Body --> raw --> JSON

    {
        "gender":"Male",
        "name": "Donald",
        "phone": 1234567891
    }


}
     */

    @BeforeClass
    public void beforeClass(){
        baseURI= ConfigurationReader.get("spartan_api_url");
    }

    @DataProvider
    public Object[][] spartanTest(){
        //src/test/java/test/resources/MOCK_DATA (1).xlsx
        //src/test/java/test/resources/MOCK_DATA.xlsx

        ExcelUtil spartan = new ExcelUtil("src/test/java/test/resources/MOCK_DATA (1).xlsx","spartan");

        String [][] dataArray = spartan.getDataArrayWithoutFirstRow();

        return dataArray;
    }

    @Test(dataProvider = "spartanTest")
    public void test1(String name, String gender, String phone){

        BigDecimal d = new BigDecimal(phone);
        long phoneLong = Long.parseLong(d.toPlainString());

        SpartanPost spartanPost = new SpartanPost(name, gender, phoneLong);

        Response response = given().log().all()
                    .accept(ContentType.JSON)
                    .and()
                    .contentType(ContentType.JSON)
                    .and()
                    .body(spartanPost)
                    .when()
                    .post("/api/spartans");

        SpartanResponse spartanResponse = response.body().as(SpartanResponse.class);
        String actualName = spartanResponse.getData().getName();
        String expectedName = spartanPost.getName();
        assertEquals(actualName, expectedName);



    }


}
