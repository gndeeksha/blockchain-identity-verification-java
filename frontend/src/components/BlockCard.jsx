import StatusBadge from "./StatusBadge";

export default function BlockCard({ block }) {
  return (
    <div className="block">
      <div className="card-header">
        <strong style={{ fontSize: "18px" }}>BLOCK ID: {block.id}</strong>
        <StatusBadge status={block.status} />
      </div>

      <div className="hash-container">
        <span className="hash-label">Previous Block Hash</span>
        <div className="hash-value">{block.previousHash}</div>
      </div>

      <div className="hash-container">
        <span className="hash-label">Current Data Fingerprint (Hash)</span>
        <div className="hash-value">{block.hash}</div>
      </div>

      <div
        className="hash-container"
        style={{ backgroundColor: "#e8f5e9", borderColor: "#c8e6c9" }}
      >
        <span className="hash-label" style={{ color: "#2e7d32" }}>
          Digital Signature (Authority Seal)
        </span>
        <div className="hash-value">
          {block.signature || "NOT SIGNED BY ADMIN"}
        </div>
      </div>

      <p
        style={{
          fontSize: "12px",
          color: "#888",
          textAlign: "right",
          marginTop: "10px",
        }}
      >
        Timestamp: {new Date().toLocaleString()}
      </p>
    </div>
  );
}
