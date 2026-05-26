package com.zemleroica.rtsmod.common;

import java.util.*;
import com.zemleroica.rtsmod.data.Team;

public class TeamManager {
    private static Map<String, Team> teams = new HashMap<>();
    private static Map<UUID, String> playerTeams = new HashMap<>();

    public static void createTeam(String teamName, int color, UUID leader) {
        Team team = new Team(teamName, color, leader);
        teams.put(teamName, team);
        playerTeams.put(leader, teamName);
    }

    public static void addPlayerToTeam(UUID playerId, String teamName) {
        Team team = teams.get(teamName);
        if (team != null) {
            team.addMember(playerId);
            playerTeams.put(playerId, teamName);
        }
    }

    public static String getPlayerTeam(UUID playerId) {
        return playerTeams.get(playerId);
    }

    public static Team getTeam(String teamName) {
        return teams.get(teamName);
    }

    public static boolean arePlayersAllies(UUID player1, UUID player2) {
        String team1 = playerTeams.get(player1);
        String team2 = playerTeams.get(player2);
        return team1 != null && team1.equals(team2);
    }

    public static Collection<Team> getAllTeams() {
        return teams.values();
    }

    public static int getTeamColor(UUID playerId) {
        String teamName = playerTeams.get(playerId);
        if (teamName != null) {
            Team team = teams.get(teamName);
            if (team != null) {
                return team.color;
            }
        }
        return 0xFFFFFF;
    }
}