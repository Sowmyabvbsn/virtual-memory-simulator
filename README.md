# 🎓 Virtual Memory Simulator Suite - Complete Educational Tool

## Final Year Project - Demand Paging, Multithreading & Concurrency

### 📖 Project Overview

This project is a **comprehensive educational simulator suite** featuring **TWO powerful applications** for demonstrating virtual memory concepts, multithreading, concurrency, synchronization, and page replacement algorithms. It provides interactive Java Swing-based GUIs where students can learn both single-threaded and multithreaded demand paging concepts through visual simulation.

### 🎯 Dual Simulation Tools

This project includes TWO complete simulators:

#### 1️⃣ **DemandPagingGUI** - Single-Thread Demand Paging Simulator
The original, foundational simulator focusing on core demand paging concepts with step-by-step visualization of page replacement algorithms.

#### 2️⃣ **MultiThreadGUI** - Multithreaded Concurrency Simulator
Advanced simulator demonstrating concurrent execution, thread scheduling, synchronization mechanisms, and deadlock detection with multiple competing threads.

---

## ✨ Application 1: DemandPagingGUI - Single-Thread Simulator

### 📘 Overview
The original demand paging simulator provides an intuitive interface for learning fundamental page replacement algorithms. Perfect for understanding basic virtual memory concepts before moving to multithreading.

### 🔹 Core Features

#### Page Replacement Algorithms
- **FIFO** (First-In-First-Out): Pages are evicted in order of arrival
- **LRU** (Least Recently Used): Evicts the page not used for the longest time
- **MRU** (Most Recently Used): Evicts the most recently used page
- **OPT** (Optimal): Evicts the page that won't be used for the longest time (theoretical best)

#### Interactive Simulation
- **Step-by-Step Mode**: Execute one page reference at a time with "Next Step" button
- **Auto-Play Mode**: Continuous execution with "Play/Pause" control
- **Visual Frame Display**: See memory frames update in real-time
- **Color Coding**: 
  - 🟢 Green for page hits (page found in memory)
  - 🔴 Red for page faults (page not in memory, replacement needed)

#### Statistics & Analysis
- **Real-time Metrics**: Hits, faults, and hit ratio calculated during execution
- **Step Counter**: Track progress through reference string
- **Eviction Notification**: See which page is being replaced
- **Final Summary**: Complete statistics popup when simulation completes

#### Comparison Tool
- **Bar Chart Visualization**: Compare all 4 algorithms on the same reference string
- **Sorted Display**: Algorithms ranked by page fault count (best to worst)
- **Color-coded Bars**: Easy visual comparison of algorithm performance

#### User Interface
- **Algorithm Selector**: Dropdown to choose FIFO, LRU, MRU, or OPT
- **Frame Count Input**: Configure number of memory frames (1-10)
- **Reference String Input**: Enter custom page reference sequence (space-separated)
- **Reset Button**: Clear simulation and start fresh
- **Default Example**: Pre-filled with: "7 0 1 2 0 3 0 4 2 3 0 3 2" (3 frames)

### 📊 Educational Value (DemandPagingGUI)
Students learn:
- How page faults occur when pages are not in memory
- How different algorithms make replacement decisions
- Why some algorithms perform better than others
- The concept of locality of reference
- Trade-offs between algorithm complexity and performance

### 🎯 How to Use DemandPagingGUI

1. **Launch the Simulator**:
   ```bash
   java DemandPagingGUI
   ```

2. **Configure Simulation**:
   - Set number of frames (default: 3)
   - Enter reference string (e.g., "7 0 1 2 0 3 0 4 2 3 0 3 2")
   - Select algorithm (FIFO, LRU, MRU, or OPT)

3. **Run Simulation**:
   - Click "Start Simulation" to initialize
   - Click "Next Step" to step through one page at a time
   - OR click "Play" for automatic continuous execution
   - Watch frames update with color coding for hits/faults

4. **Analyze Results**:
   - View real-time hit ratio and fault count
   - See which page is evicted on each fault
   - Click "Show Bar Chart" to compare all algorithms

5. **Experiment**:
   - Try different reference strings
   - Change frame count to see impact
   - Compare algorithm performance

---

## ✨ Application 2: MultiThreadGUI - Multithreaded Simulator

### 📘 Overview
Advanced simulator demonstrating concurrent execution with multiple threads competing for shared memory. Includes thread scheduling, synchronization mechanisms, and deadlock detection.

