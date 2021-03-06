package com.example.demoappmusic7;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.gauravk.audiovisualizer.visualizer.BarVisualizer;
import com.gauravk.audiovisualizer.visualizer.WaveVisualizer;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;
import com.sothree.slidinguppanel.SlidingUpPanelLayout.PanelState;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private SlidingUpPanelLayout mLayout;
    TextView txtTitle, txtArtist, txtTimeSong, txtTimeTotal,txtTitleMain,txtArtTistMain;
    ImageButton btnNext, btnPrev, btnPlay, btnRepeat, btnRandom, btnQueue,btnPlayMain;
    ImageView imgViewCover,imgViewCoverMain,imgTint;
    ListView listViewLyric;
    ArrayList<Song> listSong;
    SeekBar sbSong;
    MediaPlayer media;
    ViewPager viewPager;
    PagerAdapter2 adapter;
    RelativeLayout relative1,relative2,relativeCover,relativeWave;
    BarVisualizer barVisual;
    WaveVisualizer waveVisualizer;
    List<RowLyric> rowLyricList;
    List<Integer> timeLyricList;
    List<String> lyricList;
    LyricAdapter lyricAdapter;
    int sessionID;
    int position =0;
    int ktQueue = 0;
    int ktPlaySong = 0;
    int checkRepeat = 1;        // 1: ko lap - 2: lap tat ca - 3: lap 1 bai
    int checkRandom = 1;        // 1: ko random - 2: random
    int tintCover = 0;
    int checkCoverView = 1;
    int checkWave=1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mLayout = (SlidingUpPanelLayout) findViewById(R.id.activity_main);
        anhXa();
        addSong();
        viewPager = findViewById(R.id.pager);
        adapter = new PagerAdapter2(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Play",Toast.LENGTH_SHORT).show();
            }
        });
        addDataLyric();
        creatMedia();
        btnPlayMain.setImageResource(R.drawable.ic_play_main);
        btnPlay.setImageResource(R.drawable.ic_play2);
        listViewLyric.setVisibility(View.INVISIBLE);
        relativeWave.setVisibility(View.INVISIBLE);
        waveVisualizer.setVisibility(View.INVISIBLE);
        //media.start();
        //relative2.setVisibility(View.INVISIBLE);

        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(media.isPlaying()){
                    media.pause();
                    btnPlay.setImageResource(R.drawable.ic_play2);
                    btnPlayMain.setImageResource(R.drawable.ic_play_main);
                }else {
                    //ktra neu chua bat media bat truoc moi start
                    if(media==null)
                        creatMedia();
                    media.start();
                    btnPlay.setImageResource(R.drawable.ic_pause2);
                    btnPlayMain.setImageResource(R.drawable.ic_pause_main);
                }
            }
        });

        btnPlayMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(media.isPlaying()){
                    media.pause();
                    btnPlay.setImageResource(R.drawable.ic_play2);
                    btnPlayMain.setImageResource(R.drawable.ic_play_main);
                }else {
                    //ktra neu chua bat media bat truoc moi start
                    if(media==null)
                        creatMedia();
                    media.start();
                    btnPlay.setImageResource(R.drawable.ic_pause2);
                    btnPlayMain.setImageResource(R.drawable.ic_pause_main);
                }
            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                media.stop();
                nextSong();
            }
        });

        btnPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                media.stop();
                backSong();
            }
        });

        sbSong.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

                media.seekTo(sbSong.getProgress());
                setBooleanLyric();
            }
        });

        btnQueue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkCoverView==3)
                    checkCoverView=1;
                else checkCoverView++;
                switch (checkCoverView){
                    case 1:
                        listViewLyric.setVisibility(View.INVISIBLE);
                        relativeWave.setVisibility(View.INVISIBLE);
                        //checkCoverView=2;
                        break;
                    case 2:
                        listViewLyric.setVisibility(View.VISIBLE);
                        relativeWave.setVisibility(View.INVISIBLE);
                        //checkCoverView=3;
                        break;
                    case 3:

                        listViewLyric.setVisibility(View.INVISIBLE);
                        relativeWave.setVisibility(View.VISIBLE);
                        //checkCoverView=1;
                        break;
                }

            }
        });

        btnRepeat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkRepeat++;
                if(checkRepeat>3) checkRepeat = 1;
                switch (checkRepeat){
                    case 1:btnRepeat.setImageResource(R.drawable.ic_non_repeat);
                        break;

                    case 2:btnRepeat.setImageResource(R.drawable.ic_repeat2);
                        break;

                    case 3:btnRepeat.setImageResource(R.drawable.ic_repeat_one_2);
                }

            }
        });

        btnRandom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkRandom==1){
                    checkRandom=2;
                    btnRandom.setImageResource(R.drawable.ic_random2);
                }
                else{
                    checkRandom = 1;
                    btnRandom.setImageResource(R.drawable.ic_non_random);
                }
            }
        });


        relativeCover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listViewLyric.getVisibility()==View.VISIBLE) {
                    listViewLyric.setVisibility(View.INVISIBLE);
                    Toast.makeText(getApplicationContext(),"invisible",Toast.LENGTH_SHORT).show();

                }
                else {
                    listViewLyric.setVisibility(View.VISIBLE);
                    Toast.makeText(getApplicationContext(),"visible",Toast.LENGTH_SHORT).show();
                }
            }
        });

        txtTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, ""+media.getCurrentPosition(),Toast.LENGTH_SHORT).show();
            }
        });



        barVisual.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                barVisual.release();
                if(sessionID!=-1)
                    waveVisualizer.setAudioSessionId(sessionID);
                barVisual.setVisibility(View.INVISIBLE);
                waveVisualizer.setVisibility(View.VISIBLE);
                checkWave=2;
                return false;
            }
        });

        waveVisualizer.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                waveVisualizer.release();
                if(sessionID!=-1)
                    barVisual.setAudioSessionId(sessionID);
                waveVisualizer.setVisibility(View.INVISIBLE);
                barVisual.setVisibility(View.VISIBLE);
                checkWave=1;
                return false;
            }
        });
    }

    public void anhXa(){
        txtArtist = (TextView) findViewById(R.id.textSongArtist);
        txtTitle = (TextView) findViewById(R.id.textSongTitle);
        txtTimeSong = (TextView) findViewById(R.id.textTimeSongNow);
        txtTimeTotal = (TextView) findViewById(R.id.textTimeSongTotal);
        sbSong = (SeekBar) findViewById(R.id.seekBarSong);
        btnNext = (ImageButton) findViewById(R.id.buttonNext);
        btnPrev = (ImageButton) findViewById(R.id.buttonPrev);
        btnPlay = (ImageButton) findViewById(R.id.buttonPlay);
        btnRandom = (ImageButton) findViewById(R.id.buttonRandom);
        btnRepeat = (ImageButton) findViewById(R.id.buttonRepeat);
        btnQueue = (ImageButton) findViewById(R.id.buttonQueue);
        //imgViewCover = (ImageView) findViewById(R.id.imageViewCover);
        listSong = new ArrayList<>();
        txtTitleMain = (TextView) findViewById(R.id.textSongTitleMain);
        txtArtTistMain = (TextView) findViewById(R.id.textSongArtistMain);
        imgViewCoverMain = (ImageView) findViewById(R.id.imageViewCoverMain);
        btnPlayMain = (ImageButton) findViewById(R.id.buttonPlayMain);
        //relative2 = (RelativeLayout) findViewById(R.id.relative2);
        relativeCover = (RelativeLayout) findViewById(R.id.relativeCover);
        listViewLyric = (ListView) findViewById(R.id.listViewLyric);
        barVisual = (BarVisualizer) findViewById(R.id.bar);
        waveVisualizer = (WaveVisualizer) findViewById(R.id.wave);
        relativeWave = (RelativeLayout) findViewById(R.id.relativeWave);
    }

    @Override
    public void onBackPressed() {
        if (mLayout != null &&
                (mLayout.getPanelState() == PanelState.EXPANDED || mLayout.getPanelState() == PanelState.ANCHORED)) {
            mLayout.setPanelState(PanelState.COLLAPSED);
        } else {
            super.onBackPressed();
        }
    }


    public void creatMedia(){
        ktPlaySong = 1;
        media = MediaPlayer.create(MainActivity.this,listSong.get(position).getFile());
        txtTitle.setText(listSong.get(position).getTitle());
        txtArtist.setText(listSong.get(position).getArtist());
        relativeCover.setBackgroundResource(listSong.get(position).getCover());
        //imgViewCover.setImageResource(listSong.get(position).getCover());
        txtTitleMain.setText(listSong.get(position).getTitle());
        txtArtTistMain.setText(listSong.get(position).getArtist());
        imgViewCoverMain.setImageResource(listSong.get(position).getCover());
        relativeWave.setBackgroundResource(listSong.get(position).getCover());
        setTimeTotal();
        updateTimeSong();
        btnPlay.setImageResource(R.drawable.ic_pause2);
        btnPlayMain.setImageResource(R.drawable.ic_pause_main);
        addDataLyric();
        sessionID = media.getAudioSessionId();
        if(sessionID!=-1){
            if(checkWave==1)
                barVisual.setAudioSessionId(sessionID);
            else
                waveVisualizer.setAudioSessionId(sessionID);
        }
    }

    public void creatMedia(int pos){
        if(media!=null)
            media.stop();
        ktPlaySong = 1;
        media = MediaPlayer.create(MainActivity.this,listSong.get(pos).getFile());
        txtTitle.setText(listSong.get(pos).getTitle());
        txtArtist.setText(listSong.get(pos).getArtist());
        //imgViewCover.setImageResource(listSong.get(pos).getCover());
        relativeCover.setBackgroundResource(listSong.get(pos).getCover());
        relativeWave.setBackgroundResource(listSong.get(position).getCover());
        txtTitleMain.setText(listSong.get(pos).getTitle());
        txtArtTistMain.setText(listSong.get(pos).getArtist());
        imgViewCoverMain.setImageResource(listSong.get(pos).getCover());
        setTimeTotal();
        position = pos;
        updateTimeSong();
        btnPlay.setImageResource(R.drawable.ic_pause2);
        btnPlayMain.setImageResource(R.drawable.ic_pause_main);
        addDataLyric();
        sessionID = media.getAudioSessionId();
        if(sessionID!=-1){
            if(checkWave==1)
                barVisual.setAudioSessionId(sessionID);
            else
                waveVisualizer.setAudioSessionId(sessionID);
        }
    }

    public void nextSong(){
        if(checkRandom==2) {
            Random rd = new Random();
            position = rd.nextInt(listSong.size());
        }else{
            if(position==(listSong.size()-1))
                position = 0;
            else
                position++;
        }
        creatMedia();
        media.start();
    }

    public void backSong(){
        if(checkRandom==2) {
            Random rd = new Random();
            position = rd.nextInt(listSong.size());
        }else{
            if(position==0)
                position = listSong.size()-1;
            else
                position--;
        }

        creatMedia();
        media.start();
    }

    private void setTimeTotal(){
        SimpleDateFormat timeSong = new SimpleDateFormat("mm:ss");
        txtTimeTotal.setText(timeSong.format(media.getDuration())+"");
        sbSong.setMax(media.getDuration());
    }

    public void updateTimeSong(){
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                SimpleDateFormat timeSong = new SimpleDateFormat("mm:ss");
                txtTimeSong.setText(timeSong.format(media.getCurrentPosition()));
                sbSong.setProgress(media.getCurrentPosition());
                //ktra bai hat ket thuc -> tiep tuc phat bai tiep theo


                for(int i=0; i<timeLyricList.size(); i++){
                    if(timeLyricList.get(i)>=(media.getCurrentPosition()/1000) && timeLyricList.get(i)<(media.getCurrentPosition()/1000)+2 ){
                        RowLyric r1 = rowLyricList.get(i);
                        if(i>0){
                            RowLyric r2 = rowLyricList.get(i-1);
                            r2.setCheckShow(false);
                            rowLyricList.remove(i-1);
                            rowLyricList.add(i-1,r2);
                        }
                        r1.setCheckShow(true);

                        rowLyricList.remove(i);
                        rowLyricList.add(i,r1);
                        lyricAdapter.notifyDataSetChanged();
                        break;
                    }
                }

                if(position==3) {
                    //Toast.makeText(MainActivity.this, "End", Toast.LENGTH_SHORT).show();
                    handler.removeCallbacksAndMessages(null);
                }
                media.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        if(checkRepeat!=3) {        //che do lap lai all
                            if(checkRandom==1) {    //ko random
                                position++;
                                if (position > (listSong.size() - 1))
                                    position = 0;
                            }
                            else {
                                Random rd = new Random();
                                position = rd.nextInt(listSong.size());
                            }
                        }
                        // truong hop con lai la lap 1 => position ko đổi
                        media.stop();
                        creatMedia();
                        media.start();
                        btnPlay.setImageResource(R.drawable.ic_pause2);
                    }
                });

                handler.postDelayed(this,500);
            }
        },100);
    }

    public void addSong(){
        listSong.add(new Song("Ashes","Celine Dion",R.raw.ashes_celine_dion,R.drawable.ashes_pic_png,R.string.ashes_lyric));
        listSong.add(new Song("Perfect","Ed Sheeran",R.raw.perfect_ed_sheeran,R.drawable.perfect_pic_png,R.string.perfect_lyric));
        listSong.add(new Song("When i look at you","Miley Cyirus",R.raw.when_i_look_at_you,R.drawable.when_i_look_at_you_pic_png,R.string.when_i_look_at_you_lyric));
        listSong.add(new Song("A sky full of star","Coldplay",R.raw.a_sky_full_of_star_coldplay,R.drawable.a_sky_full_of_star_coldplay_pic,R.string.a_sky_full_of_star_lyric));
        listSong.add(new Song("Dark horse","Katy Perry",R.raw.dark_horse_katy_perry,R.drawable.dark_horse_katy_perry_pic,R.string.no_lyric));
        listSong.add(new Song("Love someone","Lukas Graham",R.raw.love_someone_lukas_graham,R.drawable.love_someone_lukas_graham_pic,R.string.no_lyric));
        listSong.add(new Song("May it be","Enya",R.raw.may_it_be_enya,R.drawable.may_it_be_enya_pic,R.string.may_it_be));
        listSong.add(new Song("Shallow","Lady Gaga ft Cooper",R.raw.shallow_ladygaga_cooper,R.drawable.shallow_ladygaga_cooper_pic,R.string.no_lyric));
        listSong.add(new Song("Stitches","Shawn Mendes",R.raw.stitches_shawn_mendes,R.drawable.stitches_shawn_mendes_pic,R.string.no_lyric));
        listSong.add(new Song("Take me home","Jess Glynne",R.raw.take_me_home_jess_glynne,R.drawable.take_me_home_jess_glynne_pic,R.string.no_lyric));
        listSong.add(new Song("Thanks U! Next!","Ariana",R.raw.thank_u_next_ariana,R.drawable.thank_u_next_ariana_pic,R.string.no_lyric));
        listSong.add(new Song("Why not me?","Enrique",R.raw.why_not_me_enrique,R.drawable.why_not_me_enrique_pic,R.string.no_lyric));
    }

    public void addDataLyric(){
        rowLyricList = new ArrayList<>();
        lyricList = new ArrayList<>();
        timeLyricList = new ArrayList<>();
        String checkS = getString(listSong.get(position).getLyric());
        //Toast.makeText(MainActivity.this,checkS,Toast.LENGTH_SHORT).show();

        if(checkS.equals("NoLyric")){
            rowLyricList.add(new RowLyric("No Lyric",false));
            lyricAdapter = new LyricAdapter(this,R.layout.row_lyric,rowLyricList);
            listViewLyric.setAdapter(lyricAdapter);
        }else {
            String[] mangStr = getString(listSong.get(position).getLyric()).split("\\_");
            for (int i = 0; i < mangStr.length; i++) {
                String temp = mangStr[i];
                int post = temp.indexOf('-');
                String s1, s2;
                s1 = temp.substring(0, post);
                s2 = temp.substring(post + 1, temp.length() );
                timeLyricList.add(Integer.parseInt(s1));
                lyricList.add(s2);
                rowLyricList.add(new RowLyric(s2, false));
            }
            lyricAdapter = new LyricAdapter(this, R.layout.row_lyric, rowLyricList);
            listViewLyric.setAdapter(lyricAdapter);
        }

    }

    public void setBooleanLyric(){
        for(int i=0; i<rowLyricList.size(); i++){
            RowLyric r1 = rowLyricList.get(i);
            if(r1.isCheckShow()==true){
                r1.setCheckShow(false);
                rowLyricList.remove(i);
                rowLyricList.add(i,r1);
            }
        }
        adapter.notifyDataSetChanged();
    }
}
