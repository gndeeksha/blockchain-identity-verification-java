import { useState, useEffect } from "react";
import API from "../api/apiService";
import StatusBadge from "../components/StatusBadge";

export default function Dashboard() {
  const [data, setData] = useState("");
  const [myRecords, setMyRecords] = useState([]);

  const fetchMyRecords = async () => {
    try {
      const res = await API.get("/identity/my");
      setMyRecords(res.data);
    } catch (err) {
      console.error(err);
    }
  };

  useEffect(() => {
    fetchMyRecords();
  }, []);

  const handleSubmit = async () => {
    if (!data) return alert("Enter data");
    await API.post("/identity/submit", { data });
    alert("Submitted");
    setData("");
    fetchMyRecords();
  };

  return (
    <div className="container">
      <h2>User Dashboard</h2>
      <div className="record-card">
        <h3>Submit Identity</h3>
        <input
          placeholder="Enter Data"
          value={data}
          onChange={(e) => setData(e.target.value)}
        />
        <button onClick={handleSubmit}>Submit to Ledger</button>
      </div>

      <h3 style={{ marginTop: "40px" }}>My Records</h3>
      {myRecords.map((r) => (
        <div key={r.id} className="record-card">
          <div className="card-header">
            <strong>RECORD ID: {r.id}</strong>
            <StatusBadge status={r.status} />
          </div>
          <p>
            <strong>Identity Data:</strong> {r.data}
          </p>
        </div>
      ))}
    </div>
  );
}
