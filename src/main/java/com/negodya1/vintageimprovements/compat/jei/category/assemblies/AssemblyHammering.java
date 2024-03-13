package com.negodya1.vintageimprovements.compat.jei.category.assemblies;

import com.mojang.blaze3d.vertex.PoseStack;
import com.negodya1.vintageimprovements.compat.jei.category.animations.AnimatedHelve;
import com.negodya1.vintageimprovements.compat.jei.category.animations.AnimatedVacuumChamber;
import com.simibubi.create.compat.jei.category.CreateRecipeCategory;
import com.simibubi.create.compat.jei.category.sequencedAssembly.SequencedAssemblySubCategory;
import com.simibubi.create.content.processing.sequenced.SequencedRecipe;
import com.simibubi.create.foundation.fluid.FluidIngredient;
import mezz.jei.api.forge.ForgeTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.builder.IRecipeSlotBuilder;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import net.minecraft.client.gui.GuiGraphics;

import static com.simibubi.create.compat.jei.category.CreateRecipeCategory.getRenderedSlot;

public class AssemblyHammering extends SequencedAssemblySubCategory {

    AnimatedHelve helve;

    public AssemblyHammering() {
        super(25);
        helve = new AnimatedHelve();
    }

    public void setRecipe(IRecipeLayoutBuilder builder, SequencedRecipe<?> recipe, IFocusGroup focuses, int x) {
        if (recipe.getRecipe().getIngredients().size() <= 1 && recipe.getRecipe().getFluidIngredients().isEmpty()) return;

        int offset = 0;

        for (int i = 1; i < recipe.getRecipe().getIngredients().size(); i++) {
            IRecipeSlotBuilder slot = builder
                    .addSlot(RecipeIngredientRole.INPUT, x + 4, 15 + offset * 16)
                    .setBackground(getRenderedSlot(), -1, -1)
                    .addIngredients(recipe.getRecipe().getIngredients().get(i));
            offset++;
        }

        for (FluidIngredient fluidIngredient : recipe.getRecipe().getFluidIngredients()) {
            builder
                    .addSlot(RecipeIngredientRole.INPUT, x + 4, 15 + offset * 16)
                    .setBackground(CreateRecipeCategory.getRenderedSlot(), -1, -1)
                    .addIngredients(ForgeTypes.FLUID_STACK, CreateRecipeCategory.withImprovedVisibility(fluidIngredient.getMatchingFluidStacks()))
                    .addTooltipCallback(CreateRecipeCategory.addFluidTooltip(fluidIngredient.getRequiredAmount()));
        }
    }

    @Override
    public void draw(SequencedRecipe<?> recipe, GuiGraphics graphics, double mouseX, double mouseY, int index) {
        PoseStack ms = graphics.pose();
        ms.pushPose();
        ms.translate(4, 38, 0);
        ms.scale(.5f, .5f, .5f);
        helve.draw(graphics, getWidth() / 2, 30, true);
        ms.popPose();
    }

}