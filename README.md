
---

# **欺诈检测服务介绍**

## 1. 准备环境

### 环境要求
- **工具**：
    - Docker（验证：`docker -v`）
    - Kubernetes（kubectl 工具，验证：`kubectl version`）
    - Google Cloud CLI（`gcloud`，验证：`gcloud version`）
    - Java 19（验证：`java -version`）

- **必要信息**：
    - GCP 项目 ID（例如：`spheric-engine-448906-m3`）
    - Artifact Registry 仓库位置与名称
    - Kubernetes 集群已配置并可访问

---

## 2. 构建 Docker 镜像

### **2.1 使用 Maven 构建 JAR 文件**
进入项目目录，执行以下命令：
```bash
mvn clean package -DskipTests
```
此命令会在 `target/` 目录下生成 `fraud-detection-*.jar` 文件。

### **2.2 构建 Docker 镜像**
执行以下命令构建 Docker 镜像：
```bash
docker build -t fraud-detection:latest .
```

### **2.3 验证 Docker 镜像**
验证镜像是否构建成功：
```bash
docker images
```
您应该看到名为 `fraud-detection` 的镜像，版本为 `latest`。

运行容器进行验证：
```bash
docker run -d -p 8080:8080 fraud-detection:latest
```
然后可以通过访问 `http://localhost:8080` 来检查容器是否正常运行。

### **2.4 测试交易接口**
验证镜像中的应用是否正常工作，您可以使用 `curl` 发送测试交易请求：

```bash
curl -X POST http://localhost:8080/fraud/check \
-H "Content-Type: application/json" \
-d '{
  "transactionId": "txn123",
  "accountId": "acc123",
  "amount": 15000
}'
```

如果服务正常，您应该能得到类似如下的响应：

- **成功响应**：
  ```json
  {
    "transactionId": "txn123",
    "status": "OK",
    "fraudulent": false
  }
  ```

- **失败响应**：
  如果请求失败，检查 Docker 容器日志：
  ```bash
  docker logs <container-id>
  ```
至此已经在本地完全验证完毕。

---

## 3. 推送镜像到 GCP Artifact Registry

### **3.1 配置 Artifact Registry**
使用以下命令启用 Artifact Registry API：
```bash
gcloud services enable artifactregistry.googleapis.com
```

### **3.2 创建 Artifact Registry 仓库**
```bash
gcloud artifacts repositories create frauddetectiondemo \
    --repository-format=docker \
    --location=northamerica-northeast1 \
    --description="Fraud Detection Demo Repository"
```

### **3.3 配置 Docker 登录**
```bash
gcloud auth configure-docker northamerica-northeast1-docker.pkg.dev
```

### **3.4 标记并推送镜像**
将镜像标记为 Artifact Registry 路径：
```bash
docker tag fraud-detection:latest northamerica-northeast1-docker.pkg.dev/spheric-engine-448906-m3/frauddetectiondemo/fraud-detection:latest
```

推送镜像：
```bash
docker push northamerica-northeast1-docker.pkg.dev/spheric-engine-448906-m3/frauddetectiondemo/fraud-detection:latest
```

---

## 4. 部署到 Kubernetes

### **4.1 创建 Secret 以存储服务账户密钥**
使用以下命令将本地服务账户密钥创建为 Kubernetes Secret：
```bash
kubectl create secret generic google-credentials \
    --from-file=./spheric-engine-448906-m3-3aa7849c9788.json
```

### **4.2 部署到 Kubernetes**
应用配置文件：
```bash
kubectl apply -f k8s/deployment.yaml
```

---

## 5. 验证部署

### **5.1 检查 Pods 是否运行成功**
运行以下命令查看 Pods 状态：
```bash
kubectl get pods
```

确保 Pods 状态为 `Running`。

### **5.2 获取服务的外部 IP**
使用以下命令获取服务的外部 IP 地址：
```bash
kubectl get services
```
输出示例：
```
NAME                      TYPE           CLUSTER-IP       EXTERNAL-IP     PORT(S)        AGE
fraud-detection-service   LoadBalancer   34.118.233.124   34.42.43.84     80:30934/TCP   10m
```

在此示例中，服务的外部 IP 是 `34.42.43.84`。

---

## 6. 测试服务

### **6.1 使用 `curl` 测试交易接口**
发送测试交易：
```bash
curl -X POST http://34.42.43.84/fraud/check \
-H "Content-Type: application/json" \
-d '{
  "transactionId": "txn123",
  "accountId": "acc123",
  "amount": 15000
}'
```

### **6.2 验证响应**
- **成功响应**：
  ```json
  {
    "transactionId": "txn123",
    "status": "OK",
    "fraudulent": false
  }
  ```
- **失败响应**：
  如果请求失败，请检查：
    1. 服务日志：`kubectl logs -l app=fraud-detection`
    2. 服务配置（特别是 GCP 凭证）。

---

## 7. 清理资源（可选）
如果不再需要部署，可以清理 Kubernetes 资源：
```bash
kubectl delete -f k8s/deployment.yaml
```

---

通过此文档，您可以从 Docker 镜像构建到 Kubernetes 部署，再到服务验证完成整个流程。如有问题，请随时反馈！