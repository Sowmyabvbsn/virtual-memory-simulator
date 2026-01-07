# 🎓 Multithreaded Virtual Memory Simulator with Concurrency

## Final Year Project - Interactive Educational Tool

### 📖 Project Overview

This project is an **advanced educational simulator** for demonstrating multithreading, concurrency, synchronization, and virtual memory concepts (demand paging and page replacement algorithms). It provides an interactive Java Swing-based GUI where students can configure multiple threads, apply different scheduling algorithms, enable synchronization mechanisms, and observe real-time execution with visual feedback.

---

## ✨ Key Features

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
├── MultiThreadGUI.java          # Main GUI with tabbed interface
├── MultiThreadSimulator.java    # Core simulation engine
├── ProcessThread.java           # Thread representation
├── ThreadScheduler.java         # Scheduling algorithms (FCFS, RR, Priority)
├── LockResource.java            # Mutex and Semaphore implementation
├── DeadlockDetector.java        # Deadlock detection algorithm
├── DemandPaging.java            # Original single-thread simulation logic
├── DemandPagingGUI.java         # Original single-thread GUI (kept for reference)
├── BarChartFrame.java           # Algorithm comparison chart
└── README.md                    # This file
```

---

## 🚀 How to Run

### Prerequisites
- Java Development Kit (JDK) 11 or higher
- Terminal/Command Prompt

### Compilation
```bash
cd /app
javac *.java
```

### Execution
```bash
java MultiThreadGUI
```

### Quick Start Script
A convenience script is provided:
```bash
./run.sh
```

---

## 📚 Usage Guide

### Tab 1: Configuration ⚙️

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

## 🔍 Troubleshooting

### Common Issues:

**Issue**: "Exception in thread 'main' java.lang.UnsupportedClassVersionError"
- **Solution**: Upgrade to JDK 11 or higher

**Issue**: GUI doesn't appear
- **Solution**: Ensure DISPLAY variable is set (for Linux) or use Windows/Mac

**Issue**: Simulation runs too fast/slow
- **Solution**: Adjust timer delay in `MultiThreadGUI.java` line with `new javax.swing.Timer(500, ...)` - change 500 to desired milliseconds

**Issue**: Deadlock not detected
- **Solution**: Some configurations may not produce deadlock. Try the pre-configured "Deadlock Scenario"

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

Final Year Project - Computer Science

## 📝 License

Educational Use

---

## 🎉 Enjoy Learning Multithreading and Concurrency!

This simulator provides a hands-on, visual approach to understanding complex operating system concepts. Experiment with different configurations, observe the results, and deepen your understanding of concurrent systems!
