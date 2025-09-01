package com.drtdrc.mineabletrials.mixin;

import net.minecraft.block.spawner.TrialSpawnerConfig;
import net.minecraft.block.spawner.TrialSpawnerLogic;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.LootTables;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.collection.Pool;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(TrialSpawnerLogic.FullConfig.class)
public class TrialSpawnerLogicFullConfigMixin {
    @Shadow @Final @Mutable public static TrialSpawnerLogic.FullConfig DEFAULT;

    @Inject(method = "<clinit>", at = @At("TAIL"))
    private static void overrideDefaultOminousLootTable(CallbackInfo ci) {

        Pool<RegistryKey<LootTable>> ominousEject = Pool.<RegistryKey<LootTable>>builder()
                .add(LootTables.OMINOUS_TRIAL_CHAMBER_KEY_SPAWNER, 3)
                .add(LootTables.OMINOUS_TRIAL_CHAMBER_CONSUMABLES_SPAWNER, 7)
                .build();

        TrialSpawnerConfig ominousCfg = TrialSpawnerConfig.builder()
                .lootTablesToEject(ominousEject)
                .build();

        DEFAULT = new TrialSpawnerLogic.FullConfig(
                RegistryEntry.of(TrialSpawnerConfig.DEFAULT),
                RegistryEntry.of(ominousCfg),
                DEFAULT.targetCooldownLength(),
                DEFAULT.requiredPlayerRange()
        );
    }
}
