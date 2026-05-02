import { useState } from "react";
import { useNavigate } from "react-router-dom";
import API from "../api/apiService";

export default function Register() {
  const navigate = useNavigate();
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");

  const handleRegister = async () => {
    try {
      await API.post("/auth/register", { username, password });
      alert("Registered successfully!");
      navigate("/login");
    } catch (err) {
      alert("Registration failed");
    }
  };

  return (
    <div className="container">
      <div className="verify-box">
        <h2>Create Account</h2>
        <p>Enter your details to join the network.</p>

        <label>Username</label>
        <input
          placeholder="Enter username"
          onChange={(e) => setUsername(e.target.value)}
        />

        <label>Password</label>
        <input
          type="password"
          placeholder="Enter password"
          onChange={(e) => setPassword(e.target.value)}
        />

        <button onClick={handleRegister} style={{ width: "100%" }}>
          Register
        </button>

        <p
          onClick={() => navigate("/login")}
          style={{
            cursor: "pointer",
            color: "#2563eb",
            textAlign: "center",
            marginTop: "20px",
          }}
        >
          Already have an account? Login
        </p>
      </div>
    </div>
  );
}
