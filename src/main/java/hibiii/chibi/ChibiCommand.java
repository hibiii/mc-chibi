//package hibiii.chibi;
//
//import com.mojang.brigadier.CommandDispatcher;
//import com.mojang.brigadier.arguments.StringArgumentType;
//import com.mojang.brigadier.context.CommandContext;
//
//import hibiii.chibi.ChibiConfig.ParticleType;
//import io.github.cottonmc.clientcommands.ArgumentBuilders;
//import io.github.cottonmc.clientcommands.ClientCommandPlugin;
//import io.github.cottonmc.clientcommands.CottonClientCommandSource;
//import net.minecraft.command.argument.ArgumentTypes;
//import net.minecraft.command.argument.TextArgumentType;
//import net.minecraft.server.command.ServerCommandSource;
//import net.minecraft.text.Text;
//import net.minecraft.text.TranslatableText;
//
//public class ChibiCommand implements ClientCommandPlugin {
//
//	@Override
//	public void registerCommands(CommandDispatcher<CottonClientCommandSource> dispatcher) {
//		dispatcher.register(ArgumentBuilders.literal("chibi")
//			.then(ArgumentBuilders.literal("particle")
//				.then(ArgumentBuilders.literal("type")
//					.then(ArgumentBuilders.argument("particle", StringArgumentType.string()))
//						.executes(context -> {
//							ParticleType temp = ParticleType.valueOf(StringArgumentType.getString(context, "particle").toUpperCase());
//							if (temp != null) {
//								Chibi.config.particleType = temp;
//								context.getSource().sendFeedback(new TranslatableText("chibi.commands.chibi.particle.type.success"));
//								return 1;
//							}
//							context.getSource().sendFeedback(new TranslatableText("chibi.commands.chibi.particle.type.fail"));
//							return 0;
//						}))));
//	}
//
//}
