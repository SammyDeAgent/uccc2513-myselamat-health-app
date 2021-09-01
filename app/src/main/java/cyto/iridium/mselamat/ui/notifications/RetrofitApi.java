package cyto.iridium.mselamat.ui.notifications;

import org.json.JSONObject;

import cyto.iridium.mselamat.ui.home.CasesModel;
import cyto.iridium.mselamat.ui.home.NewsModel;
import kotlin.text.UStringsKt;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface RetrofitApi {
    @GET
    Call<String> getData(@Url String url);
}
