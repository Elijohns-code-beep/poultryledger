# Poultry Ledger delivery checklist

- [x] Spring Boot 3 / Java 17 / Maven project runs with `mvn spring-boot:run`, uses Thymeleaf + Bootstrap 5, persists to file H2, and documents a property-only path to PostgreSQL/MySQL.
- [x] Navigation uses full-word links and a phone-friendly hamburger button that opens a sliding navigation panel; it includes Daily Log, Daily Entries, Feed Mixtures, Sittings, Chicks, Hens, Sales, and Settings.
- [x] Every date defaults to today, remains editable with an HTML date picker, and computed values are calculated in services and displayed read-only.
- [x] Feed mixtures support unique code names, any number of ingredient rows, dynamic Add ingredient, service-computed total kg/price/cost per kg, validation, and historical daily-log cost isolation.
- [x] Sittings have no actual hatch date feature or input; the expected hatch date is calculated from date given plus 21 days and filled into the read-only input.
- [x] Daily Entries shows date, kienyeji eggs, improved kienyeji eggs, total eggs, feed amount, and feed cost, with a final summed total row that updates from saved daily logs.
- [x] Sittings compute expected hatch as date given + 21 days, generate unique uppercase `dMMMyy`/suffix codes from the expected date, and create/update one linked chick group when chicks hatch.
- [x] Chick groups support manual entry, open/closed status, dated positive/negative events, alive calculations, and a one-time move-to-adult-flock workflow that creates a population event and rejects zero/twice moves.
- [x] Hens support starting populations, dated population events with reasons and running totals, and reject changes that would make a hen type negative.
- [x] Daily Log supports one date per log, eggs by hen type, computed total, mixture/Other feed rows, stored unit-cost snapshots, computed feed cost, edit-by-date behavior, and newest-first list totals.
- [x] Sales support all four channels, editable/default prices stored in Settings, buyer name only for Other, stored price snapshots, computed totals, and no stock warning or Home separation.
- [x] Settings support the two starting populations and Shop/Buyer/Home default prices; seed data creates the two hen types and defaults.
- [x] Unit tests cover cost per kg, expected hatch date, chick code generation, chick alive count, population totals, and unit-cost-at-time behavior.
- [x] README lists run instructions, pages, calculation rules, and excluded scope.
