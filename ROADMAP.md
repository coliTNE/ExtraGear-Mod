# Tempered - Roadmap

> Tempered es un overhaul de progresion para Minecraft. Cambia como el jugador avanza
> desde el primer minuto, reemplazando la progresion vanilla por un sistema mas
> realista y gradual basado en estaciones de trabajo y forjado.

## Ecosistema

| Mod | Rol | Estado |
|-----|-----|--------|
| **Tempered** (core) | Sistema de crafteo, estaciones, progresion, minerales | En desarrollo |
| **Tempered Combat** | Dual-wielding, armas 1/2 manos, mecanicas de combate | Planeado |
| GeckoLib / Player Animator | Animaciones (soft dependency, opcional) | Por evaluar |

---

## Fase 0 - Fundacion (actual)

**Estado:** Completada

Lo que ya existe:
- [x] Proyecto Forge 1.20.1 configurado
- [x] Flecha de obsidiana (item + entidad + receta + textura)
- [x] Repositorio en GitHub con git flow

---

## Fase 1 - Overhaul de Progresion Temprana

**Objetivo:** Cambiar la experiencia del jugador desde el spawn. Nada de romper
arboles a punos para obtener madera.

### 1.1 - Modificar comportamiento vanilla
- [ ] La mano desnuda NO rompe bloques de madera (o los rompe muy lento sin drop util)
- [ ] Romper hojas/pasto con la mano da items primitivos (ramas, fibras)
- [ ] Piedras sueltas aparecen en el suelo (item en el mundo o drop de grava/tierra)
- [ ] Definir que bloques SI se pueden romper a mano y cuales no

### 1.2 - Items y herramientas primitivas
- [ ] Item: Rama (drop de hojas)
- [ ] Item: Piedra suelta (drop de grava/tierra o generacion en superficie)
- [ ] Item: Fibra/cuerda primitiva (drop de pasto alto)
- [ ] Herramienta: Hacha primitiva (rama + piedra suelta) - permite romper madera
- [ ] Herramienta: Pico primitivo (rama + piedra suelta) - permite romper piedra basica
- [ ] Recetas de crafteo vanilla para herramientas primitivas (crafting 2x2 del inventario)

### 1.3 - Rebalanceo de progresion vanilla
- [ ] Definir en que punto el jugador puede craftear herramientas vanilla (madera, piedra, hierro)
- [ ] Decidir si las herramientas vanilla se craftean en mesa vanilla o en Mesa de Forjado
- [ ] Ajustar durabilidad/velocidad de herramientas primitivas

**Entregable:** Un jugador nuevo NO puede simplemente punchear un arbol. Debe
recoger ramas y piedras, craftear herramientas primitivas, y progresar de forma
mas realista.

---

## Fase 2 - Mesa de Forjado + Martillo

**Objetivo:** Introducir la primera estacion de trabajo custom con UI propia.

### 2.1 - Martillo de Forjado
- [ ] Item: Martillo de Forjado (herramienta clave del mod)
- [ ] El martillo se usa como componente en la Mesa de Forjado
- [ ] Textura y modelo
- [ ] Definir si el martillo tiene durabilidad o es permanente

### 2.2 - Mesa de Forjado (bloque + UI)
- [ ] Bloque: Mesa de Forjado con modelo 3D y textura
- [ ] Tile Entity con inventario
- [ ] Container + Screen (UI custom, estilo crafting table pero con slots para martillo)
- [ ] Sistema de recetas custom (JSON data-driven)
- [ ] Recetas iniciales: herramientas basicas de hierro, armas basicas
- [ ] Receta para craftear la mesa misma

**Entregable:** El jugador puede construir una Mesa de Forjado y usarla para crear
herramientas/armas con una UI custom.

**Conceptos Java clave:** Tile Entities, Containers, Screens, MenuTypes, RecipeTypes.

---

## Fase 3 - Horno de Aleacion

**Objetivo:** Sistema de fundicion para crear nuevos materiales.

### 3.1 - Nuevos minerales y aleaciones
- [ ] Definir lista de minerales nuevos (ej: estano, cobre mejorado, etc.)
- [ ] Definir aleaciones (ej: bronce = cobre + estano)
- [ ] Generacion de minerales en el mundo (ore generation)
- [ ] Items de lingotes/pepitas para cada mineral

### 3.2 - Horno de Aleacion (bloque + UI)
- [ ] Bloque con modelo y textura
- [ ] Tile Entity con slots de input multiple + fuel + output
- [ ] UI custom (2+ inputs, combustible, barra de progreso, output)
- [ ] Sistema de recetas (input multiple → output)
- [ ] Receta para craftear el horno

**Entregable:** El jugador puede fundir combinaciones de minerales para crear
aleaciones, desbloqueando materiales para armas/herramientas avanzadas.

---

## Fase 4 - Mesa de Masterizado

**Objetivo:** Estacion de trabajo para items de tier alto.

### 4.1 - Mesa de Masterizado (bloque + UI)
- [ ] Bloque con modelo y textura
- [ ] UI mas compleja (multiples slots, quizas requiere martillo + aleacion + base)
- [ ] Recetas para armas/herramientas avanzadas
- [ ] Posible requisito de nivel de experiencia o material especial

### 4.2 - Armas y herramientas avanzadas
- [ ] Definir set de armas/herramientas de cada tier de aleacion
- [ ] Estadisticas balanceadas (dano, durabilidad, velocidad)
- [ ] Texturas para cada item

**Entregable:** El tier final de crafteo esta disponible. El jugador puede forjar
las mejores armas y herramientas del mod.

---

## Fase 5 - Pulido y Ecosistema

### 5.1 - Config y compatibilidad
- [ ] Archivo de configuracion para server admins (ajustes de balanceo)
- [ ] Compatibilidad con JEI (Just Enough Items) para mostrar recetas custom
- [ ] Sonidos custom para forjado, fundicion, etc.
- [ ] Particulas y efectos visuales

### 5.2 - Soft dependencies (animaciones)
- [ ] Evaluar GeckoLib vs Player Animator
- [ ] Animaciones de forjado en la mesa
- [ ] Animaciones de uso de martillo

### 5.3 - Tempered Combat (mod separado)
- [ ] Crear proyecto separado para el mod de combate
- [ ] Sistema de armas de una mano / dos manos
- [ ] Dual-wielding (ballesta de bolsillo, espadas ligeras)
- [ ] Integracion con Tempered core (las armas de Tempered heredan propiedades de combate)

---

## Prioridades Tecnicas (transversales)

- [ ] CI/CD con GitHub Actions (build automatico, test)
- [ ] Estructura de paquetes Java organizada para escalar
- [ ] Tests unitarios donde tenga sentido
- [ ] Versionado semantico (SemVer)
- [ ] CHANGELOG.md actualizado por fase/release
- [ ] Branches por feature, PRs con review

---

## Notas

- Cada fase debe ser jugable y testeable de forma independiente
- No avanzar a la siguiente fase hasta que la actual este estable
- Las decisiones de game design (que minerales, que recetas, balanceo) se toman
  al inicio de cada fase, no todas de golpe
- El mod es opinionado: si lo instalas, la progresion cambia. No hay "modo vanilla"
