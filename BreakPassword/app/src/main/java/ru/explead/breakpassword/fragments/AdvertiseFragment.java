package ru.explead.breakpassword.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import ru.explead.breakpassword.MainActivity;
import ru.explead.breakpassword.R;

/**
 * Created by develop on 16.05.2017.
 */

public class AdvertiseFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_advertise, container, false);

        Button no = (Button) view.findViewById(R.id.no);
        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity)getActivity()).sendAction("Install no");
                ((MainActivity)getContext()).onBackPressed();
            }
        });

        Button yes = (Button) view.findViewById(R.id.yes);
        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity)getActivity()).sendAction("Install yes");
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=guesscolor.explead.ru.guesscolor"));
                startActivity(browserIntent);
            }
        });
        return view;
    }

}
