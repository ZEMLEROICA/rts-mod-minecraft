package com.zemleroica.rtsmod.entities;

import java.util.*;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollingGoal;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class RTSUnitEntity extends Mob {
    private static final EntityDataAccessor<String> UNIT_ID = 
        SynchedEntityData.defineId(RTSUnitEntity.class, EntityDataSerializers.STRING);
    private static final EntityDataAccessor<String> FACTION_ID = 
        SynchedEntityData.defineId(RTSUnitEntity.class, EntityDataSerializers.STRING);
    private static final EntityDataAccessor<String> ORDER_TYPE = 
        SynchedEntityData.defineId(RTSUnitEntity.class, EntityDataSerializers.STRING);

    private Vec3 targetPosition;
    private List<Vec3> patrolWaypoints = new ArrayList<>();
    private int patrolIndex = 0;

    public enum OrderType {
        IDLE, MOVE, ATTACK, DEFEND, PATROL
    }

    public RTSUnitEntity(EntityType<? extends RTSUnitEntity> type, Level level) {
        super(type, level);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new WaterAvoidingRandomStrollingGoal(this, 1.0));
    }

    @Override
    public void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(UNIT_ID, "");
        this.entityData.define(FACTION_ID, "");
        this.entityData.define(ORDER_TYPE, OrderType.IDLE.name());
    }

    public void setUnitId(String id) {
        this.entityData.set(UNIT_ID, id);
    }

    public String getUnitId() {
        return this.entityData.get(UNIT_ID);
    }

    public void setFaction(String faction) {
        this.entityData.set(FACTION_ID, faction);
    }

    public String getFaction() {
        return this.entityData.get(FACTION_ID);
    }

    public void setOrder(OrderType order) {
        this.entityData.set(ORDER_TYPE, order.name());
    }

    public OrderType getOrder() {
        return OrderType.valueOf(this.entityData.get(ORDER_TYPE));
    }

    public void moveToTarget(Vec3 target) {
        this.targetPosition = target;
        this.setOrder(OrderType.MOVE);
    }

    public void addPatrolWaypoint(Vec3 waypoint) {
        this.patrolWaypoints.add(waypoint);
    }

    public void startPatrol() {
        this.setOrder(OrderType.PATROL);
        this.patrolIndex = 0;
    }

    @Override
    public void tick() {
        super.tick();

        if (!this.level().isClientSide) {
            OrderType order = getOrder();

            switch (order) {
                case MOVE:
                    if (targetPosition != null) {
                        this.getNavigation().moveTo(targetPosition.x, targetPosition.y, targetPosition.z, 1.0);
                        if (this.distanceToSqr(targetPosition) < 4.0) {
                            this.setOrder(OrderType.IDLE);
                            this.targetPosition = null;
                        }
                    }
                    break;

                case PATROL:
                    if (!this.patrolWaypoints.isEmpty()) {
                        Vec3 nextWaypoint = this.patrolWaypoints.get(this.patrolIndex);
                        this.getNavigation().moveTo(nextWaypoint.x, nextWaypoint.y, nextWaypoint.z, 1.0);

                        if (this.distanceToSqr(nextWaypoint) < 4.0) {
                            this.patrolIndex = (this.patrolIndex + 1) % this.patrolWaypoints.size();
                        }
                    }
                    break;

                case IDLE:
                default:
                    this.getNavigation().stop();
                    break;
            }
        }
    }

    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putString("UnitId", this.getUnitId());
        tag.putString("Faction", this.getFaction());
        tag.putString("Order", this.getOrder().name());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        this.setUnitId(tag.getString("UnitId"));
        this.setFaction(tag.getString("Faction"));
        this.setOrder(OrderType.valueOf(tag.getString("Order")));
    }
}