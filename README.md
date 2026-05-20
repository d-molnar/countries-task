# Countries Land Route Service

Calculates land routes between countries via border crossings.

## Build and run

```bash
./mvnw spring-boot:run
```

## API

```
GET /routing/{origin}/{destination}
```

Returns a land route between two countries identified by cca3 codes. Returns an empty `HTTP 400` if no land route exists.

```bash
curl http://localhost:8080/routing/CZE/ITA
# {"route":["CZE","AUT","ITA"]}
```

## Test

```bash
./mvnw test
```

## Data

Border data is bundled from `src/main/resources/data/borders.json`, extracted from the [mledoze/countries](https://raw.githubusercontent.com/mledoze/countries/master/countries.json) dataset via `extract-borders.sh`. Requires `jq`.
