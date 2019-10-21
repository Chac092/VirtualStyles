package com.example.ropaapp;

import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.ropaapp.ui.main.SectionsPagerAdapter;
import com.google.android.material.tabs.TabLayout;

public class Activity_Admin extends AppCompatActivity implements Fragment_Estilista.OnFragmentInteractionListener, Fragment_Usuario.OnFragmentInteractionListener, Fragment_Administrador.OnFragmentInteractionListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_administrador);
        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);
    }


   /* @Override
    public boolean onCreateOptionsMenu(Menu menu)  {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.overflow, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();
        if (id == R.id.menuItem1){
            System.out.println("Factura");
        }else if (id == R.id.menuItem2){
            System.out.println("Sesion");
        }
        return super.onOptionsItemSelected(item);
    }*/
    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}