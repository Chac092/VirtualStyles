package com.example.ropaapp.ui.main;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.ropaapp.Fragment_Administrador;
import com.example.ropaapp.Fragment_Estilista;
import com.example.ropaapp.Fragment_Usuario;
import com.example.ropaapp.R;


/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */

public class SectionsPagerAdapter extends FragmentPagerAdapter {
    Fragment_Estilista estilista;
    Fragment_Usuario usu;
    Fragment_Administrador fragmentAdministrador;

    @StringRes
    private static final int[] TAB_TITLES = new int[]{R.string.usuario, R.string.estilista,R.string.admin};
    private final Context mContext;

    public SectionsPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                usu = new Fragment_Usuario();
                return usu;
            case 1:
                estilista = new Fragment_Estilista();
                return estilista;
            case 2:
                fragmentAdministrador = new Fragment_Administrador();
                return fragmentAdministrador;
        }
        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).
        return PlaceholderFragment.newInstance(position + 1);
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mContext.getResources().getString(TAB_TITLES[position]);
    }

    @Override
    public int getCount() {
        // Show 3 total pages.
        return TAB_TITLES.length;
    }
}