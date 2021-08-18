package ir.ceit.resa.view.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import ir.ceit.resa.R;
import ir.ceit.resa.model.view.NavigationMenuItem;

public class NavigationMenuItemAdapter extends ArrayAdapter<NavigationMenuItem> {

        Context mContext;
        int layoutResourceId;
        ArrayList<NavigationMenuItem> data = null;

        public NavigationMenuItemAdapter(Context mContext, int layoutResourceId, ArrayList<NavigationMenuItem> data) {

            super(mContext, layoutResourceId, data);
            this.layoutResourceId = layoutResourceId;
            this.mContext = mContext;
            this.data = data;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            View listItem = convertView;

            LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
            listItem = inflater.inflate(layoutResourceId, parent, false);

            ImageView imageViewIcon = (ImageView) listItem.findViewById(R.id.imageViewIcon);
            TextView textViewName = (TextView) listItem.findViewById(R.id.textViewName);

            NavigationMenuItem folder = data.get(position);


            imageViewIcon.setImageResource(folder.getIcon());
            textViewName.setText(folder.getTitle());

            return listItem;
        }
    }
