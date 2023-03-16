package com.bobvarioa.kubejsreiruntime.integrations.rei;

import me.shedaniel.rei.api.client.entry.filtering.base.BasicFilteringRule;
import me.shedaniel.rei.api.client.plugins.REIClientPlugin;
import me.shedaniel.rei.api.common.util.EntryStacks;
import me.shedaniel.rei.forge.REIPluginClient;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@REIPluginClient
public class ReiPlugin implements REIClientPlugin {
    private static BasicFilteringRule.MarkDirty filteringRule;

    private static final Set<ResourceLocation> currentlyFiltered = new HashSet<>();

    public static void add(ResourceLocation res) {
        currentlyFiltered.add(res);
        if (filteringRule != null) {
            filteringRule.markDirty();
        }
    }

    public static void remove(ResourceLocation res) {
        currentlyFiltered.remove(res);
        if (filteringRule != null) {
            filteringRule.markDirty();
        }
    }

    @Override
    public void registerBasicEntryFiltering(BasicFilteringRule<?> rule) {
        filteringRule = rule.hide(() ->
            currentlyFiltered
                .stream()
                .map(item ->
                    EntryStacks.of(ForgeRegistries.ITEMS.getValue(item)).cast()
                )
                .collect(Collectors.toList())
        );
    }
}
