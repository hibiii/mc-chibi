package hibi.chibi;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

import net.fabricmc.loader.api.FabricLoader;

public class Config {
	
	public static boolean ignDisplay = false;
	public static boolean waving = false;
	public static boolean straferunning = false;
	public static boolean akimbo = false;
	public static boolean saveAnywhere = false;
	public static boolean noWhispers = false;
	public static boolean noLiterals = false;

	private static File file = new File(FabricLoader.getInstance().getConfigDir().toFile(), "chibi.properties");

	private Config() {}

	public static void load() {
		try {
			if(file.exists()) {
				BufferedReader br = new BufferedReader(new FileReader(file));
				String line = br.readLine();
				do {
					if(line.startsWith("ignDisplay "))
						ignDisplay = Boolean.parseBoolean(line.substring(11));
					if(line.startsWith("waving "))
						waving = Boolean.parseBoolean(line.substring(7));
					if(line.startsWith("straferunning "))
						straferunning = Boolean.parseBoolean(line.substring(14));
					if(line.startsWith("akimbo "))
						akimbo = Boolean.parseBoolean(line.substring(7));
					if(line.startsWith("hotbarSaveAnywhere "))
						saveAnywhere = Boolean.parseBoolean(line.substring(19));
					if(line.startsWith("noWhispsers "))
						noWhispers = Boolean.parseBoolean(line.substring(12));
					if(line.startsWith("noLiterals "))
						noLiterals = Boolean.parseBoolean(line.substring(11));
					line = br.readLine();
				} while (line != null);
				br.close();
			}
		}
		catch (Exception e) {
		}
	}

	public static void save() {
		try {
			FileWriter writer = new FileWriter(file);
			writer.write("ignDisplay " + Boolean.toString(ignDisplay) + "\n");
			writer.write("waving " + Boolean.toString(waving) + "\n");
			writer.write("straferunning " + Boolean.toString(straferunning) + "\n");
			writer.write("akimbo " + Boolean.toString(akimbo) + "\n");
			writer.write("hotbarSaveAnywhere " + Boolean.toString(saveAnywhere) + "\n");
			writer.write("noWhispers " + Boolean.toString(noWhispers) + "\n");
			writer.write("noLiterals " + Boolean.toString(noLiterals) + "\n");
			writer.close();
		}
		catch (Exception e) {
		}
	}
}
