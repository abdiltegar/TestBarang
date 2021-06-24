package com.example.testbarang.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.testbarang.Barang;
import com.example.testbarang.EditBarang;
import com.example.testbarang.MainActivity;
import com.example.testbarang.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class AdapterLihatBarang  extends RecyclerView.Adapter<AdapterLihatBarang.ViewHolder> {

    private ArrayList<Barang> daftarBarang;
    private Context context;
    private DatabaseReference databaseReference;

    public AdapterLihatBarang(ArrayList<Barang> barangs, Context ctx){
        daftarBarang = barangs;
        context = ctx;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle;

        ViewHolder(View v){
            super(v);
            tvTitle = (TextView) v.findViewById(R.id.tv_namabarang);

            databaseReference = FirebaseDatabase.getInstance().getReference();
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_barang, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final String kode, name;
        name = daftarBarang.get(position).getNama();
        kode = daftarBarang.get(position).getKode();

        holder.tvTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        holder.tvTitle.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                PopupMenu popupMenu = new PopupMenu(view.getContext(), view);
                popupMenu.inflate(R.menu.menubarang);
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch(item.getItemId()){
                            case R.id.mnEdit:
                                Bundle bundle = new Bundle();
                                bundle.putString("kunci1",kode);
                                bundle.putString("kunci2",name);

                                Intent intent = new Intent(view.getContext(), EditBarang.class);
                                intent.putExtras(bundle);
                                view.getContext().startActivity(intent);

                                break;
                            case R.id.mnHapus:
                                AlertDialog.Builder alertDlg = new AlertDialog.Builder(view.getContext());
                                alertDlg.setTitle("Yakin data "+name+" akan dihapus ?");
                                alertDlg.setMessage("Tekan 'Ya' untuk menghapus")
                                        .setCancelable(false)
                                        .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                DeleteData(kode);
                                                Toast.makeText(view.getContext(), "Data "+name+" berhasil dihapus", Toast.LENGTH_LONG).show();
                                                Intent intent = new Intent(view.getContext(), MainActivity.class);
                                                view.getContext().startActivity(intent);
                                            }
                                        })
                                        .setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                dialog.cancel();
                                            }
                                        });
                                AlertDialog aDlg = alertDlg.create();
                                aDlg.show();
                                break;
                        }
                        return true;
                    }
                });
                popupMenu.show();
                return true;
            }
        });

        holder.tvTitle.setText(name);
    }

    @Override
    public int getItemCount() {
        return daftarBarang.size();
    }

    public void DeleteData(String kode){
        if (databaseReference != null){
            databaseReference.child("Barang").child(kode).removeValue();
        }
    }
}
