package com.example.android.tasheehlistview;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class WordAdapter extends ArrayAdapter<Ayah> {

    private final Activity context;
    private final ArrayList<Ayah> words;

    public WordAdapter(Activity context, ArrayList<Ayah> objects) {
        super(context, R.layout.list_item, objects);
        this.context = context;
        this.words = objects;
    }

    //@androidx.annotation.NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        View rowView = convertView;
        if (rowView == null) {

            LayoutInflater inflater = context.getLayoutInflater();
            rowView = inflater.inflate(R.layout.list_item, null, true);
        }

        TextView surahname = (TextView) rowView.findViewById(R.id.surah_name);
        TextView ayahno = (TextView) rowView.findViewById(R.id.ayah_no);
        TextView ayahtext = (TextView) rowView.findViewById(R.id.ayah_text);

        Ayah currentWord = getItem(position);
        surahname.setText(currentWord.getSurahName());
        ayahno.setText(currentWord.getAyahNo());
        ayahtext.setText(currentWord.getAyahText());


        return rowView;
    }
}
