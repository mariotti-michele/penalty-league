package penaltyleague.view;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class Sound {

    private boolean mute;

    private Clip cheeringSound;
    private Clip celebrationSound;
    private Clip delusionSound;
    private Clip anthemSound;
    private Clip kickSound;
    private Clip whistleSound;
    private Clip netSound;
    private Clip winnerSound;
    private Clip gameOverSound;

    public Sound() throws Exception{
        mute = false;

        File cheeringFile = ResourceManager.getSoundFile("cheering.wav");
        AudioFileFormat cheeringAudioFileFormat = AudioSystem.getAudioFileFormat(cheeringFile);
        AudioFormat cheeringAudioFormat = cheeringAudioFileFormat.getFormat();
        AudioInputStream cheeringAudioInputStream = null;

        File celebrationFile = ResourceManager.getSoundFile("celebration.wav");
        AudioFileFormat celebrationAudioFileFormat = AudioSystem.getAudioFileFormat(celebrationFile);
        AudioFormat celebrationAudioFormat = celebrationAudioFileFormat.getFormat();
        AudioInputStream celebrationAudioInputStream = null;

        File delusionFile = ResourceManager.getSoundFile("delusion.wav");
        AudioFileFormat delusionAudioFileFormat = AudioSystem.getAudioFileFormat(delusionFile);
        AudioFormat delusionAudioFormat = delusionAudioFileFormat.getFormat();
        AudioInputStream delusionAudioInputStream = null;

        File anthemFile = ResourceManager.getSoundFile("anthem.wav");
        AudioFileFormat anthemAudioFileFormat = AudioSystem.getAudioFileFormat(anthemFile);
        AudioFormat anthemAudioFormat = anthemAudioFileFormat.getFormat();
        AudioInputStream anthemAudioInputStream = null;

        File kickFile = ResourceManager.getSoundFile("kick.wav");
        AudioFileFormat kickAudioFileFormat = AudioSystem.getAudioFileFormat(kickFile);
        AudioFormat kickAudioFormat = kickAudioFileFormat.getFormat();
        AudioInputStream kickAudioInputStream = null;

        File whistleFile = ResourceManager.getSoundFile("whistle.wav");
        AudioFileFormat whistleAudioFileFormat = AudioSystem.getAudioFileFormat(whistleFile);
        AudioFormat whistleAudioFormat = whistleAudioFileFormat.getFormat();
        AudioInputStream whistleAudioInputStream = null;

        File netFile = ResourceManager.getSoundFile("net.wav");
        AudioFileFormat netAudioFileFormat = AudioSystem.getAudioFileFormat(netFile);
        AudioFormat netAudioFormat = netAudioFileFormat.getFormat();
        AudioInputStream netAudioInputStream = null;

        File winnerFile = ResourceManager.getSoundFile("winner.wav");
        AudioFileFormat winnerAudioFileFormat = AudioSystem.getAudioFileFormat(winnerFile);
        AudioFormat winnerAudioFormat = winnerAudioFileFormat.getFormat();
        AudioInputStream winnerAudioInputStream = null;

        File gameOverFile = ResourceManager.getSoundFile("gameOver.wav");
        AudioFileFormat gameOverAudioFileFormat = AudioSystem.getAudioFileFormat(gameOverFile);
        AudioFormat gameOverAudioFormat = gameOverAudioFileFormat.getFormat();
        AudioInputStream gameOverAudioInputStream = null;

        try {
            cheeringAudioInputStream = AudioSystem.getAudioInputStream(cheeringFile);
            int cheeringBufferSize = (int)cheeringAudioInputStream.getFrameLength() * cheeringAudioFormat.getFrameSize();
            DataLine.Info cheeringDataLineInfo = new DataLine.Info(Clip.class, cheeringAudioInputStream.getFormat(), cheeringBufferSize);

            celebrationAudioInputStream = AudioSystem.getAudioInputStream(celebrationFile);
            int celebrationBufferSize = (int)celebrationAudioInputStream.getFrameLength() * celebrationAudioFormat.getFrameSize();
            DataLine.Info celebrationDataLineInfo = new DataLine.Info(Clip.class, celebrationAudioInputStream.getFormat(), celebrationBufferSize);

            delusionAudioInputStream = AudioSystem.getAudioInputStream(delusionFile);
            int delusionBufferSize = (int)delusionAudioInputStream.getFrameLength() * delusionAudioFormat.getFrameSize();
            DataLine.Info delusionDataLineInfo = new DataLine.Info(Clip.class, delusionAudioInputStream.getFormat(), delusionBufferSize);

            anthemAudioInputStream = AudioSystem.getAudioInputStream(anthemFile);
            int anthemBufferSize = (int)anthemAudioInputStream.getFrameLength() * anthemAudioFormat.getFrameSize();
            DataLine.Info anthemDataLineInfo = new DataLine.Info(Clip.class, anthemAudioInputStream.getFormat(), anthemBufferSize);

            kickAudioInputStream = AudioSystem.getAudioInputStream(kickFile);
            int kickBufferSize = (int)kickAudioInputStream.getFrameLength() * kickAudioFormat.getFrameSize();
            DataLine.Info kickDataLineInfo = new DataLine.Info(Clip.class, kickAudioInputStream.getFormat(), kickBufferSize);

            whistleAudioInputStream = AudioSystem.getAudioInputStream(whistleFile);
            int whistleBufferSize = (int)whistleAudioInputStream.getFrameLength() * whistleAudioFormat.getFrameSize();
            DataLine.Info whistleDataLineInfo = new DataLine.Info(Clip.class, whistleAudioInputStream.getFormat(), whistleBufferSize);

            netAudioInputStream = AudioSystem.getAudioInputStream(netFile);
            int netBufferSize = (int)netAudioInputStream.getFrameLength() * netAudioFormat.getFrameSize();
            DataLine.Info netDataLineInfo = new DataLine.Info(Clip.class, netAudioInputStream.getFormat(), netBufferSize);

            winnerAudioInputStream = AudioSystem.getAudioInputStream(winnerFile);
            int winnerBufferSize = (int)winnerAudioInputStream.getFrameLength() * winnerAudioFormat.getFrameSize();
            DataLine.Info winnerDataLineInfo = new DataLine.Info(Clip.class, winnerAudioInputStream.getFormat(), winnerBufferSize);

            gameOverAudioInputStream = AudioSystem.getAudioInputStream(gameOverFile);
            int gameOverBufferSize = (int)gameOverAudioInputStream.getFrameLength() * gameOverAudioFormat.getFrameSize();
            DataLine.Info gameOverDataLineInfo = new DataLine.Info(Clip.class, gameOverAudioInputStream.getFormat(), gameOverBufferSize);

            if (!AudioSystem.isLineSupported(cheeringDataLineInfo) || !AudioSystem.isLineSupported(celebrationDataLineInfo)
                    || !AudioSystem.isLineSupported(delusionDataLineInfo) || !AudioSystem.isLineSupported(anthemDataLineInfo)
                    || !AudioSystem.isLineSupported(kickDataLineInfo) || !AudioSystem.isLineSupported(whistleDataLineInfo)
                    || !AudioSystem.isLineSupported(netDataLineInfo) || !AudioSystem.isLineSupported(winnerDataLineInfo)
                    || !AudioSystem.isLineSupported(gameOverDataLineInfo))
                throw new IOException("Error: the AudioSystem does not support the specified DataLine.Info object");

            try {
                cheeringSound = (Clip)AudioSystem.getLine(cheeringDataLineInfo);
                cheeringSound.open(cheeringAudioInputStream);
                cheeringSound.setFramePosition(cheeringSound.getFrameLength());

                celebrationSound = (Clip)AudioSystem.getLine(celebrationDataLineInfo);
                celebrationSound.open(celebrationAudioInputStream);
                celebrationSound.setFramePosition(celebrationSound.getFrameLength());

                delusionSound = (Clip)AudioSystem.getLine(delusionDataLineInfo);
                delusionSound.open(delusionAudioInputStream);
                delusionSound.setFramePosition(delusionSound.getFrameLength());

                anthemSound = (Clip)AudioSystem.getLine(anthemDataLineInfo);
                anthemSound.open(anthemAudioInputStream);
                anthemSound.setFramePosition(anthemSound.getFrameLength());

                kickSound = (Clip)AudioSystem.getLine(kickDataLineInfo);
                kickSound.open(kickAudioInputStream);
                kickSound.setFramePosition(kickSound.getFrameLength());

                whistleSound = (Clip)AudioSystem.getLine(whistleDataLineInfo);
                whistleSound.open(whistleAudioInputStream);
                whistleSound.setFramePosition(whistleSound.getFrameLength());

                netSound = (Clip)AudioSystem.getLine(netDataLineInfo);
                netSound.open(netAudioInputStream);
                netSound.setFramePosition(netSound.getFrameLength());

                winnerSound = (Clip)AudioSystem.getLine(winnerDataLineInfo);
                winnerSound.open(winnerAudioInputStream);
                winnerSound.setFramePosition(winnerSound.getFrameLength());

                gameOverSound = (Clip)AudioSystem.getLine(gameOverDataLineInfo);
                gameOverSound.open(gameOverAudioInputStream);
                gameOverSound.setFramePosition(gameOverSound.getFrameLength());
            }
            catch(LineUnavailableException lue) {
                throw new IOException("Error: a LineUnavailableException exception was thrown");
            }

        }
        catch(UnsupportedAudioFileException | IOException e) {
            e.printStackTrace();
        }
        finally {
            if (cheeringAudioInputStream != null)
                cheeringAudioInputStream.close();
            if (celebrationAudioInputStream != null)
                celebrationAudioInputStream.close();
            if (delusionAudioInputStream != null)
                delusionAudioInputStream.close();
            if (anthemAudioInputStream != null)
                anthemAudioInputStream.close();
            if (kickAudioInputStream != null)
                kickAudioInputStream.close();
            if (whistleAudioInputStream != null)
                whistleAudioInputStream.close();
            if (netAudioInputStream != null)
                netAudioInputStream.close();
            if (winnerAudioInputStream != null)
                winnerAudioInputStream.close();
            if (gameOverAudioInputStream != null)
                gameOverAudioInputStream.close();
        }
    }

    public void playCheeringSound(){
        if(!mute)
            cheeringSound.loop(Clip.LOOP_CONTINUOUSLY);
    }

    public void stopCheeringSound(){
        if(!mute)
            cheeringSound.stop();
    }

    public void playCelebrationSound(){
        if(!mute)
            celebrationSound.loop(1);
    }

    public void playDelusionSound(){
        if(!mute)
            delusionSound.loop(1);
    }

    public void playAnthemSound(){
        if(!mute)
            anthemSound.loop(1);
    }

    public void playKickSound(){
        if(!mute)
            kickSound.loop(1);
    }

    public void playWhistleSound(){
        if(!mute)
            whistleSound.loop(1);
    }

    public void playNetSound(){
        if(!mute)
            netSound.loop(1);
    }

    public void playWinnerSound(){
        if(!mute)
            winnerSound.loop(1);
    }

    public void playGameOverSound(){
        if(!mute)
            gameOverSound.loop(1);
    }

    public void close(){
        if(cheeringSound != null)
            cheeringSound.close();
        if(celebrationSound != null)
            celebrationSound.close();
        if(delusionSound != null)
            delusionSound.close();
        if(anthemSound != null)
            anthemSound.close();
        if(kickSound != null)
            kickSound.close();
        if(whistleSound != null)
            whistleSound.close();
        if(netSound != null)
            netSound.close();
        if(winnerSound != null)
            winnerSound.close();
        if(gameOverSound != null)
            gameOverSound.close();
    }

    public void changeMute(){
        mute = !mute;
    }
}
