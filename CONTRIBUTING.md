# Contributing to Tempered

## Setup

- **Forge**: 1.20.1 (47.3.0)
- **JDK**: Eclipse Temurin 17
- **Gradle**: 8.8 (use the wrapper `./gradlew`, do not install Gradle globally)

### Build

```bash
./gradlew build
```

### Run Minecraft with the mod

```bash
./gradlew runClient
```

## Branch Naming

| Prefix | Use |
|--------|-----|
| `feature/` | New items, blocks, mechanics |
| `fix/` | Bug fixes |
| `infra/` | CI, build, project setup |
| `refactor/` | Code restructuring |
| `docs/` | Documentation changes |

Example: `feature/primitive-axe`

## Commit Messages

Use imperative mood, capitalize, no period:
- `Add primitive axe item and recipe`
- `Fix wood blocks breakable by hand`
- `Refactor registry classes into registry package`

## Versioning

Semantic versioning (`MAJOR.MINOR.PATCH`) in `gradle.properties`:
- **PATCH**: Bug fixes, small tweaks, infra changes
- **MINOR**: New features (a Fase/milestone)
- **MAJOR**: Breaking/incompatible changes

Stay at `0.x.y` during development. `1.0.0` = stable public release.

## Package Structure

```
com.jtine.tempered/
  Tempered.java           Main mod class (@Mod entry point)
  registry/               DeferredRegister classes (ModItems, ModBlocks, etc.)
  item/                   Item classes
  entity/                 Entity classes
  block/                  Block classes (created when needed)
  block.entity/           BlockEntity classes (created when needed)
  client/                 Client-only code
    renderer/             Entity/block renderers
    screen/               GUI screens (created when needed)
  event/                  Event handlers (created when needed)
  recipe/                 Custom recipe types (created when needed)
```

New packages are created only when their first class is added.

## Workflow

1. Create branch from `main` with proper naming
2. Make changes, commit frequently
3. Open PR to `main`
4. CI must pass (GitHub Actions build)
5. Merge and delete branch
