package com.example.bookreview;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Kategori extends AppCompatActivity implements View.OnClickListener, NavigationView.OnNavigationItemSelectedListener {

    private EditText et_NamaKategori;
    private Button btnTambah;

    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private NavigationView navigationView;

    private FirebaseDatabase FD;
    private DatabaseReference dRef;

    private String nama, judul;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kategori);

        FD = FirebaseDatabase.getInstance();
        dRef = FD.getReference().child("Kategori");

        et_NamaKategori = findViewById(R.id.et_NamaKategoriK);

        btnTambah = findViewById(R.id.btn_TambahK);

        drawerLayout = findViewById(R.id.nav_barM);
        navigationView = findViewById(R.id.nav_menu);
        actionBarDrawerToggle = new ActionBarDrawerToggle(Kategori.this, drawerLayout, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent getIntent = getIntent();
        judul = getIntent.getStringExtra("JudulBuku");
        nama = getIntent.getStringExtra("getNama");

        View v = navigationView.getHeaderView(0);
        TextView ubah = v.findViewById(R.id.tv_nama_user);
        ubah.setText(nama);

        btnTambah.setOnClickListener(this);

        navigationView.setNavigationItemSelectedListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_TambahK :
                final String NamaKategori;
                Boolean isEmpty;
                NamaKategori = et_NamaKategori.getText().toString().toUpperCase().trim();
                isEmpty = false;
                if(TextUtils.isEmpty(NamaKategori)){
                    isEmpty = true;
                    et_NamaKategori.setError("Field ini tidak boleh kosong");
                }
                if (!isEmpty) {
                    dRef.orderByChild("namaKategori").equalTo(NamaKategori).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                if (dataSnapshot.exists()){
                                    Toast.makeText(getApplicationContext(),"Data tersebut sudah ada",Toast.LENGTH_SHORT).show();
                                    et_NamaKategori.setText("");
                                }
                                else {
                                    PojoKategori PK = new PojoKategori(NamaKategori);
                                    dRef.push().setValue(PK);
                                    Toast.makeText(getApplicationContext(), "Penambahan Kategori Berhasil", Toast.LENGTH_SHORT).show();
                                    Intent IMenuUtama1 = new Intent(Kategori.this, MenuUtama.class);
                                }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }
                break;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (actionBarDrawerToggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()){
            case R.id.nav_signout :
                Intent intent = new Intent(this, Login.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                break;
        }
        return false;
    }

    @Override
    public void onBackPressed(){
        Intent intent = new Intent(Kategori.this, MenuUtama.class);
        intent.putExtra("getNama", nama);
        intent.putExtra("JudulBuku", judul);
        startActivity(intent);
        finish();
    }

}


