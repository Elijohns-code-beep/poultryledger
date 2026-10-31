# Poultry Ledger implementation plan

## Approach

Poultry Ledger is implemented as a Java 17 / Spring Boot 3 MVC application using Spring Data JPA, Thymeleaf, Bootstrap 5, and a file-based H2 database. The domain is deliberately server-rendered and mobile-first: forms are compact, large touch targets are used for actions, and nested child rows are added with small vanilla JavaScript helpers. PostgreSQL and MySQL portability is preserved by using standard JPA mappings, `LocalDate`, `BigDecimal`, and property-driven datasource configuration.

All computed values are owned by services. Entities only carry persisted state and lightweight view helpers. Historical snapshots are stored on `FeedUsage.unitCostAtTime` and `Sale.pricePerEgg`; existing rows are preserved when edited. Validation uses Bean Validation plus service-level business rules for uniqueness, non-negative balances, and one-time graduation.

## Project structure

- `src/main/java/com/poultryledger/entity`: JPA entities and status/enumeration types.
- `src/main/java/com/poultryledger/repository`: Spring Data repositories.
- `src/main/java/com/poultryledger/service`: calculation, validation, snapshot, and workflow rules.
- `src/main/java/com/poultryledger/controller`: page and form endpoints.
- `src/main/resources/templates`: Thymeleaf pages and shared navigation fragments.
- `src/main/resources/static/css`: responsive visual system.
- `src/test/java`: focused service unit tests for the requested calculation rules.

## Design

Three possible directions were considered: field-notebook utility (0.06), warm agri-modern (0.08), and monochrome ledger (0.04). Chosen: **warm agri-modern**.

- **Design movement:** contemporary Kenyan farm utility with paper-ledger cues.
- **Core principles:** thumb-friendly controls, calm hierarchy, readable numbers, and clear feedback.
- **Color philosophy:** deep soil green for trust and navigation, maize gold for attention and actions, warm paper backgrounds for familiarity, and restrained red only for validation or destructive warnings.
- **Layout paradigm:** a left rail on wide screens that collapses into a top bar on phones, with pages composed as stacked ledger sections rather than dashboard tiles.
- **Signature elements:** compact section labels, gold ruled dividers, and small “sh”/count metric pills.
- **Interaction philosophy:** every save is explicit, computed figures are visually read-only, and mobile forms keep the next likely action within thumb reach.
- **Animation:** only brief row-add and feedback transitions; no decorative motion.
- **Typography system:** system sans stack for fast field use, with bold condensed-feeling headings created through weight and letter spacing rather than external font dependencies.
- **Brand essence:** a dependable pocket ledger for the everyday rhythm of a laying-hen farm; grounded, clear, practical.
- **Brand voice:** “Record today’s flock work.” and “Keep the numbers ready for the next decision.”
- **Wordmark/mark:** a simple ledger line with a half-egg notch, rendered in CSS beside the wordmark.
- **Signature brand color:** maize gold `#e2a83b`.

## Build workflow

Run `mvn spring-boot:run` for the local app, or `mvn test` for the unit suite. The default port is 3000 for the managed Preview; `server.port` is property-driven. H2 persists to `./data/poultry-ledger` and can be swapped by changing datasource/JPA properties. First startup seeds the two hen types and Shop/Buyer/Home prices.
