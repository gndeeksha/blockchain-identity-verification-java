import { useState, useEffect, useContext } from "react";
import { useNavigate } from "react-router-dom";
import API from "../api/apiService";
import { AuthContext } from "../context/AuthContext";

export default function Login() {
  const { login } = useContext(AuthContext);
  const navigate = useNavigate();
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");

  // Fresh Entry: Clear fields when page loads
  useEffect(() => {
    setUsername("");
    setPassword("");
  }, []);

  const handleLogin = async () => {
    try {
      const res = await API.post("/auth/login", { username, password });
      const token = res.data.token || res.data;
      login(token);

      const payload = JSON.parse(atob(token.split(".")[1]));
      if (payload.role === "ADMIN") {
        navigate("/admin");
      } else {
        navigate("/dashboard");
      }
    } catch (err) {
      alert("Login failed. Please check your credentials.");
    }
  };

  return (
    <div
      className="container"
      style={{
        display: "flex",
        justifyContent: "center",
        alignItems: "center",
        minHeight: "70vh",
      }}
    >
      <div className="verify-box" style={{ width: "100%", maxWidth: "400px" }}>
        <h2 style={{ textAlign: "center" }}>Sign In</h2>
        <p style={{ textAlign: "center", fontSize: "14px", color: "#666" }}>
          Access the Blockchain Identity Ledger
        </p>

        <form autoComplete="off" style={{ marginTop: "20px" }}>
          <label style={{ fontWeight: "bold", color: "#1a1a1a" }}>
            Username
          </label>
          <input
            type="text"
            value={username}
            autoComplete="new-password"
            onChange={(e) => setUsername(e.target.value)}
          />

          <label style={{ fontWeight: "bold", color: "#1a1a1a" }}>
            Password
          </label>
          <input
            type="password"
            value={password}
            autoComplete="new-password"
            onChange={(e) => setPassword(e.target.value)}
          />

          <button
            type="button"
            style={{ width: "100%", marginTop: "10px" }}
            onClick={handleLogin}
          >
            Login
          </button>
        </form>

        <p
          onClick={() => navigate("/register")}
          style={{
            cursor: "pointer",
            color: "#fb8c00",
            textAlign: "center",
            marginTop: "20px",
            fontWeight: "bold",
          }}
        >
          Don't have an account? Register
        </p>
      </div>
    </div>
  );
}