### 🔹 Multithreading & Concurrency
- **3-8 Concurrent Threads**: Each thread has its own reference string and priority
- **Shared Memory Model**: All threads compete for the same memory frames (realistic OS simulation)
- **Thread State Visualization**: Real-time display of thread states (Running, Ready, Waiting, Blocked, Completed)
- **Color-coded Thread Ownership**: Visual identification of which thread owns each memory frame

### 🔹 Scheduling Algorithms
1. **FCFS (First-Come-First-Served)**: Threads execute in arrival order
2. **Round-Robin**: Time-sliced execution with adjustable time quantum (1-10 units)
3. **Priority-based**: Higher priority threads get CPU preference

### 🔹 Synchronization Mechanisms
- **Mutex Locks**: Exclusive access to shared resources
- **Semaphores**: Counting semaphore with configurable permits (1-5)
- **Lock Visualization**: Real-time display of locked/unlocked states
- **Waiting Queue Display**: Shows which threads are blocked on locks

### 🔹 Page Replacement Algorithms
- **FIFO** (First-In-First-Out)
- **LRU** (Least Recently Used)
- **MRU** (Most Recently Used)
- **OPT** (Optimal - theoretical best)

### 🔹 Deadlock Detection & Simulation
- **Automatic Deadlock Detection**: Uses resource allocation graph cycle detection
- **Visual Deadlock Notification**: Alerts when circular wait is detected
- **Deadlock Scenarios**: Pre-configured setup to demonstrate deadlock conditions

### 🔹 Educational Scenarios
Pre-configured scenarios for learning:
1. **Race Condition Demo**: Multiple threads accessing same pages without synchronization
2. **Deadlock Scenario**: Threads create circular wait with mutexes
3. **Priority Inversion**: High-priority thread blocked by low-priority thread
4. **Starvation Example**: Low-priority thread never gets CPU time
5. **Thrashing Simulation**: Too many threads cause excessive page faults

### 🔹 Interactive Interface
- **Tabbed Layout**: Organized into Configuration, Simulation, and Statistics tabs
- **Step-by-Step Execution**: Manual stepping through simulation for detailed analysis
- **Continuous Execution**: Automatic playback with pause/resume functionality
- **Real-time Statistics**: Per-thread and global metrics
- **Execution Log**: Detailed event timeline for analysis

---

## 🏗️ Architecture

### Core Components

```
┌─────────────────────────────────────────────────┐
│           MultiThreadGUI.java                   │
│     (Main GUI - Tabbed Interface)               │
└─────────────────────────────────────────────────┘
                    |
        ┌───────────┴───────────┐
        │                       │
        ▼                       ▼
┌──────────────────┐   ┌──────────────────┐
│ Configuration    │   │  Simulation      │
│     Tab          │   │     Tab          │
└──────────────────┘   └──────────────────┘
                       |
         ┌─────────────┴─────────────┐
         ▼                           ▼
┌─────────────────────┐    ┌──────────────────┐
│ MultiThreadSimulator│    │  ThreadScheduler │
│   (Core Engine)     │    │   (Scheduling)   │
└─────────────────────┘    └──────────────────┘
         │
    ┌────┴────┬───────────┬──────────┐
    ▼         ▼           ▼          ▼
┌────────┐ ┌───────┐ ┌──────────┐ ┌─────────┐
│Process │ │ Lock  │ │ Deadlock │ │Original │
│Thread  │ │Resource│ │ Detector │ │ Files   │
└────────┘ └───────┘ └──────────┘ └─────────┘
```

### File Structure

```
/app/
│
├── ═══ APPLICATION 1: Single-Thread Simulator ═══
├── DemandPagingGUI.java         # Main GUI for single-thread simulation
├── DemandPaging.java            # Core logic for page replacement algorithms
├── BarChartFrame.java           # Algorithm comparison chart
│
├── ═══ APPLICATION 2: Multithreaded Simulator ═══
├── MultiThreadGUI.java          # Main GUI with tabbed interface
├── MultiThreadSimulator.java    # Core multithreaded simulation engine
├── ProcessThread.java           # Thread representation with states
├── ThreadScheduler.java         # Scheduling algorithms (FCFS, RR, Priority)
├── LockResource.java            # Mutex and Semaphore implementation
├── DeadlockDetector.java        # Deadlock detection algorithm
│
└── README.md                    # This comprehensive documentation
```

