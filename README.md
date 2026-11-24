# CameraCraft — Fabric 26.3

CameraCraft is a **client-side starter mod** for Minecraft Java Edition 26.3.

## Included in this first playable build

- Press **C** to open the CameraCraft field console (rebindable in Options → Controls).
- Five camera-quality profiles: Pocket, Explorer, HD, Professional and Cinema.
- Five lens presets and five filter presets in the console.
- Adjustable zoom setting (1x–8x) and a timer toggle in the console.
- Non-pausing interface, so the world remains active behind the console.
- Uses Minecraft's built-in **F2** screenshot feature for actual image files.

## Important scope note

This is a working first-stage camera-control interface, not yet a full virtual-camera renderer. The lens, filter, zoom and timer choices are currently selectable presets in the UI; they do not yet alter the game's render pipeline or schedule delayed screenshots. The selected quality profile does not upscale screenshots. Minecraft's F2 capture uses the game's normal screenshot resolution.

The next engineering stages are:
1. Add actual zoom/FOV control with safe restoration of the player's original FOV.
2. Implement real post-processing filters and depth-of-field effects.
3. Add a photo album/gallery screen and metadata.
4. Add tripod blocks/items and persistent camera placement.
5. Add a cinematic free-camera system and multiplayer-safe networking.

## Build requirements

- Minecraft Java Edition 26.3
- JDK 26
- Fabric Loader 0.20.1 or newer
- Fabric API for 26.3
- Gradle 9.9.0 (the GitHub Actions workflow installs it directly)

The project is configured with Fabric Loom 1.19-SNAPSHOT and Fabric API 0.162.0+26.3. The client GUI code uses the Minecraft 26.3 `GuiGraphicsExtractor` and `extractRenderState` APIs. This source archive does not include the Gradle wrapper scripts; the included GitHub Actions workflow installs Gradle 9.9.0 directly.

## Build

The source archive does not include `gradlew` / `gradlew.bat`. If Gradle 9.9.0 is installed locally, run:

Linux/macOS:
```bash
gradle clean build
```

Windows PowerShell:
```powershell
gradle clean build
```

Alternatively, push the project to GitHub and run **Actions → CameraCraft Fabric 26.3 Build**. The workflow installs JDK 26 and Gradle 9.9.0, then uploads the compiled JAR as an artifact.

The mod JAR is created in `build/libs/`. The source changes in this archive have not yet been confirmed by a completed CI build; check the GitHub Actions run before distributing the JAR.

## Install

1. Install Fabric for Minecraft 26.3 and use a Java 25 runtime.
2. Build the project, or obtain its compiled JAR from a successful build.
3. Put the JAR in the Minecraft instance's `mods` folder.
4. Install Fabric API 26.3 in the same instance.
5. Launch the Fabric 26.3 profile and press **C** in a world.

## Controls

- **C** — open CameraCraft console
- **F2** — take a normal Minecraft screenshot
- All CameraCraft controls can be rebound in Options → Controls.
