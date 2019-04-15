package com.example.demoappmusic7;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import java.util.ArrayList;

public class FragmentListSong3 extends Fragment implements SearchView.OnQueryTextListener{

    ArrayList<Song> listSong;
    SongAdapter3 adapter3;
    SearchView editSearch;
    ListView list;

    public FragmentListSong3(){

    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        listSong = ((MainActivity) getActivity()).listSong;

        View view = (View) inflater.inflate(R.layout.fragment_list_song3,container,false);

        list = view.findViewById(R.id.listViewSong);
        adapter3 = new SongAdapter3(getActivity(),R.layout.song_row,listSong);
        list.setAdapter(adapter3);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getActivity(),"hello",Toast.LENGTH_SHORT).show();
                ((MainActivity) getActivity()).creatMedia(position);
                ((MainActivity) getActivity()).media.start();
            }
        });
        editSearch = (SearchView) view.findViewById(R.id.searchSongList);
        //editsearch1.setBackgroundColor(Color.BLUE);
        editSearch.setOnQueryTextListener(this);

        return view;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {

        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        Toast.makeText(getActivity(),"search Song",Toast.LENGTH_SHORT).show();
        String text = newText;
        adapter3.filter(text);

        //list.setVisibility(View.VISIBLE);
        return false;
    }
}