### 📊 Educational Value (MultiThreadGUI)
Students learn:
- Multithreading and concurrent execution
- Thread states and lifecycle transitions
- Context switching overhead and impact
- Scheduling algorithm comparison and trade-offs
- Synchronization primitives (Mutex vs Semaphore)
- Race conditions and their consequences
- Deadlock conditions, detection, and prevention
- Priority inversion and starvation scenarios
- Thrashing in memory-constrained systems
- Performance metrics and system analysis

---

## 🚀 How to Run

### Quick Start (Both Applications)

```bash
cd /app
javac *.java
```

### Running Individual Applications

#### Launch Single-Thread Simulator (DemandPagingGUI):
```bash
java DemandPagingGUI
```
Perfect for:
- Learning basic demand paging concepts
- Understanding page replacement algorithms
- Step-by-step algorithm observation
- Quick algorithm comparison

#### Launch Multithreaded Simulator (MultiThreadGUI):
```bash
java MultiThreadGUI
```
Perfect for:
- Advanced multithreading concepts
- Thread scheduling algorithms
- Synchronization and deadlock
- Complex concurrency scenarios

### Prerequisites
- Java Development Kit (JDK) 11 or higher
- Terminal/Command Prompt
- Display capability for GUI (X11 for Linux, native for Windows/Mac)

---

## 📚 Comprehensive Usage Guide

### 🎓 Learning Path Recommendation

**For Beginners:**
1. Start with **DemandPagingGUI** to understand:
   - What is a page fault
   - How page replacement works
   - Differences between FIFO, LRU, MRU, OPT
   - Impact of frame count on performance

2. Then move to **MultiThreadGUI** to explore:
   - How multiple processes share memory
   - Thread scheduling effects
   - Need for synchronization
   - Deadlock scenarios

### 📖 DemandPagingGUI - Detailed Usage

#### Window Layout:
```
┌─────────────────────────────────────────────────┐
│         DEMAND PAGING SIMULATION                │
├─────────────────────────────────────────────────┤
│  Settings Panel:                                │
│  Number of Frames: [3]                          │
│  Reference String: [7 0 1 2 0 3 0 4 2 3 0 3 2]  │
│  Algorithm: [FIFO ▼]                            │
│  [Start] [Next Step] [Play] [Chart] [Reset]    │
├─────────────────────────────────────────────────┤
│  Memory Frames:                                 │
│  ┌───┐ ┌───┐ ┌───┐                              │
│  │ 7 │ │ 0 │ │ 1 │                              │
│  └───┘ └───┘ └───┘                              │
│  Evicted Page: 4                                │
├─────────────────────────────────────────────────┤
│  Status: ✗ FAULT → Page 2 not in memory        │
│  Step: 8 / 13                                   │
│  Hits: 3 | Faults: 5 | Hit Ratio: 37.50%       │
└─────────────────────────────────────────────────┘
```

#### Step-by-Step Instructions:

1. **Initial Setup**:
   - Enter number of frames (e.g., 3, 4, 5)
   - Enter reference string (space-separated page numbers)
   - Select algorithm from dropdown

2. **Start Simulation**:
   - Click "Start Simulation" button
   - Frames initialize to empty state

3. **Execute Steps**:
   - **Manual Mode**: Click "Next Step" for each page reference
     - Observe if it's a HIT (green) or FAULT (red)
     - See which page gets evicted (if any)
   - **Auto Mode**: Click "Play" for continuous execution
     - Use "Pause" to stop at any point
     - Adjustable speed (1 second per step)

4. **Interpret Results**:
   - 🟢 **Green frames**: Page just accessed (HIT)
   - 🔴 **Red frames**: New page loaded (FAULT)
   - **Evicted Page label**: Shows which page was replaced
   - **Status line**: Explains what happened this step

5. **Compare Algorithms**:
   - After completion, click "Show Bar Chart"
   - View all 4 algorithms ranked by performance
   - Lower bars = fewer faults = better performance

6. **Reset & Experiment**:
   - Click "Reset" to clear
   - Try different reference strings to see patterns
   - Experiment with frame counts

#### Example Scenarios (DemandPagingGUI):

