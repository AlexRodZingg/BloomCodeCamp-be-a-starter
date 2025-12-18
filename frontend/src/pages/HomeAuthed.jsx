export default function HomeAuthed() {
  const mockAssignments = [
    { id: 1, title: "Sprint 1 - Endpoints", status: "SUBMITTED" },
    { id: 2, title: "Sprint 2 - Auth", status: "IN_REVIEW" },
    { id: 3, title: "Sprint 3 - React Client", status: "NEEDS_CHANGES" },
  ];

  return (
    <div>
      <h1>Home (Authenticated)</h1>
      <p>(wireframe: Home Page - Authenticated)</p>

      <h2 style={{ marginTop: 16 }}>Assignments</h2>
      <ul>
        {mockAssignments.map((a) => (
          <li key={a.id}>
            <strong>{a.title}</strong> — {a.status}
          </li>
        ))}
      </ul>

      <p style={{ marginTop: 16, opacity: 0.8 }}>
        Note: mock data for Sprint 3. In a real build, this would come from a GET endpoint.
      </p>
    </div>
  );
}
