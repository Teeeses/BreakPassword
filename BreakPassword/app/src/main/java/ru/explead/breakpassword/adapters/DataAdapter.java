package ru.explead.breakpassword.adapters;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import ru.explead.breakpassword.MainActivity;
import ru.explead.breakpassword.R;
import ru.explead.breakpassword.beans.Data;
import ru.explead.breakpassword.logic.Controller;

/**
 * Created by develop on 16.01.2017.
 */

public class DataAdapter extends BaseAdapter {

    /**
     * Список объектов в адапторе
     */
    private ArrayList<Data> data = new ArrayList<Data>();
    private LayoutInflater lInflater;
    private ViewHolder viewHolder;


    public DataAdapter(Controller controller) {
        this.data = controller.getData();
        lInflater = (LayoutInflater) MainActivity.getActivity()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = lInflater.inflate(R.layout.item_data, parent, false);
            viewHolder = new ViewHolder();

            viewHolder.tvpProbablePassword = (TextView) convertView.findViewById(R.id.tvpProbablePassword);
            viewHolder.tvOnPlace = (TextView) convertView.findViewById(R.id.tvOnPlace);
            viewHolder.tvMatches = (TextView) convertView.findViewById(R.id.tvMatches);

            convertView.setTag(viewHolder);

        } else viewHolder = (ViewHolder) convertView.getTag();

        Resources res = MainActivity.getActivity().getResources();
        final Data object = data.get(position);

        viewHolder.tvOnPlace.setText(String.format(res.getString(R.string.onPlace), object.getOnPlace()));
        viewHolder.tvMatches.setText(String.format(res.getString(R.string.matches), object.getMatches()));

        viewHolder.tvpProbablePassword.setText(object.getProbable());

        return convertView;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Data getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    class ViewHolder {
        TextView tvpProbablePassword;
        TextView tvOnPlace;
        TextView tvMatches;
    }
}