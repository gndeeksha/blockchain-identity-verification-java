import { useEffect, useState } from "react";
import API from "../api/apiService";

export default function Admin() {
  const [records, setRecords] = useState([]);
  const [logs, setLogs] = useState([]);

  const fetchData = async () => {
    try {
      const resRecs = await API.get("/identity/all");
      const resLogs = await API.get("/identity/logs");
      setRecords(resRecs.data);
      setLogs(resLogs.data);
    } catch (err) {
      console.error("Error fetching data");
    }
  };

  useEffect(() => {
    fetchData();
  }, []);

  const approve = async (id) => {
    try {
      await API.post(`/identity/approve/${id}`);
      fetchData();
    } catch (err) {
      alert("Approve failed");
    }
  };

  return (
    <div className="container">
      <h2>Admin Center</h2>

      <div style={{ marginBottom: "50px" }}>
        <h3>⏳ Pending Approvals</h3>
        {records
          .filter((r) => r.status === "PENDING")
          .map((r) => (
            <div key={r.id} className="record-card">
              <div
                style={{
                  display: "flex",
                  justifyContent: "space-between",
                  alignItems: "center",
                }}
              >
                <div>
                  <p>
                    <strong>ID:</strong> {r.id} | <strong>User:</strong>{" "}
                    {r.username}
                  </p>
                  <p>
                    <strong>Data:</strong> {r.data}
                  </p>
                </div>
                <button onClick={() => approve(r.id)}>Approve</button>
              </div>
            </div>
          ))}
        {records.filter((r) => r.status === "PENDING").length === 0 && (
          <p>No pending approvals.</p>
        )}
      </div>

      <div style={{ marginBottom: "50px" }}>
        <h3>✅ Approved History</h3>
        <div className="table-container">
          <table className="audit-table">
            <thead>
              <tr>
                <th>ID</th>
                <th>User</th>
                <th>Data</th>
                <th style={{ textAlign: "right" }}>Status</th>
              </tr>
            </thead>
            <tbody>
              {records
                .filter((r) => r.status === "APPROVED")
                .map((r) => (
                  <tr key={r.id}>
                    <td>{r.id}</td>
                    <td>{r.username}</td>
                    <td>{r.data}</td>
                    <td style={{ textAlign: "right" }}>
                      <span className="status-approved">Approved</span>
                    </td>
                  </tr>
                ))}
            </tbody>
          </table>
        </div>
      </div>

      <div style={{ marginBottom: "50px" }}>
        <h3>📜 Audit Trail </h3>
        <div className="table-container">
          <table className="audit-table">
            <thead>
              <tr>
                <th>Record ID</th>
                <th>Result</th>
                <th style={{ textAlign: "right" }}>Time</th>
              </tr>
            </thead>
            <tbody>
              {logs.map((l) => (
                <tr key={l.id}>
                  <td>{l.recordId}</td>
                  <td>
                    <span
                      className={
                        l.result === "VALID"
                          ? "status-approved"
                          : "status-pending"
                      }
                    >
                      {l.result}
                    </span>
                  </td>
                  <td style={{ textAlign: "right" }}>
                    {new Date(l.timestamp).toLocaleString()}
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
          {logs.length === 0 && (
            <p style={{ padding: "20px" }}>No audit logs found.</p>
          )}
        </div>
      </div>
    </div>
  );
}
