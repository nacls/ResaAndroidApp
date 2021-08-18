package ir.ceit.resa.view.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import ir.ceit.resa.R;
import ir.ceit.resa.model.NavigationMenuItem;

public class NavigationMenuItemAdapter extends ArrayAdapter<NavigationMenuItem> {

        Context mContext;
        int layoutResourceId;
        NavigationMenuItem data[] = null;

        public NavigationMenuItemAdapter(Context mContext, int layoutResourceId, NavigationMenuItem[] data) {

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

            NavigationMenuItem folder = data[position];


            imageViewIcon.setImageResource(folder.icon);
            textViewName.setText(folder.title);

            return listItem;
        }
    }
