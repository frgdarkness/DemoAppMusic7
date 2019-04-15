package com.example.demoappmusic7;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import java.util.ArrayList;

public class FragmentListSong2 extends ListFragment implements SearchView.OnQueryTextListener {
    ArrayList<Song> listSong;
    SongAdapter2 adapter2;
    SearchView editSearch;
    ListView list;
    public FragmentListSong2(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        listSong = ((MainActivity) getActivity()).listSong;
        //ktra listview id android:id="@android:id/list"

        View view = (View) inflater.inflate(R.layout.fragment_list_song2,container,false);

        list = view.findViewById(R.id.listViewSong);
        adapter2 = new SongAdapter2(getActivity(),R.layout.song_row,listSong);
        list.setAdapter(adapter2);

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
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        Toast.makeText(getActivity(),"HELLO SONG",Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onQueryTextSubmit(String query) {

        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        Toast.makeText(getActivity(),"search Song",Toast.LENGTH_SHORT).show();
        String text = newText;
        adapter2.filter(text);

        //list.setVisibility(View.VISIBLE);

        return false;
    }
}
