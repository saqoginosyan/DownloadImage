package com.example.sargis.imagedownloader.adapter;

import android.app.DialogFragment;
import android.app.FragmentManager;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.sargis.imagedownloader.model.ImageModel;
import com.example.sargis.imagedownloader.provider.ImageProvider;
import com.example.sargis.imagedownloader.fragmentdialog.ImageViewDialogFragment;
import com.example.sargis.imagedownloader.activity.MainActivity;
import com.example.sargis.imagedownloader.R;

import java.util.List;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ImageViewHolder> {
    private final String KEY = "key";
    private Context context;
    private List<ImageModel> list;
    private TextView textView;

    public ImageAdapter(Context context, List<ImageModel> imgUrlList, TextView textView) {
        this.context = context;
        this.list = imgUrlList;
        this.textView = textView;
    }

    @NonNull
    @Override
    public ImageAdapter.ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final LayoutInflater inflater =
                (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View view = inflater.inflate(R.layout.image_item, parent, false);
        return new ImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ImageAdapter.ImageViewHolder holder, final int position) {
        final ImageModel model = list.get(position);
        final FragmentManager fragmentTransaction = ((MainActivity) context).getFragmentManager();
        holder.title.setText(model.getImageTitle());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!model.isDownloaded()) {
                    textView.setText(model.getImageUrl());
                    ImageProvider.position = holder.getAdapterPosition();

                } else {
                    textView.setText(R.string.downloaded);
                    DialogFragment dialogFragment = new ImageViewDialogFragment();
                    dialogFragment.setStyle(DialogFragment.STYLE_NO_TITLE, R.style.DialogFragmentTheme);
                    dialogFragment.show(fragmentTransaction, KEY);
                    ImageProvider.position = holder.getAdapterPosition();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ImageViewHolder extends RecyclerView.ViewHolder {
        private TextView title;

        ImageViewHolder(final View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.image_title);
        }
    }
}
