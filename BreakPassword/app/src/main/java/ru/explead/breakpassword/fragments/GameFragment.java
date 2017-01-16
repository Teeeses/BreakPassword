package ru.explead.breakpassword.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;


import java.util.ArrayList;

import ru.explead.breakpassword.R;
import ru.explead.breakpassword.adapters.DataAdapter;
import ru.explead.breakpassword.app.App;
import ru.explead.breakpassword.beans.Cell;
import ru.explead.breakpassword.beans.Data;
import ru.explead.breakpassword.logic.Controller;


/**
 * Created by develop on 13.01.2017.
 */

public class GameFragment extends Fragment {


    private Controller controller;

    /**
     * Layout для клеток для ввода цифр
     */
    private LinearLayout cellsLayout;
    private Button btnHack;

    private ListView listView;
    private DataAdapter adapter;
    private ArrayList<Data> data = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_game, container, false);

        controller = App.getController();

        btnHack = (Button) view.findViewById(R.id.btnHack);
        cellsLayout = (LinearLayout) view.findViewById(R.id.cellsLayout);
        listView = (ListView) view.findViewById(R.id.listView);
        adapter = new DataAdapter(controller);
        listView.setAdapter(adapter);

        ViewTreeObserver vto = cellsLayout.getViewTreeObserver();
        vto.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            public boolean onPreDraw() {
                cellsLayout.getViewTreeObserver().removeOnPreDrawListener(this);
                createCells(cellsLayout.getMeasuredWidth());
                addCellsOnLayout();
                return true;
            }
        });

        btnHack.setOnClickListener(btnHackClick);

        return view;
    }

    /**
     * Обработчик нажатия на кнопку попытки взлома
     */
    View.OnClickListener btnHackClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Data data = controller.toAttempt();
            System.out.println(Integer.toString(data.getOnPlace()) + "  " + Integer.toString(data.getMatches()));

            adapter.notifyDataSetChanged();
        }
    };

    /**
     * Создаем клетки
     * @param width - длина контейнера для клеток
     */
    public void createCells(int width) {
        float padding = getPadding();

        ArrayList<Cell> cells = new ArrayList<>();
        int size = (int)(width - 2*padding*controller.getNumberCells() + 1)/controller.getNumberCells();
        for(int i = 0; i < controller.getNumberCells(); i++) {
            LayoutInflater inflater = getActivity().getLayoutInflater();
            View view = inflater.inflate(R.layout.cell, null, false);
            RelativeLayout layout = (RelativeLayout) view.findViewById(R.id.root);
            TextView tvCell = (TextView) view.findViewById(R.id.tvCell);
            tvCell.setLayoutParams(new RelativeLayout.LayoutParams(size, size));
            final Cell cell = new Cell(layout, tvCell);
            cells.add(cell);

            cell.getTvCell().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    cell.plusOne();
                }
            });

            cell.getTvCell().setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    cell.setValue(Cell.NO_ACTIVE);
                    return false;
                }
            });
        }
        controller.setCells(cells);
    }

    /**
     * Добавляем клетки на layout
     */
    public void addCellsOnLayout() {
        for(int i = 0; i < controller.getNumberCells(); i++) {
            cellsLayout.addView(controller.getCells().get(i).getLayout());
        }
    }

    /**
     * Получаем отступы у layout клетки, для корректного отображения на экране
     * @return - значение отступа
     */
    public float getPadding() {
        float size = getActivity().getResources().getDimension(R.dimen.radius);
        return size;
    }

}