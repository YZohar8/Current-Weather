# Current Weather App

A Java application that fetches weather data for major cities, displays the results in the console, and saves them into an Excel file.

---

## Features
- Fetches real-time temperature for a list of cities.
- Displays the cities sorted by temperature (highest to lowest).
- Generates an Excel report (`weather_results.xlsx`) with all results.
- Fully containerized with Docker (builds and runs automatically).

---

## 1. Getting the Code

### If you have Git installed
Clone the repository:
```bash
git clone https://github.com/YZohar8/Current-Weather.git
cd Current-Weather
```

### If you don’t have Git installed
1. Go to the repository URL in your browser (e.g., `https://github.com/YZohar8/Current-Weather`).
2. Click the green **"Code"** button.
3. Select **"Download ZIP"**.
4. Extract the ZIP file and open a terminal/PowerShell in the extracted folder.

---

## 2. Prerequisites

Make sure you have the following installed:

- **Java 17 (JDK)**  
  [Download here](https://adoptium.net/temurin/releases/) (choose JDK 17).
  
  Verify installation:
  ```bash
  java -version
  ```

- **Maven**  
  [Download here](https://maven.apache.org/download.cgi) (Binary ZIP).  
  Follow [installation instructions](https://maven.apache.org/install.html).

  Verify installation:
  ```bash
  mvn -v
  ```

- **Docker** (for containerized usage)  
  [Download Docker Desktop](https://www.docker.com/products/docker-desktop/).

  Verify installation:
  ```bash
  docker -v
  ```

---

## 3. Running the Project (Without Docker)

1. Build the project:
```bash
mvn clean package
```

?. run tests:
```bash
mvn test
```

2. Run the app:
```bash
mvn exec:java 
```

3. The application will:
   - Fetch the temperatures for the cities.
   - Display the sorted results.
   - Save results into `Excel/weather_results.xlsx`.

---

## 4. Running the Project with Docker

1. Build the Docker image:
```bash
docker build -t current-weather-app .
```

2. Run the container:
```bash
docker run --rm -v ${PWD}/Excel:/app/Excel current-weather-app
```

3. The results will be saved into the `Excel` folder.


?. run tests:
```bash
mvn clean package
mvn test
```
---

## Output Example
Console output:
```
Weather results:
Cairo : 38.4°C
Delhi : 30.1°C
Shanghai : 29.9°C
...
```

Generated Excel file (`Excel/weather_results.xlsx`):
- Sheet: **Weather Results**
- Columns: **City**, **Temperature (°C)**


## Implementation Details

### Rate-limiting
To avoid overwhelming the weather API and prevent being blocked, I implemented a simple rate-limiting mechanism that controls the number of API requests sent within a specific time frame. This ensures compliance with the API usage policies and maintains stable performance.

### Concurrency
The application uses concurrency to speed up fetching weather data for multiple cities. By leveraging Java's concurrency utilities (such as `ExecutorService`), multiple API calls run in parallel without blocking the main thread, which improves response time and resource utilization.

### Retry Logic
In case of transient failures like network timeouts or temporary API errors, a retry mechanism is in place. It retries failed API requests a limited number of times with a short delay between attempts. This increases robustness and reduces the chance of failing due to temporary issues.

### Potential Improvements with More Time
- **Advanced Rate Limiting:** Implement adaptive rate-limiting that dynamically adjusts based on API response headers or error codes.
- **Better Error Handling:** Introduce more granular error handling with specific responses for different HTTP status codes.
- **Asynchronous Processing:** Switch to fully asynchronous API calls using reactive programming frameworks to further improve scalability.
- **Configuration:** Externalize retry parameters and rate limits to configuration files for easier tuning without code changes.
- **Logging Enhancements:** Improve logging detail and integrate with centralized logging systems for easier monitoring and troubleshooting.
