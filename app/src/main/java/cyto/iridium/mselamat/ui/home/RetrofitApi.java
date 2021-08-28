package cyto.iridium.mselamat.ui.home;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface RetrofitApi {
    @GET
    Call<NewsModel> getNews(@Url String url);

    @GET
    Call<CasesModel> getCases(@Url String url);

}
