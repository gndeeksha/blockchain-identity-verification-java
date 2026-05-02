import { useEffect, useState } from "react";
import API from "../api/apiService";
import BlockCard from "../components/BlockCard";

export default function Explorer() {
  const [blocks, setBlocks] = useState([]);

  useEffect(() => {
    API.get("/identity/all").then((res) => setBlocks(res.data));
  }, []);

  return (
    <div className="container" style={{ textAlign: "center" }}>
      <h2>Blockchain Ledger</h2>
      <div className="explorer-container">
        {blocks.map((b, i) => (
          <div
            key={b.id}
            style={{
              display: "flex",
              flexDirection: "column",
              alignItems: "center",
            }}
          >
            <BlockCard block={b} />
            {i < blocks.length - 1 && <div className="chain-link">↓</div>}
          </div>
        ))}
      </div>
    </div>
  );
}
