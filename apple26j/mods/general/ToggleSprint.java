package apple26j.mods.general;

import static apple26j.fontrenderer.FixedFontRenderer.*;
import static apple26j.utils.RenderUtil.drawRect;

import java.awt.Color;

import org.lwjgl.input.Keyboard;

import com.google.common.eventbus.Subscribe;

import apple26j.events.entity.EventUpdate;
import apple26j.events.mc.EventKey;
import apple26j.interfaces.ModInterface;
import apple26j.mods.*;
import apple26j.settings.Setting;

@ModInterface(name = "Toggle Sprint", description = "Allows you to toggle automatic sprint.", width = 111.5F, height = 15, category = Category.GENERAL)
public class ToggleSprint extends Mod
{
	private boolean flag = true, canSprint = true;
	
	public ToggleSprint()
	{
		this.addSetting(new Setting("GUI", this, true));
	}
	
	@Override
	public void onEnable()
	{
		super.onEnable();
		this.canSprint = true;
		this.flag = true;
	}
	
	@Subscribe
	public void onUpdate(EventUpdate e)
	{
		if (this.canSprint)
		{
			if (!this.flag)
			{
				this.flag = true;
			}

			// Sets the sprint keybind pressed to true
			mc.gameSettings.keyBindSprint.setPressed(true);
		}
		
		else
		{
			if (this.flag)
			{
				// Sets the sprint keybind pressed if the key CTRL is down; else not
				mc.gameSettings.keyBindSprint.setPressed(Keyboard.isKeyDown(mc.gameSettings.keyBindSprint.getKeyCode()));
				this.flag = false;
			}
		}
	}
	
	@Subscribe
	public void onKey(EventKey e)
	{
		if (e.getKey() == mc.gameSettings.keyBindSprint.getKeyCode())
		{
			// Sets the ability to sprint either true or false
			this.canSprint = !this.canSprint;
		}
	}
	
	@Override
	public void onRender()
	{
		super.onRender();

		if (this.getSetting("GUI").getCheckBoxValue())
		{
			drawRect(this.x, this.y, this.width, this.height, new Color(0, 0, 0, 128).getRGB());
			drawStringWithShadow("(Sprinting [" + (this.canSprint ? "Toggled" : "Vanilla") + "])", (this.x + 52.5F + 2.5F) - (getStringWidth("(Sprinting [" + (this.canSprint ? "Toggled" : "Vanilla") + "])", 8) / 2), this.y + 2.5F, 8, -1);
		}
	}
}