**Scenario 1: FIFO Belady's Anomaly**
```
Frames: 3
Reference: 1 2 3 4 1 2 5 1 2 3 4 5
Algorithm: FIFO
Result: 9 page faults

Frames: 4
Reference: 1 2 3 4 1 2 5 1 2 3 4 5
Algorithm: FIFO
Result: 10 page faults (MORE faults with MORE frames!)
```

**Scenario 2: LRU Performance**
```
Frames: 3
Reference: 7 0 1 2 0 3 0 4 2 3 0 3 2
Algorithm: LRU
Result: Good performance with temporal locality
```

**Scenario 3: OPT (Theoretical Best)**
```
Frames: 3
Reference: 1 2 3 4 1 2 5 1 2 3 4 5
Algorithm: OPT
Result: Minimum possible page faults (reference for comparison)
```

---

### 📖 MultiThreadGUI - Detailed Usage

#### Tab 1: Configuration ⚙️

1. **Set Number of Threads** (3-8): Use the spinner to select how many threads to simulate
2. **Choose Scheduling Algorithm**:
   - FCFS: First-come-first-served
   - Round-Robin: Select and adjust time quantum with slider (1-10)
   - Priority: Threads execute based on priority values
3. **Configure Synchronization**:
   - None: No synchronization (demonstrates race conditions)
   - Mutex: Exclusive locks (can demonstrate deadlock)
   - Semaphore: Counting semaphore with 1-5 permits
4. **Select Page Replacement Algorithm**: FIFO, LRU, MRU, or OPT
5. **Set Number of Memory Frames** (2-10)
6. **Configure Each Thread**:
   - Reference String: Space-separated page numbers (e.g., "1 2 3 4 5")
   - Priority: 1-10 (higher = more priority)
7. **Load Pre-configured Scenarios** (optional): Select from dropdown and click "Load"

### Tab 2: Simulation ▶️

1. **Start Simulation**: Click "▶ Start" to begin automatic execution
2. **Pause/Resume**: Click "⏸ Pause" to pause, then "▶ Resume" to continue
3. **Step-by-Step**: Click "⏭ Step" to execute one step at a time
4. **Reset**: Click "🔄 Reset" to clear and start over

**Visual Elements:**
- **Memory Frames**: Shows current pages in memory with color-coded thread ownership
- **Thread Status**: Real-time state of each thread with emoji indicators
  - 🟢 Running
  - ⚪ Ready
  - 🟡 Waiting
  - 🔴 Blocked
  - ✅ Completed
- **Lock Status**: Shows which locks are available (🔓) or held (🔒)
- **Execution Log**: Detailed timeline of events (page hits, faults, context switches, lock operations)
- **Progress Bar**: Visual indication of simulation completion

### Tab 3: Statistics 📊

After simulation completes, view:
- **Global Statistics**: Total steps, context switches, page faults/hits, global hit ratio
- **Per-Thread Statistics**: Individual page faults, hits, hit ratio, context switches, waiting time
- **Lock Statistics**: Number of acquisitions per lock
- **Deadlock Information**: If deadlock detected, shows which threads are involved

---

## 🎯 Learning Objectives

### Students can learn:

1. **Multithreading Concepts**:
   - Thread states and transitions
   - Context switching overhead
   - Concurrent execution visualization

2. **Scheduling Algorithms**:
   - Compare FCFS, Round-Robin, and Priority scheduling
   - Understand time quantum impact in Round-Robin
   - Observe priority inversion and starvation

3. **Synchronization**:
   - Difference between Mutex and Semaphore
   - Race conditions without synchronization
   - Blocking and waiting behavior

4. **Deadlock**:
   - Conditions for deadlock (circular wait)
   - Visual detection using resource allocation graphs
   - Impact on system performance

5. **Memory Management**:
   - Page replacement algorithm comparison
   - Thrashing with too many threads
   - Frame allocation strategies

6. **Performance Analysis**:
   - Hit ratio calculation
   - Context switch overhead
   - Lock contention impact

---

## 🔧 Technical Details

### Synchronization Implementation
- Uses Java's `synchronized` keyword for thread-safe operations
- Lock waiting queue implemented with `LinkedList`
- Deadlock detection uses DFS-based cycle detection in resource allocation graph

### Scheduling Implementation
- FCFS: Simple FIFO queue
- Round-Robin: Queue with quantum counter
- Priority: Selection of highest priority thread from ready queue

### Page Replacement Implementation
- Shared memory frames across all threads
- Frame ownership tracked for visualization
- Each algorithm maintains its own data structures (FIFO queue for FIFO, LRU list, etc.)

