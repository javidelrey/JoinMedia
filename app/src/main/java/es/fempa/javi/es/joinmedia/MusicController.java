package es.fempa.javi.es.joinmedia;

import android.content.Context;
import android.view.View;
import android.widget.MediaController;

/**
 * Created by Miguel on 10/01/2018.
 */

public class MusicController extends MediaController {

    private MusicController controller;

    public MusicController(Context c){
        super(c);
        setController();
    }

    public void hide(){}

    private void setController(){
        controller = new MusicController(this.getContext());
        controller.setPrevNextListeners(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //playNext();
            }
        }, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // playPrev();
            }
        });

        controller.setMediaPlayer((MediaPlayerControl) this);
        controller.setAnchorView(findViewById(R.id.song_list));
        controller.setEnabled(true);
    }
}
