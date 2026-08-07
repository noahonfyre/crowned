package com.starwindstudios.crowned.client

import com.starwindstudios.crowned.client.render.CrownModel
import com.starwindstudios.crowned.client.render.CrownRenderLayer
import net.fabricmc.api.ClientModInitializer
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry
import net.fabricmc.fabric.api.client.rendering.v1.LivingEntityFeatureRendererRegistrationCallback
import net.minecraft.client.model.PlayerModel
import net.minecraft.client.renderer.entity.RenderLayerParent
import net.minecraft.client.renderer.entity.player.AvatarRenderer
import net.minecraft.client.renderer.entity.state.AvatarRenderState

object CrownedClient : ClientModInitializer {
	override fun onInitializeClient() {
        EntityModelLayerRegistry.registerModelLayer(CrownModel.LAYER_LOCATION, CrownModel::createLayerDefinition)

        LivingEntityFeatureRendererRegistrationCallback.EVENT.register { type, renderer, helper, context ->
            if(renderer !is AvatarRenderer) return@register
            val parent = renderer as RenderLayerParent<AvatarRenderState, PlayerModel>

            helper.register(CrownRenderLayer(parent, context))
        }
	}
}