package apple26j.gui;

import java.awt.*;
import java.io.*;

import javax.swing.*;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.filechooser.FileFilter;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import com.google.common.io.Files;

import apple26j.Apple;
import apple26j.fontrenderer.FixedFontRenderer;
import apple26j.music.MusicPlayer;
import apple26j.utils.*;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;

public class MusicGUI extends GuiScreen
{
	private static final String[] songsList = new String[] {
		"Cartoon - On & On",
		"Heroes Tonight",
		"Invincible",
		"My Heart",
		"Mortals",
		"Blank",
		"Sky High",
		"Symbolism",
		"Fearless II",
		"Hope",
		"Nekozilla",
		"Energy",
		"Feel Good",
		"Limitless",
		"Link"
	};
	
	private long refreshRate = (long) (1000F / GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDisplayMode().getRefreshRate());
	private float x, y, index1 = 0, scrollIndex = 0;
	private TimeUtil timeUtil = new TimeUtil();
	private boolean isGuiClosing = false;
	private boolean upDirection = false;
	private String previousSong = "";
	private String currentSong = "";
	
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
		
		RenderUtil.drawRect(0, 0, this.width, this.height, new Color(0, 0, 0, (int) (this.index1 * 75)).getRGB());
		
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
		RenderUtil.drawImage(new ResourceLocation("shadow.png"), (this.width / 2) - 221 + (25 - (this.index1 * 25)), (this.height / 2) - 167 + (25 - (this.index1 * 25)), 442, 334);
		RenderUtil.drawRect(this.x - 200 + (25 - (this.index1 * 25)), this.y - 150 + (25 - (this.index1 * 25)), this.x + 200 + (25 - (this.index1 * 25)), this.y + 150 + (25 - (this.index1 * 25)), new Color(230, 230, 230, (int) (this.index1 * 255)).getRGB());
		RenderUtil.drawRect(this.x - 173 + (25 - (this.index1 * 25)), this.y - 130 + (25 - (this.index1 * 25)), this.x + 65 + (25 - (this.index1 * 25)), this.y - 110 + (25 - (this.index1 * 25)), new Color(0, 0, 0, (int) (this.index1 * 16)).getRGB());
		RenderUtil.drawRect(this.x + 72 + (25 - (this.index1 * 25)), this.y - 130 + (25 - (this.index1 * 25)), this.x + 167 + (25 - (this.index1 * 25)), this.y - 110 + (25 - (this.index1 * 25)), (this.isInside(mouseX, mouseY, this.x + 72 + (25 - (this.index1 * 25)), this.y - 130 + (25 - (this.index1 * 25)), this.x + 167 + (25 - (this.index1 * 25)), this.y - 110 + (25 - (this.index1 * 25))) ? new Color(0, 0, 0, (int) (this.index1 * 32)) : new Color(0, 0, 0, (int) (this.index1 * 16))).getRGB());
		RenderUtil.drawCircle(this.x - 190 + (25 - (this.index1 * 25)), this.y - 142 + (25 - (this.index1 * 25)), 4, (this.isInside(mouseX, mouseY, this.x - 200, this.y - 150, this.x - 180, this.y - 134) ? new Color(230, 90, 55, (int) (this.index1 * 255)) : new Color(255, 90, 80, (int) (this.index1 * 255))).getRGB());
		FixedFontRenderer.drawString("Songs Player" + (Apple.CLIENT.songsDownloading ? " (Songs are still downloading)" : ""), this.x - (FixedFontRenderer.getStringWidth("Songs Player" + (Apple.CLIENT.songsDownloading ? " (Songs are still downloading)" : ""), 8) / 2) + (25 - (this.index1 * 25)), (this.height / 2) - 145 + (25 - (this.index1 * 25)), 8, new Color(75, 75, 75, (int) (this.index1 * 255)).getRGB());
		FixedFontRenderer.drawString("Add Custom Song", this.x + 78 + (25 - (this.index1 * 25)), this.y - 123 + (25 - (this.index1 * 25)), 8, new Color(75, 75, 75, (int) (this.index1 * 255)).getRGB());
		GL11.glEnable(GL11.GL_SCISSOR_TEST);
		RenderUtil.scissor(this.x - 173 + (25 - (this.index1 * 25)), this.y - 100 + (25 - (this.index1 * 25)), this.x + 167 + (25 - (this.index1 * 25)), this.y + 125 + (25 - (this.index1 * 25)));
		int xOffset = 7;
		int yOffset = 0;
		
