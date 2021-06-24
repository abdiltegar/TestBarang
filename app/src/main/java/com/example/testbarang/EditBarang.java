package com.example.testbarang;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class EditBarang extends AppCompatActivity {
    TextView tv_key;
    EditText ed_Nama;
    Button btnEdit;
    DatabaseReference databaseReference;
    String kode, nama;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_barang);

        tv_key = findViewById(R.id.tv_key);
        ed_Nama = findViewById(R.id.edNama);
        btnEdit = findViewById(R.id.btEdit);

        databaseReference = FirebaseDatabase.getInstance().getReference();

        Bundle bundle = this.getIntent().getExtras();
        kode = bundle.getString("kunci1");
        nama = bundle.getString("kunci2");

        tv_key.setText(kode);
        ed_Nama.setText(nama);

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                barangEdit(new Barang(kode,ed_Nama.getText().toString()));
            }
        });
    }

    public void barangEdit(Barang barang){
        databaseReference.child("Barang")
                .child(kode)
                .setValue(barang)
                .addOnSuccessListener(this, new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(EditBarang.this, "Data berhasil diupdate", Toast.LENGTH_LONG).show();
                        finish();
                    }
                });
    }
}