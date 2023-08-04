package me.pr3.invasionplus;

import me.pr3.invasionplus.content.blocks.BlockEntities;
import me.pr3.invasionplus.content.blocks.nexus.Nexus;
import me.pr3.invasionplus.content.entities.Entities;
import me.pr3.invasionplus.content.items.Ruby;
import me.pr3.invasionplus.content.screens.ScreenHandlers;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import static me.pr3.invasionplus.Constants.INVASION_PLUS;

/**
 * @author tim
 */
public class InvasionPlus  implements ModInitializer {
    public static final Ruby RUBY = Registry.register(Registries.ITEM, new Identifier(INVASION_PLUS, "ruby"), new Ruby());
    public static final Nexus NEXUS = Registry.register(Registries.BLOCK, new Identifier(INVASION_PLUS, "nexus"), new Nexus());

    private static final ItemGroup INVASION_PLUS_ITEMS_GROUP = FabricItemGroup.builder()
            .icon(() -> new ItemStack(RUBY))
            .displayName(Text.translatable("itemGroup.invasionplus.items"))
            .build();

    @Override
    public void onInitialize() {
        BlockEntities.registerBlockEntities();
        ScreenHandlers.registerAllScreenHandlers();
        Entities.registerEntities();
        Registry.register(Registries.ITEM, new Identifier(INVASION_PLUS, "nexus"), new BlockItem(NEXUS, new FabricItemSettings()));
        Registry.register(Registries.ITEM_GROUP, new Identifier(INVASION_PLUS, "items"), INVASION_PLUS_ITEMS_GROUP);
        ItemGroupEvents.modifyEntriesEvent(RegistryKey.of(Registries.ITEM_GROUP.getKey(), new Identifier(INVASION_PLUS, "items"))).register(content -> {
            content.add(RUBY);
            content.add(NEXUS);
        });
    }
}
