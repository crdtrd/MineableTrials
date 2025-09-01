package com.drtdrc.mineabletrials.mixin;


import net.minecraft.block.*;
import net.minecraft.block.enums.NoteBlockInstrument;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.sound.BlockSoundGroup;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;

import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.function.Function;

@Mixin(Blocks.class)
public class BlocksMixin {
    @Inject(
            method = "register(Lnet/minecraft/registry/RegistryKey;Ljava/util/function/Function;Lnet/minecraft/block/AbstractBlock$Settings;)Lnet/minecraft/block/Block;",
            at = @At(value = "HEAD"),
            cancellable = true
    )
    private static void onRegister(RegistryKey<Block> key, Function<AbstractBlock.Settings, Block> factory, AbstractBlock.Settings settings, CallbackInfoReturnable<Block> cir) {
        if (key.getValue().toString().contentEquals("minecraft:vault")) {

            AbstractBlock.Settings newVaultSettings = AbstractBlock.Settings.create().mapColor(MapColor.STONE_GRAY).instrument(NoteBlockInstrument.BASEDRUM).nonOpaque().sounds(BlockSoundGroup.VAULT).luminance(state -> state.get(VaultBlock.VAULT_STATE).getLuminance()).strength(50.0f).blockVision(Blocks::never).requiresTool();

            Block block = factory.apply(newVaultSettings.registryKey(key));
            cir.setReturnValue(Registry.register(Registries.BLOCK, key, block));
            return;
        }
        if (key.getValue().toString().contentEquals("minecraft:trial_spawner")) {
            AbstractBlock.Settings newTrialSpawnerSettings = AbstractBlock.Settings.create().mapColor(MapColor.STONE_GRAY).instrument(NoteBlockInstrument.BASEDRUM).luminance(state -> state.get(TrialSpawnerBlock.TRIAL_SPAWNER_STATE).getLuminance()).strength(50.0f).sounds(BlockSoundGroup.TRIAL_SPAWNER).blockVision(Blocks::never).nonOpaque().requiresTool();

            Block block = factory.apply(newTrialSpawnerSettings.registryKey(key));
            cir.setReturnValue(Registry.register(Registries.BLOCK, key, block));
            return;
        }
    }
}