package antopower.musicplayer;


import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;


/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment {

    final String MEDIA_PATH = Environment.getExternalStorageDirectory().getPath() + "/";
    private ArrayList<HashMap<String, String>> songsList = new ArrayList<>();
    audioPlayer audioPlayer = new audioPlayer();

    public MainFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        ArrayList<HashMap<String, String>> array = getPlayList();

        ListView lv = (ListView) view.findViewById(R.id.listView);
        setListViewOnItemClickListener(lv);

        ImageButton playPauseButton = (ImageButton) getActivity().findViewById(R.id.player_playPause);
        playPauseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                audioPlayer.pause();
            }
        });

        try {
            SimpleAdapter adapter = new SimpleAdapter(getActivity(), array,
                    R.layout.list_item, new String[]{"songTitle"}, new int[]{
                    R.id.songTitle});

            lv.setAdapter(adapter);
        } catch (Exception e) {
            //do something clever with the exception
            System.out.println(e.getMessage());
        }

        // Inflate the layout for this fragment
        return view;
    }

    private void setListViewOnItemClickListener(final ListView lv) {
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            @SuppressWarnings("unchecked")
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                HashMap<String, String> songMetadata = (HashMap<String, String>) lv.getItemAtPosition(position);
                audioPlayer.play(songMetadata);
                TextView songTitleElement = (TextView) getActivity().findViewById(R.id.player_songTitle);
                songTitleElement.setText(songMetadata.get("songTitle"));
            }
        });
    }

    /**
     * Function to read all mp3 files and store the details in
     * ArrayList
     */
    public ArrayList<HashMap<String, String>> getPlayList() {
        System.out.println(MEDIA_PATH);
        if (MEDIA_PATH != null) {
            File home = new File(MEDIA_PATH);
            File[] listFiles = home.listFiles();
            if (listFiles != null && listFiles.length > 0) {
                for (File file : listFiles) {
                    System.out.println(file.getAbsolutePath());
                    if (file.isDirectory()) {
                        scanDirectory(file);
                    } else {
                        addSongToList(file);
                    }
                }
            }
        }
        // return songs list array
        return songsList;
    }

    private void scanDirectory(File directory) {
        if (directory != null) {
            File[] listFiles = directory.listFiles();
            if (listFiles != null && listFiles.length > 0) {
                for (File file : listFiles) {
                    if (file.isDirectory()) {
                        scanDirectory(file);
                    } else {
                        addSongToList(file);
                    }

                }
            }
        }
    }

    private void addSongToList(File song) {
        String mp3Pattern = ".mp3";
        if (song.getName().endsWith(mp3Pattern)) {
            HashMap<String, String> songMap = new HashMap<>();
            songMap.put("songTitle", song.getName().substring(0, (song.getName().length() - 4)));
            songMap.put("songPath", song.getPath());
            songMap.put("songPlaylist", "All");

            // Adding each song to SongList
            songsList.add(songMap);
        }
    }

}
