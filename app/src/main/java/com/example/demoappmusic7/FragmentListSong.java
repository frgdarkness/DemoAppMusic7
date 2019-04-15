package com.example.demoappmusic7;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class FragmentListSong extends ListFragment {
    ArrayList<Song> songArrayList;
    SongAdapter2 adapter2;

    public FragmentListSong(){

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        songArrayList = ((MainActivity) getActivity()).listSong;
        adapter2 = new SongAdapter2(getActivity(),R.layout.song_row,songArrayList);
        setListAdapter(adapter2);
        final View view = inflater.inflate(R.layout.fragment_list_song2,container,false);

        return view;
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {

        super.onListItemClick(l, v, position, id);
        Toast.makeText(getActivity(),"hello",Toast.LENGTH_SHORT).show();
        ((MainActivity) getActivity()).creatMedia(position);
        ((MainActivity) getActivity()).media.start();
    }


}
