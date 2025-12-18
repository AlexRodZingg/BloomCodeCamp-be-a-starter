import { useState } from "react";
import { useNavigate } from "react-router-dom";

export default function Login({ onLogin }) {
  const navigate = useNavigate();
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");

  function handleSubmit(e) {
    e.preventDefault();

    // Sprint 3: mock login flow (no validation / no backend required).
    onLogin();
    navigate("/home");
  }

  return (
    <div>
      <h1>Login</h1>
      <p>(wireframe: Login Page)</p>

      <form onSubmit={handleSubmit} style={{ display: "grid", gap: 12, maxWidth: 320 }}>
        <label>
          Username
          <input value={username} onChange={(e) => setUsername(e.target.value)} />
        </label>

        <label>
          Password
          <input type="password" value={password} onChange={(e) => setPassword(e.target.value)} />
        </label>

        <button type="submit">Login</button>
      </form>
    </div>
  );
}
