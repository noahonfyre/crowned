package com.starwindstudios.crowned.client.mixin;

import com.starwindstudios.crowned.client.access.RenderStateAccessor;
import net.minecraft.client.renderer.entity.state.AvatarRenderState;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(AvatarRenderState.class)
public class AvatarRenderStateMixin implements RenderStateAccessor {
    @Unique
    public Player player;

    @Override
    public Player getPlayer() {
        return player;
    }

    @Override
    public void setPlayer(@NotNull Player player) {
        this.player = player;
    }
}
