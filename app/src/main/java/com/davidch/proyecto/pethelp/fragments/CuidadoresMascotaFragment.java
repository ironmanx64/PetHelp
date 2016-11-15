package com.davidch.proyecto.pethelp.fragments;

import android.database.Cursor;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.ListFragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.CursorAdapter;
import android.support.v4.widget.SimpleCursorAdapter;
import android.os.Bundle;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;

import com.davidch.proyecto.pethelp.DescriptionPetActivity;
import com.davidch.proyecto.pethelp.R;
import com.davidch.proyecto.pethelp.datos.PethelpContentProvider;
import com.davidch.proyecto.pethelp.datos.acciones.AccionesCuidador;
import com.davidch.proyecto.pethelp.datos.tablas.Cuidadores;
import com.davidch.proyecto.pethelp.datos.tablas.Mascotas;

import java.util.Arrays;

public class CuidadoresMascotaFragment extends ListFragment
        implements
            LoaderManager.LoaderCallbacks<Cursor>,
            View.OnClickListener,
            AbsListView.MultiChoiceModeListener {

    public static final int LOADER_CUIDADORES = 1;

    private CursorAdapter adapterCuidadores;
    private FloatingActionButton buttonAniadirCuidador;
    private long idMascota;
    private AccionesCuidador accionesCuidador;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        accionesCuidador = new AccionesCuidador(getContext().getContentResolver(), getContext());

        DescriptionPetActivity activity = (DescriptionPetActivity)getActivity();
        idMascota = activity.getIntent().getLongExtra(Mascotas.ID, -1);

        adapterCuidadores = new SimpleCursorAdapter(getContext(),
                R.layout.item_cuidador,
                null,
                new String [] {Cuidadores.NICK},
                new int [] {R.id.textViewCuidador},
                0);
        setListAdapter(adapterCuidadores);
        getListView().setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
        getListView().setMultiChoiceModeListener(this);

        getLoaderManager().initLoader(LOADER_CUIDADORES, null, this);

        buttonAniadirCuidador = (FloatingActionButton)getView()
                .findViewById(R.id.buttonfloatingmascotasedescripcion);

        buttonAniadirCuidador.setOnClickListener(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_cuidadores_mascota, container, false);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(getContext(),
                PethelpContentProvider.getUriCuidadores(),
                Cuidadores.PROYECCION_COMPLETA,
                Cuidadores.ID_MASCOTA + " =? AND " +
                Cuidadores.BORRADO + " = 0",
                new String [] {Long.toString(idMascota)},
                null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        adapterCuidadores.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        adapterCuidadores.swapCursor(null);
    }

    @Override
    public void onClick(View v) {
        if (v == buttonAniadirCuidador) {
            DialogFragment dialog = AniadirCuidadorDialogFragment.crear(idMascota);
            dialog.show(getFragmentManager(), "aniadirCuidador");
        }
    }

    @Override
    public void onItemCheckedStateChanged(ActionMode mode, int position, long id, boolean checked) {

    }

    @Override
    public boolean onCreateActionMode(ActionMode mode, Menu menu) {
        final DescriptionPetActivity activity = (DescriptionPetActivity) getActivity();
        activity.getMenuInflater().inflate(R.menu.gestion_cuidadores, menu);
        activity.setActionMode(mode);
        return true;
    }

    @Override
    public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
        return false;
    }

    @Override
    public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
        if (item.getItemId() == R.id.menuItemBorrar) {
            long [] ids = getListView().getCheckedItemIds();
            accionesCuidador.borrar(ids, idMascota);
        }
        return false;
    }

    @Override
    public void onDestroyActionMode(ActionMode mode) {
        final DescriptionPetActivity activity = (DescriptionPetActivity) getActivity();
        activity.setActionMode(null);
    }
}
