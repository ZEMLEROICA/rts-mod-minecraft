package com.zemleroica.rtsmod.entities;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class BaseEntity extends Mob {
    private static final EntityDataAccessor<String> FACTION_ID = 
        SynchedEntityData.defineId(BaseEntity.class, EntityDataSerializers.STRING);
    private static final EntityDataAccessor<Integer> SPAWN_ANIMATION_TICKS = 
        SynchedEntityData.defineId(BaseEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Boolean> IS_SPAWNING = 
        SynchedEntityData.defineId(BaseEntity.class, EntityDataSerializers.BOOLEAN);

    private int spawnAnimationCounter = 0;
    private static final int SPAWN_ANIMATION_DURATION = 40;

    public BaseEntity(EntityType<? extends BaseEntity> type, Level level) {
        super(type, level);
        this.maxUpStep = 0.0F;
        this.noPhysics = false;
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
    }

    @Override
    public void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(FACTION_ID, "");
        this.entityData.define(SPAWN_ANIMATION_TICKS, 0);
        this.entityData.define(IS_SPAWNING, false);
    }

    public void setFaction(String faction) {
        this.entityData.set(FACTION_ID, faction);
    }

    public String getFaction() {
        return this.entityData.get(FACTION_ID);
    }

    public void startSpawnAnimation() {
        this.entityData.set(IS_SPAWNING, true);
        this.spawnAnimationCounter = 0;
    }

    public boolean isSpawning() {
        return this.entityData.get(IS_SPAWNING);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putString("Faction", this.getFaction());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        this.setFaction(tag.getString("Faction"));
    }

    @Override
    public void tick() {
        super.tick();
        
        this.setDeltaMovement(0, 0, 0);

        if (this.isSpawning()) {
            this.spawnAnimationCounter++;
            this.entityData.set(SPAWN_ANIMATION_TICKS, this.spawnAnimationCounter);

            if (this.spawnAnimationCounter >= SPAWN_ANIMATION_DURATION) {
                this.entityData.set(IS_SPAWNING, false);
                this.spawnAnimationCounter = 0;
            }
        }
    }

    public int getSpawnAnimationProgress() {
        return this.spawnAnimationCounter;
    }

    public static int getSpawnAnimationDuration() {
        return SPAWN_ANIMATION_DURATION;
    }
}