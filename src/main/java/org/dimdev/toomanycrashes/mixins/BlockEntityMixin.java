package org.dimdev.toomanycrashes.mixins;

import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.crash.CrashReportSection;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = BlockEntity.class, priority = 10000)
public class BlockEntityMixin {
    private boolean skipNbt = false;

    @Inject(method = "populateCrashReport", at = @At("TAIL"))
    private void onPopulateCrashReport(CrashReportSection section, CallbackInfo ci) {
        if (!skipNbt) {
            skipNbt = true;
            section.add("Block Entity NBT", () -> ((BlockEntity) (Object) this).toTag(new CompoundTag()).toString());
            skipNbt = false;
        }
    }
}
