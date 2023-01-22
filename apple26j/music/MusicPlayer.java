package apple26j.music;

import java.io.File;

import javax.sound.sampled.*;

public class MusicPlayer
{
	private static volatile boolean playing = false, flag1 = false, flag2 = false;
	private static volatile long microsecondPosition = 0;
	
	public static void playSong(String song)
	{
		File songFile = new File("songs" + File.separator + song + ".wav");
		
		new Thread(() ->
		{
			try (AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(songFile))
			{
				Clip clip = AudioSystem.getClip();
				clip.open(audioInputStream);
				
				clip.addLineListener(new LineListener()
				{
					@Override
					public void update(LineEvent lineEvent)
					{
						if (lineEvent.getType() == LineEvent.Type.STOP)
						{
							playing = false;
						}
					}
				});
				
				if (flag2)
				{
					clip.setMicrosecondPosition(microsecondPosition);
					flag2 = false;
				}
				
				playing = true;
				clip.start();
				
				while (playing)
				{
					if (flag1)
					{
						clip.stop();
						playing = false;
						flag1 = false;
						break;
					}
					
					else if (flag2)
					{
						microsecondPosition = clip.getMicrosecondPosition();
						clip.stop();
						playing = false;
						break;
					}
				}
			}
			
			catch (Exception e)
			{
				playing = false;
				flag1 = false;
				flag2 = false;
			}
		}).start();
	}
	
	public static void stopSong()
	{
		flag1 = true;
	}
	
	public static void pauseSong()
	{
		flag2 = true;
	}
	
	public static boolean isPlaying()
	{
		return playing;
	}
}
