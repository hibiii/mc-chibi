package hibiii.hibex;

import hibiii.hibex.Hibix.OptionPreventSwing;
import io.github.prospector.modmenu.api.ConfigScreenFactory;
import io.github.prospector.modmenu.api.ModMenuApi;
import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;

public class HibexModmenu implements ModMenuApi {
	
	// Cloth Config is holding my hand through this, thx :)
	@Override
	public ConfigScreenFactory<?> getModConfigScreenFactory() {
		return parent -> {
			ConfigBuilder builder = ConfigBuilder.create();
			ConfigEntryBuilder entryBuilder = builder.entryBuilder();
			builder.setParentScreen(parent).setTitle(new TranslatableText("hibex.menu.title"));
			
			// This is dumb (chaining methods), but it's also really cool.
			builder.getOrCreateCategory(new TranslatableText("hibex.menu.general"))
			
			// Prevent Weapon Swing
			.addEntry(entryBuilder
					.startEnumSelector(new TranslatableText("hibex.option.weapon_swing_threshold"),OptionPreventSwing.class,Hibix.optionPreventSwing)
					.setDefaultValue(OptionPreventSwing.OFF)
					.setSaveConsumer(newValue -> Hibix.optionPreventSwing = newValue)
					.setEnumNameProvider(enumKey -> {
						switch ((Hibix.OptionPreventSwing)enumKey) {
							case OFF: return new TranslatableText("hibex.option.weapon_swing_threshold.none");
							case LENIENT: return new TranslatableText("hibex.option.weapon_swing_threshold.lenient");
							case STRICT: return new TranslatableText("hibex.option.weapon_swing_threshold.strict");
						}
						return Text.of("InvalidEnumVal");})
					.build())
			
			// PWS Warning
			.addEntry(entryBuilder.startTextDescription(new TranslatableText("hibex.option.weapon_swing_threshold.warning"))
					.build());
			
			return builder.build();
		};
	}
}
