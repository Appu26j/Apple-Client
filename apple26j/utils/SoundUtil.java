package apple26j.utils;

import java.io.File;

import javax.sound.sampled.*;

import apple26j.interfaces.MinecraftInterface;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.util.ResourceLocation;

public class SoundUtil implements MinecraftInterface
{
	public static synchronized void playClickSound()
	{
		mc.getSoundHandler().playSound(PositionedSoundRecord.create(new ResourceLocation("gui.button.press"), 1));
	}
	
	public static synchronized void playEnableSound()
	{
		File enableSound = new File(System.getProperty("java.io.tmpdir"), "enable.wav");
		
		if (enableSound.exists())
		{
			new Thread(() ->
			{
				try
				{
					Clip clip = AudioSystem.getClip();
					AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(enableSound);
					clip.open(audioInputStream);
					clip.start();
				}
				
				catch (Exception e)
				{
					playClickSound();
				}
			}).start();
		}
		
		else
		{
			playClickSound();
		}
	}
	
	public static synchronized void playDisableSound()
	{
		File disableSound = new File(System.getProperty("java.io.tmpdir"), "disable.wav");
		
		if (disableSound.exists())
		{
			new Thread(() ->
			{
				try
				{
					Clip clip = AudioSystem.getClip();
					AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(disableSound);
					clip.open(audioInputStream);
					clip.start();
				}
				
				catch (Exception e)
				{
					playClickSound();
				}
			}).start();
		}
		
		else
		{
			playClickSound();
		}
	}
}
