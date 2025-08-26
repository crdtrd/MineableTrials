package com.drtdrc.mineabletrials.mixin;

import net.minecraft.block.entity.TrialSpawnerBlockEntity;
import net.minecraft.block.spawner.TrialSpawnerLogic;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(TrialSpawnerBlockEntity.class)
public interface TrialSpawnerBlockEntityAccessor {
    // Field name may be 'logic' in your Yarn; if your IDE shows a different name, change the string accordingly.
    @Accessor("logic")
    TrialSpawnerLogic mineabletrials$getLogic();
}
