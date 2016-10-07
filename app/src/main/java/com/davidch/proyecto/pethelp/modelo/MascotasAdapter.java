package com.davidch.proyecto.pethelp.modelo;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.davidch.proyecto.pethelp.R;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by adeka on 22/08/2016.
 */
public class MascotasAdapter extends RecyclerView.Adapter<MascotasAdapter.MascotaViewHolder> {

    private List<Mascota> mascotas;
    private OnMascotaClickListener listener;

    public MascotasAdapter(List<Mascota> mascotas, OnMascotaClickListener listener) {
        this.mascotas = mascotas;
        this.listener = listener;
    }

    public static class MascotaViewHolder extends RecyclerView.ViewHolder {

        private TextView textViewNombre;
        private ImageView imageViewDuenio;
        private ImageView imageViewCuidador;

        public MascotaViewHolder(View itemView) {
            super(itemView);

            textViewNombre = (TextView) itemView.findViewById(R.id.textViewnombremascota);
            imageViewDuenio = (ImageView) itemView.findViewById(R.id.imageViewduenio);
            imageViewCuidador = (ImageView) itemView.findViewById(R.id.imageViewcuidator);
        }
    }

    private class MascotasAdapterOnClickListener implements View.OnClickListener {

        private Mascota mascota;

        public MascotasAdapterOnClickListener(Mascota mascota) {
            this.mascota = mascota;
        }

        @Override
        public void onClick(View v) {
            listener.onMascotaClick(mascota);
        }
    }

    public static interface OnMascotaClickListener {
        void onMascotaClick(Mascota mascota);
    }

    @Override
    public MascotaViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View raiz = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_mascotas, parent, false);
        return new MascotaViewHolder(raiz);
    }

    @Override
    public int getItemCount() {
        return mascotas.size();
    }

    @Override
    public void onBindViewHolder(MascotaViewHolder holder, int position) {

        Mascota mascota = mascotas.get(position);
        holder.textViewNombre.setText(mascota.getNombre());
        MascotasAdapterOnClickListener clickListener = new MascotasAdapterOnClickListener(mascota);
        holder.itemView.setOnClickListener(clickListener);
    }
}
