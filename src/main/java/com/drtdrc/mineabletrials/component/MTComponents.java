package com.drtdrc.mineabletrials.component;

import com.drtdrc.mineabletrials.MineableTrials;
import com.mojang.serialization.Codec;
import net.minecraft.component.ComponentType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public final class MTComponents {
    private MTComponents() {}

    public static ComponentType<Integer> VAULT_COOLDOWN_TICKS;
    public static ComponentType<Boolean> ALLOW_OMINOUS_SPAWNER;

    public static void init() {
        VAULT_COOLDOWN_TICKS = Registry.register(
                Registries.DATA_COMPONENT_TYPE,
                Identifier.of(MineableTrials.MOD_ID, "vault_cooldown_ticks"),
                ComponentType.<Integer>builder().codec(Codec.INT).build()
        );

        ALLOW_OMINOUS_SPAWNER = Registry.register(
                Registries.DATA_COMPONENT_TYPE,
                Identifier.of(MineableTrials.MOD_ID, "allow_ominous_spawner"),
                ComponentType.<Boolean>builder().codec(Codec.BOOL).build()
        );
    }
}
