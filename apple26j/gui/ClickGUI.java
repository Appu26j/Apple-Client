package apple26j.gui;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

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
	private Category selectedCategory = Category.ALL;
	private TimeUtil timeUtil = new TimeUtil();
	private float index1 = 0, scrollIndex = 0;
	private boolean isGuiClosing = false;
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
		RenderUtil.drawImage(new ResourceLocation("shadow.png"), (this.width / 2) - 248 + (25 - (this.index1 * 25)), (this.height / 2) - 167 + (25 - (this.index1 * 25)), 496, 334);
		RenderUtil.drawRect((this.width / 2) - 225 + (25 - (this.index1 * 25)), (this.height / 2) - 150 + (25 - (this.index1 * 25)), (this.width / 2) + 225 + (25 - (this.index1 * 25)), (this.height / 2) + 150 + (25 - (this.index1 * 25)), new Color(230, 230, 230, (int) (this.index1 * 255)).getRGB());
		RenderUtil.drawCircle((this.width / 2) - 215 + (25 - (this.index1 * 25)), (this.height / 2) - 142 + (25 - (this.index1 * 25)), 4, (this.isInside(mouseX, mouseY, (this.width / 2) - 225, (this.height / 2) - 150, (this.width / 2) - 205, (this.height / 2) - 134) ? new Color(230, 90, 55, (int) (this.index1 * 255)) : new Color(255, 90, 80, (int) (this.index1 * 255))).getRGB());
		FixedFontRenderer.drawString("Click GUI", (this.width / 2) - (FixedFontRenderer.getStringWidth("Click GUI", 8) / 2) + (25 - (this.index1 * 25)), (this.height / 2) - 145 + (25 - (this.index1 * 25)), 8, new Color(75, 75, 75, (int) (this.index1 * 255)).getRGB());
		
		if (this.selectedMod == null)
		{
			float offset = 10;
			
			for (Category category : Category.values())
			{
				RenderUtil.drawRect(((this.width / 2) - 208) + offset + (25 - (this.index1 * 25)), (this.height / 2) - 128 + (25 - (this.index1 * 25)), ((this.width / 2) - 137) + offset + (25 - (this.index1 * 25)), (this.height / 2) - 108 + (25 - (this.index1 * 25)), this.selectedCategory == category ? new Color(200, 50, 50, (int) (this.index1 * 255)).getRGB() : (this.isInside(mouseX, mouseY, ((this.width / 2) - 208) + offset, (this.height / 2) - 128, ((this.width / 2) - 137) + offset, (this.height / 2) - 108) ? new Color(0, 0, 0, (int) (this.index1 * 64)) : new Color(0, 0, 0, (int) (this.index1 * 32))).getRGB());
				FixedFontRenderer.drawString(category.name(), ((this.width / 2) - 202) + offset + (25 - (this.index1 * 25)), (this.height / 2) - 122 + (25 - (this.index1 * 25)), 8, (this.selectedCategory == category ? new Color(230, 230, 230, (int) (this.index1 * 255)) : new Color(75, 75, 75, (int) (this.index1 * 255))).getRGB());
				offset += 79.75F;
			}
			
			GL11.glEnable(GL11.GL_SCISSOR_TEST);
			RenderUtil.scissor((this.width / 2) - 198 + (25 - (this.index1 * 25)), (this.height / 2) - 99 + (25 - (this.index1 * 25)), (this.width / 2) + 192 + (25 - (this.index1 * 25)), (this.height / 2) + 150 + (25 - (this.index1 * 25)));
			
			for (ClickGUIsModGUI clickGUIsModGUI : this.clickGUIsModGUIs)
			{
				clickGUIsModGUI.drawScreen(mouseX, mouseY, partialTicks);
			}
			
			GL11.glDisable(GL11.GL_SCISSOR_TEST);
		}
		
		else
		{
			FixedFontRenderer.drawString(this.selectedMod.getName(), (this.width / 2) - 208 + (25 - (this.index1 * 25)), (this.height / 2) - 120 + (25 - (this.index1 * 25)), 16, new Color(75, 75, 75, (int) (this.index1 * 255)).getRGB());
			FixedFontRenderer.drawString(this.selectedMod.getDescription(), (this.width / 2) - 208 + (25 - (this.index1 * 25)), (this.height / 2) - 100 + (25 - (this.index1 * 25)), 8, new Color(75, 75, 75, (int) (this.index1 * 255)).getRGB());
			int settingsOffset = 0;
			
			for (Setting setting : Apple.CLIENT.getSettingsManager().getSettings(this.selectedMod))
			{
				if (setting.getTypeOfSetting().equals("Check Box"))
				{
					RenderUtil.drawRect((this.width / 2) - 208 + (25 - (this.index1 * 25)), ((this.height / 2) - 85) + settingsOffset + (25 - (this.index1 * 25)), (this.width / 2) - (201 - FixedFontRenderer.getStringWidth(setting.getName() + ": " + (setting.getCheckBoxValue() ? "True" : "False"), 8)) + (25 - (this.index1 * 25)), ((this.height / 2) - 70) + settingsOffset + (25 - (this.index1 * 25)), (this.isInside(mouseX, mouseY, (this.width / 2) - 208, ((this.height / 2) - 85) + settingsOffset, (this.width / 2) - (201 - FixedFontRenderer.getStringWidth(setting.getName() + ": " + (setting.getCheckBoxValue() ? "True" : "False"), 8)), ((this.height / 2) - 70) + settingsOffset) ? new Color(0, 0, 0, (int) (this.index1 * 32)) : new Color(0, 0, 0, (int) (this.index1 * 16))).getRGB());
					FixedFontRenderer.drawString(setting.getName() + ": " + (setting.getCheckBoxValue() ? "True" : "False"), (this.width / 2) - 204 + (25 - (this.index1 * 25)), ((this.height / 2) - 81) + settingsOffset + (25 - (this.index1 * 25)), 8, new Color(75, 75, 75, (int) (this.index1 * 255)).getRGB());
					settingsOffset += 17;
				}
				
				if (setting.getTypeOfSetting().equals("Mode"))
				{
					RenderUtil.drawRect((this.width / 2) - 208 + (25 - (this.index1 * 25)), ((this.height / 2) - 85) + settingsOffset + (25 - (this.index1 * 25)), (this.width / 2) - (201 - FixedFontRenderer.getStringWidth(setting.getName() + ": " + setting.getModeValue(), 8)) + (25 - (this.index1 * 25)), ((this.height / 2) - 70) + settingsOffset + (25 - (this.index1 * 25)), (this.isInside(mouseX, mouseY, (this.width / 2) - 208, ((this.height / 2) - 85) + settingsOffset, (this.width / 2) - (201 - FixedFontRenderer.getStringWidth(setting.getName() + ": " + setting.getModeValue(), 8)), ((this.height / 2) - 70) + settingsOffset) ? new Color(0, 0, 0, (int) (this.index1 * 32)) : new Color(0, 0, 0, (int) (this.index1 * 16))).getRGB());
					FixedFontRenderer.drawString(setting.getName() + ": " + setting.getModeValue(), (this.width / 2) - 204 + (25 - (this.index1 * 25)), ((this.height / 2) - 81) + settingsOffset + (25 - (this.index1 * 25)), 8, new Color(75, 75, 75, (int) (this.index1 * 255)).getRGB());
					settingsOffset += 17;
				}
			}
		}
	}
	
	@Override
    public void handleMouseInput() throws IOException
	{
		super.handleMouseInput();
		int i = Integer.compare(Mouse.getEventDWheel(), 0);
		this.scrollIndex += (i * 25);
		this.scrollIndex = this.scrollIndex > 0 ? 0 : this.scrollIndex;
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
			float offset = 10;
			
			for (Category category : Category.values())
			{
				if (this.isInside(mouseX, mouseY, ((this.width / 2) - 208) + offset, (this.height / 2) - 128, ((this.width / 2) - 137) + offset, (this.height / 2) - 108) && mouseButton == 0)
				{
					SoundUtil.playClickSound();
					this.selectedCategory = category;
					this.clickGUIsModGUIs.clear();
					int xOffset = 27;
					int yOffset = 51;
					
					for (Mod mod : Apple.CLIENT.getModsManager().getMods(this.selectedCategory))
					{
						if (xOffset == 432)
						{
							xOffset = 27;
							yOffset += 135;
						}
						
						this.clickGUIsModGUIs.add(new ClickGUIsModGUI(mod, this, xOffset, yOffset, this.width, this.height));
						xOffset += 135;
					}
				}
				
				offset += 79.75F;
			}
			
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
				
				if (setting.getTypeOfSetting().equals("Mode"))
				{
					if (this.isInside(mouseX, mouseY, (this.width / 2) - 208, ((this.height / 2) - 85) + settingsOffset, (this.width / 2) - (201 - FixedFontRenderer.getStringWidth(setting.getName() + ": " + setting.getModeValue(), 8)), ((this.height / 2) - 70) + settingsOffset))
					{
						int index = setting.getModes().indexOf(setting.getModeValue());
						
						if (mouseButton == 0)
						{
							SoundUtil.playClickSound();
							
							if (index < setting.getModes().size() - 1)
							{
				                index++;
				            }
							
							else
				            {
				                index = 0;
				            }
						}
						
						if (mouseButton == 1)
						{
							SoundUtil.playClickSound();
							
							if (index > 0)
							{
				                index--;
				            }
							
							else
				            {
				                index = setting.getModes().size() - 1;
				            }
						}
						
						setting.setModeValue(setting.getModes().get(index));
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
		int xOffset = 27;
		int yOffset = 51;
		
		for (Mod mod : Apple.CLIENT.getModsManager().getMods(this.selectedCategory))
		{
			if (xOffset == 432)
			{
				xOffset = 27;
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
