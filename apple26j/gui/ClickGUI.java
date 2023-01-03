package apple26j.gui;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

import apple26j.Apple;
import apple26j.fontrenderer.FixedFontRenderer;
import apple26j.mods.*;
import apple26j.settings.Setting;
import apple26j.utils.*;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;

public class ClickGUI extends GuiScreen
{
	private long refreshRate = (long) (1000F / GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDisplayMode().getRefreshRate());
	private ArrayList<ClickGUIsModGUI> clickGUIsModGUIs = new ArrayList<>();
	private TimeUtil timeUtil = new TimeUtil();
	private boolean isGuiClosing = false;
	private float index1 = 0;
	private Mod selectedMod;
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks)
    {
		super.drawScreen(mouseX, mouseY, partialTicks);
		
		if (this.mc.gameSettings.enableVsync || this.timeUtil.hasTimePassed(this.refreshRate))
		{
			if (this.isGuiClosing)
			{
				if (this.index1 > 0)
				{
					this.index1 -= 0.1F;
					this.index1 = this.index1 < 0 ? 0 : this.index1;
				}
			}
			
			else
			{
				if (this.index1 < 1)
				{
					this.index1 += 0.1F;
					this.index1 = this.index1 > 1 ? 1 : this.index1;
				}
			}
		}
		
		RenderUtil.drawRect(0, 0, this.width, this.height, new Color(0, 0, 0, this.isGuiClosing ? (int) (this.index1 * 75) : 75).getRGB());
		
		if (this.isGuiClosing && this.index1 == 0)
		{
			try
			{
				super.keyTyped('0', 1);
				this.isGuiClosing = false;
			}
			
			catch (Exception e)
			{
				;
			}
		}
		
		GlStateManager.color(1, 1, 1, this.index1);
		RenderUtil.drawImage(new ResourceLocation("shadow.png"), (this.width / 2) - 248, (this.height / 2) - 167, 496, 334);
		RenderUtil.drawRect((this.width / 2) - 225, (this.height / 2) - 150, (this.width / 2) + 205.5F, (this.height / 2) + 150, new Color(230, 230, 230, (int) (this.index1 * 255)).getRGB());
		RenderUtil.drawRect((this.width / 2) + 205.5F, (this.height / 2) - 130, (this.width / 2) + 225, (this.height / 2) + 150, new Color(230, 230, 230, (int) (this.index1 * 255)).getRGB());
		RenderUtil.drawRect((this.width / 2) + 205.5F, (this.height / 2) - 150, (this.width / 2) + 225, (this.height / 2) - 130, new Color(230, 230, 230, (int) (this.index1 * 255)).getRGB());
		RenderUtil.drawCircle((this.width / 2) - 215, (this.height / 2) - 142, 4, (this.isInside(mouseX, mouseY, (this.width / 2) - 225, (this.height / 2) - 150, (this.width / 2) - 205, (this.height / 2) - 134) ? new Color(230, 90, 55, (int) (this.index1 * 255)) : new Color(255, 90, 80, (int) (this.index1 * 255))).getRGB());
		FixedFontRenderer.drawString("Click GUI", (this.width / 2) - (FixedFontRenderer.getStringWidth("Click GUI", 8) / 2), (this.height / 2) - 145, 8, new Color(75, 75, 75, (int) (this.index1 * 255)).getRGB());
		
		if (this.selectedMod == null)
		{
			for (ClickGUIsModGUI clickGUIsModGUI : this.clickGUIsModGUIs)
			{
				clickGUIsModGUI.drawScreen(mouseX, mouseY, partialTicks);
			}
		}
		
		else
		{
			FixedFontRenderer.drawString(this.selectedMod.getName(), (this.width / 2) - 208, (this.height / 2) - 120, 16, new Color(75, 75, 75, (int) (this.index1 * 255)).getRGB());
			FixedFontRenderer.drawString(this.selectedMod.getDescription(), (this.width / 2) - 208, (this.height / 2) - 100, 8, new Color(75, 75, 75, (int) (this.index1 * 255)).getRGB());
			int settingsOffset = 0;
			
			for (Setting setting : Apple.CLIENT.getSettingsManager().getSettings(this.selectedMod))
			{
				if (setting.getTypeOfSetting().equals("Check Box"))
				{
					RenderUtil.drawRect((this.width / 2) - 208, ((this.height / 2) - 85) + settingsOffset, (this.width / 2) - (201 - FixedFontRenderer.getStringWidth(setting.getName() + ": " + (setting.getCheckBoxValue() ? "True" : "False"), 8)), ((this.height / 2) - 70) + settingsOffset, (this.isInside(mouseX, mouseY, (this.width / 2) - 208, ((this.height / 2) - 85) + settingsOffset, (this.width / 2) - (201 - FixedFontRenderer.getStringWidth(setting.getName() + ": " + (setting.getCheckBoxValue() ? "True" : "False"), 8)), ((this.height / 2) - 70) + settingsOffset) ? new Color(0, 0, 0, (int) (this.index1 * 32)) : new Color(0, 0, 0, (int) (this.index1 * 16))).getRGB());
					FixedFontRenderer.drawString(setting.getName() + ": " + (setting.getCheckBoxValue() ? "True" : "False"), (this.width / 2) - 204, ((this.height / 2) - 81) + settingsOffset, 8, new Color(75, 75, 75, (int) (this.index1 * 255)).getRGB());
					settingsOffset += 17;
				}
			}
		}
    }
	
	@Override
	protected void keyTyped(char typedChar, int keyCode) throws IOException
    {
		if (keyCode == 1)
        {
			if (this.selectedMod != null)
			{
				this.selectedMod = null;
			}
			
			else
			{
	            this.isGuiClosing = true;
			}
        }
    }
	
	@Override
	protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException
    {
		super.mouseClicked(mouseX, mouseY, mouseButton);
		
		if (this.isInside(mouseX, mouseY, (this.width / 2) - 225, (this.height / 2) - 150, (this.width / 2) - 205, (this.height / 2) - 134) && mouseButton == 0)
		{
			SoundUtil.playClickSound();
			this.keyTyped('0', 1);
		}
		
		if (this.selectedMod == null)
		{
			for (ClickGUIsModGUI clickGUIsModGUI : this.clickGUIsModGUIs)
			{
				clickGUIsModGUI.mouseClicked(mouseX, mouseY, mouseButton);
			}
		}
		
		else
		{
			int settingsOffset = 0;
			
			for (Setting setting : Apple.CLIENT.getSettingsManager().getSettings(this.selectedMod))
			{
				if (setting.getTypeOfSetting().equals("Check Box"))
				{
					if (this.isInside(mouseX, mouseY, (this.width / 2) - 208, ((this.height / 2) - 85) + settingsOffset, (this.width / 2) - (201 - FixedFontRenderer.getStringWidth(setting.getName() + ": " + (setting.getCheckBoxValue() ? "True" : "False"), 8)), ((this.height / 2) - 70) + settingsOffset) && mouseButton == 0)
					{
						SoundUtil.playClickSound();
						setting.setCheckBoxValue(!setting.getCheckBoxValue());
					}
					
					settingsOffset += 17;
				}
			}
		}
    }
	
	@Override
	public void initGui()
	{
		super.initGui();
		this.clickGUIsModGUIs.clear();
		int xOffset = 17;
		int yOffset = 34;
		
		for (Mod mod : Apple.CLIENT.getModsManager().getMods(Category.ALL))
		{
			if (xOffset == 422)
			{
				xOffset = 17;
				yOffset += 135;
			}
			
			this.clickGUIsModGUIs.add(new ClickGUIsModGUI(mod, this, xOffset, yOffset, this.width, this.height));
			xOffset += 135;
		}
	}
	
	public boolean isInside(int mouseX, int mouseY, float x, float y, float width, float height)
	{
		return mouseX > x && mouseX < width && mouseY > y && mouseY < height;
	}
	
	public float getIndex1()
	{
		return this.index1;
	}
	
	public void setSelectedMod(Mod selectedMod)
	{
		this.selectedMod = selectedMod;
	}
	
	@Override
	public boolean doesGuiPauseGame()
    {
        return false;
    }
}
