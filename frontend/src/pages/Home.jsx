import { useNavigate } from "react-router-dom";

export default function Home() {
  const navigate = useNavigate();

  return (
    <div
      className="container"
      style={{ textAlign: "center", marginTop: "40px" }}
    >
      <div className="verify-box" style={{ padding: "50px" }}>
        <div style={{ fontSize: "70px", marginBottom: "20px" }}>🛡️</div>
        <h1 style={{ fontSize: "3.5rem", color: "#1a1a1a", margin: "0" }}>
          BlockID
        </h1>
        <p
          style={{
            fontSize: "1.2rem",
            color: "#666",
            margin: "20px auto 40px",
            maxWidth: "600px",
          }}
        >
          Secure, decentralized identity verification using Java Blockchain
          technology. Tamper-proof and transparent.
        </p>

        <div style={{ display: "flex", justifyContent: "center", gap: "20px" }}>
          <button
            onClick={() => navigate("/login")}
            style={{ padding: "15px 40px" }}
          >
            Get Started
          </button>
          <button
            onClick={() => navigate("/verify")}
            style={{
              padding: "15px 40px",
              backgroundColor: "white",
              color: "#fb8c00",
              border: "2px solid #fb8c00",
            }}
          >
            Verify Identity
          </button>
        </div>
      </div>

      <div
        style={{
          display: "grid",
          gridTemplateColumns: "repeat(auto-fit, minmax(250px, 1fr))",
          gap: "20px",
          marginTop: "40px",
        }}
      >
        <div className="record-card">
          <h3>🔒 Encrypted</h3>
          <p>Sensitive data is secured with AES-256 encryption.</p>
        </div>
        <div className="record-card">
          <h3>⛓️ Immutable</h3>
          <p>
            Each identity is cryptographically chained to the previous block.
          </p>
        </div>
        <div className="record-card">
          <h3>✅ Verifiable</h3>
          <p>Instantly confirm authenticity without exposing private data.</p>
        </div>
      </div>
    </div>
  );
}
