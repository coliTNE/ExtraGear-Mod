# Tempered - Claude Code Instructions

## Project Overview
Tempered is a Minecraft Forge 1.20.1 progression overhaul mod. It replaces vanilla
progression with a realistic, gradual system based on custom workstations and forging.

- **Mod ID**: tempered
- **Package**: com.jtine.tempered
- **Forge**: 1.20.1 (47.3.0)
- **JDK**: Eclipse Temurin 17

## Build & Run

Build:
```bash
export PATH="/c/Program Files/Eclipse Adoptium/jdk-17.0.18.8-hotspot/bin:$PATH"
export JAVA_HOME="/c/Program Files/Eclipse Adoptium/jdk-17.0.18.8-hotspot"
./gradlew build
```

Run Minecraft client with mod:
```bash
export PATH="/c/Program Files/Eclipse Adoptium/jdk-17.0.18.8-hotspot/bin:$PATH"
export JAVA_HOME="/c/Program Files/Eclipse Adoptium/jdk-17.0.18.8-hotspot"
./gradlew runClient
```

## Package Structure

```
com.jtine.tempered/
  Tempered.java           @Mod entry point
  registry/               DeferredRegister classes (ModItems, ModBlocks, etc.)
  item/                   Item classes
  entity/                 Entity classes
  block/                  Block classes
  client/                 Client-only code (renderers, screens)
    renderer/             Entity/block renderers
    screen/               GUI screens
  event/                  Forge event handlers
  recipe/                 Custom recipe types
```

Create new packages only when adding their first class. No empty packages.

## Conventions

### Git
- Branch from `main`: `feature/`, `fix/`, `infra/`, `refactor/`, `docs/`
- Commit messages: imperative mood, capitalize, no period
- Version in `gradle.properties` (SemVer)

### Code
- Use Forge's DeferredRegister pattern for all registries
- Registry classes go in `registry/` package
- Client-only code must be in `client/` package with proper `Dist.CLIENT` annotations
- Separate client and server logic (Forge requirement for dedicated servers)

### Resources
- Assets: `src/main/resources/assets/tempered/`
- Data: `src/main/resources/data/`
- Language files: `lang/en_us.json` and `lang/es_es.json` (always keep both updated)

## User Preferences
- Communicate in Spanish
- Explain Java concepts by comparing to JavaScript/Python equivalents
- Don't rush - explain what we're doing and why
- Use AskUserQuestion when in doubt
- Commit frequently with meaningful messages

## Do NOT
- Create empty Java packages
- Skip the build verification after structural changes
- Add features beyond what was requested
- Forget to update both language files (en_us.json and es_es.json)
- Push to origin without user confirmation
