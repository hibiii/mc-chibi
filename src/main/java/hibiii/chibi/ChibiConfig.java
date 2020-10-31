package hibiii.chibi;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import me.sargunvohra.mcmods.autoconfig1u.ConfigData;
import me.sargunvohra.mcmods.autoconfig1u.annotation.Config;

@Config(name = "chibi")
public class ChibiConfig implements ConfigData {
	
	// PreventWeaponSwing
	public enum PreventSwing {
		OFF,
		LENIENT,
		STRICT
	}
	public PreventSwing preventSwing = PreventSwing.OFF;
	
	// SyncAttack
	public boolean syncAttack = false;
	
	// PlayerParticles
	public boolean playerParticles = false;
	
	// grondag the barbarian? grondag the helpful renderer guy :)
	// Code made after studying Canvas's menu code
	public Screen getScreen (Screen previous) {
		final ConfigBuilder builder = ConfigBuilder.create()
				.setParentScreen(previous)
				.setTitle(new TranslatableText("chibi.menu.title"))
				.setDefaultBackgroundTexture(new Identifier("textures/environment/end_sky.png"))
				.setAlwaysShowTabs(false)
				.setDoesConfirmSave(false);
		builder.setGlobalized(true);
		builder.setGlobalizedExpanded(false);
		
		final ConfigEntryBuilder entryBuilder = builder.entryBuilder();
		
		// --- Combat --
		builder.getOrCreateCategory(new TranslatableText("chibi.menu.category.combat"))
		
			// Prevent Swing
			.addEntry(entryBuilder.startEnumSelector(
					new TranslatableText("chibi.option.prevent_swing"),
					PreventSwing.class,
					preventSwing)
				.setEnumNameProvider( value -> new TranslatableText("chibi.option.prevent_swing." + value.toString()))
				.setTooltip(new TranslatableText("chibi.option.prevent_swing.tooltip"))
				.setSaveConsumer(newValue -> preventSwing = newValue)
				.build())
			
			// Sync Attack
			.addEntry(entryBuilder.startBooleanToggle(
					new TranslatableText("chibi.option.sync_attack"),
					syncAttack)
				.setTooltip(
					new TranslatableText("chibi.option.sync_attack.tooltip"),
					new TranslatableText("chibi.warn.experimental").formatted(Formatting.RED))
				.setSaveConsumer(newValue -> syncAttack = newValue)
				.build())
			
			// Warning
			.addEntry(entryBuilder.startTextDescription(
					new TranslatableText ("chibi.option.combat.warning"))
				.setTooltip(
					new TranslatableText("chibi.warn.combat.line1"),
					new TranslatableText("chibi.warn.combat.line2"),
					new TranslatableText("chibi.warn.combat.line3"),
					new TranslatableText("chibi.warn.combat.line4"),
					new TranslatableText("chibi.warn.generic.dont_cheat").formatted(Formatting.YELLOW).formatted(Formatting.UNDERLINE))
				.build());
		
		// --- Cosmetic --
		builder.getOrCreateCategory(new TranslatableText("chibi.menu.category.cosmetic"))
		
			// Player Particles
			.addEntry(entryBuilder.startBooleanToggle(
					new TranslatableText("chibi.option.player_particles"),
					playerParticles)
				.setTooltip(
						new TranslatableText("chibi.option.player_particles.tooltip"),
						new TranslatableText("chibi.warn.generic.client_side").formatted(Formatting.GRAY))
				.setSaveConsumer(newValue -> playerParticles = newValue)
				.build());
		
		return builder.build();
	}
}
