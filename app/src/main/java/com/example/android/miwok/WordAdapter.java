package com.example.android.miwok;

import android.app.Activity;
import android.media.MediaPlayer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import java.util.ArrayList;

public class WordAdapter extends ArrayAdapter<Word> {

    private int mBackgroundColor;
    MediaPlayer mediaPlayer;

    public WordAdapter(Activity context, ArrayList<Word> words, int backgroundColor) {
        super(context, 0, words);
        mBackgroundColor = backgroundColor;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View listItemView = convertView;
        if(listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.simple_list_view_custom, parent, false);
        }

        Word currentWord = getItem(position);

        TextView textView1 = listItemView.findViewById(R.id.miwok_text_view);
        if (currentWord != null) {
            textView1.setText(currentWord.getMiwokTranslation());
        }

        TextView textView2 = listItemView.findViewById(R.id.default_text_view);
        if (currentWord != null) {
            textView2.setText(currentWord.getDefaultTranslation());
        }

        ImageView imageView = listItemView.findViewById(R.id.image);
        if (currentWord != null) {
            if (currentWord.hasImage()) {
                imageView.setImageResource(currentWord.getImageResourceId());
                imageView.setVisibility(View.VISIBLE);
            } else {
                imageView.setVisibility(View.GONE);
            }
        }

        int color = listItemView.getResources().getColor(mBackgroundColor);
        LinearLayout linearLayout = listItemView.findViewById(R.id.text_container);
        linearLayout.setBackgroundColor(color);

        return listItemView;
    }
}
