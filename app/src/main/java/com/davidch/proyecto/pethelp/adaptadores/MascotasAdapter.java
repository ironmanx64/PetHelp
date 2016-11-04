package com.davidch.proyecto.pethelp.adaptadores;


import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.davidch.proyecto.pethelp.R;
import com.davidch.proyecto.pethelp.datos.tablas.Mascotas;
import com.davidch.proyecto.pethelp.modelo.Mascota;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by adeka on 22/08/2016.
 */
public class MascotasAdapter extends RecyclerView.Adapter<MascotasAdapter.MascotaViewHolder> {

    public static final String [] PROYECCION_MASCOTAS = new String [] {
            Mascotas.ID,
            Mascotas.NOMBRE
    };

    public interface OnMascotaClickListener {
        void onMascotaClick(long id);
        void onMascotaLongClick(long id);
    }

    public class MascotaViewHolder extends RecyclerView.ViewHolder
        implements View.OnClickListener, View.OnLongClickListener {

        private View root;

        private TextView textViewNombre;
        private ImageView imageViewDuenio;
        private ImageView imageViewCuidador;

        private long id;

        public MascotaViewHolder(View itemView) {
            super(itemView);

            root = itemView;

            textViewNombre = (TextView) itemView.findViewById(R.id.textViewnombremascota);
            imageViewDuenio = (ImageView) itemView.findViewById(R.id.imageViewduenio);
            imageViewCuidador = (ImageView) itemView.findViewById(R.id.imageViewcuidator);

            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        public void bind(Cursor cursor) {
            if (!modoSeleccion) {
                root.setSelected(false);
            }
            id = cursor.getLong(0);
            textViewNombre.setText(cursor.getString(1));
        }

        @Override
        public void onClick(View v) {
            if (modoSeleccion) {
                cambiarSeleccionar();
            }
            else {
                if (listener != null) {
                    listener.onMascotaClick(id);
                }
            }
        }

        @Override
        public boolean onLongClick(View v) {
            if (!modoSeleccion) {
                modoSeleccion = true;
                listener.onMascotaLongClick(id);
                cambiarSeleccionar();
                return true;
            }
            else {
                return false;
            }
        }

        public void cambiarSeleccionar() {
            if (root.isSelected()) {
                idsSeleccionados.remove(id);
                root.setSelected(false);
            }
            else {
                idsSeleccionados.add(id);
                root.setSelected(true);
            }
        }
    }

    private boolean modoSeleccion = false;
    private Cursor cursor;
    private OnMascotaClickListener listener;
    private Set<Long> idsSeleccionados = new HashSet<>();

    public MascotasAdapter(Cursor cursor, OnMascotaClickListener listener) {
        this.cursor = cursor;
        this.listener = listener;
    }

    @Override
    public MascotaViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View raiz = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_mascotas, parent, false);
        return new MascotaViewHolder(raiz);
    }

    @Override
    public int getItemCount() {
        if (cursor != null) {
            return cursor.getCount();
        }
        else {
            return 0;
        }
    }

    @Override
    public void onBindViewHolder(MascotaViewHolder holder, int position) {

        if (cursor == null) {
            throw new IllegalStateException("cursor es null");
        }

        if (!cursor.moveToPosition(position)) {
            throw new IllegalArgumentException("posicion no válida: " + position);
        }

        holder.bind(cursor);
    }

    public void switchCursor(Cursor nuevoCursor) {
        if (cursor != null) {
            cursor.close();
        }
        cursor = nuevoCursor;
        notifyDataSetChanged();
    }

    public void salirDeSeleccion() {
        modoSeleccion = false;
        idsSeleccionados.clear();
        notifyDataSetChanged();
    }

    public List<Long> getIdsSeleccionados() {
        return new ArrayList<>(idsSeleccionados);
    }
}
