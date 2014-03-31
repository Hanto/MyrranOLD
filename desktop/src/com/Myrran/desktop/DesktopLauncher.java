package com.Myrran.desktop;

import Constantes.MiscData;
import com.badlogic.gdx.Files;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.Myrran.Myrran;

public class DesktopLauncher
{
	public static void main (String[] arg)
    {
        System.setProperty("user.name","Myrran");

		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
        cfg.title = "Myrran";
        cfg.fullscreen = false;
        cfg.vSyncEnabled = false;
        cfg.foregroundFPS = 5000;
        cfg.useGL30 = true;
        cfg.width = MiscData.WINDOW_Horizontal_Resolution;
        cfg.height = MiscData.WINDOW_Vertical_Resolution;
        cfg.addIcon("Images/Spell Icons/FireBall.png", Files.FileType.Internal);
        new LwjglApplication(new Myrran(), cfg);
	}
}
