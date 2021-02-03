package com.lcmf.mmgo.recyclemenu;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.lcmf.mmgo.R;

import java.util.List;

public class HolyMenuAdapter extends RecyclerView.Adapter<HolyMenuAdapter.ViewHolder>
        //,implements View.OnClickListener
{

    private List<HolyMenuItem> mMenuList;
    private Context mCtx;

//    @Override
//    public void onClick(View view) {
//
//    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView menuImage;
        TextView menuName;

        public ViewHolder(View view) {
            super(view);
            menuImage = (ImageView) view.findViewById(R.id.menu_image);
            menuName = (TextView) view.findViewById(R.id.menu_name);
        }

    }

    public HolyMenuAdapter(List<HolyMenuItem> fruitList, Context context) {
        mMenuList = fruitList;
        this.mCtx = context;
    }

    @Override

    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.menu_item, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        HolyMenuItem holyMenuItem = mMenuList.get(position);
        holder.menuImage.setImageResource(holyMenuItem.getImageId());
        holder.menuName.setText(holyMenuItem.getName());

        //holder.menuImage.setOnClickListener(this);
        //holder.menuName.setOnClickListener(this);

        holder.menuImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(mCtx, "pos:" + position, Toast.LENGTH_SHORT).show();
                try {
                    mCtx.startActivity(new Intent(mCtx, Class.forName(holyMenuItem.getClassName())));
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mMenuList.size();
    }

}