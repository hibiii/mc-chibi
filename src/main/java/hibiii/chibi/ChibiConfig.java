package hibiii.chibi;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import me.sargunvohra.mcmods.autoconfig1u.ConfigData;
import me.sargunvohra.mcmods.autoconfig1u.annotation.Config;
import me.sargunvohra.mcmods.autoconfig1u.serializer.ConfigSerializer.SerializationException;

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
	public enum SyncAttack {
		OFF,
		TICK_SCALING,
		COOLDOWN_CORRECTION,
		HYBRID
	}
	public SyncAttack syncAttack = SyncAttack.OFF;
	
	// PlayerParticles
	public boolean playerParticles = false;
	public enum ParticleType {
		ASH,
		ENDER_SMOKE, // Portal
		GREEN_SPARKLES, // Composter
		HEART,
		MYCELIUM,
		PURPLE_SPARKLES, // Witch
		WHITE_ASH,
		WHITE_SPARKLES // End rod
	}
	public ParticleType particleType = ParticleType.HEART;
	public int particleInterval = 1;
	
	// grondag the barbarian? grondag the helpful renderer guy :)
	// Code made after studying Canvas's menu code
	public Screen getScreen (Screen previous) {
		final ConfigBuilder builder = ConfigBuilder.create()
				.setParentScreen(previous)
				.setTitle(new TranslatableText("chibi.menu.title"))
				.setDefaultBackgroundTexture(new Identifier("chibi:menu_background.png"))
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
				.setDefaultValue(PreventSwing.OFF)
				.setEnumNameProvider( value -> new TranslatableText("chibi.option.prevent_swing." + value.toString()))
				.setTooltip(new TranslatableText("chibi.option.prevent_swing.tooltip"))
				.setSaveConsumer(newValue -> preventSwing = newValue)
				.build())
			
			// Sync Attack
			.addEntry(entryBuilder.startEnumSelector(
					new TranslatableText("chibi.option.sync_attack"),
					SyncAttack.class,
					syncAttack)
				.setDefaultValue(SyncAttack.OFF)
				.setEnumNameProvider(value -> new TranslatableText("chibi.option.sync_attack." + value.toString()))
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
				.setDefaultValue(false)
				.setTooltip(
						new TranslatableText("chibi.option.player_particles.tooltip"),
						new TranslatableText("chibi.warn.generic.client_side").formatted(Formatting.GRAY))
				.setSaveConsumer(newValue -> playerParticles = newValue)
				.build())
			.addEntry(entryBuilder.startEnumSelector(
					new TranslatableText("chibi.option.particle_type"),
					ParticleType.class,
					particleType)
				.setDefaultValue(ParticleType.HEART)
				.setEnumNameProvider(value -> new TranslatableText("chibi.option.particle." + value.toString()))
				.setSaveConsumer(newValue -> particleType = newValue)
				.build())
			.addEntry(entryBuilder.startIntSlider(
					new TranslatableText("chibi.option.particle_intensity"),
					21 - particleInterval,
					1, 20)
				.setDefaultValue(10)
				.setSaveConsumer(newValue -> particleInterval = 21 - newValue)
				.build());
		
		builder.setSavingRunnable(() -> {
			try {
				Chibi.configSerializer.serialize(this);
			} catch (SerializationException e) {
				e.printStackTrace();
			}
		});
		
		return builder.build();
	}
}
