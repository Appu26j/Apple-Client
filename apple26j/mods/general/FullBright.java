package apple26j.mods.general;

import com.google.common.eventbus.Subscribe;

import apple26j.events.entity.EventUpdate;
import apple26j.interfaces.ModInterface;
import apple26j.mods.*;

@ModInterface(name = "Full Bright", description = "Allows you to see everything with full brightness.", category = Category.GENERAL)
public class FullBright extends Mod
{
	private float previousBrightness = 0;
	
	@Override
	public void onEnable()
	{
		super.onEnable();
		this.previousBrightness = mc.gameSettings.gammaSetting;
	}
	
	@Subscribe
	public void onUpdate(EventUpdate e)
	{
		mc.gameSettings.gammaSetting = 100;
	}
	
	@Override
	public void onDisable()
	{
		super.onDisable();
		mc.gameSettings.gammaSetting = this.previousBrightness;
	}
}
