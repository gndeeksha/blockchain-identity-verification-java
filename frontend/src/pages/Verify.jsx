import { useState } from "react";
import API from "../api/apiService";

export default function Verify() {
  const [id, setId] = useState("");
  const [data, setData] = useState("");
  const [result, setResult] = useState(null);

  const handleVerify = async () => {
    setResult(null);
    if (!id || !data) return alert("Please enter ID and Data");
    try {
      const res = await API.post("/identity/verify", { id, data });
      setResult(res.data);
    } catch (err) {
      setResult("RECORD NOT FOUND ❌");
    }
  };

  return (
    <div className="container">
      <div className="verify-box">
        <h2>Identity Verification</h2>
        <p>Verify data authenticity against the blockchain ledger.</p>

        <label style={{ fontWeight: "bold" }}>Record ID</label>
        <input
          type="number"
          placeholder="e.g. 1"
          value={id}
          onChange={(e) => setId(e.target.value)}
        />

        <label style={{ fontWeight: "bold" }}>Identity Data</label>
        <textarea
          placeholder="Enter the Data to verify"
          value={data}
          onChange={(e) => setData(e.target.value)}
          rows="4"
        />

        <button onClick={handleVerify} style={{ width: "100%" }}>
          Verify Authenticity
        </button>

        {result && (
          <div
            className={
              result.includes("VALID") ? "result-valid" : "result-invalid"
            }
          >
            <h3
              style={{
                margin: 0,
                color: result.includes("VALID") ? "white" : "inherit",
              }}
            >
              {result}
            </h3>
          </div>
        )}
      </div>
    </div>
  );
}
