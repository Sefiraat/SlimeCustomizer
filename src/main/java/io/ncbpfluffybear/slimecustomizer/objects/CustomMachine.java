package io.ncbpfluffybear.slimecustomizer.objects;

import io.github.thebusybiscuit.slimefun4.core.attributes.RecipeDisplayItem;
import io.ncbpfluffybear.slimecustomizer.Utils;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Objects.Category;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.abstractItems.AContainer;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.abstractItems.MachineRecipe;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import me.mrCookieSlime.Slimefun.cscorelib2.collections.Pair;
import me.mrCookieSlime.Slimefun.cscorelib2.item.CustomItem;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * The {@link CustomMachine} class is a generified
 * {@link AContainer}.
 *
 * @author NCBPFluffyBear
 */
public class CustomMachine extends AContainer implements RecipeDisplayItem {

    public static final ItemStack MULTI_INPUT_ITEM = new CustomItem(
        Material.LIME_STAINED_GLASS_PANE, "&aMultiple Inputs", "", "&7> Click to view the items");
    public static final ItemStack MULTI_OUTPUT_ITEM = new CustomItem(
        Material.LIME_STAINED_GLASS_PANE, "&aMultiple Outputs", "", "&7> Click to view the items");

    private final String id;
    private final ItemStack progressItem;
    private final int energyConsumption;
    private final int energyBuffer;
    private final HashMap<Pair<ItemStack[], ItemStack[]>, Integer> customRecipes;

    public CustomMachine(Category category, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe,
                         String id, Material progressItem, int energyConsumption, int energyBuffer,
                         HashMap<Pair<ItemStack[], ItemStack[]>, Integer> customRecipes) {
        super(category, item, recipeType, recipe);

        this.id = id;
        this.progressItem = new CustomItem(progressItem, "");
        this.energyConsumption = energyConsumption;
        this.energyBuffer = energyBuffer;
        this.customRecipes = customRecipes;

        // Gets called in AContainer, but customRecipes is null at that time.
        // registerDefaultRecipes();
    }

    @Override
    public ItemStack getProgressBar() {
        return progressItem;
    }

    @Override
    public int getCapacity() {
        return energyBuffer;
    }

    @Override
    public int getEnergyConsumption() {
        return energyConsumption;
    }

    @Override
    public int getSpeed() {
        return 1;
    }

    @Override
    protected void registerDefaultRecipes() {
        if (customRecipes == null) {
            return;
        }

        customRecipes.forEach((recipe, time) ->
            registerRecipe(time, recipe.getFirstValue().clone(), recipe.getSecondValue().clone())
        );

    }

    @Override
    public List<ItemStack> getDisplayRecipes() {
        List<ItemStack> displayRecipes = new ArrayList<>(recipes.size() * 2);

        for (int i = 0; i < recipes.size(); i++) {
            MachineRecipe recipe = recipes.get(i);
            if (recipe.getInput().length == 2) {
                displayRecipes.add(Utils.keyItem(MULTI_INPUT_ITEM.clone(), i));
            } else {
                displayRecipes.add(recipe.getInput()[0]);
            }

            if (recipe.getOutput().length == 2) {
                displayRecipes.add(Utils.keyItem(MULTI_OUTPUT_ITEM.clone(), i));
            } else {
                displayRecipes.add(recipe.getOutput()[0]);
            }
        }

        return displayRecipes;
    }

    @Override
    public String getMachineIdentifier() {
        return id;
    }
}
