package com.starwindstudios.crowned

import net.fabricmc.api.ModInitializer
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents
import net.minecraft.core.Registry
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.core.registries.Registries
import net.minecraft.resources.ResourceKey
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.item.CreativeModeTabs
import net.minecraft.world.item.Item
import org.slf4j.Logger
import org.slf4j.LoggerFactory

object Crowned : ModInitializer {
	const val ID: String = "crowned"

	val LOGGER: Logger = LoggerFactory.getLogger(ID)

    val CROWN = register("crown") { props -> Item(props) }

	override fun onInitialize() {
		LOGGER.info("")

        ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.TOOLS_AND_UTILITIES)
            .register { creativeTab -> creativeTab.accept(CROWN) }
    }

    fun <T: Item> register(name: String, factory: (Item.Properties) -> T): T {
        val key: ResourceKey<Item> = ResourceKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(ID, name))
        val item = Registry.register(BuiltInRegistries.ITEM, key, factory(Item.Properties().setId(key)))
        return item
    }
}
