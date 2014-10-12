package ua.levstreet.game.desktop;

import ua.levstreet.game.StarCatcher;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.tools.texturepacker.TexturePacker.Settings;

public class DesktopLauncher {
	public static void main(String[] arg) {
		Settings settings = new Settings();
		settings.maxWidth = 2048;
		settings.maxHeight = 2048;
		settings.filterMin = TextureFilter.MipMapLinearLinear;
		settings.filterMag = TextureFilter.Linear;
		// TexturePacker.process(settings, "../images",
		// "../android/assets/atlas",
		// "common");
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = 960;
		config.height = 540;
		new LwjglApplication(new StarCatcher(), config);
	}
}
