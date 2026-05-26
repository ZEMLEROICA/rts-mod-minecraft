package com.zemleroica.rtsmod.data;

import java.util.UUID;
import java.util.HashSet;
import java.util.Set;

public class Team {
    public String name;
    public int color;
    public Set<UUID> members;
    public UUID leader;

    public Team(String name, int color, UUID leader) {
        this.name = name;
        this.color = color;
        this.leader = leader;
        this.members = new HashSet<>();
        this.members.add(leader);
    }

    public Team() {
        this.members = new HashSet<>();
    }

    public void addMember(UUID playerId) {
        this.members.add(playerId);
    }

    public void removeMember(UUID playerId) {
        if (!playerId.equals(leader)) {
            this.members.remove(playerId);
        }
    }

    public boolean isMember(UUID playerId) {
        return this.members.contains(playerId);
    }
}