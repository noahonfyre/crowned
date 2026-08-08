package com.starwindstudios.crowned.client.render

import com.mojang.blaze3d.vertex.PoseStack
import com.mojang.math.Axis
import com.starwindstudios.crowned.Crowned
import com.starwindstudios.crowned.client.access.RenderStateAccessor
import net.minecraft.client.model.HumanoidModel
import net.minecraft.client.model.PlayerModel
import net.minecraft.client.renderer.RenderType
import net.minecraft.client.renderer.SubmitNodeCollector
import net.minecraft.client.renderer.entity.EntityRendererProvider
import net.minecraft.client.renderer.entity.LivingEntityRenderer
import net.minecraft.client.renderer.entity.RenderLayerParent
import net.minecraft.client.renderer.entity.layers.RenderLayer
import net.minecraft.client.renderer.entity.state.AvatarRenderState
import net.minecraft.resources.ResourceLocation

class CrownRenderLayer(
    parent: RenderLayerParent<AvatarRenderState, PlayerModel>,
    ctx: EntityRendererProvider.Context
) : RenderLayer<AvatarRenderState, PlayerModel>(parent) {
    private var model: HumanoidModel<AvatarRenderState> = CrownModel(ctx.modelSet.bakeLayer(CrownModel.LAYER_LOCATION))

    override fun submit(
        poseStack: PoseStack,
        submitNodeCollector: SubmitNodeCollector,
        lightCoords: Int,
        state: AvatarRenderState,
        yRot: Float,
        xRot: Float
    ) {
        if(state !is RenderStateAccessor) return
        val player = state.player
        if(player.isCrouching) return

        if(!player.inventory.contains { stack -> stack.item == Crowned.CROWN }) return

        poseStack.pushPose()

        val overlayCoords = LivingEntityRenderer.getOverlayCoords(state, 0.0f)

        val rotation = (player.tickCount % 100f) / 100f * 360f
        poseStack.mulPose(Axis.YP.rotationDegrees(rotation))

        submitNodeCollector.submitModel(
            this.model,
            state,
            poseStack,
            RenderType.entityCutoutNoCull(ResourceLocation.fromNamespaceAndPath(Crowned.ID, "textures/entity/crown.png")),
            lightCoords,
            overlayCoords,
            state.outlineColor,
            null
        )

        poseStack.popPose()
    }
}