### GUI Updates
- Uses `SwingUtilities.invokeLater()` for thread-safe UI updates
- Simulation runs on `javax.swing.Timer` to avoid blocking EDT
- Visual refresh rate: 500ms per step (adjustable in code)

---

## 📊 Example Scenarios

### Scenario 1: Basic Multithreading
```
Threads: 3
Scheduling: FCFS
Synchronization: None
Thread-1: 1 2 3 4 5 (Priority: 5)
Thread-2: 2 3 4 5 6 (Priority: 5)
Thread-3: 3 4 5 6 7 (Priority: 5)
Frames: 4
Algorithm: FIFO
```
**Learning**: Basic concurrent execution, page faults with multiple threads

### Scenario 2: Round-Robin with Different Priorities
```
Threads: 4
Scheduling: Round-Robin (Quantum: 3)
Synchronization: None
Thread-1: 1 2 3 4 5 6 7 (Priority: 10)
Thread-2: 2 3 4 5 6 7 8 (Priority: 8)
Thread-3: 3 4 5 6 7 8 9 (Priority: 5)
Thread-4: 4 5 6 7 8 9 10 (Priority: 2)
Frames: 5
Algorithm: LRU
```
**Learning**: Time quantum impact, context switching, priority doesn't matter in RR

### Scenario 3: Deadlock Demonstration
```
Threads: 3
Scheduling: FCFS
Synchronization: Mutex
Thread-1: 1 2 3 4 5 (Priority: 5)
Thread-2: 5 4 3 2 1 (Priority: 5)
Thread-3: 2 3 4 5 1 (Priority: 5)
Frames: 3
Algorithm: FIFO
```
**Learning**: Deadlock detection, circular wait, system deadlock

### Scenario 4: Priority Inversion
```
Threads: 3
Scheduling: Priority
Synchronization: Mutex
Thread-1: 1 2 3 4 5 (Priority: 10) [High priority]
Thread-2: 2 3 4 5 6 (Priority: 5) [Medium priority]
Thread-3: 3 4 5 6 7 (Priority: 1) [Low priority]
Frames: 4
Algorithm: LRU
```
**Learning**: High priority thread blocked by low priority thread holding lock

---

## 🎓 For Final Year Project Presentation

### Key Highlights to Present:

1. **Educational Value**: 
   - Interactive learning tool for complex OS concepts
   - Visual feedback for better understanding
   - Pre-configured scenarios for guided learning

2. **Technical Complexity**:
   - Real multithreading with Java concurrency
   - Three scheduling algorithms
   - Deadlock detection algorithm
   - Synchronization primitives
   - Four page replacement algorithms

3. **User Interface**:
   - Professional tabbed GUI
   - Real-time visualization
   - Detailed statistics and metrics
   - Step-by-step execution control

4. **Practical Applications**:
   - Operating Systems education
   - Concurrency training
   - Performance analysis learning
   - System design concepts

### Demo Flow Suggestion:

1. Start with "Race Condition Demo" scenario
2. Show execution without synchronization
3. Enable Mutex to show synchronization
4. Load "Deadlock Scenario" to demonstrate deadlock detection
5. Compare different scheduling algorithms
6. Show statistics and analysis capabilities

---


## 📖 References & Concepts

### Key Concepts Demonstrated:
- **Demand Paging**: Pages loaded into memory only when accessed
- **Page Fault**: Occurs when referenced page is not in memory
- **Page Replacement**: Algorithm to decide which page to evict
- **Context Switch**: Saving/restoring thread state when switching
- **Deadlock**: Circular wait where threads block each other
- **Priority Inversion**: High priority thread blocked by low priority thread
- **Starvation**: Thread never gets CPU time due to scheduling
- **Thrashing**: Excessive page faults due to insufficient memory

### Algorithms Implemented:
- FIFO, LRU, MRU, OPT page replacement
- FCFS, Round-Robin, Priority scheduling
- Deadlock detection (Resource Allocation Graph cycle detection)

---

## 👨‍💻 Author

Final Year Project - Information Technology

## 📝 License

Educational Use

---

## 🎉 Enjoy Learning Multithreading and Concurrency!

This simulator provides a hands-on, visual approach to understanding complex operating system concepts. Experiment with different configurations, observe the results, and deepen your understanding of concurrent systems!
