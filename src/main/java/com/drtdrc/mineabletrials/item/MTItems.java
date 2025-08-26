package com.drtdrc.mineabletrials.item;

import com.drtdrc.mineabletrials.MineableTrials;
import net.minecraft.block.Blocks;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;

public final class MTItems {
    private MTItems() {}

    public static BlockItem OMINOUS_VAULT_ITEM;

    public static void init() {
        // Registry key + settings are required (prevents "Item id not set" crash)
        RegistryKey<Item> OMINOUS_VAULT_KEY =
                RegistryKey.of(RegistryKeys.ITEM, id("ominous_vault"));

        OMINOUS_VAULT_ITEM = Registry.register(
                Registries.ITEM, OMINOUS_VAULT_KEY,
                new OminousVaultItem(
                        Blocks.VAULT,
                        new Item.Settings().registryKey(OMINOUS_VAULT_KEY).maxCount(1)
                )
        );
    }

    public static Identifier id(String path) {
        return Identifier.of(MineableTrials.MOD_ID, path);
    }
}
