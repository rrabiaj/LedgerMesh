#!/bin/bash
# LedgerMesh startup script – runs all three services concurrently.
# Services are independent on Replit (H2 in-memory DB, no Docker needed).

set -e
REPO_ROOT="$(cd "$(dirname "$0")" && pwd)"

echo "=== LedgerMesh 🚀 ==="
echo "Starting all services from: $REPO_ROOT"
echo ""

# Config Server (port 8888)
echo "[1/3] Config Server  → http://localhost:8888"
(cd "$REPO_ROOT/infra/config-server" && \
  ./mvnw -q spring-boot:run \
    -Dspring-boot.run.jvmArguments="-Xmx256m" \
    2>&1 | sed 's/^/[config-server] /') &
CONFIG_PID=$!

# Discovery Server / Eureka (port 8761)
echo "[2/3] Discovery Server → http://localhost:8761"
(cd "$REPO_ROOT/infra/discovery-server" && \
  ./mvnw -q spring-boot:run \
    -Dspring-boot.run.jvmArguments="-Xmx256m" \
    2>&1 | sed 's/^/[discovery-server] /') &
DISCOVERY_PID=$!

# Account Service (port 8080)
echo "[3/3] Account Service  → http://localhost:8080"
(cd "$REPO_ROOT/services/account-service" && \
  ./mvnw -q spring-boot:run \
    -Dspring-boot.run.jvmArguments="-Xmx384m" \
    2>&1 | sed 's/^/[account-service] /') &
ACCOUNT_PID=$!

echo ""
echo "All services starting (first run downloads Maven deps – may take a few minutes)."
echo "Press Ctrl+C to stop all services."
echo ""

# Forward SIGINT/SIGTERM to all child processes
trap 'echo "Shutting down..."; kill $CONFIG_PID $DISCOVERY_PID $ACCOUNT_PID 2>/dev/null; wait' SIGINT SIGTERM

# Wait for any service to exit, then shut down the rest
wait -n 2>/dev/null || true
echo "A service has exited. Stopping all remaining services..."
kill $CONFIG_PID $DISCOVERY_PID $ACCOUNT_PID 2>/dev/null || true
wait
