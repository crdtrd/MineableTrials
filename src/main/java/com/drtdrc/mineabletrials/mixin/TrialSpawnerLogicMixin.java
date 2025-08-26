package com.drtdrc.mineabletrials.mixin;

import com.drtdrc.mineabletrials.duck.TrialSpawnerPlacementCooldownAccess;
import net.minecraft.block.spawner.TrialSpawnerLogic;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.storage.ReadView;
import net.minecraft.storage.WriteView;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(TrialSpawnerLogic.class)
public class TrialSpawnerLogicMixin implements TrialSpawnerPlacementCooldownAccess {

    @Unique private int mt$placementCooldown = 0;

    // Tick on server: decrement our placement cooldown while the spawner exists in-world
    @Inject(method = "tickServer", at = @At("HEAD"))
    private void mineabletrials$tickServer(ServerWorld world, BlockPos pos, boolean ominous, CallbackInfo ci) {
        if (mt$placementCooldown > 0) mt$placementCooldown--;
    }

    // Persist the placement cooldown on the logic itself
    @Inject(method = "readData", at = @At("HEAD"))
    private void mineabletrials$readData(ReadView view, CallbackInfo ci) {
        this.mt$placementCooldown = view.getInt("mt_placed_cd", this.mt$placementCooldown);
    }

    @Inject(method = "writeData", at = @At("HEAD"))
    private void mineabletrials$writeData(WriteView view, CallbackInfo ci) {
        if (this.mt$placementCooldown > 0) view.putInt("mt_placed_cd", this.mt$placementCooldown);
        else view.remove("mt_placed_cd");
    }

    // Duck impl
    @Override public int  mt$getPlacementCooldown() { return mt$placementCooldown; }
    @Override public void mt$setPlacementCooldown(int ticks) { mt$placementCooldown = Math.max(0, ticks); }
}
