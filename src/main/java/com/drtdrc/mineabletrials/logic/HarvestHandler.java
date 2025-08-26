package com.drtdrc.mineabletrials.logic;

import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;
import net.minecraft.block.Blocks;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.ItemEnchantmentsComponent;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.state.property.Properties;

public final class HarvestHandler {
    private HarvestHandler() {}

    public static void register() {
        PlayerBlockBreakEvents.BEFORE.register((world, player, pos, state, be) -> {
            if (world.isClient()) return true;

            var block = state.getBlock();
            boolean isVault = block == Blocks.VAULT;
            boolean isTrialSpawner = block == Blocks.TRIAL_SPAWNER;
            if (!isVault && !isTrialSpawner) return true;

            ItemStack tool = player.getMainHandStack();

            // iron+ pickaxe
            if (!(tool.isIn(ItemTags.PICKAXES) && tool.isSuitableFor(Blocks.DIAMOND_ORE.getDefaultState()))) {
                return true;
            }

            // must have Silk Touch
            if (!hasSilkTouch(tool)) return true;

            // Decide *which* vanilla item to drop
            ItemStack drop;
//            if (isVault) {
//                boolean ominous = state.contains(Properties.OMINOUS)
//                        && state.get(Properties.OMINOUS);
//                if (ominous) {
//                    // Drop our ominous-vault item
//                    drop = new ItemStack(MTItems.OMINOUS_VAULT_ITEM);
//                } else {
//                    // Drop the normal vanilla vault item
//                    var item = block.asItem();
//                    if (item == Items.AIR) return true;
//                    drop = new ItemStack(item);
//                }
//            } else { // Trial Spawner
//                var item = block.asItem();
//                if (item == Items.AIR) return true;
//                drop = new ItemStack(item);
//            }
            var item = block.asItem();
            if (item == Items.AIR) return true;
            drop = new ItemStack(item);

            // Break without drops and spawn our chosen vanilla/custom stack
            world.breakBlock(pos, false, player);
            net.minecraft.block.Block.dropStack(world, pos, drop);
            return false;

        });
    }

    private static boolean hasSilkTouch(ItemStack tool) {
        ItemEnchantmentsComponent ench =
                tool.getOrDefault(DataComponentTypes.ENCHANTMENTS, ItemEnchantmentsComponent.DEFAULT);
        for (RegistryEntry<Enchantment> e : ench.getEnchantments()) {
            @SuppressWarnings("unchecked")
            RegistryEntry<net.minecraft.enchantment.Enchantment> ee =
                    e;
            if (ee.matchesKey(Enchantments.SILK_TOUCH) && ench.getLevel(ee) > 0) {
                return true;
            }
        }
        return false;
    }
}
