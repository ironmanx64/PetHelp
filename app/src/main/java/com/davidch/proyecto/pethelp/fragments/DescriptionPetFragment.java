package com.davidch.proyecto.pethelp.fragments;

import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.davidch.proyecto.pethelp.DescriptionPetActivity;
import com.davidch.proyecto.pethelp.R;
import com.davidch.proyecto.pethelp.datos.PethelpContentProvider;
import com.davidch.proyecto.pethelp.datos.tablas.Mascotas;
import com.davidch.proyecto.pethelp.modelo.Mascota;

public class DescriptionPetFragment extends Fragment
    implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final int LOADER_MASCOTA = 1;

    private long idMascota;
    private Mascota mascota = null;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        DescriptionPetActivity activity = (DescriptionPetActivity)getActivity();
        idMascota = activity.getIntent().getLongExtra(Mascotas.ID, -1);

        getLoaderManager().initLoader(LOADER_MASCOTA, null, this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_descripcion_pet, container, false);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(getContext(),
                PethelpContentProvider.getUriMascota(idMascota),
                Mascotas.PROYECCION_COMPLETA,
                null, null, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        if (data.moveToFirst()) {
            mascota = new Mascota(data);
            getActivity().setTitle(mascota.getNombre());
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        getActivity().setTitle("");
    }
}
