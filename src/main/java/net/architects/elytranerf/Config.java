package net.architects.elytranerf;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.config.ModConfigEvent;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

// An example config class. This is not required, but it's a good idea to have one to keep your config organized.
// Demonstrates how to use Forge's config APIs
@Mod.EventBusSubscriber(modid = ElytraNerfMod.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class Config
{
    private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();

    private static final ForgeConfigSpec.IntValue FLY_SPEED_PERCENTAGE = BUILDER
            .comment("""
                    Average Speed Percent: 200% ~ 24.839 BPS, 100% ~ 13.495 BPS, and 0% ~ 3.019 BPS;
                    Values are taken as the max speed when gliding at an angle of 4 degrees down.
                    The Function of this comes by effectivly increasing air resistance. Therefore lower values can make the elytra feel too slow
                    and prevent players from falling through the air""")
            .defineInRange("Percent value", 100, 0, 200);

    static final ForgeConfigSpec SPEC = BUILDER.build();

    public static int FLY_PERCENT;
    public static double Multiplier;

    @SubscribeEvent
    static void onLoad(final ModConfigEvent event)
    {
        FLY_PERCENT = FLY_SPEED_PERCENTAGE.get();

//        Multiplier = Math.pow((((((double)FLY_PERCENT/100)*26.639)+4.131)-3.9),(1/64.16063664))*0.95;
        Multiplier = Math.pow((((((double)FLY_PERCENT/100)*26.639)+4.131)-3.9),(1/79.795673515))*0.95;
        System.out.println("Elytra Speed Percent: " + FLY_PERCENT + ", Elytra Modifier: " + Multiplier);

    }
}
