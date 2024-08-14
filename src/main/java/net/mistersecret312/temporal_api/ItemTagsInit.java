package net.mistersecret312.temporal_api;

import net.minecraft.item.Item;
import net.minecraft.tags.ITag;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.ResourceLocation;

public class ItemTagsInit {
    public static final ITag.INamedTag<Item> SONIC = makeItem(new ResourceLocation(TemporalAPIMod.MOD_ID, "sonic"));
    public static final ITag.INamedTag<Item> MANUAL = makeItem(new ResourceLocation(TemporalAPIMod.MOD_ID, "manual"));

    public static ITag.INamedTag<Item> makeItem(ResourceLocation resourceLocation) {
        return ItemTags.makeWrapperTag(resourceLocation.toString()); //makeWrapperTag can be static inited and is aware of tag reloads. Do not use createOptional because that gets loaded too early.
    }
}
