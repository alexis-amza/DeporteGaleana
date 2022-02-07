package com.amza.deportegaleana.actividades;

import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.amza.deportegaleana.R;
import com.amza.deportegaleana.clases.preferencias;
import com.amza.deportegaleana.fragments.PartidosFinalizadosFragment;
import com.amza.deportegaleana.fragments.ProximosPartidosFragment;

public class ContenedorPartidosActivity extends AppCompatActivity implements ProximosPartidosFragment.OnFragmentInteractionListener,
        PartidosFinalizadosFragment.OnFragmentInteractionListener {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    ImageButton btnAyudaPartidos, btnRegresarPartidos;

    RelativeLayout cabeceraPartidos;
    TabLayout tabLayout;
    AppBarLayout appBarLayout;
    Toolbar toolbarPartidos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contenedor_partidos);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        cabeceraPartidos = findViewById(R.id.idCabeceraPartidos);
        tabLayout = (TabLayout) findViewById(R.id.tabsPartidos);
        appBarLayout = (AppBarLayout) findViewById(R.id.idappbarPartidos);
        toolbarPartidos = (Toolbar) findViewById(R.id.toolbar);

        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));

        btnAyudaPartidos = findViewById(R.id.idbtnAyudaPartidos);
        btnRegresarPartidos = findViewById(R.id.idbtnRegresarPartidos);

        asignarValoresPreferencias();
        eventoBotones();

    }

    private void asignarValoresPreferencias() {
        cabeceraPartidos.setBackgroundColor(getResources().getColor(preferencias.colorTema));
        tabLayout.setBackgroundColor(getResources().getColor(preferencias.colorTema));
        appBarLayout.setBackgroundColor(getResources().getColor(preferencias.colorTema));
        toolbarPartidos.setBackgroundColor(getResources().getColor(preferencias.colorTema));
    }


    public void eventoBotones(){
        btnAyudaPartidos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialogoAyudaPartidos().show();

            }
        });


        btnRegresarPartidos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }


    public AlertDialog dialogoAyudaPartidos(){
        AlertDialog.Builder builder = new AlertDialog.Builder(ContenedorPartidosActivity.this);

        builder.setTitle("Ayuda").setMessage("En este apartado de la aplicacion podrás visualizar los partidos correspondientes a la " +
                "proxima jornada por jugarse, así como los partidos y resultados de las jornadas anteriores.").setPositiveButton("Aceptar",
                new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialog, int which){

                    }
                });

        return builder.create();
    }


    @Override
    public void onFragmentInteraction(Uri uri) {

    }


    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static Fragment newInstance(int sectionNumber) {
            Fragment fragment = null;

            switch (sectionNumber){
                case 1:
                    fragment = new ProximosPartidosFragment();
                    break;
                case 2:
                    fragment = new PartidosFinalizadosFragment();
                    break;
            }

            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_contenedor_partidos, container, false);
            TextView textView = (TextView) rootView.findViewById(R.id.section_label);
            textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));
            return rootView;
        }
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).

            return PlaceholderFragment.newInstance(position + 1);

        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 2;
        }
    }
}
