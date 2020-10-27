package hibiii.hibex;

import io.github.prospector.modmenu.api.ConfigScreenFactory;
import io.github.prospector.modmenu.api.ModMenuApi;
import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;

public class HibexModmenu implements ModMenuApi {
	// Sensible19
	public enum WeaponSwingControl {
		NONE,
		LENIENT,
		STRICT
	}
	public static WeaponSwingControl weaponSwingThreshold;
	
	public HibexModmenu() {
		weaponSwingThreshold = WeaponSwingControl.NONE;
	}
	
	@Override
	public ConfigScreenFactory<?> getModConfigScreenFactory() {
		return parent -> {
			
			ConfigBuilder builder = ConfigBuilder.create();
			ConfigEntryBuilder entryBuilder = builder.entryBuilder();
			builder.setParentScreen(parent).setTitle(new TranslatableText("hibex.menu.title"));
			builder.getOrCreateCategory(new TranslatableText("hibex.menu.general"))
			.addEntry(entryBuilder
					.startEnumSelector(new TranslatableText("hibex.option.weapon_swing_threshold"),WeaponSwingControl.class,weaponSwingThreshold)
					.setDefaultValue(WeaponSwingControl.NONE)
					.setSaveConsumer(newValue -> weaponSwingThreshold = newValue)
					.setEnumNameProvider(enumKey -> {
						switch ((WeaponSwingControl)enumKey) {
							case NONE: return new TranslatableText("hibex.option.weapon_swing_threshold.none");
							case LENIENT: return new TranslatableText("hibex.option.weapon_swing_threshold.lenient");
							case STRICT: return new TranslatableText("hibex.option.weapon_swing_threshold.strict");
						}
						return Text.of("SwitchError");})
					.build())
			.addEntry(entryBuilder.startTextDescription(new TranslatableText("hibex.option.weapon_swing_threshold.warning"))
					.build());
			return builder.build();
		};
	}
}
