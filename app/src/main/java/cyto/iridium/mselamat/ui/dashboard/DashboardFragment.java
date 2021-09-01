package cyto.iridium.mselamat.ui.dashboard;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import cyto.iridium.mselamat.R;
import cyto.iridium.mselamat.databinding.FragmentDashboardBinding;

public class DashboardFragment extends Fragment {

    private DashboardViewModel dashboardViewModel;
    private FragmentDashboardBinding binding;
    private Button pkp,pkpb,pkpd,pkpp;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        dashboardViewModel =
                new ViewModelProvider(this).get(DashboardViewModel.class);

        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

//        final TextView textView = binding.textDashboard;
//        dashboardViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }
//        });

        String urlPKP="https://covid-19.moh.gov.my/faqsop/sop-perintah-kawalan-pergerakan-pkp";
        String urlPKPB="https://covid-19.moh.gov.my/faqsop/sop-perintah-kawalan-pergerakan-bersyarat-pkpb-cmco";
        String urlPKPD="https://covid-19.moh.gov.my/faqsop/sop-perintah-kawalan-pergerakan-diperketatkan-pkpd-emco";
        String urlPKPP="https://covid-19.moh.gov.my/faqsop/sop-pkp-pemulihan";

        pkp=binding.btnPKP;
        pkpb=binding.btnPKPB;
        pkpd=binding.btnPKPD;
        pkpp=binding.btnPKPP;

        pkp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(urlPKP));
                startActivity(i);
            }
        });

        pkpb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(urlPKPB));
                startActivity(i);
            }
        });

        pkpd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(urlPKPD));
                startActivity(i);
            }
        });

        pkpp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(urlPKPP));
                startActivity(i);
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