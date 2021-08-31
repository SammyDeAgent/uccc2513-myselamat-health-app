package cyto.iridium.mselamat.ui.notifications;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import cyto.iridium.mselamat.R;
import cyto.iridium.mselamat.databinding.FragmentNotificationsBinding;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class NotificationsFragment extends Fragment {

    private NotificationsViewModel notificationsViewModel;
    private FragmentNotificationsBinding binding;

    private TextView text;
    private TextView text2;
    private TextView datetext;
    private TextView textZone;

    private ProgressBar loadingPB;

    private Button btnLoadAll;
    private Button btnLoadOne;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        notificationsViewModel =
                new ViewModelProvider(this).get(NotificationsViewModel.class);

        binding = FragmentNotificationsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

//        final TextView textView = binding.textNotifications;
//        notificationsViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }
//        });

        text = binding.textData;
        text2 = binding.textData2;
        textZone = binding.textZone;
        datetext = binding.datetext;
        loadingPB = binding.PBLoadingHotspot;

        btnLoadAll = binding.btnShowAll;
        btnLoadOne = binding.btnShowStates;

        textZone.setVisibility(View.GONE);
        loadingPB.setVisibility(View.GONE);

        String ytdDate = getYesterdayDateString();
        String[][] states = {
                {
                        "johor",
                        "kedah",
                        "kelantan",
                        "melaka",
                        "negerisembilan",
                        "pahang",
                        "perak",
                        "perlis",
                        "penang",
                        "sabah",
                        "sarawak",
                        "selangor",
                        "terengganu",
                        "kl",
                        "labuan",
                        "putrajaya"
                },
                {
                        "Johor",
                        "Kedah",
                        "Kelantan",
                        "Melaka",
                        "Negeri Sembilan",
                        "Pahang",
                        "Perak",
                        "Perlis",
                        "Penang",
                        "Sabah",
                        "Sarawak",
                        "Selangor",
                        "Terengganu",
                        "Kuala Lumpur",
                        "Labuan",
                        "Putrajaya"
                }
        };

        btnLoadAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textZone.setVisibility(View.GONE);
                loadingPB.setVisibility(View.VISIBLE);
                text.setText("");
                text2.setText("");
                ExecutorService service = Executors.newSingleThreadExecutor();
                for(int  i = 0; i < 16; i++){
                    service.execute(new hotspotThread(ytdDate, states[0][i], states[1][i]));
                }
                service.shutdown();
                datetext.setText("Record Date: " + ytdDate);
                loadingPB.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        loadingPB.setVisibility(View.GONE);
                    }
                },2500);
            }
        });

        btnLoadOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Select a State")
                        .setItems(states[1], new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                textZone.setVisibility(View.GONE);
                                loadingPB.setVisibility(View.VISIBLE);
                                text.setText("");
                                text2.setText("");
                                ExecutorService service = Executors.newSingleThreadExecutor();
                                service.execute(new hotspotThread(ytdDate, states[0][which], states[1][which]));
                                service.shutdown();
                                datetext.setText("Record Date: " + ytdDate);
                                loadingPB.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        loadingPB.setVisibility(View.GONE);
                                    }
                                },1500);
                                textZone.setVisibility(View.VISIBLE);
                            }
                        });
                builder.create();
                builder.show();
            }
        });


        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private Date yesterday() {
        final Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -1);
        return cal.getTime();
    }

    private String getYesterdayDateString() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return dateFormat.format(yesterday());
    }

    private class hotspotThread implements Runnable{

        String date;
        String state;
        String name;

        public hotspotThread(String date, String state, String name){
            this.date = date;
            this.state = state;
            this.name = name;
        }

        public void run(){
            String baseUrl = "https://msiacovidapi.herokuapp.com/";
            String url =
                    baseUrl +
                            "?start_date=" + date +
                            "&end_date=" + date +
                            "&state=" + state;
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    //.addConverterFactory(GsonConverterFactory.create())
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .build();
            RetrofitApi retrofitApi = retrofit.create(RetrofitApi.class);

            Call<String> call = retrofitApi.getData(url);
            call.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {

                    JSONObject jsonData = null;
                    try {
                        jsonData = new JSONObject(response.body());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    try {
                        jsonData = jsonData.getJSONObject(date);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    try {
                        text.append(name + "\n");
                        text2.append(jsonData.getString("New cases") + "\n");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    try {
                        textZone.setText("Overall Zone: " + zoneCheck(Integer.valueOf(jsonData.getString("New cases"))));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {

                }
            });

        }
    }

    private String zoneCheck(Integer cases){
        if(cases > 560){
            textZone.setBackgroundColor(Color.RED);
            return "RED - HIGH ALERT";
        }

        if(cases > 280){
            textZone.setBackgroundColor(Color.parseColor("#FFA500"));
            return "ORANGE - WARNING ";
        }

        if(cases > 0){
            textZone.setBackgroundColor(Color.YELLOW);
            return "YELLOW - CAUTION";
        }

        if(cases == 0){
            textZone.setBackgroundColor(Color.parseColor("#00FF00"));
            return "GREEN - SAFE";
        }

        return null;

    }
}

