package apple26j;

import java.io.*;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.stream.Collectors;

import org.lwjgl.input.Keyboard;

import com.google.common.eventbus.*;

import apple26j.events.*;
import apple26j.events.gui.EventRender;
import apple26j.events.mc.EventKey;
import apple26j.gui.DragGUI;
import apple26j.interfaces.MinecraftInterface;
import apple26j.mods.*;
import net.minecraft.util.ResourceLocation;

public enum Apple implements MinecraftInterface
{
	CLIENT;
	
	private DragGUI dragGUI;
	private EventBus eventBus;
	private ModsManager modsManager;
	private EventsManager eventsManager;
	private UpdateCheckThread updateCheckThread;
	public static final double CLIENT_VERSION = 1.1;
	
	public void init()
	{
		(this.updateCheckThread = new UpdateCheckThread()).start();
		this.eventBus = new EventBus("Apple Client");
		this.eventsManager = new EventsManager();
		this.modsManager = new ModsManager();
		this.dragGUI = new DragGUI();
		this.eventBus.register(this);
		this.extractSoundFiles();
		this.extractUpdater();
	}
	
	@Subscribe
	public void onKey(EventKey e)
	{
		if (e.getKey() == Keyboard.KEY_RSHIFT)
		{
			mc.displayGuiScreen(this.dragGUI);
		}
	}
	
	@Subscribe
	public void onRender(EventRender e)
	{
		if (!(mc.currentScreen instanceof DragGUI))
		{
			for (Mod mod : this.modsManager.getMods(Category.ALL).stream().filter(mod -> mod.hasGUI()).collect(Collectors.toCollection(ArrayList::new)))
			{
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
		File enableSound = new File(System.getProperty("java.io.tmpdir"), "enable.wav");
		File disableSound = new File(System.getProperty("java.io.tmpdir"), "disable.wav");
		
		if (!(enableSound.exists() || disableSound.exists()))
		{
			try
			{
				enableSound.createNewFile();
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
