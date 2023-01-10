package apple26j.mods;

import apple26j.Apple;
import apple26j.interfaces.*;
import apple26j.settings.Setting;
import apple26j.utils.SoundUtil;

public class Mod implements MinecraftInterface
{
	private ModInterface modInterface = this.getClass().getAnnotation(ModInterface.class);
	private String name = this.modInterface.name(), description = this.modInterface.description();
	protected float width = this.modInterface.width(), height = this.modInterface.height();
	protected float x = this.modInterface.x(), y = this.modInterface.y();
	private Category category = this.modInterface.category();
	private boolean enabled = false;
	
	public void toggle()
	{
		if (this.enabled)
		{
			this.onDisable();
			this.enabled = false;
		}
		
		else
		{
			this.onEnable();
			this.enabled = true;
		}
	}
	
	public void onRender()
	{
		;
	}
	
	public void onEnable()
	{
		Apple.CLIENT.getEventBus().register(this);
		
		if (mc.loadingScreen != null)
		{
			SoundUtil.playEnableSound();
		}
	}
	
	public void onDisable()
	{
		Apple.CLIENT.getEventBus().unregister(this);
		
		if (mc.loadingScreen != null)
		{
			SoundUtil.playDisableSound();
		}
	}
	
	public String getName()
	{
		return this.name;
	}
	
	public String getDescription()
	{
		return this.description;
	}
	
	public float getX()
	{
		return this.x;
	}
	
	public void setX(float x)
	{
		this.x = x;
	}
	
	public float getY()
	{
		return this.y;
	}
	
	public void setY(float y)
	{
		this.y = y;
	}
	
	public float getWidth()
	{
		return this.width;
	}
	
	public void setWidth(float width)
	{
		this.width = width;
	}
	
	public float getHeight()
	{
		return this.height;
	}
	
	public void setHeight(float height)
	{
		this.height = height;
	}
	
	public Category getCategory()
	{
		return this.category;
	}
	
	public boolean hasGUI()
	{
		return this.width != this.x && this.height != this.y;
	}
	
	public boolean isEnabled()
	{
		return this.enabled;
	}
	
	public void setEnabled(boolean enabled)
	{
		if (enabled)
		{
			if (!this.enabled)
			{
				toggle();
			}
		}
		
		else
		{
			if (this.enabled)
			{
				toggle();
			}
		}
	}
	
	public void addSetting(Setting setting)
	{
		Apple.CLIENT.getSettingsManager().addSetting(setting);
	}
	
	public Setting getSetting(String name)
	{
		return Apple.CLIENT.getSettingsManager().getSetting(name, this);
	}
}
