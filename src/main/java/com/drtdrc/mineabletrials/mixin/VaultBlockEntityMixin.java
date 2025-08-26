package com.drtdrc.mineabletrials.mixin;

import com.drtdrc.mineabletrials.duck.VaultServerDataAccess;
import net.minecraft.block.entity.VaultBlockEntity;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(VaultBlockEntity.class)
public abstract class VaultBlockEntityMixin implements VaultServerDataAccess {
//    @Unique private static final int MT_HOUR_TICKS = 20 * 60 * 60; // 72,000
//    @Unique private int mt$cooldownTicks = 0;
//
//    // Persist using the new views (no NBT imports needed)
//    @Inject(method = "readData", at = @At("HEAD"))
//    private void mt$readData(net.minecraft.storage.ReadView view, CallbackInfo ci) {
//        this.mt$cooldownTicks = Math.max(0, view.getInt("mt_cooldown_ticks", this.mt$cooldownTicks));
//    }
//
//    @Inject(method = "writeData", at = @At("HEAD"))
//    private void mt$writeData(net.minecraft.storage.WriteView view, CallbackInfo ci) {
//        if (this.mt$cooldownTicks > 0) {
//            view.putInt("mt_cooldown_ticks", this.mt$cooldownTicks);
//        } else {
//            view.remove("mt_cooldown_ticks");
//        }
//    }
//
//    // Accessor impl
//    @Override public int mt$getCooldownTicks() { return mt$cooldownTicks; }
//    @Override public void mt$setCooldownTicks(int ticks) { this.mt$cooldownTicks = Math.max(0, ticks); }
}
