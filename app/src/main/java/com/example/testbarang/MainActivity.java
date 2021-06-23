package com.example.testbarang;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private Button bTambah, bLihat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bTambah = findViewById(R.id.btnTambah);
        bLihat = findViewById(R.id.btnLihat);

        bTambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(TambahData.getActItent(MainActivity.this));
            }
        });

        bLihat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(LihatBarang.getActIntent(MainActivity.this));
            }
        });
    }
}