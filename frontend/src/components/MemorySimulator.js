import React, { useState, useEffect } from "react";

export default function MemorySimulator() {
  const [frames, setFrames] = useState([]);
  const [hits, setHits] = useState(0);
  const [faults, setFaults] = useState(0);
  const [input, setInput] = useState("");
  const [ramSize, setRamSize] = useState(3);
  const [maxPages, setMaxPages] = useState(10);
  const [errorMsg, setErrorMsg] = useState("");
  const [speed, setSpeed] = useState(800);
  const [lastAction, setLastAction] = useState(null);
  const [diskHighlight, setDiskHighlight] = useState(null);
  const [hitRate, setHitRate] = useState(0);
  const [algorithm, setAlgorithm] = useState("FIFO");
  const [isSimulating, setIsSimulating] = useState(false);

  // Initialize RAM frames when ramSize changes
  useEffect(() => {
    setFrames(Array(ramSize).fill({ page: -1, status: "idle" }));
  }, [ramSize]);

  const diskPages = Array.from({ length: maxPages }, (_, i) => i);
  const formatted = 
        Number.isInteger(hitRate)? hitRate : hitRate.toFixed(3);

  const animateSimulation = async (steps) => {
    let hitsCount = 0;
    let faultsCount = 0;

    for (let step of steps) {
      setDiskHighlight(step.pageId);
      await new Promise(r => setTimeout(r, speed / 2));

      if (step.isHit) {
        hitsCount++;
        setLastAction("hit");
      } else {
        faultsCount++;
        setLastAction("fault");
      }

      const frameData = step.frames[0].map((pageId, idx) => {
        const isCurrentPage = pageId === step.pageId;
        return {
          page: pageId,
          status: isCurrentPage ? (step.isHit ? "hit" : "fault") : "idle"
        };
      });

      setFrames(frameData);
      setHits(hitsCount);
      setFaults(faultsCount);
      setHitRate((hitsCount * 100) / (hitsCount + faultsCount));

      await new Promise(r => setTimeout(r, speed));

      setFrames(frameData.map(f => ({ ...f, status: "idle" })));
      setLastAction(null);
      setDiskHighlight(null);
    }
  };

  const handleSimulate = async () => {
    if (isSimulating) return;

    setErrorMsg("");

    const sequence = input
      .split(",")
      .map(n => parseInt(n.trim()))
      .filter(n => !isNaN(n));

    if (sequence.length === 0) {
      setErrorMsg("Enter a page sequence, e.g.: 0,1,2,0,3,0,4,2");
      return;
    }

    const invalidPages = sequence.filter(n => n < 0 || n >= maxPages);
    if (invalidPages.length > 0) {
      setErrorMsg(`Pages must be between 0 and ${maxPages - 1}. Invalid: ${invalidPages.join(", ")}`);
      return;
    }

    setIsSimulating(true);
    setHits(0);
    setFaults(0);
    setHitRate(0);
    setFrames(Array(ramSize).fill({ page: -1, status: "idle" }));

    try {
      const response = await fetch("http://localhost:8079/simulate", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({
          sequence,
          ramSize,
          maxPages,
          algorithm
        })
      });

      if (!response.ok) {
        throw new Error("Simulation failed");
      }

      const result = await response.json();
      await animateSimulation(result.steps);
    } catch (error) {
      setErrorMsg("Failed to connect to backend. Make sure the server is running on port 8080.");
    } finally {
      setIsSimulating(false);
    }
  };

  return (
    <div style={{ padding: "20px", textAlign: "center" }}>
      <h2>Memory Simulator</h2>

      {errorMsg && (
        <div style={{ color: "white", backgroundColor: "#f44336", padding: "10px", borderRadius: "5px", marginBottom: "15px" }}>
          {errorMsg}
        </div>
      )}

      <div style={{ marginBottom: "15px" }}>
        <label>
          RAM frames: 
          <input type="number" min="1" max="10" value={ramSize} onChange={e => setRamSize(parseInt(e.target.value))} style={{ marginLeft: "10px", width: "60px" }}/>
        </label>
      </div>

      <div style={{ marginBottom: "15px" }}>
        <label>
          Max pages (disk):
          <input type="number" min="1" max="100" value={maxPages} onChange={e => setMaxPages(parseInt(e.target.value))} style={{ marginLeft: "10px", width: "60px" }}/>
        </label>
      </div>

      <div style={{ marginBottom: "15px" }}>
        <label>
          Page Replacement Algorithm:
          <select value={algorithm} onChange={e => setAlgorithm(e.target.value)} style={{ marginLeft: "10px", padding: "5px", borderRadius: "3px" }}>
            <option value="FIFO">FIFO (First In First Out)</option>
            <option value="LRU">LRU (Least Recently Used)</option>
            <option value="MRU">MRU (Most Recently Used)</option>
            <option value="OPTIMAL">Optimal</option>
          </select>
        </label>
      </div>

      <div style={{ marginBottom: "15px" }}>
        <label>
          Sequence of pages:
        </label>
      </div>
      <div style={{ marginBottom: "15px" }}>
        <input type="text" placeholder="e.g.: 0,1,2,0,3,0,4,2" value={input} onChange={e => setInput(e.target.value)} style={{ width: "300px", padding: "5px" }} />
      </div>

      <div style={{ marginBottom: "15px" }}>
        <button
          onClick={handleSimulate}
          disabled={isSimulating}
          style={{
            padding: "10px 20px",
            backgroundColor: isSimulating ? "#ccc" : "#d021f3ff",
            color: "white",
            border: "none",
            borderRadius: "5px",
            cursor: isSimulating ? "not-allowed" : "pointer",
            fontSize: "16px",
            fontWeight: "bold"
          }}>
          {isSimulating ? "Simulating..." : "Simulate"}
        </button>
      </div>

      <div style={{ marginBottom: "20px" }}>
        <label>
          Animation speed (ms per step):
          <input type="number" min="100" max="5000" value={speed} onChange={e => setSpeed(parseInt(e.target.value))} style={{ marginLeft: "10px", width: "60px" }} />
        </label>
      </div>

      <h3>Disk (Virtual Memory):</h3>
      <div style={{ display: "flex", justifyContent: "center", gap: "5px", flexWrap: "wrap", marginBottom: "20px" }}>
        {diskPages.map(p => (
          <div key={p} style={{
            width: "40px",
            height: "40px",
            border: "2px solid black",
            display: "flex",
            justifyContent: "center",
            alignItems: "center",
            backgroundColor: diskHighlight === p ? "#ffeb3b" : "#eee",
            transition: "background-color 0.3s ease"
          }}>
            {p}
          </div>
        ))}
      </div>

      <h3>RAM Frames:</h3>
      <div style={{ display: "flex", justifyContent: "center", gap: "10px" }}>
        {frames.map((f, i) => (
          <div key={i} style={{
            width: "60px",
            height: "60px",
            border: "2px solid black",
            display: "flex",
            justifyContent: "center",
            alignItems: "center",
            fontSize: "20px",
            backgroundColor: f.status === "idle" ? "#eee" : f.status === "hit" ? "#a5d6a7" : "#f44336",
            transition: "background-color 0.5s ease, transform 0.3s ease",
            transform: f.status !== "idle" ? "scale(1.1)" : "scale(1)"
          }}>
            {f.page !== -1 ? f.page : "-"}
          </div>
        ))}
      </div>

      <div style={{ marginTop: "20px", fontSize: "18px" }}>
        <p style={{ fontWeight: "bold", color: "#d021f3ff" }}>
          Algorithm: {algorithm}
        </p>
        <p style={{ fontWeight: lastAction === "hit" ? "bold" : "normal", color: lastAction === "hit" ? "green" : "black" }}>
          Hits: {hits}
        </p>
        <p style={{ fontWeight: lastAction === "fault" ? "bold" : "normal", color: lastAction === "fault" ? "red" : "black" }}>
          Faults: {faults}
        </p>
        <p>
          Hit rate: {formatted} %
        </p>
      </div>
    </div>
  );
}

