package ir.ceit.resa.view.adapter;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import ir.ceit.resa.R;
import ir.ceit.resa.model.Announcement;
import ir.ceit.resa.service.SolarCalendar;

public class AnnouncementAdapter extends
        RecyclerView.Adapter<AnnouncementAdapter.ViewHolder> {


    private List<Announcement> announcements;

    public AnnouncementAdapter(List<Announcement> announcements) {
        this.announcements = announcements;
    }

    @NonNull
    @NotNull
    @Override
    public AnnouncementAdapter.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup viewGroup, int i) {
        Context context = viewGroup.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View announcementView = inflater.inflate(R.layout.announcement_item, viewGroup, false);
        AnnouncementAdapter.ViewHolder viewHolder = new AnnouncementAdapter.ViewHolder(announcementView);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull AnnouncementAdapter.ViewHolder viewHolder, int i) {
        Announcement announcement = announcements.get(i);

        TextView announcementText = viewHolder.announcementText;
        announcementText.setText(announcement.getMessage());

        TextView writerAndDate = viewHolder.announcementWriterDate;
        String writerDate = SolarCalendar.getShamsiDate(announcement.getCreationDate()) + " ،" + announcement.getWriter();
        writerAndDate.setText(writerDate);

        LinearLayout.LayoutParams announcementParams = new LinearLayout.
                LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                android.view.ViewGroup.LayoutParams.WRAP_CONTENT);
        announcementParams.gravity = Gravity.CENTER;

        viewHolder.parentLayout.setLayoutParams(announcementParams);

        viewHolder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("Announcement clicked: item " + i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return announcements.size();
    }


    public void clear() {
        announcements.clear();
        notifyDataSetChanged();
    }

    public void addAll(List<Announcement> list) {
        announcements.addAll(list);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public LinearLayout parentLayout;
        public ImageView pin;
        public TextView announcementText;
        public TextView announcementWriterDate;

        public ViewHolder(View itemView) {
            super(itemView);

            parentLayout = itemView.findViewById(R.id.announcement_parent_layout);
            pin = itemView.findViewById(R.id.pin);
            announcementText = itemView.findViewById(R.id.announcement_tv);
            announcementWriterDate = itemView.findViewById(R.id.writer_date_tv);
        }
    }
}