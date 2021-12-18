# Chibi Mod
## Hibi's Custom Mod

This mod is a test bed for experimental features that may be turned into their own mods or may be not, experiments in implementation or ideas, or features that may not be 100% in the green.
Some of these features historically have been a click limiter when attacking, custom sounds for the player getting hurt and not all players, and waving your hands at will.
If you play on a multiplayer server, please check with moderators which features are allowed by the server rules.

This mod won't be published in the near future, and isn't suggested for use in production.
Feel free to mess around with it though.

*Reality can be whatever I want.*\
-funny purple man

## Feature History

### A/D Boosting
- Reasoning: Pressing A or D in boats will give you a very minor boost if you're not pressing forward or backwards
- Implementation: Injection before `setVelocity` into `BoatEntity updatePaddles` caputure local float f, add 0.005 to it (equivalent to simple turning) when left and right are pressed.
- Result: Failure - nothing happened even with a correct implementation.
- Present: No.

### Akimbo
- Reasoning: rar rar rar holding two weapon good rar rar rar
- Implementation: (none)
- Result: (none)
- Present: No.

### Custom Player Hurt
- Reasoning: Add some personalization to getting hurt.
- Implementaion: Return a different sound from the client player when it's asked to give a hurt sound.
- Result: Succes!
- Present: No.

### Hat
- Reasoning: Hat
- Implementation: Pretend there's a hat item on the head when the player is rendered.
- Result: Better Implementation Needed - a feature renderer would be easier and would allow more customization.
- Present: No - assets remain.

### Player Particles
- Reasoning: Add some flair to the player, akin to Hypixel's.
- Implementation: Injection at head into `MinecraftClient tick`, add particles to the world at the player's position when tick counter mod particle interval is 0.
- Result: Success!
- Present: No.

### Prevent Swing
- Reasoning: Prevents the player from attacking when their hit wouldn't be 100% effective, seen in combat tests.
- Implementation: Cancellable injection at head into `MinecraftClient doAttack`, check if the attack cooldown on attack is less than a threshold, and if it is, cancel the attack.
- Result: Success!
- Present: No.

### Show Own Name
- Reasoning: Why can't I see my own name if I can see other players' names?
- Implementation: Cancellable injection at head into `LivingEntityRenderer hasLabel`, check if normally the nametag would show if I wasn't *the* client player.
- Result: Success!
- Present: Yes.

### Straferunning
- Reasoning: th e speed is al ot mate
- Implementation: Prevent 3D vector normalization if called by `Entity movementInputToVelocity`.
- Result: (none)
- Present: Yes.

### Sync Attack
- Reasoning: Clientside TPS is almost always 20 TPS but server TPS can easily dip below 18, and attack cooldowns are calculated tickwise, not by realtime.
- Implementaion: Cooldown correction rolls back the cooldown every tick by the difference between the client and the server. Tick scaling adds to the cooldown only when the server has done at least 1 tick. Hybrid uses both methods. Injection at head into `PlayerEntity tick`. The TPS estimation is done by injecting at head into `ClientPlayNetworkHandler onWorldTimeUpdate`.
- Result: Mixed.
- Present: No.

### Troglodyte
- Reasoning: Bring back a feature from alpha versions: left click stuff to use it. Ever heard of punching a door open?
- Implementation: Inject into `MinecraftClient doAttack` after the game is sure it hit a block was hit. If you can use on the block, then do so without cancelling.
- Result: Failure - normal blocks in hand, flint and steel, tools would be used on the block as the original feature was more subtle.
- Present: No.

### Walk Away
- Reasoning: We have a button for going fast, what if we had a button for going slowly?
- Implementation: Register to the start of a client tick to store if the walk button is pressed. Cancellable injection at head into `ClientPlayerEntity shouldSlowDown` and if the key is held down, return yes.
- Result: Success!
- Present: Yes.

### Wave Away
- Reasoning: We like to punch the air when looking at something far away to "point" at it. Why not wave the arms for things that are close by?
- Implementaion: Register to the start of a client tick to wave the respective hand if a key is pressed. Cancellable injection at tail into `MinecraftClient doItemUse` and swing the off-hand if (a) the crosshair is targetting nothing and (b) the off-hand is empty.
- Result: Success!
- Present: Yes.

## Turbo Thanks

"Thanks" can't simply describe my grattitude for these people, so a turbo thanks it is:

- supersaiyansubtlety and Reece for helping me fix missing references early on
- LlamaLad7 for helping me fix Mixin shenanigans on PlayerHurtOverride, twice

## License

This mod wouldn't have a license if not having a license wasn't a bad thing, so it's licensed under Unlicense.
