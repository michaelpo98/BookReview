package com.example.bookreview;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Registrasi extends AppCompatActivity implements View.OnClickListener {

    private EditText et_username, et_password, et_password1, et_nama;
    private Button btn_register;

    private FirebaseDatabase database;
    private DatabaseReference dRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrasi);

        et_username = findViewById(R.id.et_UsernameR);
        et_password = findViewById(R.id.et_InputPassR);
        et_password1 = findViewById(R.id.et_InputPass1R);
        et_nama = findViewById(R.id.et_NamaR);

        database = FirebaseDatabase.getInstance();
        dRef = database.getReference().child("Akun");

        btn_register = findViewById(R.id.btn_RegisterR);

        btn_register.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.btn_RegisterR :

                boolean isEmpty, isLength, isPassword;
                isEmpty = false;
                isLength = false;
                isPassword = false;

                final String username, password, password1, nama;
                username = et_username.getText().toString().toUpperCase().trim();
                password = et_password.getText().toString().trim();
                password1 = et_password1.getText().toString().trim();
                nama = et_nama.getText().toString().trim();

                if (TextUtils.isEmpty(username)){
                    isEmpty = true;
                    et_username.setError("Field ini tidak boleh kosong");
                }
                if (TextUtils.isEmpty(password)){
                    isEmpty = true;
                    et_password.setError("Field ini tidak boleh kosong");
                }
                if (TextUtils.isEmpty(password1)){
                    isEmpty = true;
                    et_password1.setError("Field ini tidak boleh kosong");
                }
                if (TextUtils.isEmpty(nama)){
                    isEmpty = true;
                    et_nama.setError("Field ini tidak boleh kosong");
                }

                if ((username.length()<5) && (!isEmpty)){
                    isLength = true;
                    et_username.setError("Panjang username minimal 5 ");
                }

                if ((!password.equals(password1)) && (!isEmpty) ){
                    isPassword = true;
                    et_password1.setError("Password 1 tidak sama dengan pengulangan password");
                }

                if ((!isEmpty && !isLength) && !isPassword) {
                    dRef.child(username).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()){
                                Toast.makeText(getApplicationContext(), "Username telah ada", Toast.LENGTH_SHORT).show();
                                et_username.setText("");
                                et_password.setText("");
                                et_password1.setText("");
                                et_nama.setText("");
                            } else {
                                PojoAkun PA = new PojoAkun(username, password, password1, nama);
                                dRef.child(username).setValue(PA);
                                Toast.makeText(getApplicationContext(), "Telah Berhasil Daftar", Toast.LENGTH_SHORT)
                                        .show();
                                Intent ILogin = new Intent(Registrasi.this, Login.class);
                                startActivity(ILogin);
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
}
