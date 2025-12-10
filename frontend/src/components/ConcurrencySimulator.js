import React, { useState } from "react";

export default function ConcurrencySimulator() {
  const [selectedSimulation, setSelectedSimulation] = useState("deadlock");
  const [loading, setLoading] = useState(false);
  const [simulationResult, setSimulationResult] = useState(null);
  const [aiRecommendation, setAiRecommendation] = useState("");

  const [numThreads, setNumThreads] = useState(5);
  const [producers, setProducers] = useState(2);
  const [consumers, setConsumers] = useState(2);
  const [bufferSize, setBufferSize] = useState(5);
  const [poolSize, setPoolSize] = useState(4);
  const [taskCount, setTaskCount] = useState(10);

  const API_URL = "http://localhost:8080/api/concurrency";

  const runSimulation = async () => {
    setLoading(true);
    setSimulationResult(null);
    setAiRecommendation("");

    try {
      let endpoint = "";
      let body = {};

      switch (selectedSimulation) {
        case "deadlock":
          endpoint = `${API_URL}/simulate/deadlock`;
          break;
        case "race-condition":
          endpoint = `${API_URL}/simulate/race-condition`;
          body = { numThreads };
          break;
        case "producer-consumer":
          endpoint = `${API_URL}/simulate/producer-consumer`;
          body = { producers, consumers, bufferSize };
          break;
        case "thread-pool":
          endpoint = `${API_URL}/simulate/thread-pool`;
          body = { poolSize, taskCount };
          break;
        default:
          break;
      }

      const response = await fetch(endpoint, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(body),
      });

      const data = await response.json();
      setSimulationResult(data);
      setAiRecommendation(data.aiRecommendation || "");
    } catch (error) {
      console.error("Error running simulation:", error);
      alert("Failed to run simulation. Make sure the backend server is running.");
    } finally {
      setLoading(false);
    }
  };

  const getThreadStateColor = (state) => {
    switch (state) {
      case "NEW":
        return "#e0e0e0";
      case "RUNNABLE":
        return "#90caf9";
      case "RUNNING":
        return "#66bb6a";
      case "BLOCKED":
        return "#f44336";
      case "WAITING":
        return "#ffb74d";
      case "TIMED_WAITING":
        return "#ffa726";
      case "TERMINATED":
        return "#757575";
      default:
        return "#e0e0e0";
    }
  };

  return (
    <div style={{ padding: "20px", maxWidth: "1200px", margin: "0 auto" }}>
      <h2>Multithreading & Concurrency Simulator</h2>

      <div style={{ marginBottom: "20px" }}>
        <label style={{ marginRight: "15px", fontWeight: "bold" }}>
          Select Simulation Type:
        </label>
        <select
          value={selectedSimulation}
          onChange={(e) => setSelectedSimulation(e.target.value)}
          style={{ padding: "8px", fontSize: "16px", borderRadius: "5px" }}
        >
          <option value="deadlock">Deadlock Detection</option>
          <option value="race-condition">Race Condition</option>
          <option value="producer-consumer">Producer-Consumer</option>
          <option value="thread-pool">Thread Pool</option>
        </select>
      </div>

      {selectedSimulation === "race-condition" && (
        <div style={{ marginBottom: "15px" }}>
          <label>
            Number of Threads:
            <input
              type="number"
              min="2"
              max="20"
              value={numThreads}
              onChange={(e) => setNumThreads(parseInt(e.target.value))}
              style={{ marginLeft: "10px", padding: "5px", width: "60px" }}
            />
          </label>
        </div>
      )}

      {selectedSimulation === "producer-consumer" && (
        <div style={{ marginBottom: "15px" }}>
          <label style={{ marginRight: "15px" }}>
            Producers:
            <input
              type="number"
              min="1"
              max="10"
              value={producers}
              onChange={(e) => setProducers(parseInt(e.target.value))}
              style={{ marginLeft: "10px", padding: "5px", width: "60px" }}
            />
          </label>
          <label style={{ marginRight: "15px" }}>
            Consumers:
            <input
              type="number"
              min="1"
              max="10"
              value={consumers}
              onChange={(e) => setConsumers(parseInt(e.target.value))}
              style={{ marginLeft: "10px", padding: "5px", width: "60px" }}
            />
          </label>
          <label>
            Buffer Size:
            <input
              type="number"
              min="1"
              max="20"
              value={bufferSize}
              onChange={(e) => setBufferSize(parseInt(e.target.value))}
              style={{ marginLeft: "10px", padding: "5px", width: "60px" }}
            />
          </label>
        </div>
      )}

      {selectedSimulation === "thread-pool" && (
        <div style={{ marginBottom: "15px" }}>
          <label style={{ marginRight: "15px" }}>
            Pool Size:
            <input
              type="number"
              min="1"
              max="10"
              value={poolSize}
              onChange={(e) => setPoolSize(parseInt(e.target.value))}
              style={{ marginLeft: "10px", padding: "5px", width: "60px" }}
            />
          </label>
          <label>
            Task Count:
            <input
              type="number"
              min="1"
              max="50"
              value={taskCount}
              onChange={(e) => setTaskCount(parseInt(e.target.value))}
              style={{ marginLeft: "10px", padding: "5px", width: "60px" }}
            />
          </label>
        </div>
      )}

      <button
        onClick={runSimulation}
        disabled={loading}
        style={{
          padding: "10px 20px",
          backgroundColor: loading ? "#ccc" : "#d021f3ff",
          color: "white",
          border: "none",
          borderRadius: "5px",
          cursor: loading ? "not-allowed" : "pointer",
          fontSize: "16px",
        }}
      >
        {loading ? "Running..." : "Run Simulation"}
      </button>

      {simulationResult && (
        <div style={{ marginTop: "30px" }}>
          <div
            style={{
              backgroundColor: simulationResult.deadlockDetected ? "#ffebee" : "#e8f5e9",
              padding: "15px",
              borderRadius: "5px",
              marginBottom: "20px",
            }}
          >
            <h3>
              {simulationResult.deadlockDetected ? "⚠️ Deadlock Detected!" : "✅ No Deadlock"}
            </h3>
            <p>Execution Time: {simulationResult.executionTime} ms</p>
          </div>

          <h3>Thread States:</h3>
          <div
            style={{
              display: "grid",
              gridTemplateColumns: "repeat(auto-fill, minmax(200px, 1fr))",
              gap: "15px",
              marginBottom: "20px",
            }}
          >
            {simulationResult.threads &&
              simulationResult.threads.map((thread) => (
                <div
                  key={thread.id}
                  style={{
                    border: "2px solid #333",
                    borderRadius: "8px",
                    padding: "15px",
                    backgroundColor: getThreadStateColor(thread.state),
                    transition: "all 0.3s ease",
                  }}
                >
                  <h4 style={{ margin: "0 0 10px 0" }}>{thread.name}</h4>
                  <p style={{ margin: "5px 0" }}>State: <strong>{thread.state}</strong></p>
                  <p style={{ margin: "5px 0" }}>Priority: {thread.priority}</p>
                  <p style={{ margin: "5px 0" }}>Held Resources: {thread.heldResources}</p>
                  <p style={{ margin: "5px 0" }}>Waiting For: {thread.waitingForResources}</p>
                </div>
              ))}
          </div>

          <h3>Resources:</h3>
          <div
            style={{
              display: "grid",
              gridTemplateColumns: "repeat(auto-fill, minmax(200px, 1fr))",
              gap: "15px",
              marginBottom: "20px",
            }}
          >
            {simulationResult.resources &&
              simulationResult.resources.map((resource) => (
                <div
                  key={resource.id}
                  style={{
                    border: "2px solid #333",
                    borderRadius: "8px",
                    padding: "15px",
                    backgroundColor: resource.locked ? "#ffcdd2" : "#c8e6c9",
                  }}
                >
                  <h4 style={{ margin: "0 0 10px 0" }}>{resource.name}</h4>
                  <p style={{ margin: "5px 0" }}>
                    Status: <strong>{resource.locked ? "Locked" : "Free"}</strong>
                  </p>
                  {resource.holder && (
                    <p style={{ margin: "5px 0" }}>Held by: {resource.holder}</p>
                  )}
                </div>
              ))}
          </div>

          <h3>Execution Log:</h3>
          <div
            style={{
              backgroundColor: "#f5f5f5",
              padding: "15px",
              borderRadius: "5px",
              maxHeight: "300px",
              overflowY: "auto",
              fontFamily: "monospace",
              fontSize: "14px",
            }}
          >
            {simulationResult.executionLog &&
              simulationResult.executionLog.map((log, index) => (
                <div key={index} style={{ marginBottom: "5px" }}>
                  {log}
                </div>
              ))}
          </div>
        </div>
      )}

      {aiRecommendation && (
        <div
          style={{
            marginTop: "30px",
            backgroundColor: "#fff3e0",
            padding: "20px",
            borderRadius: "8px",
            border: "2px solid #ff9800",
          }}
        >
          <h3 style={{ marginTop: 0 }}>🤖 AI Recommendations:</h3>
          <pre
            style={{
              whiteSpace: "pre-wrap",
              fontFamily: "monospace",
              fontSize: "14px",
              lineHeight: "1.6",
            }}
          >
            {aiRecommendation}
          </pre>
        </div>
      )}

      <div
        style={{
          marginTop: "30px",
          padding: "15px",
          backgroundColor: "#e3f2fd",
          borderRadius: "5px",
        }}
      >
        <h3>Legend - Thread States:</h3>
        <div style={{ display: "flex", flexWrap: "wrap", gap: "15px" }}>
          {[
            { state: "NEW", color: "#e0e0e0" },
            { state: "RUNNABLE", color: "#90caf9" },
            { state: "RUNNING", color: "#66bb6a" },
            { state: "BLOCKED", color: "#f44336" },
            { state: "WAITING", color: "#ffb74d" },
            { state: "TIMED_WAITING", color: "#ffa726" },
            { state: "TERMINATED", color: "#757575" },
          ].map((item) => (
            <div key={item.state} style={{ display: "flex", alignItems: "center" }}>
              <div
                style={{
                  width: "20px",
                  height: "20px",
                  backgroundColor: item.color,
                  marginRight: "8px",
                  border: "1px solid #333",
                }}
              ></div>
              <span>{item.state}</span>
            </div>
          ))}
        </div>
      </div>
    </div>
  );
}
