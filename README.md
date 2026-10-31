# Poultry Ledger

Poultry Ledger is a phone-friendly Spring Boot 3 / Java 17 / Thymeleaf application for keeping daily egg, feed, hatching, flock and sales records for a small Kenyan laying-hen farm. Amounts are displayed in Kenya shillings as `sh`. The home page is the Daily Log.

## Run locally

Install Java 17+ and Maven, then run:

```bash
mvn spring-boot:run
```

Open `http://localhost:3000`. Run the focused unit suite with `mvn test`.

The default database is a file-based H2 database at `./data/poultry-ledger`. The datasource is property-driven; to switch to PostgreSQL or MySQL, set `SPRING_DATASOURCE_URL`, `SPRING_DATASOURCE_DRIVER`, `SPRING_DATASOURCE_USERNAME`, and `SPRING_DATASOURCE_PASSWORD`, and change the dialect if needed. Hibernate uses `ddl-auto=update` for the small local ledger.

## Pages

Daily Log records eggs by hen type and feed usage with immutable unit-cost snapshots. Feed Mixtures stores ingredients and calculates quantity, price, and cost per kg. Sittings tracks 21-day hatch dates and linked chick groups. Chicks tracks groups and events, including graduation into the adult flock. Hens tracks starting populations and population events with running totals. Sales stores channel, quantity, price snapshot, and total. Settings stores starting populations and default Shop/Buyer/Home prices.

## Calculation rules

A mixture's total quantity is the sum of ingredient kg, total price is the sum of ingredient prices, and cost per kg is total price divided by total quantity. A sitting's expected hatch date is date given plus 21 days; the chick group code uses the expected date until an actual hatch date is entered, then uses that actual date, with numeric suffixes for collisions. Group alive count is starting count plus chick event changes. Adult population is starting population plus population event changes, and a type cannot fall below zero. A daily feed cost is quantity kg times the stored unit cost at the time the usage row was created. A sale total is quantity eggs times its stored price per egg.

No login, dashboard, summary page, monthly report, alerts, charts, exports, health records, vaccination/drug tracking, or egg-stock warnings are included.
