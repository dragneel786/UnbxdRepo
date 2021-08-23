import com.fasterxml.jackson.databind.JsonNode;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;


public interface APIService {
    @GET
    Call<JsonNode> getQuery(@Url String url);
}