		for (String song : new File("songs").list())
		{
			song = song.substring(0, song.indexOf("."));
			boolean isSongUnknown = true;
			
			for (int i = 0; i < this.songsList.length; i++)
			{
				if (this.songsList[i].equals(song))
				{
					isSongUnknown = false;
					break;
				}
			}
			
			if (xOffset == 367)
			{
				xOffset = 7;
				yOffset += 140;
			}
			
			RenderUtil.drawRect((this.x - 180) + xOffset + (25 - (this.index1 * 25)), (this.y - 100) + yOffset + this.scrollIndex + (25 - (this.index1 * 25)), (this.x - 80) + xOffset + (25 - (this.index1 * 25)), (this.y + 16) + yOffset + this.scrollIndex + (25 - (this.index1 * 25)), (this.isInside(mouseX, mouseY, this.x - 180, this.y - 100, this.x + 160, this.y + 125) && this.isInside(mouseX, mouseY, (this.x - 180) + xOffset, (this.y - 100) + yOffset + this.scrollIndex, (this.x - 80) + xOffset, (this.y + 16) + yOffset + this.scrollIndex) ? new Color(0, 0, 0, (int) (this.index1 * 32)) : new Color(0, 0, 0, (int) (this.index1 * 16))).getRGB());
			GlStateManager.color(this.isInside(mouseX, mouseY, this.x - 180, this.y - 100, this.x + 160, this.y + 125) && this.isInside(mouseX, mouseY, (this.x - 180) + xOffset, (this.y - 100) + yOffset + this.scrollIndex, (this.x - 80) + xOffset, (this.y + 16) + yOffset + this.scrollIndex) ? 0.8F : 1, this.isInside(mouseX, mouseY, this.x - 180, this.y - 100, this.x + 160, this.y + 125) && this.isInside(mouseX, mouseY, (this.x - 180) + xOffset, (this.y - 100) + yOffset + this.scrollIndex, (this.x - 80) + xOffset, (this.y + 16) + yOffset + this.scrollIndex) ? 0.8F : 1, this.isInside(mouseX, mouseY, this.x - 180, this.y - 100, this.x + 160, this.y + 125) && this.isInside(mouseX, mouseY, (this.x - 180) + xOffset, (this.y - 100) + yOffset + this.scrollIndex, (this.x - 80) + xOffset, (this.y + 16) + yOffset + this.scrollIndex) ? 0.8F : 1, this.index1);			
			RenderUtil.drawImage(isSongUnknown && !song.contains("NCS") ? new ResourceLocation("unknown.jpg") : new ResourceLocation("NCS.jpg"), (this.x - 180) + xOffset + (25 - (this.index1 * 25)), (this.y - 100) + yOffset + this.scrollIndex + (25 - (this.index1 * 25)), 100, 100);
			FixedFontRenderer.drawString(song, (this.x - 177) + xOffset + (25 - (this.index1 * 25)), (this.y + 5) + yOffset + this.scrollIndex + (25 - (this.index1 * 25)), 8, new Color(75, 75, 75, (int) (this.index1 * 255)).getRGB());
			xOffset += 120;
		}
		
