import { useNavigate, Link } from "react-router-dom";

export default function Navbar() {
  const navigate = useNavigate();
  const token = localStorage.getItem("token");

  // Decode role from token to show/hide Admin link
  let role = "";
  if (token) {
    try {
      const payload = JSON.parse(atob(token.split(".")[1]));
      role = payload.role;
    } catch (e) {
      console.error("Token error");
    }
  }

  const handleLogout = () => {
    localStorage.removeItem("token");
    navigate("/"); // Redirect to Home on logout
    window.location.reload();
  };

  return (
    <nav style={navStyle}>
      {/* ✅ Logo now links to Home (/) */}
      <Link
        to="/"
        style={{
          fontWeight: "bold",
          fontSize: "20px",
          textDecoration: "none",
          color: "white",
        }}
      >
        🛡️ BlockID
      </Link>

      <div style={{ display: "flex", gap: "15px", alignItems: "center" }}>
        <Link to="/verify" style={linkStyle}>
          Verify
        </Link>
        <Link to="/explorer" style={linkStyle}>
          Explorer
        </Link>

        {!token ? (
          <>
            {/* ✅ Login now points to /login */}
            <Link to="/login" style={linkStyle}>
              Login
            </Link>
            <Link to="/register" style={linkStyle}>
              Register
            </Link>
          </>
        ) : (
          <>
            {role === "USER" && (
              <Link to="/dashboard" style={linkStyle}>
                Dashboard
              </Link>
            )}
            {role === "ADMIN" && (
              <Link to="/admin" style={linkStyle}>
                Admin Panel
              </Link>
            )}
            <button onClick={handleLogout} style={logoutBtn}>
              Logout
            </button>
          </>
        )}
      </div>
    </nav>
  );
}

const navStyle = {
  display: "flex",
  justifyContent: "space-between",
  padding: "15px 50px",
  backgroundColor: "#1e293b", // Matches professional UI
  color: "white",
  alignItems: "center",
};

const linkStyle = {
  color: "white",
  textDecoration: "none",
  fontWeight: "500",
};

const logoutBtn = {
  backgroundColor: "#ef4444",
  color: "white",
  border: "none",
  padding: "8px 15px",
  borderRadius: "5px",
  cursor: "pointer",
};
