package de.canitzp.voltagedrop.api.recipe;

import de.canitzp.ctpcore.recipe.ICustomRecipe;
import net.minecraft.item.ItemStack;

/**
 * @author canitzp
 */
public enum Recipes{

    ARC_OVEN("ArcOven"),
    ELECTRIC_FURNACE("ElectricFurnace")
    ;

    private String cat;
    Recipes(String cat){
        this.cat = cat;
    }

    public String getCategory(){
        return cat;
    }

    public abstract static class SimpleBurn implements ICustomRecipe{
        public ItemStack input, output, secondOutput;
        public int burnTime;
        public float exp, secondChance;

        public SimpleBurn(ItemStack input, ItemStack output, ItemStack secondOutput, float secondChance, int burnTime, float exp){
            this.input = input;
            this.output = output;
            this.secondOutput = secondOutput;
            this.burnTime = burnTime;
            this.exp = exp;
            this.secondChance = secondChance;
        }

        public SimpleBurn(ItemStack input, ItemStack output, ItemStack secondOutput, float secondChance, int burnTime){
            this(input, output, secondOutput, secondChance, burnTime, 0.0F);
        }

        public SimpleBurn(ItemStack input, ItemStack output, ItemStack secondOutput, float secondChance){
            this(input, output, secondOutput, secondChance, 200);
        }

        public SimpleBurn(ItemStack input, ItemStack output, int burnTime){
            this(input, output, ItemStack.EMPTY, 0, burnTime);
        }

        public SimpleBurn(ItemStack input, ItemStack output){
            this(input, output, 200);
        }

        @Override
        public ItemStack getKey(){
            return this.input;
        }

        public ItemStack getInput(){
            return input;
        }

        public ItemStack getOutput(){
            return output;
        }

        public ItemStack getSecondOutput(){
            return secondOutput;
        }

        public int getBurnTime(){
            return burnTime;
        }

        public float getExp(){
            return exp;
        }

        public float getSecondChance(){
            return secondChance;
        }
    }

    public static class ArcOven extends SimpleBurn{
        public ArcOven(ItemStack input, ItemStack output, ItemStack secondOutput, float secondChance, int burnTime, float exp){
            super(input, output, secondOutput, secondChance, burnTime, exp);
        }

        public ArcOven(ItemStack input, ItemStack output, ItemStack secondOutput, float secondChance, int burnTime){
            super(input, output, secondOutput, secondChance, burnTime);
        }

        public ArcOven(ItemStack input, ItemStack output, ItemStack secondOutput, float secondChance){
            super(input, output, secondOutput, secondChance);
        }

        public ArcOven(ItemStack input, ItemStack output, int burnTime){
            super(input, output, burnTime);
        }

        public ArcOven(ItemStack input, ItemStack output){
            super(input, output);
        }

        @Override
        public String getCategory(){
            return ARC_OVEN.getCategory();
        }
    }

    public static class ElectricFurnace extends SimpleBurn{

        public ElectricFurnace(ItemStack input, ItemStack output, ItemStack secondOutput, float secondChance, int burnTime, float exp){
            super(input, output, secondOutput, secondChance, burnTime, exp);
        }

        public ElectricFurnace(ItemStack input, ItemStack output, ItemStack secondOutput, float secondChance, int burnTime){
            super(input, output, secondOutput, secondChance, burnTime);
        }

        public ElectricFurnace(ItemStack input, ItemStack output, ItemStack secondOutput, float secondChance){
            super(input, output, secondOutput, secondChance);
        }

        public ElectricFurnace(ItemStack input, ItemStack output, int burnTime){
            super(input, output, burnTime);
        }

        public ElectricFurnace(ItemStack input, ItemStack output){
            super(input, output);
        }

        @Override
        public String getCategory(){
            return ELECTRIC_FURNACE.getCategory();
        }
    }

}
