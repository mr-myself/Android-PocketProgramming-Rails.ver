package ffab0.pocketprogramming.util;

import android.media.AudioManager;

import ffab0.pocketprogramming.models.Common;

public class SoundEffect {

    static public boolean check() {
        AudioManager audioManager = (AudioManager) Common.getContext().getSystemService(Common.getContext().AUDIO_SERVICE);
        int ringerMode = audioManager.getRingerMode();

        if(ringerMode == AudioManager.RINGER_MODE_SILENT || ringerMode == AudioManager.RINGER_MODE_VIBRATE ) {
           return false;
        } else {
            return true;
        }
    }
}
