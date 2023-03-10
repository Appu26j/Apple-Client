package apple26j;

import java.io.*;
import java.net.URL;
import java.nio.channels.*;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.stream.Collectors;

import org.apache.commons.io.FileUtils;
import org.lwjgl.input.Keyboard;

import com.google.common.eventbus.*;

import apple26j.events.*;
import apple26j.events.gui.EventRender;
import apple26j.events.mc.EventKey;
import apple26j.gui.*;
import apple26j.interfaces.MinecraftInterface;
import apple26j.mods.*;
import apple26j.settings.SettingsManager;
import net.minecraft.util.ResourceLocation;

public enum Apple implements MinecraftInterface
{
	CLIENT;
	
	private DragGUI dragGUI;
	private MusicGUI musicGUI;
	private EventBus eventBus;
	private ModsManager modsManager;
	private EventsManager eventsManager;
	private SettingsManager settingsManager;
	private UpdateCheckThread updateCheckThread;
	public static final double CLIENT_VERSION = 1.93;
	public static volatile boolean songsDownloading = false;
	
	public void init()
	{
		(this.updateCheckThread = new UpdateCheckThread()).start();
		this.settingsManager = new SettingsManager();
		this.eventBus = new EventBus("Apple Client");
		this.eventsManager = new EventsManager();
		this.modsManager = new ModsManager();
		this.musicGUI = new MusicGUI();
		this.dragGUI = new DragGUI();
		this.eventBus.register(this);
		this.extractSoundFiles();
		this.extractUpdater();
		this.extractSongs();
	}
	
	@Subscribe
	public void onKey(EventKey e)
	{
		if (e.getKey() == Keyboard.KEY_RSHIFT)
		{
			// Displays DragGUI
			mc.displayGuiScreen(this.dragGUI);
		}
		
		else if (e.getKey() == Keyboard.KEY_M)
		{
			// Displays MusicGUI
			mc.displayGuiScreen(this.musicGUI);
		}
	}
	
	@Subscribe
	public void onRender(EventRender e)
	{
		if (!(mc.currentScreen instanceof DragGUI))
		{
			for (Mod mod : this.modsManager.getMods(Category.ALL).stream().filter(mod -> mod.hasGUI() && mod.isEnabled()).collect(Collectors.toCollection(ArrayList::new)))
			{
				// Render mods' GUI
				mod.onRender();
			}
		}
	}
	
	public EventBus getEventBus()
	{
		return this.eventBus;
	}
	
	public ModsManager getModsManager()
	{
		return this.modsManager;
	}
	
	public EventsManager getEventsManager()
	{
		return this.eventsManager;
	}
	
	public SettingsManager getSettingsManager()
	{
		return this.settingsManager;
	}
	
	public void postEvent(Event e)
	{
		this.eventBus.post(e);
	}
	
	public Event getEvent(Class classs)
	{
		return this.eventsManager.getEvent(classs);
	}
	
	public UpdateCheckThread getUpdateCheckThread()
	{
		return this.updateCheckThread;
	}
	
	public void extractSoundFiles()
	{
		File enableSound = new File("sounds" + File.separator + "enable.wav");
		File disableSound = new File("sounds" + File.separator + "disable.wav");
		
		if (!(enableSound.exists() || disableSound.exists()))
		{
			try
			{
				enableSound.getParentFile().mkdirs();
				enableSound.createNewFile();
				disableSound.getParentFile().mkdirs();
				disableSound.createNewFile();
				InputStream inputStream1 = mc.getResourceManager().getResource(new ResourceLocation("sounds/enable.wav")).getInputStream();
				InputStream inputStream2 = mc.getResourceManager().getResource(new ResourceLocation("sounds/disable.wav")).getInputStream();
				
				try (BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(Files.newOutputStream(enableSound.toPath())))
		        {
		            byte [] bytes = new byte[4096];
		            int read;

		            while ((read = inputStream1.read(bytes)) != -1)
		            {
		                bufferedOutputStream.write(bytes, 0, read);
		            }
		        }
				
				try (BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(Files.newOutputStream(disableSound.toPath())))
		        {
		            byte [] bytes = new byte[4096];
		            int read;

		            while ((read = inputStream2.read(bytes)) != -1)
		            {
		                bufferedOutputStream.write(bytes, 0, read);
		            }
		        }
			}
			
			catch (Exception e)
			{
				;
			}
		}
	}
	
	public void extractSongs()
	{
		File songs = new File("songs");
		
		try
		{
			if (!songs.exists())
			{
				songs.mkdirs();
			}
			
			new Thread(() ->
			{
				try
				{
					songsDownloading = true;
					
					for (String song : MusicGUI.getSongsList())
					{
						File songFile = new File(songs, song + ".wav");
						
						if (songFile.exists())
						{
							continue;
						}
						
						song = song.replaceAll(" ", "%20").replaceAll("&", "%26");
						songFile.createNewFile();
						URL website = new URL("https://github.com/Appu26j/Apple-Client-Songs/raw/main/" + song + ".wav");
						FileUtils.copyURLToFile(website, songFile);
					}
					
					songsDownloading = false;
				}
				
				catch (Exception e)
				{
					songsDownloading = false;
				}
			}).start();
		}
		
		catch (Exception e)
		{
			;
		}
	}
	
	public void extractUpdater()
	{
		File updater = new File("updater.jar");
		
		if (!updater.exists())
		{
			try
			{
				updater.createNewFile();
				InputStream inputStream = mc.getResourceManager().getResource(new ResourceLocation("updater/updater.jar")).getInputStream();
				
				try (BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(Files.newOutputStream(updater.toPath())))
		        {
		            byte [] bytes = new byte[4096];
		            int read;

		            while ((read = inputStream.read(bytes)) != -1)
		            {
		                bufferedOutputStream.write(bytes, 0, read);
		            }
		        }
			}
			
			catch (Exception e)
			{
				;
			}
		}
	}
	
	public void shutdown()
	{
		this.eventBus.unregister(this);
	}
}
