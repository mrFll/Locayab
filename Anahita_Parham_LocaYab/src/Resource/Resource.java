package Resource;

import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.IOException;
import java.io.InputStream;
import com.teamdev.jxmaps.Icon;

public class Resource {
	public static Image getImage(String name) {
		return Toolkit.getDefaultToolkit().getImage(Resource.class.getResource(name));
	}

	public static Font getFont(float f) {
		InputStream is = Resource.class.getResourceAsStream("dastnevis.ttf");
		Font font = null;
		try {
			font = Font.createFont(Font.TRUETYPE_FONT, is);
			Font sizedFont = font.deriveFont(f);
			return sizedFont;
		} catch (FontFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return font;
	}

	public static Icon getIcon(String name) {
		Icon icon=new Icon();
		icon.loadFromFile(name);
		return icon;
	}

}
