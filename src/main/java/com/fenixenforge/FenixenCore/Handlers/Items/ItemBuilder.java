package com.fenixenforge.FenixenCore.Handlers.Items;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class ItemBuilder {
    private Material material;
    private String name;
    private List<String> lore = new ArrayList<>();
    private Map<Enchantment, Integer> enchantments = new HashMap<>();
    private List<ItemFlag> flags = new ArrayList<>();
    private List<PotionEffect> potionEffects = new ArrayList<>();
    private Integer customModelData = null;
    private boolean unbreakable = false;
    private Consumer<ItemMeta> others = meta -> {};

    // Método estático para iniciar el builder
    public static ItemBuilder items() {
        return new ItemBuilder();
    }

    public ItemBuilder material(Material material) {
        this.material = material;
        return this;
    }

    public ItemBuilder name(String name) {
        this.name = name;
        return this;
    }

    public ItemBuilder lore(String... lines) {
        for (String line : lines) {
            lore.add(line);
        }
        return this;
    }

    public ItemBuilder enchant(Enchantment enchantment, int level) {
        enchantments.put(enchantment, level);
        return this;
    }

    public ItemBuilder flags(ItemFlag... flags) {
        for (ItemFlag flag : flags) {
            this.flags.add(flag);
        }
        return this;
    }

    public ItemBuilder potionsefect(PotionEffect effect) {
        this.potionEffects.add(effect);
        return this;
    }

    public ItemBuilder mount(int customModelData) {
        this.customModelData = customModelData;
        return this;
    }

    public ItemBuilder unbreakable(boolean unbreakable) {
        this.unbreakable = unbreakable;
        return this;
    }

    public ItemBuilder otrasopciones(Consumer<ItemMeta> others) {
        this.others = others;
        return this;
    }

    public ItemStack build() {
        if (material == null) {
            material = Material.STONE; // Material por defecto
        }
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        if (name != null) {
            meta.setDisplayName(name);
        }
        if (!lore.isEmpty()) {
            meta.setLore(lore);
        }
        for (Map.Entry<Enchantment, Integer> entry : enchantments.entrySet()) {
            meta.addEnchant(entry.getKey(), entry.getValue(), true);
        }
        for (ItemFlag flag : flags) {
            meta.addItemFlags(flag);
        }
        if (customModelData != null) {
            meta.setCustomModelData(customModelData);
        }
        meta.setUnbreakable(unbreakable);
        others.accept(meta);
        item.setItemMeta(meta);
        return item;
    }
}
