# Tempered - Roadmap

> Tempered es un overhaul de progresion early-to-mid game para Minecraft. Cambia como
> el jugador avanza desde el primer minuto, reemplazando la progresion vanilla por un
> sistema mas realista y gradual basado en estaciones de trabajo y forjado.

## Ecosistema

| Mod | Rol | Estado |
|-----|-----|--------|
| **Tempered** (core) | Sistema de crafteo, estaciones, progresion, minerales | En desarrollo |
| **Tempered Combat** | Mecanicas de combate, armas 1/2 manos, dual-wielding | Planeado (futuro) |

**Dependencias:** GeckoLib 4.7.3 (animaciones de entidades, bloques, items, armaduras)

---

## Fase 0 - Fundacion

**Estado:** Completada

- [x] Proyecto Forge 1.20.1 configurado
- [x] Repositorio en GitHub con CI/CD
- [x] Estructura de paquetes organizada

---

## Fase 1 - Overhaul de Progresion Temprana

**Estado:** En progreso (6/8 PRs completados)

**Objetivo:** Cambiar la experiencia del jugador desde el spawn. Nada de romper
arboles a punos para obtener madera.

### 1.1 - Modificar comportamiento vanilla
- [x] Romper hojas/pasto con la mano da items primitivos (ramas, fibras)
- [x] Piedras sueltas y ramas aparecen en el suelo (generacion en superficie por bioma)
- [x] Herramientas vanilla de madera/piedra deshabilitadas (recetas + creative tab)
- [ ] La mano desnuda NO rompe bloques de madera (restricciones de rotura)
- [ ] Definir que bloques SI se pueden romper a mano y cuales no

> **TODO:** Ajustar densidad de generacion de piedritas y ramas por bioma.
> Actualmente la generacion es excesiva. Revisar placed features (tries, rarity_filter)
> y agregar mas reglas de placement (ej: no generar sobre arena, nieve, etc.)

### 1.2 - Items y herramientas primitivas
- [x] Items: Rama, Piedrita, Fibra Vegetal, Trozo de Tronco, Rama Extraña
- [x] Herramientas: Cuchillo Primitivo, Hacha Primitiva, Honda Primitiva
- [x] Mesa de Crafteo Primitiva con sistema de recetas 2x3 custom
- [x] Recetas: Fogata, Honda Primitiva (via mesa primitiva)
- [x] Mesa se craftea con 4 Trozos de Tronco en grid vanilla 2x2

### 1.3 - Pendiente
- [ ] Restricciones de rotura de bloques a mano (PR 7)
- [ ] Rebalanceo general y version bump a 0.2.0 (PR 8)

**Entregable:** Un jugador nuevo NO puede simplemente punchear un arbol. Debe
recoger ramas y piedras, craftear herramientas primitivas, y progresar de forma
mas realista.

---

## Fase 2 - Mesa de Forjado + Martillo

**Objetivo:** Introducir la primera estacion de trabajo custom con UI propia.

### 2.1 - Martillo de Forjado
- [ ] Item: Martillo de Forjado (herramienta clave del mod)
- [ ] El martillo se usa como componente en la Mesa de Forjado
- [ ] Textura y modelo (GeckoLib animado)
- [ ] Definir si el martillo tiene durabilidad o es permanente

### 2.2 - Mesa de Forjado (bloque + UI)
- [ ] Bloque: Mesa de Forjado con modelo 3D animado (GeckoLib)
- [ ] BlockEntity con inventario
- [ ] Container + Screen (UI custom con slots para martillo)
- [ ] Sistema de recetas custom (JSON data-driven)
- [ ] Recetas iniciales: herramientas basicas de hierro, armas basicas
- [ ] Receta para craftear la mesa misma

**Entregable:** El jugador puede construir una Mesa de Forjado y usarla para crear
herramientas/armas con una UI custom.

---

## Fase 3 - Horno de Aleacion

**Objetivo:** Sistema de fundicion para crear nuevos materiales.

### 3.1 - Nuevos minerales y aleaciones
- [ ] Definir lista de minerales nuevos (ej: estano, cobre mejorado, etc.)
- [ ] Definir aleaciones (ej: bronce = cobre + estano)
- [ ] Generacion de minerales en el mundo (ore generation)
- [ ] Items de lingotes/pepitas para cada mineral

### 3.2 - Horno de Aleacion (bloque + UI)
- [ ] Bloque con modelo animado (GeckoLib)
- [ ] BlockEntity con slots de input multiple + fuel + output
- [ ] UI custom (2+ inputs, combustible, barra de progreso, output)
- [ ] Sistema de recetas (input multiple -> output)
- [ ] Receta para craftear el horno

**Entregable:** El jugador puede fundir combinaciones de minerales para crear
aleaciones, desbloqueando materiales para armas/herramientas avanzadas.

---

## Fase 4 - Mesa de Masterizado

**Objetivo:** Estacion de trabajo para items de tier alto.

- [ ] Bloque con modelo animado (GeckoLib)
- [ ] UI compleja (multiples slots, requiere martillo + aleacion + base)
- [ ] Recetas para armas/herramientas avanzadas
- [ ] Set de armas/herramientas de cada tier de aleacion

**Entregable:** El tier final de crafteo esta disponible. El jugador puede forjar
las mejores armas y herramientas del mod.

---

## Fase 5 - Pulido y Ecosistema

- [ ] Compatibilidad con JEI (Just Enough Items) para mostrar recetas custom
- [ ] Sonidos custom para forjado, fundicion, etc.
- [ ] Particulas y efectos visuales
- [ ] Tempered Combat: disenar mecanicas de combate como mod separado

---

## Notas

- Cada fase debe ser jugable y testeable de forma independiente
- No avanzar a la siguiente fase hasta que la actual este estable
- Las decisiones de game design se toman al inicio de cada fase, no todas de golpe
- El mod es opinionado: si lo instalas, la progresion cambia. No hay "modo vanilla"
