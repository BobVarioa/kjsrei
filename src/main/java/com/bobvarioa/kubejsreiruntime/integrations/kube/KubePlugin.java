package com.bobvarioa.kubejsreiruntime.integrations.kube;

import com.bobvarioa.kubejsreiruntime.integrations.rei.ReiPlugin;
import dev.latvian.mods.kubejs.KubeJSPlugin;
import dev.latvian.mods.kubejs.script.BindingsEvent;
import dev.latvian.mods.kubejs.script.ScriptManager;
import dev.latvian.mods.kubejs.util.ConsoleJS;
import net.minecraft.client.Minecraft;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.List;

public class KubePlugin extends KubeJSPlugin {
    @Override
    public void registerBindings(BindingsEvent event) {
        if(event.getType().isClient()) {
            event.add("REIRuntime", ReiRuntimeMethods.class);
        }
    }

    @Override
    public void clientInit() {

    }

    public interface ReiRuntimeMethods {
        static void hideItem(ItemStack item) {
            var mc = Minecraft.getInstance();
            if (mc.level != null && !mc.level.isClientSide()) {
                ConsoleJS.getCurrent(ScriptManager.getCurrentContext()).error("REIRuntime methods must be called on the client!");
                return;
            }

            ReiPlugin.add(ForgeRegistries.ITEMS.getKey(item.getItem()));
        }

        static void hideItems(List<ItemStack> itemList) {
            for (var item : itemList) {
                hideItem(item);
            }
        }

        static void showItem(ItemStack item) {
            var mc = Minecraft.getInstance();

            if (mc.level != null && !mc.level.isClientSide()) {
                ConsoleJS.getCurrent(ScriptManager.getCurrentContext()).error("REIRuntime methods must be called on the client!");
                return;
            }
            ReiPlugin.remove(ForgeRegistries.ITEMS.getKey(item.getItem()));
        }

        static void showItems(List<ItemStack> itemList) {
            for (var item : itemList) {
                showItem(item);
            }
        }
    }
}
