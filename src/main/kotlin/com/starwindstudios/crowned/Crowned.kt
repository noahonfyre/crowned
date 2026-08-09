package com.starwindstudios.crowned

import jdk.javadoc.internal.doclets.formats.html.Signatures
import net.fabricmc.api.ModInitializer
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents
import net.minecraft.core.Registry
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.core.registries.Registries
import net.minecraft.resources.ResourceKey
import net.minecraft.resources.ResourceLocation
import net.minecraft.tags.TagKey
import net.minecraft.world.item.CreativeModeTabs
import net.minecraft.world.item.Item
import org.slf4j.Logger
import org.slf4j.LoggerFactory

object Crowned : ModInitializer {
	const val ID: String = "crowned"

	val LOGGER: Logger = LoggerFactory.getLogger(ID)

    val CROWN = register("crown") { props -> Item(props) }

    val CHERRY_CROWN = register("cherry_crown") { props -> Item(props) }
    val CANYON_CROWN = register("canyon_crown") { props -> Item(props) }
    val JUNGLE_CROWN = register("jungle_crown") { props -> Item(props) }
    val END_CROWN = register("end_crown") { props -> Item(props) }
    val EIS_CROWN = register("eis_crown") { props -> Item(props) }
    val PILZ_CROWN = register("pilz_crown") { props -> Item(props) }

    val CROWNS: TagKey<Item> = createKey(Registries.ITEM, "crowns")

	override fun onInitialize() {
		LOGGER.info("")

        ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.TOOLS_AND_UTILITIES)
            .register { creativeTab -> creativeTab.accept(CROWN) }
    }

    fun <T : Any> createKey(registry: ResourceKey<Registry<T>>, path: String): TagKey<T> {
        return TagKey.create(registry, ResourceLocation.fromNamespaceAndPath(ID, path))
    }

    fun <T: Item> register(name: String, factory: (Item.Properties) -> T): T {
        val key: ResourceKey<Item> = ResourceKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(ID, name))
        val item = Registry.register(BuiltInRegistries.ITEM, key, factory(Item.Properties().setId(key)))
        return item
    }
}
