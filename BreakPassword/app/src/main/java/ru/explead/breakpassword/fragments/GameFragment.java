package ru.explead.breakpassword.fragments;

import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;


import java.util.ArrayList;

import ru.explead.breakpassword.MainActivity;
import ru.explead.breakpassword.R;
import ru.explead.breakpassword.adapters.DataAdapter;
import ru.explead.breakpassword.app.App;
import ru.explead.breakpassword.app.UtilsDesign;
import ru.explead.breakpassword.beans.Cell;
import ru.explead.breakpassword.beans.KeyButton;
import ru.explead.breakpassword.dialog.DialogMenu;
import ru.explead.breakpassword.logic.Controller;
import ru.explead.breakpassword.logic.UtilsWinText;


/**
 * Created by develop on 13.01.2017.
 */

public class GameFragment extends Fragment {


    private Controller controller;
    private UtilsDesign utilsDesign;

    /**
     * Layout для клеток для ввода цифр
     */
    private LinearLayout cellsLayout;
    /**
     * Главный слой клавиатуры
     */
    private LinearLayout rootKeyboard;
    private RelativeLayout rootGameFragment;
    private Button btnHack;
    private TextView tvAttempts;
    private TextView tvPassword;

    private ListView listView;
    private DataAdapter adapter;

    /**
     * Указывает - открыта кастомная клавиатура или нет
     */
    public static boolean isShowKeyboard = false;

    /**
     * Ширина контейнера под клетки
     */
    private int width;

    /**
     * Массив кнопок из кстомной клавиатуры
     */
    private KeyButton[] keyButtons;

