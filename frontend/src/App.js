import React, { useState } from "react";
import MemorySimulator from "./components/MemorySimulator";
import ConcurrencySimulator from "./components/ConcurrencySimulator";

function App() {
  const [activeTab, setActiveTab] = useState("memory");

  return (
    <div
      className="App"
      style={{
        background: "linear-gradient(to right, #c1e8ffff, #eec5f3ff)",
        minHeight: "100vh",
        paddingBottom: "30px"
      }}>
      <h1 style={{color: "#d021f3ff", textAlign: "center", paddingTop: "20px"}}>
        Operating Systems Simulator
      </h1>

      <div style={{textAlign: "center", marginBottom: "30px"}}>
        <button
          onClick={() => setActiveTab("memory")}
          style={{
            padding: "12px 30px",
            margin: "0 10px",
            backgroundColor: activeTab === "memory" ? "#d021f3ff" : "#fff",
            color: activeTab === "memory" ? "#fff" : "#d021f3ff",
            border: "2px solid #d021f3ff",
            borderRadius: "8px",
            cursor: "pointer",
            fontSize: "16px",
            fontWeight: "bold",
            transition: "all 0.3s ease"
          }}
        >
          Virtual Memory
        </button>
        <button
          onClick={() => setActiveTab("concurrency")}
          style={{
            padding: "12px 30px",
            margin: "0 10px",
            backgroundColor: activeTab === "concurrency" ? "#d021f3ff" : "#fff",
            color: activeTab === "concurrency" ? "#fff" : "#d021f3ff",
            border: "2px solid #d021f3ff",
            borderRadius: "8px",
            cursor: "pointer",
            fontSize: "16px",
            fontWeight: "bold",
            transition: "all 0.3s ease"
          }}
        >
          Multithreading & Concurrency
        </button>
      </div>

      <div>
        {activeTab === "memory" && <MemorySimulator />}
        {activeTab === "concurrency" && <ConcurrencySimulator />}
      </div>
    </div>
  );
}

export default App;
