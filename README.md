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
