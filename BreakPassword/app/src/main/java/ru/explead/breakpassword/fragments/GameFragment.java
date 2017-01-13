package ru.explead.breakpassword.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;
import android.widget.TextView;


import java.util.ArrayList;

import ru.explead.breakpassword.R;
import ru.explead.breakpassword.app.App;
import ru.explead.breakpassword.logic.Cell;
import ru.explead.breakpassword.logic.Controller;

/**
 * Created by develop on 13.01.2017.
 */

public class GameFragment extends Fragment {

    private LinearLayout cellsLayout;
    private Controller controller;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_game, container, false);

        controller = App.getController();

        cellsLayout = (LinearLayout) view.findViewById(R.id.cellsLayout);

        ViewTreeObserver vto = cellsLayout.getViewTreeObserver();
        vto.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            public boolean onPreDraw() {
                cellsLayout.getViewTreeObserver().removeOnPreDrawListener(this);
                createCells(cellsLayout.getMeasuredWidth());
                addCellsOnLayout();
                return true;
            }
        });

        return view;
    }

    public void createCells(int width) {
        ArrayList<Cell> cells = new ArrayList<>();
        int size = width/controller.getNumberCells();
        for(int i = 0; i < controller.getNumberCells(); i++) {
            LayoutInflater inflater = getActivity().getLayoutInflater();
            TextView tvCell = (TextView) inflater.inflate(R.layout.cell, null, false);
            tvCell.setLayoutParams(new LinearLayout.LayoutParams(size, size));
            final Cell cell = new Cell(tvCell);
            cells.add(cell);

            cell.getTvCell().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    cell.plusOne();
                }
            });
        }
        controller.setCells(cells);
    }

    public void addCellsOnLayout() {
        for(int i = 0; i < controller.getNumberCells(); i++) {
            cellsLayout.addView(controller.getCells().get(i).getTvCell());
        }
    }

}