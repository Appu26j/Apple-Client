package apple26j.mods.general;

import com.google.common.eventbus.Subscribe;

import apple26j.events.entity.EventUpdate;
import apple26j.interfaces.ModInterface;
import apple26j.mods.*;

@ModInterface(name = "Full Bright", description = "Allows you to see everything with full brightness.", category = Category.GENERAL)
public class FullBright extends Mod
{
	// The original brightness
	private float previousBrightness = 0;
	
	@Override
	public void onEnable()
	{
		super.onEnable();
		// Sets the previousBrightness level to the original brightness
		this.previousBrightness = mc.gameSettings.gammaSetting;
	}
	
	@Subscribe
	public void onUpdate(EventUpdate e)
	{
		// Sets the brightness to 100, resulting in full brightness
		mc.gameSettings.gammaSetting = 100;
	}
	
	@Override
	public void onDisable()
	{
		super.onDisable();
		// Sets it back to the original brightness
		mc.gameSettings.gammaSetting = this.previousBrightness;
	}
}
