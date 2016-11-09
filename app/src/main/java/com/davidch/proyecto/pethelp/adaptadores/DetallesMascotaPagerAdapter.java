package com.davidch.proyecto.pethelp.adaptadores;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.view.View;

import com.davidch.proyecto.pethelp.fragments.CuidadoresMascotaFragment;
import com.davidch.proyecto.pethelp.fragments.DescriptionPetFragment;

/**
 * Created by adeka on 09/11/2016.
 */

public class DetallesMascotaPagerAdapter extends FragmentStatePagerAdapter {

    public DetallesMascotaPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new DescriptionPetFragment();
            case 1:
                return new CuidadoresMascotaFragment();
            default:
                throw new RuntimeException("Posicion no valida: " + position);
        }
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "Mascota";
            case 1:
                return "Cuidadores";
            default:
                throw new RuntimeException("Posicion no valida: " + position);
        }
    }

    @Override
    public int getCount() {
        return 2;
    }
}
