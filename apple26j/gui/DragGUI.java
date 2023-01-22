package apple26j.gui;

import static apple26j.utils.RenderUtil.drawImage;

import java.awt.Color;
import java.awt.GraphicsEnvironment;
import java.io.IOException;
import java.util.ArrayList;
import java.util.stream.Collectors;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;

import apple26j.Apple;
import apple26j.fontrenderer.FixedFontRenderer;
import apple26j.mods.Category;
import apple26j.mods.Mod;
import apple26j.utils.RenderUtil;
import apple26j.utils.SoundUtil;
import apple26j.utils.TimeUtil;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;

public class DragGUI extends GuiScreen
{
	private long refreshRate = (long) (1000F / GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDisplayMode().getRefreshRate());
	private float index1 = 0, dragX, dragY, dragWidth, dragHeight;
	private boolean isGuiClosing = false, isClickGUIOpening = false;
	private ArrayList<ModGUI> modGUIs = new ArrayList<>();
	private ClickGUI clickGUI = new ClickGUI();
	private TimeUtil timeUtil = new TimeUtil();
	private Mod draggingMod;
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks)
    {
		super.drawScreen(mouseX, mouseY, partialTicks);
		
		if (this.draggingMod != null)
		{
			this.draggingMod.setX(mouseX - this.dragX);
			this.draggingMod.setY(mouseY - this.dragY);
			this.draggingMod.setWidth(mouseX - this.dragWidth);
			this.draggingMod.setHeight(mouseY - this.dragHeight);
		}
		
		if (this.mc.gameSettings.enableVsync || this.timeUtil.hasTimePassed(this.refreshRate))
		{
			if (this.isGuiClosing || this.isClickGUIOpening)
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
		
		RenderUtil.drawRect(0, 0, this.width, this.height, new Color(0, 0, 0, this.isClickGUIOpening ? 75 : (int) (this.index1 * 75)).getRGB());
		
		if ((this.isGuiClosing || this.isClickGUIOpening) && this.index1 == 0)
		{
			if (this.isClickGUIOpening)
			{
				this.mc.displayGuiScreen(this.clickGUI);
				this.isClickGUIOpening = false;
			}
			
			else
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
		}
		
		GlStateManager.color(1, 1, 1, this.index1);
		drawImage(new ResourceLocation("icons/icon_16x16.png"), (this.width / 2) - 42.5F, (this.height / 2) - 105, 85, 85);
		RenderUtil.drawImage(new ResourceLocation("shadow.png"), (this.width / 2) - 55, (this.height / 2) - 17, 110, 34);
		RenderUtil.drawRect((this.width / 2) - 50, (this.height / 2) - 15, (this.width / 2) + 50, (this.height / 2) + 15, this.isInside(mouseX, mouseY, (this.width / 2) - 50, (this.height / 2) - 15, (this.width / 2) + 50, (this.height / 2) + 15) ? new Color(200, 200, 200, (int) (this.index1 * 255)).getRGB() : new Color(230, 230, 230, (int) (this.index1 * 255)).getRGB());
		FixedFontRenderer.drawString("SETTINGS", (this.width / 2) - (FixedFontRenderer.getStringWidth("SETTINGS", 12) / 2), (this.height / 2) - 5, 12, new Color(75, 75, 75, (int) (this.index1 * 255)).getRGB());
		
		for (ModGUI modGUI : this.modGUIs)
		{
			modGUI.drawScreen(mouseX, mouseY, partialTicks);
		}
    }
	
	@Override
	protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException
    {
		super.mouseClicked(mouseX, mouseY, mouseButton);
		
		if (this.isInside(mouseX, mouseY, (this.width / 2) - 50, (this.height / 2) - 15, (this.width / 2) + 50, (this.height / 2) + 15) && mouseButton == 0)
		{
			SoundUtil.playClickSound();
			isClickGUIOpening = true;
		}
		
		for (ModGUI modGUI : this.modGUIs)
		{
			if (modGUI.isInsideMod(mouseX, mouseY))
			{
				Mod mod = modGUI.getMod();
				this.draggingMod = mod;
				this.dragX = mouseX - mod.getX();
				this.dragY = mouseY - mod.getY();
				this.dragWidth = mouseX - mod.getWidth();
				this.dragHeight = mouseY - mod.getHeight();
			}
		}
    }
	
	@Override
	protected void mouseReleased(int mouseX, int mouseY, int state)
    {
		super.mouseReleased(mouseX, mouseY, state);
		this.draggingMod = null;
    }
	
	@Override
	public void initGui()
	{
		super.initGui();
		Mouse.setCursorPosition(Display.getWidth() / 2, (Display.getHeight() / 2) - 50);
		this.modGUIs.clear();

		for (Mod mod : Apple.CLIENT.getModsManager().getMods(Category.ALL).stream().filter(mod -> mod.hasGUI() && mod.isEnabled()).collect(Collectors.toCollection(ArrayList::new)))
		{
			this.modGUIs.add(new ModGUI(mod, this));
		}
	}
	
	@Override
	protected void keyTyped(char typedChar, int keyCode) throws IOException
    {
		if (keyCode == 1)
        {
            this.isGuiClosing = true;
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
	
	@Override
	public boolean doesGuiPauseGame()
    {
        return false;
    }
}
