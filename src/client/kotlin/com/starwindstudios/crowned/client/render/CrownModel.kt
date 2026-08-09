package com.starwindstudios.crowned.client.render

import com.starwindstudios.crowned.Crowned
import net.minecraft.client.model.PlayerModel
import net.minecraft.client.model.geom.ModelLayerLocation
import net.minecraft.client.model.geom.ModelPart
import net.minecraft.client.model.geom.PartPose
import net.minecraft.client.model.geom.builders.CubeDeformation
import net.minecraft.client.model.geom.builders.CubeListBuilder
import net.minecraft.client.model.geom.builders.LayerDefinition
import net.minecraft.resources.ResourceLocation

class CrownModel(root: ModelPart) : PlayerModel(root, false) {
    companion object {
        val LAYER_LOCATION: ModelLayerLocation = ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(Crowned.ID, "player"), "crown")

        fun createLayerDefinition(): LayerDefinition {
            val mesh = createMesh(CubeDeformation.NONE, false)
            val root = mesh.root.clearRecursively()
            val body = root.getChild("body")

            body.addOrReplaceChild("crown",
                CubeListBuilder
                    .create()
                    .texOffs(0, 0)
                    .addBox(
                        -4.0f, -7.0f, -4.0f,
                        8.0f, 7.0f, 8.0f,
                        CubeDeformation(0.0f)
                    ),
                PartPose.offset(0.0f, -9.5f, 0.0f)
            )

            return LayerDefinition.create(mesh, 32, 32)
        }
    }
}