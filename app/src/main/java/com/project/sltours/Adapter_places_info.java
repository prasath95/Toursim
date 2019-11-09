package com.project.sltours;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class Adapter_places_info extends RecyclerView.Adapter<Adapter_places_info.viewoder> {
    private Context mcontext;
    private List<Model_Placedetails> muploads;

    public Adapter_places_info(Context mcontext, List<Model_Placedetails> muploads) {
        this.mcontext = mcontext;
        this.muploads = muploads;
    }

    @NonNull
    @Override
    public viewoder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(mcontext).inflate(R.layout.layout_places_details,parent,false);

        return new viewoder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewoder holder, int position) {
        Model_Placedetails mpd=muploads.get(position);

        holder.tvplacename.setText(mpd.getPlacename());
        holder.tvrating.setText("Rating - "+mpd.getRating());
        holder.tvdesc.setText(mpd.getDescription());

        Picasso.get()
                .load(mpd.getImg())
                .centerCrop()
                .fit()
                .into(holder.iv);


    }

    @Override
    public int getItemCount() {
        return muploads.size();
    }

    public class viewoder extends RecyclerView.ViewHolder {

        TextView tvplacename,tvrating,tvdesc;
        ImageView iv;
        Button btnadd,btnmap;


        public viewoder(@NonNull View itemView) {
            super(itemView);

            tvplacename=itemView.findViewById(R.id.textView);
            tvrating=itemView.findViewById(R.id.textView3);
            tvdesc=itemView.findViewById(R.id.textView2);

            iv=itemView.findViewById(R.id.imageView2);

            btnadd=itemView.findViewById(R.id.addid);
            btnmap=itemView.findViewById(R.id.mapid);
        }
    }
}
