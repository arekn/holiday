# Holiday Information Service

## Config

### application.yml
Default settings are contained in **application.yml** file.

```
holiday:
  api:
    endpoint: "https://holidayapi.com/v1/holidays"
    key: "1d3526af-ee45-4241-b4db-5100f55e77d3"
  service:
    searchRangeInMonths: 60
```
**holiday.api.endpoint** - Holiday API service endpoint. External service providing holiday information.
**holiday.api.key** - API Key provided from [Holiday API](https://holidayapi.com/). Default key is test key returning dummy holiday data. 
**holiday.service.searchRangeInMonths** - Service is checking for common holidays on a monthly basis. Default value is 60 months meaning that service is searching in 5 years+ from request date.

### Command line arguments
Above settings can be changed without compilation. Example:
```
java -jar holiday-0.0.1-SNAPSHOT.jar --holiday.api.key="dummy-api-key" --holiday.service.searchRangeInMonths=10
```

## Running

###Gradle

Edit *holiday/src/main/resources/application.yml* file and use included gradle wrapper:

```
/.gradlew bootRun
```

### Java -jar
Compile project using gradle wrapper:
```
/.gradlew clean build
```
Output **.jar** is located in: *build/libs/holiday-0.0.1-SNAPSHOT.jar* Go to jar location and run:
```
java -jar holiday-0.0.1-SNAPSHOT.jar --holiday.api.key="dummy-api-key" --holiday.service.searchRangeInMonths=10
```

## Using service

Service is available at *http://localhost:8080/holidays*.
GET request requires two arguments:

**date** - Date in **YYYY-MM-DD** format. Search starting date.

**countries** - List of countries. Supported countries available at [Holiday API](https://holidayapi.com/).

Example requests:

```
/holidays?date=2000-02-01&countries=US,PL

/holidays?date=2000-02-01&countries=US&countries=PL
```

### Response format

Request:
```
/holidays?date=2000-02-01&countries=US,PL,DE
```

Response:
```
{
  "date": "2000-05-01",
  "names": [
    "May Day",
    "Święto Pracy",
    "Tag der Arbeit"
  ]
}
```