    private String lastAttempt = "";
    private SoundPool soundPool;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_game, container, false);

        controller = App.getController();
        utilsDesign = new UtilsDesign();

        soundPool = new SoundPool(4, AudioManager.STREAM_MUSIC, 100);
        soundPool.load(getActivity(), R.raw.one, 1);


        tvPassword = (TextView) view.findViewById(R.id.tvPassword);
        rootKeyboard = (LinearLayout) view.findViewById(R.id.rootKeyboard);
        tvAttempts = (TextView) view.findViewById(R.id.tvAttempts);
        btnHack = (Button) view.findViewById(R.id.btnHack);
        ImageView btnMenu = (ImageView) view.findViewById(R.id.btnMenu);
        cellsLayout = (LinearLayout) view.findViewById(R.id.cellsLayout);
        listView = (ListView) view.findViewById(R.id.listView);
        adapter = new DataAdapter(controller);
        listView.setAdapter(adapter);

        rootGameFragment = (RelativeLayout) view.findViewById(R.id.rootGameFragment);
        rootGameFragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                closeKeyboard();
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                closeKeyboard();
            }
        });

        btnMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogMenu dialogMenu = new DialogMenu(getActivity(), controller);
                dialogMenu.show();
                closeKeyboard();
            }
        });

        ViewTreeObserver vto = cellsLayout.getViewTreeObserver();
        vto.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            public boolean onPreDraw() {
                cellsLayout.getViewTreeObserver().removeOnPreDrawListener(this);
                width = cellsLayout.getMeasuredWidth();
                createCells();
                addCellsOnLayout();
                return true;
            }
        });

        btnHack.setOnClickListener(btnHackClick);
        tvAttempts.setText(String.format(getActivity().getResources().getString(R.string.committedAttempts), controller.getNumberAttempts()));

        createKeyboard(view);

        return view;
    }

    /**
     * Обработчик нажатия на кнопку попытки взлома
     */
    View.OnClickListener btnHackClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if(controller.getStatus() == controller.FINISH) {
                onRestart();
                return;
            }
            if(controller.toStringPasswordProbable().equals(lastAttempt)) {
                showSnackBar(view, MainActivity.getRes().getString(R.string.changeValues));
                return;
            } else if(controller.isEmptyCells()) {
                controller.toAttempt();
                tvAttempts.setText(String.format(getActivity().getResources().getString(R.string.committedAttempts), controller.getNumberAttempts()));
                adapter.notifyDataSetChanged();
                lastAttempt = controller.toStringPasswordProbable();
            } else {
                showSnackBar(view, MainActivity.getRes().getString(R.string.cellIsEmpty));
                return;
            }

            if(controller.getStatus() == controller.ACTIVE) {
                Animation wrong = AnimationUtils.loadAnimation(getContext(), R.anim.wrong);
                tvPassword.startAnimation(wrong);
            }

            if(controller.getStatus() == controller.FINISH) {
                btnHack.setText(MainActivity.getRes().getString(R.string.new_game));
                showSnackBar(view, UtilsWinText.getWinText(controller.getLevel(), controller.getNumberAttempts()));
                MainActivity.saveSettings(controller.getLevel(), controller.getNumberAttempts());
            }
        }
    };

    /**
     * Показывает snackBar с сообщение об неверном действии или иной информации
     * @param view - по этой вью и происходит событие
     * @param str - строка
     */
    private void showSnackBar(View view, String str) {
        Snackbar customBar = Snackbar.make(view , str, Snackbar.LENGTH_LONG);
        customBar.setDuration(1000);
        View viewSnackBar = customBar.getView();
        TextView tvSnackBar = (TextView) viewSnackBar.findViewById(android.support.design.R.id.snackbar_text);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1)
            tvSnackBar.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        else
            tvSnackBar.setGravity(Gravity.CENTER_HORIZONTAL);
        customBar.show();
    }

    /**
     * Создаем кастомную клавиатуру
     * @param view - экранная view
     */
    private void createKeyboard(View view) {
        keyButtons = new KeyButton[10];
        keyButtons[0] = new KeyButton(0, (Button)view.findViewById(R.id.btn0));
        keyButtons[1] = new KeyButton(1, (Button)view.findViewById(R.id.btn1));
        keyButtons[2] = new KeyButton(2, (Button)view.findViewById(R.id.btn2));
        keyButtons[3] = new KeyButton(3, (Button)view.findViewById(R.id.btn3));
        keyButtons[4] = new KeyButton(4, (Button)view.findViewById(R.id.btn4));
        keyButtons[5] = new KeyButton(5, (Button)view.findViewById(R.id.btn5));
        keyButtons[6] = new KeyButton(6, (Button)view.findViewById(R.id.btn6));
        keyButtons[7] = new KeyButton(7, (Button)view.findViewById(R.id.btn7));
        keyButtons[8] = new KeyButton(8, (Button)view.findViewById(R.id.btn8));
        keyButtons[9] = new KeyButton(9, (Button)view.findViewById(R.id.btn9));

        for(final KeyButton button : keyButtons) {
            button.getButton().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    soundPool.play(1, 0.5f, 0.5f, 1, 0, 1f);
                    controller.getFocusCell().setValue(button.getValue());
                    controller.removeAllFocusCells();
                    controller.changeFocusCell();
                    if(controller.getIdFocusCell() == controller.NO_ACTIVE) {
                        closeKeyboard();
                    }
                }
            });
        }
    }

    /**
     * Создаем клетки
     */
    private void createCells() {
        int padding = (int)utilsDesign.getMarginCells(controller.getLevel());
        ArrayList<Cell> cells = new ArrayList<>();
        int size = (width - 2*padding*controller.getNumberCells() + 1)/controller.getNumberCells();
        for(int i = 0; i < controller.getNumberCells(); i++) {
            LayoutInflater inflater = getActivity().getLayoutInflater();
            View view = inflater.inflate(R.layout.cell, null, false);
            RelativeLayout layout = (RelativeLayout) view.findViewById(R.id.root);
            layout.setPadding(padding, 0, padding, 0);
            TextView tvCell = (TextView) view.findViewById(R.id.tvCell);
            tvCell.setTextSize(utilsDesign.getTextSize(controller.getLevel()));
            tvCell.setLayoutParams(new RelativeLayout.LayoutParams(size, size));
            final Cell cell = new Cell(i, layout, tvCell);
            cells.add(cell);

            cell.getTvCell().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(controller.getStatus() == controller.ACTIVE) {
                        controller.removeAllFocusCells();
                        controller.setFocusCell(cell);
                        showKeyboard();
                    }
                }
            });
        }
        controller.setCells(cells);
    }

    /**
     * Открываем кастомную клавиатуру
     */
    private void showKeyboard() {
        if(!isShowKeyboard) {
            Animation bottomUp = AnimationUtils.loadAnimation(getContext(),
                    R.anim.slide_up_dialog);
            rootKeyboard.startAnimation(bottomUp);
            rootKeyboard.setVisibility(View.VISIBLE);
            isShowKeyboard = true;
        }
    }

    /**
     * Закрываем кастомную клавиатуру
     */
    public void closeKeyboard() {
        if(isShowKeyboard) {

            controller.setIdFocusCell(controller.NO_ACTIVE);
            controller.removeAllFocusCells();

            Animation bottomUp = AnimationUtils.loadAnimation(getContext(),
                    R.anim.slide_out_down);
            rootKeyboard.startAnimation(bottomUp);
            rootKeyboard.setVisibility(View.GONE);
            isShowKeyboard = false;
        }
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
     * Начать игру заново
     */
    public void onRestart() {
        btnHack.setText(MainActivity.getRes().getString(R.string.hack));
        closeKeyboard();
        cellsLayout.removeAllViews();
        controller.restart();
        createCells();
        addCellsOnLayout();
        adapter.notifyDataSetChanged();
        tvAttempts.setText(String.format(getActivity().getResources().getString(R.string.committedAttempts), controller.getNumberAttempts()));
    }

}