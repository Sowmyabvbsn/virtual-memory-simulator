# Operating Systems Simulator

An interactive educational tool for simulating and visualizing Operating System concepts including Virtual Memory Management and Multithreading/Concurrency patterns.

## Features

### 1. Virtual Memory Simulator
- FIFO page replacement algorithm
- Visual representation of RAM frames and disk pages
- Real-time hit/fault tracking
- Configurable RAM size and page count
- Animated step-by-step execution
- Hit rate calculation

### 2. Multithreading & Concurrency Simulator
- **Deadlock Detection**: Simulates circular wait conditions and detects deadlocks
- **Race Condition**: Demonstrates race conditions with multiple threads
- **Producer-Consumer**: Classic synchronization problem with bounded buffer
- **Thread Pool**: Simulates thread pool execution patterns

### 3. AI-Based Recommendations
- Intelligent analysis of simulation results
- Best practice code patterns for Java concurrency
- Specific recommendations based on simulation type
- Educational explanations of concurrency issues

### 4. MySQL Database Integration
- Persistent storage of simulation results
- Historical tracking of simulations
- Performance metrics storage

## Technology Stack

### Backend
- Java 17
- Spring Boot 3.5.7
- Spring Data JPA
- MySQL 8.0+
- Maven

### Frontend
- React 19.2.0
- JavaScript (ES6+)
- CSS3

## Prerequisites

1. Java Development Kit (JDK) 17 or higher
2. Node.js 16+ and npm
3. MySQL 8.0 or higher
4. Maven 3.6+

## Database Setup

1. Install MySQL and start the MySQL service

2. Create a database (optional - will be created automatically):
```sql
CREATE DATABASE simulator_db;
```

3. Update database credentials in `backend/src/main/resources/application.properties` if needed:
```properties
spring.datasource.username=root
spring.datasource.password=root
```

The application will automatically create the necessary tables on startup.

## Installation & Running

### Backend Setup

1. Navigate to the backend directory:
```bash
cd backend
```

2. Build the project:
```bash
./mvnw clean install
```

3. Run the Spring Boot application:
```bash
./mvnw spring-boot:run
```

The backend server will start on `http://localhost:8080`

### Frontend Setup

1. Navigate to the frontend directory:
```bash
cd frontend
```

2. Install dependencies:
```bash
npm install
```

3. Start the development server:
```bash
npm start
```

The frontend will open automatically at `http://localhost:3000`

## Usage Guide

### Virtual Memory Simulation

1. Select the "Virtual Memory" tab
2. Configure:
   - RAM frames (1-10)
   - Max pages on disk (1-100)
   - Animation speed (ms per step)
3. Enter a page sequence (e.g., `0,1,2,0,3,0,4,2`)
4. Click "Simulate" to watch the animation
5. Observe:
   - Real-time RAM frame updates
   - Hit/fault counts
   - Hit rate percentage
   - Page highlighting in disk and RAM

### Concurrency Simulation

1. Select the "Multithreading & Concurrency" tab
2. Choose a simulation type:

   **Deadlock Detection**
   - Automatically creates 2 threads and 2 resources
   - Demonstrates circular wait condition
   - Shows deadlock detection

   **Race Condition**
   - Configure number of threads (2-20)
   - Simulates concurrent access to shared resources
   - Demonstrates synchronization issues

   **Producer-Consumer**
   - Set number of producers (1-10)
   - Set number of consumers (1-10)
   - Set buffer size (1-20)
   - Shows classic bounded buffer problem

   **Thread Pool**
   - Configure pool size (1-10)
   - Set task count (1-50)
   - Demonstrates thread pool execution

3. Click "Run Simulation"
4. View results:
   - Thread states with color coding
   - Resource allocation status
   - Execution log
   - AI recommendations

### Understanding Thread States

- **NEW**: Thread is created but not started
- **RUNNABLE**: Thread is ready to run
- **RUNNING**: Thread is currently executing
- **BLOCKED**: Thread is waiting for a resource
- **WAITING**: Thread is waiting indefinitely
- **TIMED_WAITING**: Thread is waiting for a specific time
- **TERMINATED**: Thread has completed execution

