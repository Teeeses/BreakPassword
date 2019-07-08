package ru.explead.breakpassword.fragments;

import android.os.Bundle;
import android.os.Handler;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import ru.explead.breakpassword.MainActivity;
import ru.explead.breakpassword.R;

/**
 * Created by develop on 13.01.2017.
 */

public class BannerFragment extends Fragment {

    private Handler handler = new Handler();

    private Runnable run = () -> ((MainActivity)requireContext()).openGameFragment();


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_banner, container, false);

        TextView companySurname = view.findViewById(R.id.companySurname);
        TextView companyName = view.findViewById(R.id.companyName);

        Animation animation = AnimationUtils.loadAnimation(getActivity(), R.anim.name_animation);
        companyName.startAnimation(animation);

        Animation animation2 = AnimationUtils.loadAnimation(getActivity(), R.anim.surname_animation);
        companySurname.startAnimation(animation2);

        return view;
    }

    @Override
    public void onStop() {
        super.onStop();
        handler.removeCallbacks(run);
    }

    @Override
    public void onResume() {
        super.onResume();
        handler.postDelayed(run, 4000);
    }
}