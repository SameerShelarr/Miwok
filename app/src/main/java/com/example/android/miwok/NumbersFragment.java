package com.example.android.miwok;


import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class NumbersFragment extends Fragment {

    MediaPlayer mediaPlayer;
    AudioManager audioManager;
    AudioManager.OnAudioFocusChangeListener afChangeListener =
            new AudioManager.OnAudioFocusChangeListener() {
                public void onAudioFocusChange(int focusChange) {
                    if (focusChange == AudioManager.AUDIOFOCUS_LOSS) {
                        if(mediaPlayer != null){
                            mediaPlayer.pause();
                            mediaPlayer.seekTo(0);}
                    }
                    else if (focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT) {
                        if(mediaPlayer != null){
                            mediaPlayer.pause();
                            mediaPlayer.seekTo(0);}
                    } else if (focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK) {
                        if(mediaPlayer != null){
                            mediaPlayer.pause();
                            mediaPlayer.seekTo(0);}
                    } else if (focusChange == AudioManager.AUDIOFOCUS_GAIN) {
                        mediaPlayer.start();
                    }
                }
            };

    public NumbersFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.list_common, container, false);

        final ArrayList<Word> words = new ArrayList<>();
        words.add(new Word("lutti", "One", R.drawable.number_one, R.raw.number_one));
        words.add(new Word("otiiko", "Two", R.drawable.number_two, R.raw.number_two));
        words.add(new Word("tolookosu", "Three", R.drawable.number_three, R.raw.number_three));
        words.add(new Word("oyyisa", "Four", R.drawable.number_four, R.raw.number_four));
        words.add(new Word("massokka", "Five", R.drawable.number_five, R.raw.number_five));
        words.add(new Word("temmokka", "Six", R.drawable.number_six, R.raw.number_six));
        words.add(new Word("kenekaku", "Seven", R.drawable.number_seven, R.raw.number_seven));
        words.add(new Word("kawinta", "Eight", R.drawable.number_eight, R.raw.number_eight));
        words.add(new Word("wo’e", "Nine", R.drawable.number_nine, R.raw.number_nine));
        words.add(new Word("na’aacha", "Ten", R.drawable.number_ten, R.raw.number_ten));

        WordAdapter wordAdapter = new WordAdapter(getActivity(), words, R.color.category_numbers);

        ListView listView = (ListView) rootView.findViewById(R.id.list);
        listView.setAdapter(wordAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                releaseMediaPlayer();
                Word word = words.get(position);
                mediaPlayer = MediaPlayer.create(getContext(), word.getAudioResourceId());

                audioManager = (AudioManager) getActivity().getSystemService(Context.AUDIO_SERVICE);
                int result = audioManager.requestAudioFocus(afChangeListener, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN);

                if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                    mediaPlayer.start();
                }

                mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mediaPlayer) {
                        releaseMediaPlayer();
                        audioManager.abandonAudioFocus(afChangeListener);
                    }
                });
            }
        });

        return rootView;

    }

    private void releaseMediaPlayer() {
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        releaseMediaPlayer();
    }
}
