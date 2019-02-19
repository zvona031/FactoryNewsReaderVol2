package hr.ferit.zvonimirpavlovic.factorynewsreader;



import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface APIInterface {
    @GET("articles")
    Call<NewsCell> getNews(@Query("apiKey") String key, @Query("sortBy") String sort,@Query("source") String source);

}
