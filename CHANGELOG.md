# Changelog

All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.1.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## [Unreleased]

### Added
- GeckoLib 4.8.2 integration with MixinGradle refmap remapping
- ForgeConfigSpec for configurable drop rates, durability, and damage values
- MixinGradle plugin for proper SRG→official mapping in dev environment

### Changed
- Reorganized registry classes (ModRecipeTypes, ModLootModifiers) into `registry/` package
- Extracted magic numbers in PrimitiveCraftingMenu to named constants
- Updated git flow: develop-based branching (features → develop → main)

### Removed
- Obsidian Arrow (item, entity, renderer, textures) — was a learning exercise
- Forge MDK template files (README.txt, CREDITS.txt, LICENSE.txt)

## [0.1.1] - 2025-02-21

### Added
- Primitive items: Branch, Pebble, Plant Fiber, Log Chunk, Strange Branch
- Primitive tools: Knife, Axe, Sling (with pull animation)
- Primitive Crafting Table block with custom 2x3 recipe grid
- Custom recipe type `tempered:primitive_crafting` with shaped pattern matching
- First recipes: Campfire, Primitive Sling (via primitive crafting table)
- Crafting table recipe: 4 Log Chunks in vanilla 2x2 grid
- Pebble and Branch world generation across biome categories (dark/medium/light/shore)
- Biome tags for generation: is_dark_wood, is_light_wood, is_medium_wood, is_river_shore
- Loot modifiers: grass drops fiber (40%), leaves drop fiber (15%), leaves drop branches
- Strange Branch: 25% chance drop from leaves (replaces vanilla sticks)
- Require Primitive Axe to get log drops
- Vanilla wooden/stone tool recipes disabled (forge:false condition)
- Vanilla wooden/stone tools removed from creative tabs
- GitHub Actions CI workflow

### Changed
- Reorganized Java packages: registry/, item/, block/, entity/, crafting/, loot/, client/

## [0.1.0] - 2025-02-13

### Added
- Initial Forge 1.20.1 mod setup
- Custom creative tab "Tempered"
- Spanish and English language files
- CHANGELOG.md, CONTRIBUTING.md, ROADMAP.md

[Unreleased]: https://github.com/coliTNE/ExtraGear-Mod/compare/v0.1.1...HEAD
[0.1.1]: https://github.com/coliTNE/ExtraGear-Mod/compare/v0.1.0...v0.1.1
[0.1.0]: https://github.com/coliTNE/ExtraGear-Mod/releases/tag/v0.1.0
