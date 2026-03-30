package utils;

import java.net.URL;
import javax.sound.sampled.*;

public class SoundManager {
    private Clip bgmClip;

    public void playBGM(String fileName) {
        try {
            URL url = getClass().getResource("/resources/" + fileName);
            if (url != null) {
                AudioInputStream audioIn = AudioSystem.getAudioInputStream(url);
                bgmClip = AudioSystem.getClip();
                bgmClip.open(audioIn);
                bgmClip.loop(Clip.LOOP_CONTINUOUSLY); 
                bgmClip.start();
            } else {
                System.err.println("Không tìm thấy file: " + fileName);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void stopBGM() {
        if (bgmClip != null && bgmClip.isRunning()) {
            bgmClip.stop();
        }
    }
    
    public void resumeBGM() {
        if (bgmClip != null && !bgmClip.isRunning()) {
            bgmClip.start();
        }
    }

public static void playOneShot(String fileName) {
    new Thread(() -> {
        try {
            URL url = SoundManager.class.getClassLoader().getResource("resources/" + fileName);
            
            if (url == null) {
                url = SoundManager.class.getResource("/resources/" + fileName);
            }

            if (url != null) {
                AudioInputStream audioIn = AudioSystem.getAudioInputStream(url);
                Clip clip = AudioSystem.getClip();
                clip.open(audioIn);
                clip.start();
                
                clip.addLineListener(event -> {
                    if (event.getType() == LineEvent.Type.STOP) {
                        clip.close();
                    }
                });
            } else {
                System.err.println("Không tìm thấy file ăn điểm: " + fileName);
            }
        } catch (Exception e) {
            System.err.println("Lỗi phát âm thanh hiệu ứng: " + e.getMessage());
        }
    }).start(); 
}
} 
