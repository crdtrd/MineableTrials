package com.drtdrc.mineabletrials.item;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;

public class OminousVaultItem extends BlockItem {
    public OminousVaultItem(Block block, Settings settings) { super(block, settings); }

    @Override
    public ActionResult place(ItemPlacementContext ctx) {
        // Place normally, then flip the OMINOUS property to true if the block supports it
        ActionResult res = super.place(ctx);
        if (!res.isAccepted() || ctx.getWorld().isClient()) return res;

        var pos = ctx.getBlockPos();
        BlockState st = ctx.getWorld().getBlockState(pos);
        if (st.isOf(Blocks.VAULT) && st.contains(Properties.OMINOUS) && !st.get(Properties.OMINOUS)) {
            ctx.getWorld().setBlockState(pos, st.with(Properties.OMINOUS, true), 3);
        }
        return res;
    }
}
