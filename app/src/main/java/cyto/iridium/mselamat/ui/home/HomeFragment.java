package cyto.iridium.mselamat.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.ParseException;
import java.util.ArrayList;

import cyto.iridium.mselamat.R;
import cyto.iridium.mselamat.databinding.FragmentHomeBinding;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HomeFragment extends Fragment {

    private RecyclerView newsRV;
    private ProgressBar loadingPB;
    private ArrayList<Article> articleArrayList;
    private NewsAdapter newsAdapter;

    private TextView totalcasesTV, newcasesTV;

    private HomeViewModel homeViewModel;
    private FragmentHomeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

//        final TextView textView = binding.textHome;
//        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }
//        });

        newsRV = binding.RVNews;
        loadingPB = binding.PBLoading;
        articleArrayList = new ArrayList<>();
        newsAdapter = new NewsAdapter(articleArrayList, this);

        newsRV.setLayoutManager(new LinearLayoutManager(getActivity()));
        newsRV.setAdapter(newsAdapter);

        loadingPB.setVisibility(View.VISIBLE);

        String url = "https://newsapi.org/v2/top-headlines?country=my&category=health&apiKey=cfad285923fd4aa1a451924f6fb5184f";
        String baseURL = "https://newsapi.org/";
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseURL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RetrofitApi retrofitApi = retrofit.create(RetrofitApi.class);

        Call<NewsModel> call;
        call = retrofitApi.getNews(url);
        call.enqueue(new Callback<NewsModel>() {
            @Override
            public void onResponse(Call<NewsModel> call, Response<NewsModel> response) {
                NewsModel newsModel = response.body();
                loadingPB.setVisibility(View.GONE);
                ArrayList<Article> articles = newsModel.getArticles();
                for (int i = 0; i < articles.size(); i++) {
                    try {
                        articleArrayList.add(new Article(articles.get(i).getTitle(), articles.get(i).getDescription(), articles.get(i).getUrl(),
                                articles.get(i).getUrlToImage(), articles.get(i).getPublishedAt(), articles.get(i).getContent()));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
                newsAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<NewsModel> call, Throwable t) {
                //Toast.makeText(MainActivity.this, "Fail to get news", Toast.LENGTH_SHORT).show();
            }
        });

        newsAdapter.notifyDataSetChanged();

        totalcasesTV = binding.totalcases;
        newcasesTV = binding.newcases;

        String casesURL = "https://corona.lmao.ninja/v2/countries/Malaysia?yesterday&strict&query%20";
        String baseURL2 = "https://corona.lmao.ninja/";
        Retrofit retrofit2 = new Retrofit.Builder()
                .baseUrl(baseURL2)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RetrofitApi retrofitApi2 = retrofit.create(RetrofitApi.class);
        Call<CasesModel> call2;
        call2 = retrofitApi.getCases(casesURL);
        call2.enqueue(new Callback<CasesModel>() {
            @Override
            public void onResponse(Call<CasesModel> call, Response<CasesModel> response) {
                CasesModel casesModel=response.body();
                loadingPB.setVisibility(View.GONE);
                //System.out.println(casesModel.getCases()+" "+casesModel.getTodayCases());

                totalcasesTV.setText("Total cases: "+casesModel.getCases());
                newcasesTV.setText("New cases: "+casesModel.getTodayCases());

            }

            @Override
            public void onFailure(Call<CasesModel> call, Throwable t) {
                //Toast.makeText(MainActivity.this, "Fail to get stats", Toast.LENGTH_SHORT).show();
            }
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}