export default function HomeAuthed() {
  // Mock data aligned with backend AssignmentEnum and AssignmentStatusEnum
  const mockAssignments = [
    {
      assignmentNumber: 1,
      assignmentName: "Spring Boot Service",
      status: "Submitted",
    },
    {
      assignmentNumber: 2,
      assignmentName: "Spring Boot Data JPA",
      status: "In Review",
    },
    {
      assignmentNumber: 3,
      assignmentName: "Spring Boot Postgresql",
      status: "Needs Update",
    },
    {
      assignmentNumber: 4,
      assignmentName: "Docker Compose Setup",
      status: "Completed",
    },
    {
      assignmentNumber: 5,
      assignmentName: "React Frontend Hooks",
      status: "Pending Submission",
    },
  ];

  return (
    <div>
      <h1>Home (Authenticated)</h1>
      <p>(wireframe: Home Page – Authenticated)</p>

      <h2 style={{ marginTop: 16 }}>Assignments</h2>

      <ul>
        {mockAssignments.map((a) => (
          <li key={a.assignmentNumber} style={{ marginBottom: 8 }}>
            <strong>
              Assignment {a.assignmentNumber}: {a.assignmentName}
            </strong>{" "}
            — {a.status}
          </li>
        ))}
      </ul>

      <p style={{ marginTop: 16, opacity: 0.8 }}>
        Note: This is mock data for Ticket 3. In a full integration, this list
        would be populated from a backend endpoint and the status values would
        map directly to AssignmentStatusEnum.
      </p>
    </div>
  );
}

