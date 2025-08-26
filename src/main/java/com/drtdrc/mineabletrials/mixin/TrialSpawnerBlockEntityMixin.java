package com.drtdrc.mineabletrials.mixin;

import com.drtdrc.mineabletrials.duck.TrialSpawnerOminousAccess;
import net.minecraft.block.entity.TrialSpawnerBlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(TrialSpawnerBlockEntity.class)
public abstract class TrialSpawnerBlockEntityMixin implements TrialSpawnerOminousAccess {

    @Unique private boolean mt$allowOminous = false;

    // persist ONLY the ominous flag on the BE
    @Inject(method = "readData", at = @At("HEAD"))
    private void mineabletrials$readData(net.minecraft.storage.ReadView view, CallbackInfo ci) {
        this.mt$allowOminous = view.getBoolean("mt_allow_ominous", this.mt$allowOminous);
    }

    @Inject(method = "writeData", at = @At("HEAD"))
    private void mineabletrials$writeData(net.minecraft.storage.WriteView view, CallbackInfo ci) {
        if (this.mt$allowOminous) view.putBoolean("mt_allow_ominous", true);
        else view.remove("mt_allow_ominous");
    }

    // no serverTick here — spawners tick in TrialSpawnerLogic

    @Override public boolean mt$getAllowOminous() { return mt$allowOminous; }
    @Override public void mt$setAllowOminous(boolean allow) { this.mt$allowOminous = allow; }
}
