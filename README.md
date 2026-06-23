# 🚀 DevOps Practice Application [ Product Catalog Console ]

![Helm](https://img.shields.io/badge/Helm-V3-blue?style=for-the-badge&logo=helm) ![Kubernetes](https://img.shields.io/badge/kubernetes-%23326ce5.svg?style=for-the-badge&logo=kubernetes&logoColor=white) ![ArgoCD](https://img.shields.io/badge/argo%20cd-%23ef7b4d.svg?style=for-the-badge&logo=argo&logoColor=white)

Welcome to the **DevOps Practice Application**. This is a lightweight Spring Boot 3.x microservice tailored specifically for DevOps engineers to practice containerization, CI/CD pipelines, monitoring, and chaos engineering.

It features a **clean, single-screen responsive UI dashboard** connected to live backend endpoints and an integrated chaos testing laboratory.

---

## 🛠️ Project Stack & Architecture

- **Backend:** Java, Spring Boot 3.x
- **DevOps Core:** Spring Boot Actuator (Health & Metrics tracking)
- **Frontend UI:** Single-Screen Premium Dashboard (HTML5, Tailwind CSS, JavaScript Fetch API)
- **Containerization:** Production-optimized Multi-stage Dockerfile (Dependency Caching & Non-Root User)

---

## 🔌 Available Endpoints

The application exposes the following endpoints, mapped visually onto the UI dashboard:

| Endpoint | Method | Purpose / DevOps Use-Case |
| :--- | :--- | :--- |
| `/` or `/index.html` | `GET` | The Main Single-Screen Control Console |
| `/api/products` | `GET` | Simulates fetching live transactional inventory data |
| `/api/slow` | `GET` | **Chaos Endpoint:** Injects a strict **5-second delay** (useful to test high latency alerts & gateway timeouts) |
| `/api/error` | `GET` | **Chaos Endpoint:** Triggers an intentional **HTTP 500 Internal Error** (useful to test alert manager routing) |
| `/actuator/health` | `GET` | Exposes liveness and readiness state for Kubernetes Probes |
| `/actuator/metrics` | `GET` | Exposes JVM raw performance data for scraping |

> 💡 **Networking Note:** To test or verify these endpoints from your terminal, avoid using raw network layer commands like `ping -c 10 http://localhost:8081/api/error` as they do not support application layer protocols or ports. Instead, use: `curl -v http://localhost:8081/api/error`

---

## 🐳 Running Locally with Docker

This project comes equipped with a production-optimized **multi-stage Dockerfile** that leverages dependency layer caching (`go-offline`) and drops root privileges (`USER daemon`) for security hardening.

### 1. Build the Docker Image

```bash
docker build -t sachinthokal/spring-boot-app:1.0.3 .

```

### 2. Launch the Container

```bash
docker run -d -p 8081:8081 --name product-catalog-app sachinthokal/spring-boot-app:1.0.3

```

Once running, access the dashboard on: **`http://localhost:8081/`**

---

## 🔄 GitOps Integration (ArgoCD Pipeline)

This application is fully automated using GitOps principles. Application code modifications are decoupled from infrastructure configurations:

- **Centralized Helm Infrastructure Repo:** [Helm_For_Devops](https://github.com/sachinthokal/Helm_For_Devops)
- **Hosted Chart Registry:** Hosted via GitHub Pages at `https://sachinthokal.github.io/Helm_For_Devops/`

The environment is continuously tracked and dynamically synchronized to the Kubernetes cluster using the following declarative **ArgoCD Application Configuration**:

```yaml
apiVersion: argoproj.io/v1alpha1
kind: Application
metadata:
  name: spring-boot-app
  namespace: argocd
spec:
  project: default
  source:
    repoURL: '[https://sachinthokal.github.io/Helm_For_Devops/](https://sachinthokal.github.io/Helm_For_Devops/)'
    chart: spring-boot-helm-chart
    targetRevision: '*' # Automatically pulls and deploys the latest packaged chart release
  destination:
    server: '[https://kubernetes.default.svc](https://kubernetes.default.svc)'
    namespace: default
  syncPolicy:
    automated:
      prune: true
      selfHeal: true

```

---

## 🎯 Planned DevOps Practice Milestones

This repository is built to be a testing ground for the following implementations:

- [x] **Milestone 1:** Basic REST API Setup & Local Verification
- [x] **Milestone 2:** Embedded Single-Screen User Interface
- [x] **Milestone 3:** Native Optimized Multistage Dockerfile Containerization
- [x] **Milestone 4:** Centralized Helm Repository Hosting (via GitHub Pages)
- [x] **Milestone 5:** Continuous Delivery Orchestration using ArgoCD Declarative Sync
- [ ] **Milestone 6:** Monitoring Setup using Prometheus & Grafana Dashboards
