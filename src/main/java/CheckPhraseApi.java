import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;
import ro.pippo.core.Application;
import ro.pippo.jackson.JacksonJsonEngine;
import java.io.IOException;


public class CheckPhraseApi extends Application{

    private static ObjectMapper objectMapper = new ObjectMapper();
    LogClass logger = new LogClass();

    @Override
    protected void onInit(){
        registerContentTypeEngine(JacksonJsonEngine.class);

        GET("/", routeContext -> {
            routeContext.send("It's a default URL");
        });

        POST("/checkPhrase", routeContext -> {
            String requestBody = routeContext.getRequest().getBody();
            System.out.println(requestBody);

            JsonNode responseInJson = parse(requestBody);
            JsonNode responseFromConceptNet = makeApiRequest(String.valueOf(responseInJson.get("phrase")));

            routeContext.json().send(responseFromConceptNet);
        });
    }

    private JsonNode parse(String body) {
        JsonNode responseInJson = null;
        try {
            responseInJson = objectMapper.readTree(body);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            logger.logMessage("There is an Exception on line 43 of CheckPhraseApi.java file");
        }

        return responseInJson;
    }

    private JsonNode makeApiRequest(String word){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://api.conceptnet.io/c/en/")
                .addConverterFactory(JacksonConverterFactory.create())
                .build();

        APIService apiCallToConceptNet = retrofit.create(APIService.class);

        String[] wordsUrl = word.split(" ");
        word = String.join("_", wordsUrl).replaceAll("^\"|\"$", "");
        final Call<JsonNode> call = apiCallToConceptNet.getQuery("http://api.conceptnet.io/c/en/" + word);
        JsonNode response = null;
        try {
             response = call.execute().body();
             throw new IOException();
        } catch (IOException e) {
//            e.printStackTrace();
            logger.logMessage("There is an Exception on line 65 of CheckPhraseApi.java file");
        }
        JsonNode responseToClient = parse("{ \"isPhrase\": " + !response.get("edges").isEmpty() + "}");
        return responseToClient;
    }
}
