import { BrowserRouter, Routes, Route, Navigate, Link, useNavigate } from "react-router-dom";
import { useEffect, useState } from "react";
import HomePublic from "./pages/HomePublic";
import Login from "./pages/Login";
import HomeAuthed from "./pages/HomeAuthed";

function RequireAuth({ isAuthed, children }) {
  if (!isAuthed) return <Navigate to="/login" replace />;
  return children;
}

export default function App() {
  const [isAuthed, setIsAuthed] = useState(() => localStorage.getItem("isAuthed") === "true");

  useEffect(() => {
    localStorage.setItem("isAuthed", String(isAuthed));
  }, [isAuthed]);

  return (
    <BrowserRouter>
      <div style={{ padding: 16, fontFamily: "system-ui" }}>
        <nav style={{ display: "flex", gap: 12, marginBottom: 16 }}>
          <Link to="/">Public Home</Link>
          <Link to="/login">Login</Link>
          <Link to="/home">Authed Home</Link>

          {isAuthed && (
            <button onClick={() => setIsAuthed(false)} style={{ marginLeft: "auto" }}>
              Logout
            </button>
          )}
        </nav>

        <Routes>
          <Route path="/" element={<HomePublic />} />
          <Route path="/login" element={<Login onLogin={() => setIsAuthed(true)} />} />
          <Route
            path="/home"
            element={
              <RequireAuth isAuthed={isAuthed}>
                <HomeAuthed />
              </RequireAuth>
            }
          />
          <Route path="*" element={<Navigate to="/" replace />} />
        </Routes>
      </div>
    </BrowserRouter>
  );
}
