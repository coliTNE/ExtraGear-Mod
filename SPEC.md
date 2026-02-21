# Tempered - Project Specification

## Vision

Tempered is an early-to-mid game progression overhaul for Minecraft.
It replaces vanilla progression with a realistic, gradual system where players
gather primitive materials, craft at custom workstations, and forge advanced gear.
The mod is opinionated: installing it fundamentally changes how progression works.

## Stack

| Component | Version |
|-----------|---------|
| Minecraft Forge | 1.20.1 (47.3.0) |
| GeckoLib | 4.7.3 |
| JDK | Eclipse Temurin 17 |
| Mod ID | `tempered` |
| Package | `com.jtine.tempered` |

**Constraints:** Forge API only (no mixins). Single mod for now.
Future: Tempered Combat (separate mod for combat mechanics).

## Package Structure

```
com.jtine.tempered/
  Tempered.java        @Mod entry point, registry + config init
  config/              ForgeConfigSpec (drop rates, durability, damage)
  registry/            DeferredRegister classes (Items, Blocks, Entities, Tabs, Menus, Recipes, LootModifiers)
  item/                Item + tool classes, ModToolTiers enum
  entity/              Projectile entities (PebbleProjectile)
  block/               Custom blocks (LooseItemBlock, PrimitiveCraftingTableBlock)
  crafting/            Custom recipe types, menus, serializers
  loot/                Global loot modifiers (drops, replacements, restrictions)
  client/              Client-only: renderers, screens (Dist.CLIENT isolated)
```

## What's Implemented (Fase 1, v0.1.1)

**Items:** Branch, Pebble, Plant Fiber, Log Chunk, Strange Branch
**Tools:** Primitive Knife, Primitive Axe, Primitive Sling (ranged, uses pebbles)
**Blocks:** Pebble/Branch surface blocks (world gen by biome), Primitive Crafting Table
**Crafting:** Custom 2x3 `tempered:primitive_crafting` recipe type with sliding pattern match
**Loot:** Grass drops fiber (40%), leaves drop fiber (15%), leaves drop branches/strange branches (25%)
**Vanilla changes:** Wooden/stone tool recipes disabled, require axe for log drops
**Config:** ForgeConfigSpec for all drop rates, durabilities, and damage values

## Design Decisions

1. **Forge API only** - No mixins, no access transformers. Keeps compatibility simple.
2. **GeckoLib for everything** - All animated content (entities, blocks, items, armor) uses GeckoLib.
3. **ForgeConfigSpec** - All gameplay-affecting numbers are configurable (not hardcoded).
4. **Data-driven recipes** - Custom recipe types use JSON, enabling datapack overrides.
5. **Biome-aware generation** - Surface blocks use custom biome tags for density control.
6. **No empty packages** - Packages created only when their first class is added.

## Git Flow

- `main` = stable releases (tagged)
- `develop` = working branch (features merge here)
- Branch from `develop`: `feature/`, `fix/`, `infra/`, `refactor/`, `docs/`
- PRs target `develop`. Releases: `develop` -> `main` with version bump.

## Roadmap Reference

See [ROADMAP.md](ROADMAP.md) for the full 5-phase development plan.
Current: Fase 1 (6/8 PRs done). Next: block break restrictions + rebalance.
