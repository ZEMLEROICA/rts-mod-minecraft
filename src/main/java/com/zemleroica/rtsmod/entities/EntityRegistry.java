package com.zemleroica.rtsmod.entities;

import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import com.zemleroica.rtsmod.RTSMod;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;

public class EntityRegistry {
    private static final DeferredRegister<EntityType<?>> ENTITY_TYPES = 
        DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, RTSMod.MOD_ID);

    public static final RegistryObject<EntityType<BaseEntity>> BASE_ENTITY = 
        ENTITY_TYPES.register("base", () ->
            EntityType.Builder.of(BaseEntity::new, MobCategory.MISC)
                .sized(1.0f, 1.0f)
                .build(RTSMod.MOD_ID + ":base")
        );

    public static final RegistryObject<EntityType<RTSUnitEntity>> RTS_UNIT = 
        ENTITY_TYPES.register("rts_unit", () ->
            EntityType.Builder.of(RTSUnitEntity::new, MobCategory.MISC)
                .sized(0.6f, 1.8f)
                .build(RTSMod.MOD_ID + ":rts_unit")
        );

    public static void register(IEventBus modEventBus) {
        ENTITY_TYPES.register(modEventBus);
    }
}