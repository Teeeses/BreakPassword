package ru.explead.breakpassword.fragments;

import android.content.res.Resources;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

import ru.explead.breakpassword.HackCallback;
import ru.explead.breakpassword.MainActivity;
import ru.explead.breakpassword.R;
import ru.explead.breakpassword.adapters.DataAdapter;
import ru.explead.breakpassword.adapters.KeyboardAdapter;
import ru.explead.breakpassword.app.App;
import ru.explead.breakpassword.app.Utils;
import ru.explead.breakpassword.app.UtilsDesign;
import ru.explead.breakpassword.beans.Cell;
import ru.explead.breakpassword.beans.Complexity;
import ru.explead.breakpassword.dialog.DialogMenu;
import ru.explead.breakpassword.logic.Controller;
import ru.explead.breakpassword.logic.UtilsWinText;


/**
 * Created by develop on 13.01.2017.
 */

public class GameFragment extends Fragment implements HackCallback, KeyboardAdapter.OnKeyboardClickListener {

    private Controller controller;
    private UtilsDesign utilsDesign;

    /**
     * Layout для клеток для ввода цифр
     */
    private LinearLayout cellsLayout;
    private Button btnHack;
    private TextView tvAttempts;
    private TextView tvPassword;
    private LinearLayout layoutWin;
    private TextView tvWin;
    private ImageView ivWin;

    private DataAdapter adapter;

    /**
     * Указывает - открыта кастомная клавиатура или нет
     */
    public static boolean isShowKeyboard = false;

    /**
     * Ширина контейнера под клетки
     */
    private int width;

    private String lastAttempt = "";
    private SoundPool soundPool;

    private Animation wrong;

