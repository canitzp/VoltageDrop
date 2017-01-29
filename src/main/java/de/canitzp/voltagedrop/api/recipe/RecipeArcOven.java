package de.canitzp.voltagedrop.api.recipe;

import de.canitzp.ctpcore.util.StackUtil;
import net.minecraft.item.ItemStack;

import java.util.HashMap;
import java.util.Map;

/**
 * @author canitzp
 */
public class RecipeArcOven{

    private static Map<ItemStack, RecipeArcOven.RecipePattern> recipes = new HashMap<>();

    public static void addRecipe(RecipeArcOven.RecipePattern pattern){
        recipes.put(pattern.in, pattern);
    }

    public static void removeRecipe(RecipeArcOven.RecipePattern pattern){
        recipes.remove(pattern);
    }

    public static RecipeArcOven.RecipePattern getRecipe(ItemStack in){
        for (Map.Entry<ItemStack, RecipeArcOven.RecipePattern> entry : recipes.entrySet()){
            if (StackUtil.compareItemStacks(in, entry.getKey())){
                return entry.getValue();
            }
        }
        return null;
    }

    public static class RecipePattern{
        public final ItemStack in, out, secondOut;
        public int burnTime;
        float expDrop, secondChance;
        public RecipePattern(ItemStack in, ItemStack out, ItemStack secondOut, int burnTime, float expDrop, float secondChance){
            this.in = in;
            this.out = out;
            this.secondOut = secondOut;
            this.burnTime = burnTime;
            this.expDrop = expDrop;
            this.secondChance = secondChance;
        }

        public RecipePattern(ItemStack in, ItemStack out, ItemStack secondOut, int burnTime, float secondChance){
            this(in, out, secondOut, burnTime, 0.0F, secondChance);
        }

        public RecipePattern(ItemStack in, ItemStack out, int burnTime){
            this(in, out, ItemStack.EMPTY, burnTime, 0);
        }

        public RecipePattern(ItemStack in, ItemStack out){
            this(in, out, 200);
        }

    }

}
