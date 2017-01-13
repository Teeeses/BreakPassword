package ru.explead.breakpassword.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;




import ru.explead.breakpassword.R;
import ru.explead.breakpassword.Surface;

/**
 * Created by develop on 13.01.2017.
 */

public class GameFragment extends Fragment {

    private Surface viewSurface;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_game, container, false);

        viewSurface = (Surface) view.findViewById(R.id.viewSurface);
        return view;
    }


}