    private RecyclerView keyboard;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_game, container, false);

        controller = new Controller(this);
        App.setController(controller);

        wrong = AnimationUtils.loadAnimation(getContext(), R.anim.wrong);

        utilsDesign = new UtilsDesign();

        soundPool = new SoundPool(4, AudioManager.STREAM_MUSIC, 100);
        soundPool.load(getActivity(), R.raw.one, 1);
        soundPool.load(getActivity(), R.raw.sound_win, 2);

        layoutWin = view.findViewById(R.id.layoutWin);
        layoutWin.setOnClickListener(view16 -> layoutWin.setVisibility(View.GONE));
        tvWin = view.findViewById(R.id.tvWin);
        ivWin = view.findViewById(R.id.ivWin);
        tvPassword = view.findViewById(R.id.tvPassword);
        keyboard = view.findViewById(R.id.keyboard);
        tvAttempts = view.findViewById(R.id.tvAttempts);
        btnHack = view.findViewById(R.id.btnHack);
        ImageView btnMenu = view.findViewById(R.id.btnMenu);
        cellsLayout = view.findViewById(R.id.cellsLayout);
        ListView listView = view.findViewById(R.id.listView);
        adapter = new DataAdapter(requireActivity(), controller);
        listView.setAdapter(adapter);

        RelativeLayout rootGameFragment = view.findViewById(R.id.rootGameFragment);
        rootGameFragment.setOnClickListener(view15 -> closeKeyboard());

        listView.setOnItemClickListener((adapterView, view14, i, l) -> closeKeyboard());

        btnMenu.setOnClickListener(view13 -> {
            DialogMenu dialogMenu = new DialogMenu(getActivity(), controller);
            dialogMenu.show();
            closeKeyboard();
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

        final RelativeLayout layoutHelper = view.findViewById(R.id.layoutHelper);
        layoutHelper.setOnClickListener(view1 -> layoutHelper.setVisibility(View.GONE));

        setBestResult();
        createKeyboard();
        return view;
    }

    private void setBestResult() {
        if(App.getController().getLevel() == Complexity.EASY) {
            setBestResultText(MainActivity.getPref().getInt(Utils.BEST_EASY, 0));
        } else if(App.getController().getLevel() == Complexity.MEDIUM) {
            setBestResultText(MainActivity.getPref().getInt(Utils.BEST_MEDIUM, 0));
        }else if(App.getController().getLevel() == Complexity.HARD) {
            setBestResultText(MainActivity.getPref().getInt(Utils.BEST_HARD, 0));
        } else if(App.getController().getLevel() == Complexity.VERY_HARD) {
            setBestResultText(MainActivity.getPref().getInt(Utils.BEST_VERY_HARD, 0));
        }
    }

    private void setBestResultText(int bestValue) {
        Resources res = requireActivity().getResources();
        if(bestValue != 0) {
            tvAttempts.setText(String.format(res.getString(R.string.committedAttemptsAndBest),
                    controller.getNumberAttempts(), bestValue));
        } else {
            tvAttempts.setText(String.format(res.getString(R.string.committedAttempts),
                    controller.getNumberAttempts()));
        }
    }

    /**
     * Обработчик нажатия на кнопку попытки взлома
     */
    private View.OnClickListener btnHackClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            if(controller.getStatus() == Controller.FINISH) {
                onRestart();
                return;
            }
            if(controller.toStringPasswordProbable().equals(lastAttempt)) {
                showSnackBar(view, MainActivity.getRes().getString(R.string.changeValues));
            } else if(controller.isEmptyCells()) {
                controller.toAttempt();
                setBestResult();
                lastAttempt = controller.toStringPasswordProbable();
                adapter.notifyDataSetChanged();
            } else {
                showSnackBar(view, MainActivity.getRes().getString(R.string.cellIsEmpty));
            }
        }
    };

    @Override
    public void win() {
        btnHack.setText(MainActivity.getRes().getString(R.string.new_game));
        MainActivity.saveSettings(controller.getLevel(), controller.getNumberAttempts());
        setVisibilityLayoutWin();
    }

    @Override
    public void wrong() {
        tvPassword.startAnimation(wrong);
    }

    /**
     * Показывает победный слой
     */
    private void setVisibilityLayoutWin() {
        soundPool.play(2, 0.5f, 0.5f, 1, 0, 1f);
        tvWin.setText(UtilsWinText.getWinText(controller.getLevel(), controller.getNumberAttempts()));
        layoutWin.setVisibility(View.VISIBLE);

        Animation win = AnimationUtils.loadAnimation(getContext(), R.anim.image_win_animation);
        ivWin.startAnimation(win);
    }

    /**
     * Показывает snackBar с сообщение об неверном действии или иной информации
     * @param view - по этой вью и происходит событие
     * @param str - строка
     */
    private void showSnackBar(View view, String str) {
        Snackbar customBar = Snackbar.make(view , str, Snackbar.LENGTH_LONG);
        customBar.setDuration(1000);
        View viewSnackBar = customBar.getView();
        TextView tvSnackBar = viewSnackBar.findViewById(R.id.snackbar_text);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1)
            tvSnackBar.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        else
            tvSnackBar.setGravity(Gravity.CENTER_HORIZONTAL);
        customBar.show();
    }

    /**
     * Создаем кастомную клавиатуру
     */
    private void createKeyboard() {
        KeyboardAdapter adapter = new KeyboardAdapter(requireContext(), this);
        keyboard.setLayoutManager(new GridLayoutManager(requireContext(), 5));
        keyboard.setAdapter(adapter);
    }

    @Override
    public void onKeyboardItemClick(int value) {
        if(isShowKeyboard) {
            soundPool.play(1, 0.5f, 0.5f, 1, 0, 1f);
            controller.getFocusCell().setValue(value);
            controller.removeAllFocusCells();
            controller.changeFocusCell();
            if (controller.getIdFocusCell() == Controller.NO_ACTIVE) {
                closeKeyboard();
            }
        }
    }

    /**
     * Создаем клетки
     */
    private void createCells() {
        ArrayList<Cell> cells = new ArrayList<>();
        int size = width/controller.getNumberCells();
        for(int i = 0; i < controller.getNumberCells(); i++) {
            LayoutInflater inflater = requireActivity().getLayoutInflater();
            View view = inflater.inflate(R.layout.cell, null, false);
            RelativeLayout layout = view.findViewById(R.id.root);
            layout.setLayoutParams(new RelativeLayout.LayoutParams(size, size));
            TextView tvCell = view.findViewById(R.id.tvCell);
            tvCell.setTextSize(utilsDesign.getTextSize(controller.getLevel()));
            final Cell cell = new Cell(i, layout, tvCell);
            cells.add(cell);


            cell.getTvCell().setTypeface(Utils.getTypeFaceLevel(requireActivity()));
            cell.getTvCell().setOnClickListener(view1 -> {
                if(controller.getStatus() == Controller.ACTIVE) {
                    controller.removeAllFocusCells();
                    controller.setFocusCell(cell);
                    showKeyboard();
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
            isShowKeyboard = true;
            Animation bottomUp = AnimationUtils.loadAnimation(getContext(),
                    R.anim.slide_up_dialog);
            keyboard.startAnimation(bottomUp);
            keyboard.setVisibility(View.VISIBLE);
        }
    }

    /**
     * Закрываем кастомную клавиатуру
     */
    public void closeKeyboard() {
        if(isShowKeyboard) {
            isShowKeyboard = false;
            controller.setIdFocusCell(Controller.NO_ACTIVE);
            controller.removeAllFocusCells();

            Animation bottomUp = AnimationUtils.loadAnimation(getContext(),
                    R.anim.slide_out_down);
            keyboard.startAnimation(bottomUp);
            keyboard.setVisibility(View.GONE);
        }
    }

    /**
     * Добавляем клетки на layout
     */
    private void addCellsOnLayout() {
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
        setBestResult();
    }
}