		GL11.glDisable(GL11.GL_SCISSOR_TEST);
		RenderUtil.drawRect(this.x - 200 + (25 - (this.index1 * 25)), this.y + 125 + (25 - (this.index1 * 25)), this.x + 200 + (25 - (this.index1 * 25)), this.y + 150 + (25 - (this.index1 * 25)), new Color(200, 200, 200, (int) (this.index1 * 255)).getRGB());
		RenderUtil.drawCircle(this.x + (25 - (this.index1 * 25)), this.y + 137 + (25 - (this.index1 * 25)), 10, (this.isInside(mouseX, mouseY, this.x - 10, this.y + 127, this.x + 10, this.y + 147) ? new Color(220, 220, 220, (int) (this.index1 * 255)) : new Color(230, 230, 230, (int) (this.index1 * 255))).getRGB());
		FixedFontRenderer.drawString(this.currentSong, this.x - 190 + (25 - (this.index1 * 25)), this.y + 134 + (25 - (this.index1 * 25)), 8, new Color(75, 75, 75, (int) (this.index1 * 255)).getRGB());
		GlStateManager.color(1, 1, 1, this.index1);
		RenderUtil.drawImage(!MusicPlayer.isPlaying() ? new ResourceLocation("play.png") : new ResourceLocation("pause.png"), this.x - 6 + (25 - (this.index1 * 25)), this.y + 130 + (25 - (this.index1 * 25)), 15, 15);
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
			this.isGuiClosing = true;
        }
    }
	
	@Override
	protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException
    {
		super.mouseClicked(mouseX, mouseY, mouseButton);
		
		if (this.isInside(mouseX, mouseY, this.x - 200, this.y - 150, this.x - 180, this.y - 134) && mouseButton == 0)
		{
			SoundUtil.playClickSound();
			this.keyTyped('0', 1);
		}
		
		int xOffset = 7;
		int yOffset = 0;
		
		for (String song : new File("songs").list())
		{
			song = song.substring(0, song.indexOf("."));
			
			if (xOffset == 367)
			{
				xOffset = 7;
				yOffset += 140;
			}
			
			if (this.isInside(mouseX, mouseY, this.x - 180, this.y - 100, this.x + 160, this.y + 125) && this.isInside(mouseX, mouseY, (this.x - 180) + xOffset, (this.y - 100) + yOffset + this.scrollIndex, (this.x - 80) + xOffset, (this.y + 16) + yOffset + this.scrollIndex) && mouseButton == 0)
			{
				this.currentSong = song;
				
				if (!this.previousSong.equals(song) && MusicPlayer.isPlaying())
				{
					MusicPlayer.stopSong();
					MusicPlayer.playSong(song);
					this.previousSong = song;
				}
			}
			
			xOffset += 120;
		}
		
		if (this.isInside(mouseX, mouseY, this.x - 10, this.y + 127, this.x + 10, this.y + 147) && mouseButton == 0)
		{
			if (MusicPlayer.isPlaying())
			{
				MusicPlayer.pauseSong();
			}
			
			else
			{
				MusicPlayer.playSong(this.currentSong);
			}
		}
		
		if (this.isInside(mouseX, mouseY, this.x + 72 + (25 - (this.index1 * 25)), this.y - 130 + (25 - (this.index1 * 25)), this.x + 167 + (25 - (this.index1 * 25)), this.y - 110 + (25 - (this.index1 * 25))) && mouseButton == 0)
		{	
			LookAndFeel oldLookAndFeel = UIManager.getLookAndFeel();
			JFileChooser jFileChooser = new JFileChooser();
			
			try
			{
				UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
				SwingUtilities.updateComponentTreeUI(jFileChooser);
			}
			
			catch (Exception e)
			{
				;
			}
			
			jFileChooser.setFileFilter(new FileFilter()
			{
				public boolean accept(File file)
				{
					return file.isDirectory() || file.getAbsolutePath().endsWith(".wav");
				}
				
				@Override
				public String getDescription()
				{
					return null;
				}
			});
			
			jFileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
			jFileChooser.setDialogTitle("Choose a .wav file");
			jFileChooser.showOpenDialog(null);
			
			if (jFileChooser.getSelectedFile() != null)
			{
				Files.copy(jFileChooser.getSelectedFile(), new File("songs" + File.separator + jFileChooser.getSelectedFile().getName()));
			}
			
			try
			{
				UIManager.setLookAndFeel(oldLookAndFeel);
			}
			
			catch (Exception e)
			{
				;
			}
		}
    }
	
	@Override
	public void initGui()
	{
		super.initGui();
		this.x = (this.width / 2);
		this.y = (this.height / 2);
	}
	
	public boolean isInside(int mouseX, int mouseY, float x, float y, float width, float height)
	{
		return mouseX > x && mouseX < width && mouseY > y && mouseY < height;
	}
	
	public static String[] getSongsList()
	{
		return songsList;
	}
	
	@Override
	public boolean doesGuiPauseGame()
    {
        return false;
    }
}
