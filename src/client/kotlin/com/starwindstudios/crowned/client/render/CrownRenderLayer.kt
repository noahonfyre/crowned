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
import kotlin.math.tanh


class CrownRenderLayer(
    parent: RenderLayerParent<AvatarRenderState, PlayerModel>,
    val ctx: EntityRendererProvider.Context
) : RenderLayer<AvatarRenderState, PlayerModel>(parent) {
    private var model: HumanoidModel<AvatarRenderState> = CrownModel(ctx.modelSet.bakeLayer(CrownModel.LAYER_LOCATION))
    val textures = mapOf(
        Crowned.CHERRY_CROWN to ResourceLocation.fromNamespaceAndPath(Crowned.ID, "textures/entity/crown/cherry.png"),
        Crowned.CANYON_CROWN to ResourceLocation.fromNamespaceAndPath(Crowned.ID, "textures/entity/crown/canyon.png"),
        Crowned.JUNGLE_CROWN to ResourceLocation.fromNamespaceAndPath(Crowned.ID, "textures/entity/crown/jungle.png"),
        Crowned.END_CROWN to ResourceLocation.fromNamespaceAndPath(Crowned.ID, "textures/entity/crown/end.png"),
        Crowned.EIS_CROWN to ResourceLocation.fromNamespaceAndPath(Crowned.ID, "textures/entity/crown/eis.png"),
        Crowned.PILZ_CROWN to ResourceLocation.fromNamespaceAndPath(Crowned.ID, "textures/entity/crown/pilz.png")
    )
    val fallbackTexture = ResourceLocation.fromNamespaceAndPath(Crowned.ID, "textures/entity/crown.png")

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

        var texture: ResourceLocation? = null

        if(!player.inventory.contains {
            stack ->
                val isMatching = stack.`is`(Crowned.CROWNS)
                texture = textures[stack.item]
                return@contains isMatching
        }) return

        if(texture == null) {
            texture = fallbackTexture
        }

        poseStack.pushPose()

        val overlayCoords = LivingEntityRenderer.getOverlayCoords(state, 0.0f)

        val progress = ((player.tickCount + state.ageInTicks) / 2f % 100f) / 100f * 360f
        val rotation = (tanh((progress/50f)-3.6f)+1f)*180f

        poseStack.mulPose(Axis.YP.rotationDegrees(rotation))

        submitNodeCollector.submitModel(
            this.model,
            state,
            poseStack,
            RenderType.entityCutoutNoCull(texture),
            lightCoords,
            overlayCoords,
            state.outlineColor,
            null
        )

        poseStack.popPose()
    }
}