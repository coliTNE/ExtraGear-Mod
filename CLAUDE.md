# Tempered - Minecraft Forge 1.20.1 Mod

See [SPEC.md](SPEC.md) for project context, stack, and architecture.

## Build & Run

```bash
export PATH="/c/Program Files/Eclipse Adoptium/jdk-17.0.18.8-hotspot/bin:$PATH"
export JAVA_HOME="/c/Program Files/Eclipse Adoptium/jdk-17.0.18.8-hotspot"
./gradlew build      # compile + verify
./gradlew runClient  # launch Minecraft with mod
```

## Rules

- Communicate in Spanish. Explain Java concepts via JS/Python comparisons.
- ALWAYS update both `lang/en_us.json` AND `lang/es_es.json` together.
- NO recipes unless explicitly requested.
- NO push without user confirmation.
- Verify build (`./gradlew build`) after structural changes.
- Use AskUserQuestion when in doubt. Don't rush.

## Forge 1.20.1 Gotchas

- `IForgeMenuType.create()` NOT `.regular()` for menu registration.
- `MenuScreens.register()` inside `FMLClientSetupEvent.enqueueWork()` NOT `RegisterMenuScreensEvent`.
- `noOcclusion()` on block properties for non-full-cube blocks (prevents face culling).
- Biome modifiers path: `data/<modid>/forge/biome_modifier/` NOT `data/forge/`.
- RecipeType registration needs double cast: `(RecipeType<T>)(RecipeType<?>)` with `@SuppressWarnings`.
- Client-only code MUST be in `client/` with `Dist.CLIENT` — crashes dedicated servers otherwise.

## GeckoLib 4.7.3 Patterns

- Items: implement `GeoItem`, use `initializeClient()` with `IClientItemExtensions`.
- Model JSON: `"parent": "builtin/entity"` in `models/item/`.
- Files: `geo/` for geometry, `animations/` for animations, `textures/` for textures.
- Register animated singletons: `SingletonGeoAnimatable.registerSyncedAnimatable()`.

## Specialized Agents

Use `/verify`, `/lang-check`, `/review` commands for quick validations.
For complex tasks, Claude has specialized agents in `.claude/agents/`:
- **weapon-modeler** — Design weapons/tools with correct display transforms
- **geckolib-item-creator** — Create animated GeckoLib items end-to-end
- **geckolib-entity-creator** — Create animated GeckoLib entities
- **geckolib-block-creator** — Create animated GeckoLib blocks
- **resource-validator** — Audit all mod resources for missing files
- **forge-reviewer** — Code review for Forge 1.20.1 patterns
- **build-debugger** — Diagnose compilation and runtime errors
