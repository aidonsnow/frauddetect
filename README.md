### **README: Fraud Detection Service**

---

### **Disclaimer**
以下是从使用 Maven 构建 JAR 文件到使用 Docker 构建并运行容器的完整流程说明。

---

### **完整流程**

#### **1. 确保环境准备好**
- **工具**：
    - 安装 Maven（验证：`mvn -v`）。
    - 安装 Docker（验证：`docker -v`）。

- **项目结构**：
    - 项目中包含 `pom.xml` 文件。
    - 应用的主类已正确配置在 `pom.xml` 的 `spring-boot-maven-plugin` 中。

---

#### **2. 使用 Maven 构建项目**
1. 运行以下命令，在项目根目录下构建项目：
   ```bash
   mvn clean package -DskipTests
   ```
    - **`clean`**：清理上一次的构建结果。
    - **`package`**：打包项目，生成 `target/*.jar` 文件。
    - **`-DskipTests`**：跳过单元测试，缩短构建时间。

---

#### **3. 构建 Docker 镜像**
1. 运行以下命令构建 Docker 镜像：
   ```bash
   docker build -t fraud-detection:latest .
   ```
2. 验证镜像是否构建成功：
   ```bash
   docker images
   ```
   确认输出中是否包含镜像名称 `fraud-detection:latest`。

---

#### **4. 运行 Docker 容器**
1. 使用以下命令运行容器，将服务暴露在本地 `8080` 端口：
   ```bash
   docker run -p 8080:8080 fraud-detection:latest
   ```

---

#### **5. 测试服务是否正常**
1. 使用 `curl` 测试服务的 POST 接口：
   ```bash
   curl -X POST http://localhost:8080/fraud/check \
        -H "Content-Type: application/json" \
        -d '{"transactionId":"txn123", "accountId":"acc123", "amount":15000}'
   ```
    - **请求数据**：
      ```json
      {
        "transactionId": "txn123",
        "accountId": "acc123",
        "amount": 15000
      }
      ```

2. 验证服务是否返回预期响应。

---

### **总结**
1. 确保环境准备和项目结构完整。
2. 使用 Maven 构建 JAR 文件。
3. 使用 Docker 构建并运行镜像。
4. 通过 `curl` 或其他工具测试服务接口。

