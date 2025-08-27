package com.drtdrc.mineabletrials.mixin;

import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.VaultBlock;
import net.minecraft.block.enums.VaultState;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.Property;
import net.minecraft.util.math.Direction;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(VaultBlock.class)
public abstract class VaultBlockMixin extends BlockWithEntity {
    protected VaultBlockMixin(Settings settings) {
        super(settings);
    }

    @Shadow @Final public static EnumProperty<Direction> FACING;
    @Shadow @Final public static Property<VaultState> VAULT_STATE;


}
