package com.example.top10downloader;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

public class FeedAdapter extends ArrayAdapter {
    private static final String TAG = "FeedAdapter";
    private final int layoutResource;
    private final LayoutInflater layoutInflater;
    private List<Entry>applications;

    public FeedAdapter(@NonNull Context context, int resource, List<Entry> applications) {
        super(context, resource);
        this.layoutResource=resource;
        this.layoutInflater= LayoutInflater.from(context);
        this.applications = applications;
    }

    @Override
    public int getCount() {
        return applications.size();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        viewHolder viewHolder;
        if(convertView==null){
            convertView = layoutInflater.inflate(layoutResource,parent,false);
            viewHolder = new viewHolder(convertView);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (viewHolder)convertView.getTag();
        }
        /*TextView artist = convertView.findViewById(R.id.artist);
        TextView name = convertView.findViewById(R.id.name);
        TextView summary = convertView.findViewById(R.id.summary);*/
        Entry currApp = applications.get(position);
        viewHolder.artist.setText(applications.get(position).getArtist());
        viewHolder.name.setText(applications.get(position).getName());
        viewHolder.summary.setText(applications.get(position).getSummary());
        return convertView;
    }
    private class viewHolder{
        private final TextView artist;
        private final TextView name;
        private final TextView summary;
        public viewHolder(View v){
            this.artist= v.findViewById(R.id.artist);
            this.name = v.findViewById(R.id.name);
            this.summary=v.findViewById(R.id.summary);

        }
    }
}
