package rest;

import entities.Movie;
import utils.EMF_Creator;
import io.restassured.RestAssured;
import static io.restassured.RestAssured.given;
import io.restassured.parsing.Parser;
import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.stream.Stream;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.core.UriBuilder;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.grizzly.http.util.HttpStatus;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.CoreMatchers.is;
import org.hamcrest.Matcher;
import static org.hamcrest.Matchers.arrayContaining;
import static org.hamcrest.Matchers.arrayContainingInAnyOrder;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

//Uncomment the line below, to temporarily disable this test
//@Disabled
public class MovieResourceTest {

    private static final int SERVER_PORT = 7777;
    private static final String SERVER_URL = "http://localhost/api";
    private static Movie m1, m2, m3;

    static final URI BASE_URI = UriBuilder.fromUri(SERVER_URL).port(SERVER_PORT).build();
    private static HttpServer httpServer;
    private static EntityManagerFactory emf;

    static HttpServer startServer() {
        ResourceConfig rc = ResourceConfig.forApplication(new ApplicationConfig());
        return GrizzlyHttpServerFactory.createHttpServer(BASE_URI, rc);
    }

    @BeforeAll
    public static void setUpClass() {
        //This method must be called before you request the EntityManagerFactory
        EMF_Creator.startREST_TestWithDB();
        emf = EMF_Creator.createEntityManagerFactoryForTest();

        httpServer = startServer();
        //Setup RestAssured
        RestAssured.baseURI = SERVER_URL;
        RestAssured.port = SERVER_PORT;
        RestAssured.defaultParser = Parser.JSON;
    }

    @AfterAll
    public static void closeTestServer() {
        //System.in.read();

        //Don't forget this, if you called its counterpart in @BeforeAll
        EMF_Creator.endREST_TestWithDB();
        httpServer.shutdownNow();
    }

    // Setup the DataBase (used by the test-server and this test) in a known state BEFORE EACH TEST
    //TODO -- Make sure to change the EntityClass used below to use YOUR OWN (renamed) Entity class
    @BeforeEach
    public void setUp() {
        EntityManager em = emf.createEntityManager();
         m1 = new Movie("aa", 2010, new String[] {"A","B"} );
         m2 = new Movie("bb and aa", 2011, new String[] {"C","D"} );
         m3 = new Movie("bb", 2011, new String[] {"C","D"} );
        try {
            em.getTransaction().begin();
            em.createNamedQuery("Movie.deleteAllRows").executeUpdate();
            em.persist(m1);
            em.getTransaction().commit();
            em.getTransaction().begin();
            em.persist(m2);
            em.getTransaction().commit();
            em.getTransaction().begin();
            em.persist(m3);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    @Test
    public void testServerIsUp() {
        System.out.println("Testing is server UP");
        given().when().get("/movie/isalive").then()
                .assertThat().statusCode(200)
                .body("msg",equalTo("Movie API is up"));
    }
   
   
    @Test
    public void testCount() throws Exception {
        given()
                .contentType("application/json")
                .get("/movie/count").then()
                .assertThat()
                .statusCode(HttpStatus.OK_200.getStatusCode())
                .body("count", equalTo(3));
    }
    
    @Test
    public void doThisWhenYouHaveProblems(){
        given().log().all().when().get("/movie/all").then().log().body();
    }
    
    @Test
    public void testGetAll(){
        System.out.println("Test get all");
        given()
            .contentType("application/json")
            .get("/movie/all").then()
            .assertThat()
            .statusCode(HttpStatus.OK_200.getStatusCode())
            .body("title", hasItems("aa", "bb", "bb and aa"));
    }
    
    @Test
    public void testById() {
        System.out.println("Test by Id");
        //given().log().all().when().get("/movie/byid/" + m1.getId()).then().log().body();
        
        given()
                .contentType("application/json")
                .get("/movie/byid/" + m1.getId()).then()
                .assertThat()
                .statusCode(HttpStatus.OK_200.getStatusCode())
                .body("title", equalTo(m1.getTitle()));
    }
    
    @Test
    public void testByTitle() {
        System.out.println("Testing by title");
        //given().log().all().when().get("movie/bytitle/" + m1.getTitle()).then().log().body();
        given()
                .contentType("application/json")
                .get("movie/bytitle/" + m1.getTitle()).then()
                .assertThat()
                .statusCode(HttpStatus.OK_200.getStatusCode())
                .body("year", hasItems(2010, 2011));
    }
    
    @Test
    public void testGetAllWithActors() {
        String[][] all = new String[][] {{"A","B"},{"C","D"},{"C","D"}};
        String[] allActors = new String[3];
        for(int i=0;i<all.length;i++){
            String str = "";
            for(int k=0;k<all[i].length;k++){
                System.out.println(all[i][k]);
                str = str.concat(all[i][k]);
                //break;
            }
                allActors[i] = str;
        }
        for (String a : allActors) {
            System.out.println(a);
        }
        given().log().all().when().get("movie/all").then().log().body();
            given()
                    .contentType("application/json")
                    .get("movie/all").then()
                    .assertThat()
                    .statusCode(HttpStatus.OK_200.getStatusCode())
            .body("actors[0]", hasItems(all[0]));                
    }
}