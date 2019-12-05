package com.example.bookreview;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

public class Login extends AppCompatActivity implements View.OnClickListener {

    private EditText et_User, et_Pass;
    private Button btn_Login, btn_Register;

    private FirebaseDatabase FD;
    private DatabaseReference dr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        et_User = findViewById(R.id.et_UsernameL);
        et_Pass = findViewById(R.id.et_PasswordL);
        btn_Login = findViewById(R.id.btn_LoginL);
        btn_Register = findViewById(R.id.btn_RegisterL);

        FD = FirebaseDatabase.getInstance();
        dr = FD.getReference().child("Akun");

        btn_Login.setOnClickListener(this);
        btn_Register.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_LoginL :
                final String username, password;
                Boolean isEmpty = false;
                username = et_User.getText().toString().toUpperCase().trim();
                password = et_Pass.getText().toString().trim();
                if (TextUtils.isEmpty(username)){
                    et_User.setError("Field ini tidak boleh kosong");
                    isEmpty = true;
                }
                if (TextUtils.isEmpty(password)){
                    et_Pass.setError("Field ini tidak boleh kosong");
                }
                if ((username.equals("ADMIN")) && (password.equals("admin"))){
                    Intent IMenuUtamaA = new Intent(Login.this, MenuUtama.class);
                    IMenuUtamaA.putExtra("getNama", "ADMIN");
                    startActivity(IMenuUtamaA);
                }
                if (!isEmpty) {
                    dr.orderByChild(username).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            for (DataSnapshot data : dataSnapshot.getChildren()) {
                                String usernameD = data.child("username").getValue().toString();
                                String passwordD = data.child("password").getValue().toString();
                                if ((usernameD.equals(username)) && (passwordD.equals(password))) {
                                    Intent IMenuUtama = new Intent(Login.this, MenuUtama.class);
                                    IMenuUtama.putExtra("getBoolean", false);
                                    IMenuUtama.putExtra("getNama", usernameD);
                                    startActivity(IMenuUtama);
                                }
                                if ((usernameD.equals(username)) && (!passwordD.equals(password))) {
                                    Toast.makeText(getApplicationContext(), "Password salah", Toast.LENGTH_SHORT).show();
                                    et_User.setText("");
                                    et_Pass.setText("");
                                }
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }
                break;
            case R.id.btn_RegisterL :
                Intent IRegistrasi = new Intent (Login.this, Registrasi.class);
                startActivity(IRegistrasi);
                break;
        }
    }
}
