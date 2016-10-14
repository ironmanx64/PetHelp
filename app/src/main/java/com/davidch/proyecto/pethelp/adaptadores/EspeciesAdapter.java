package com.davidch.proyecto.pethelp.adaptadores;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.davidch.proyecto.pethelp.R;
import com.davidch.proyecto.pethelp.modelo.Especie;

import java.util.List;

/**
 * Created by adeka on 14/10/2016.
 */

public class EspeciesAdapter extends ArrayAdapter<Especie>{


    public EspeciesAdapter(Context context,  List<Especie> listaespecies) {
        super(context, android.R.layout.select_dialog_item, listaespecies);
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {

        View item = convertView;

        if(item==null){
            LayoutInflater inflater = LayoutInflater.from(getContext());
            item = inflater.inflate(android.R.layout.select_dialog_item, parent, false);
        }

        ((TextView)item).setText(getItem(position).getEspecie());

        return item;


    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return getDropDownView(position, convertView, parent);
    }
}
