# RTS Mod for Minecraft Forge 1.20.1

A comprehensive real-time strategy mod for Minecraft Forge 1.20.1 with army management, base placement, faction systems, minimap, team coordination, and animated base spawning.

## Features

### 🧠 Core RTS Mechanics
- **Army Management**: Recruit units from your base via GUI
- **Unit Selection & Orders**: Select units and issue commands (move, attack, defend, patrol)
- **Client-side Highlighting**: Selected units glow for the controlling player only
- **Isometric/Top-Down Camera**: Classic RTS perspective

### 👑 Faction System
- Choose a faction upon first join
- Faction-specific bases and unit pools
- Team colors for player identification

### 🏚️ Base Deployment
- Place your base anywhere on the map
- Right-click to open recruitment GUI
- Production queue for unit spawning
- Configurable spawn delays

### 🔵 Spawn Point Expansion
- Place blue wool to create additional spawn points
- Each spawn point reduces global unit spawn time by 2 seconds
- Faster unit production with more spawn points

### 🎯 Win Condition
- Destroy the enemy's central mob to win
- Automatic win detection and announcement

### 📊 Minimap System
- Real-time unit tracking on minimap
- Team-color-coded unit icons
- Player-centered minimap view

### 👥 Team System
- Create and manage teams
- Team-based shared vision and communication
- Allied unit highlighting
- Team-specific colors

### ✨ Base Spawn Animations
- Smooth base placement animation (40 ticks)
- Visual feedback for base creation
- Particle effects during spawn

### ⚙️ Admin Features
- Admin GUI for faction management
- Faction creation/editing/deletion
- Unit pool configuration
- Base mob selection
- Central mob selection
- Spawn time adjustment

## Installation

1. Download the latest release from the GitHub repository
2. Place the JAR file in your Minecraft mods folder
3. Launch Minecraft with Forge 1.20.1

## Usage

### Player Gameplay
1. Join the server → Choose a faction
2. Choose a team or create one
3. Click a block to place your base
4. Right-click the base to open recruitment menu
5. Select units to spawn (with configurable delay)
6. Select units with left-click
7. Right-click on the ground to issue movement orders
8. Use minimap to track allied and enemy units
9. Destroy the enemy's central mob to win

### Admin Commands
- `/rts faction create <name>` - Create a new faction
- `/rts faction delete <name>` - Delete a faction
- `/rts team create <name>` - Create a team
- `/rts admin` - Open admin panel

## Configuration

All faction and team data is stored in:
- World save folder: `world/rts_mod_data/factions/`
- Team data: `world/rts_mod_data/teams/`

JSON format allows easy manual editing.

## Technical Details

- **Minecraft Version**: 1.20.1
- **Forge Version**: 47.2.0+
- **Java Version**: 17+
- **Networking**: Forge SimpleChannel for multiplayer compatibility
- **Client-Side**: Client-only highlighting and minimap rendering
- **Server-Side**: Unit management, base logic, team coordination

## Building from Source

```bash
# Clone the repository
git clone https://github.com/ZEMLEROICA/rts-mod-minecraft.git
cd rts-mod-minecraft

# Build the mod
./gradlew build

# Built JAR will be in: build/libs/rts-mod-1.0.0.jar
```

## File Structure

```
src/main/java/com/zemleroica/rtsmod/
├── RTSMod.java (Main mod class)
├── client/ (Client-side rendering and UI)
│   ├── RTSClientEvents.java
│   └── MinimapRenderer.java
├── common/ (Server-side logic)
│   ├── FactionManager.java
│   ├── TeamManager.java
│   ├── UnitManager.java
│   ├── BaseManager.java
│   ├── SpawnPointManager.java
│   └── packets/ (Network communication)
├── entities/ (Custom entities)
│   ├── BaseEntity.java
│   ├── RTSUnitEntity.java
│   └── EntityRegistry.java
├── gui/ (GUIs)
│   ├── FactionSelectScreen.java
│   ├── TeamSelectScreen.java
│   ├── BasePlacementScreen.java
│   └── ...
└── data/ (Data models)
    ├── Faction.java
    └── Team.java
```

## Credits

Created by ZEMLEROICA

## License

MIT License

## Support

For issues, questions, or suggestions, please open an issue on GitHub.
