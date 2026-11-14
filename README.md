# Hourglass Timer — Fabric 1.21.11

Client-side Fabric mod. Press **H** to start/restart a 60-minute real-time countdown. A compact HUD appears in the top-right corner. When it reaches zero, it disappears and sends a local chat message.

## Requirements
- Minecraft Java Edition 1.21.11
- Fabric Loader 0.18.6 or newer
- Fabric API 0.141.4+1.21.11
- Java 21 (for building)

## Build
Run `gradle build` from this folder. The remapped mod jar should be created in `build/libs/`.

## Change the key
In Minecraft, open **Options → Controls → Key Binds → Hourglass Timer** and change **Start / restart 1-hour timer**. Default: **H**.