## API Endpoints

### Virtual Memory
- `POST /simulate` - Run virtual memory simulation

### Concurrency
- `POST /api/concurrency/simulate/deadlock` - Simulate deadlock
- `POST /api/concurrency/simulate/race-condition` - Simulate race condition
- `POST /api/concurrency/simulate/producer-consumer` - Simulate producer-consumer
- `POST /api/concurrency/simulate/thread-pool` - Simulate thread pool
- `GET /api/concurrency/history` - Get all simulation history
- `GET /api/concurrency/history/{type}` - Get history by type

## Database Schema

### simulation_results
- id (Primary Key)
- simulation_type (VARCHAR)
- input_sequence (VARCHAR)
- hits (INT)
- faults (INT)
- ram_size (INT)
- created_at (TIMESTAMP)
- result_data (TEXT)

### thread_simulation_results
- id (Primary Key)
- thread_count (INT)
- simulation_type (VARCHAR)
- deadlock_detected (BOOLEAN)
- execution_time_ms (BIGINT)
- created_at (TIMESTAMP)
- thread_states (TEXT)
- ai_recommendation (TEXT)

## Project Structure

```
project/
├── backend/
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/org/virtualmemory/
│   │   │   │   ├── controller/
│   │   │   │   │   ├── SimulationController.java
│   │   │   │   │   └── ConcurrencyController.java
│   │   │   │   ├── entity/
│   │   │   │   │   ├── SimulationResult.java
│   │   │   │   │   └── ThreadSimulationResult.java
│   │   │   │   ├── model/
│   │   │   │   │   ├── SimulatedThread.java
│   │   │   │   │   ├── Resource.java
│   │   │   │   │   ├── ConcurrencySimulator.java
│   │   │   │   │   └── [other models]
│   │   │   │   ├── repository/
│   │   │   │   │   ├── SimulationResultRepository.java
│   │   │   │   │   └── ThreadSimulationResultRepository.java
│   │   │   │   ├── service/
│   │   │   │   │   └── AIRecommendationService.java
│   │   │   │   └── VirtualMemorySimulatorApplication.java
│   │   │   └── resources/
│   │   │       └── application.properties
│   └── pom.xml
├── frontend/
│   ├── public/
│   ├── src/
│   │   ├── components/
│   │   │   ├── MemorySimulator.js
│   │   │   └── ConcurrencySimulator.js
│   │   ├── App.js
│   │   ├── App.css
│   │   └── index.js
│   └── package.json
└── README.md
```

## Learning Objectives

This simulator helps students understand:

1. **Virtual Memory Management**
   - Page replacement algorithms
   - Hit vs. fault concepts
   - Performance implications

2. **Thread Lifecycle**
   - Thread states and transitions
   - Thread scheduling

3. **Concurrency Issues**
   - Deadlock conditions
   - Race conditions
   - Synchronization problems

4. **Synchronization Patterns**
   - Producer-Consumer pattern
   - Thread pools
   - Resource management

5. **Best Practices**
   - AI-generated recommendations
   - Java concurrency utilities
   - Proper synchronization techniques

## Troubleshooting

### Backend won't start
- Ensure MySQL is running
- Check database credentials in `application.properties`
- Verify port 8080 is not in use

### Frontend can't connect to backend
- Ensure backend is running on port 8080
- Check CORS settings in controllers
- Verify API_URL in React components

### Database connection errors
- Verify MySQL service is running
- Check username/password
- Ensure database exists or auto-creation is enabled

## Future Enhancements

- Additional page replacement algorithms (LRU, Optimal)
- More concurrency patterns (Readers-Writers, Dining Philosophers)
- Real-time performance metrics
- Export simulation results
- Comparison between different algorithms
- Interactive deadlock resolution

## Contributors

This project is designed for educational purposes as part of a 4th year 2nd semester Operating Systems course.

## License

This project is created for educational purposes.
