import { Link } from "react-router-dom";

export default function HomePublic() {
  return (
    <div>
      <h1>Assignment Review App</h1>
      <p>Public landing page (wireframe: Home Page - Public).</p>

      <div style={{ marginTop: 16 }}>
        <Link to="/login">
          <button>Go to Login</button>
        </Link>
      </div>
    </div>
  );
}
