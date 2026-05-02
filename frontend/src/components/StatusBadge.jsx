export default function StatusBadge({ status }) {
  const isApproved = status === "APPROVED";
  const badgeStyle = {
    padding: "4px 10px",
    borderRadius: "5px",
    fontSize: "12px",
    fontWeight: "bold",
    textTransform: "uppercase",
    backgroundColor: isApproved ? "#eafaf1" : "#fef5e7",
    color: isApproved ? "#27ae60" : "#f39c12",
    border: `1px solid ${isApproved ? "#27ae60" : "#f39c12"}`,
  };

  return <span style={badgeStyle}>{status}</span>;
}
