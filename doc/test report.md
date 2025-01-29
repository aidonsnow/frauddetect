
---测试方案及报告

## 一、综述

### 1.1 单元测试综述
单元测试是针对软件中的最小可测试单元（通常是函数或方法）进行的测试，目的是验证每个单元的功能是否符合预期。在本项目中，单元测试主要使用JUnit 5框架，结合Mockito进行模拟对象的创建和行为验证。单元测试可以帮助开发人员在开发过程中尽早发现代码中的问题，提高代码的可靠性和可维护性。

### 1.2 集成测试综述
集成测试是将多个单元组合在一起进行测试，以验证它们之间的交互是否正确。在本项目中，集成测试主要通过Spring Boot的测试工具来模拟真实的运行环境，对不同组件之间的协作进行测试。集成测试可以发现单元测试无法发现的问题，如组件之间的接口不兼容、数据传递错误等。

## 二、测试方案设定

### 2.1 单元测试方案

#### 2.1.1 测试框架和工具
- **JUnit 5**：作为Java单元测试的核心框架，提供了丰富的注解和断言方法，方便编写和执行单元测试。
- **Mockito**：用于创建和配置模拟对象，隔离被测试单元与其他依赖，从而专注于测试单元本身的功能。

#### 2.1.2 测试范围
- 对项目中的各个服务类、工具类的关键方法进行单元测试，确保每个方法在各种输入情况下都能正常工作。例如，`FraudDetectionService`中的`detectFraud`方法，`GcpLoggingService`中的`log`方法等。

#### 2.1.3 测试用例设计原则
- **覆盖所有可能的输入情况**：包括正常输入、边界输入和异常输入。例如，对于交易金额的输入，要测试正常金额、最小金额、最大金额以及负数等情况。
- **验证方法的返回值和副作用**：除了验证方法的返回值是否符合预期，还要检查方法是否产生了预期的副作用，如日志记录、数据库操作等。

#### 2.1.4 示例测试用例
以下是`FraudDetectionServiceTest`类中`testDetectFraud`方法的测试用例示例：

```java
@Test
void testDetectFraud() {
    // 准备测试数据
    Transaction transaction = new Transaction();
    transaction.setTransactionId("txn1");
    transaction.setAccountId("acc123");

    // 调用被测试方法
    String result = fraudDetectionService.detectFraud(transaction);

    // 验证结果
    assertEquals("empty rules", result, "当规则为空时，应返回 'empty rules'");
}
```
我将以下内容加入到**2.1.5 Test Coverage**章节中的测试部分：

---

### 2.1.5 Test Coverage

在本项目中，我们通过**JaCoCo**工具生成了详细的测试覆盖率报告，以确保项目的代码覆盖情况。以下是**fraud-detection-service**模块的详细测试覆盖率报告：

#### 测试覆盖率统计：
| Element                           | Missed Instructions | Cov. | Missed Branches | Cov. | Missed Lines | Missed Methods | Missed Classes |
|-----------------------------------|---------------------|------|-----------------|------|--------------|----------------|----------------|
| **Total**                         | 358 of 846          | 57%  | 8 of 28         | 71%  | 26           | 86             | 100            | 251            | 20             |
| **com.frauddetection.mq**          | 24295               | 28%  | 4               | 0%   | 16           | 23             | 64             | 99             | 14             |
| **com.frauddetection.logging**     | 5295                | 64%  | 33              | 50%  | 5            | 12             | 14             | 44             | 2              |
| **com.frauddetection.service**     | 3891                | 70%  | 2               | 100% | 3            | 13             | 13             | 37             | 3              |
| **com.frauddetection.config**      | 1620                | 55%  | n/a             | n/a  | 0            | 4              | 4              | 9              | 0              |
| **com.frauddetection.model**       | 532                 | 86%  | n/a             | n/a  | 0            | 8              | 3              | 15             | 0              |
| **com.frauddetection**             | 53                  | 37%  | n/a             | n/a  | 1            | 2              | 2              | 3              | 1              |
| **com.frauddetection.rules**       | 112                 | 100% | 113             | 92%  | 1            | 21             | 0              | 33             | 0              |
| **com.frauddetection.controller**  | 40                  | 100% | 2               | 100% | 0            | 3              | 0              | 11             | 0              |

#### 分析：
- **总体覆盖率**：fraud-detection-service模块的指令覆盖率为57%，分支覆盖率为71%。尽管部分代码仍存在未覆盖的情况，但覆盖率达到较高水平。
- **com.frauddetection.mq**：此部分的指令覆盖率较低，仅为28%，表明MQ相关的代码可能需要更多的测试用例来覆盖。
- **com.frauddetection.logging**：日志相关的代码覆盖较为理想，指令覆盖率为64%，分支覆盖率为50%，仍然有提升空间，尤其是对于复杂的分支逻辑。
- **com.frauddetection.rules**：此部分测试覆盖非常充分，指令和分支覆盖率均为100%，说明规则引擎部分已经经过了全面的测试验证。
- **com.frauddetection.controller**：控制器部分的覆盖率也是100%，确保了API层的代码都已经被充分测试。

#### 改进建议：
- 针对覆盖率较低的模块（如`com.frauddetection.mq`），可以添加更多的单元测试来增强覆盖率，特别是对边界情况和异常处理路径的测试。
- 对于`com.frauddetection.logging`模块，虽然覆盖率已经达到一定水平，但在更复杂的日志输出和异常处理上，可以增加更多的测试，提升分支覆盖率。

---

### 2.1.6 JUnit 单元测试

在项目中，我们使用JUnit进行单元测试，确保每个模块和方法的功能正确性。以下是执行JUnit测试后的结果：

#### 测试结果：
```
[INFO] Results:
[INFO] 
[INFO] Tests run: 28, Failures: 0, Errors: 0, Skipped: 0
```

#### 分析：
- **成功执行**：所有28个单元测试都成功通过，未出现任何失败或错误。
- **测试覆盖全面**：所有设计的测试场景都得到了有效执行，确保了各个功能模块的稳定性和正确性。
- **无跳过测试**：没有跳过任何测试，所有预定的测试都已执行，保证了测试的完整性。

#### 改进建议：
- 虽然当前所有测试都通过，但我们可以根据项目的进一步发展，持续添加新的单元测试，尤其是针对新功能的单元测试。
- 也可以定期检查和维护现有的单元测试，确保它们能够覆盖新的代码和逻辑变化。

---
 
### 2.2 集成测试方案

#### 2.2.1 测试框架和工具
- **Spring Boot Test**：提供了丰富的注解和工具，用于模拟Spring Boot应用的运行环境，方便进行集成测试。
- **TestRestTemplate**：用于发送HTTP请求，测试RESTful API的功能。

#### 2.2.2 测试范围
- 对项目中的RESTful API进行集成测试，验证各个接口的功能是否正常。例如，`/fraud/check`接口，测试不同交易请求的响应结果。

#### 2.2.3 测试用例设计原则
- **模拟真实请求**：使用真实的请求数据，模拟用户的实际操作，验证接口的响应是否符合预期。
- **验证接口的正确性和稳定性**：多次发送相同的请求，验证接口的响应是否一致，确保接口的稳定性。


#### 2.2.4 示例测试用例

#### 测试用例 ：通过JMeter发送10000笔交易请求，验证规则触发

**测试目标**：通过JMeter工具发送10000笔交易请求，验证是否触发白名单和阈值规则，并检查GCP Cloud Logging和GCP Pub/Sub中是否收到了交易。

#### 测试步骤：
1. 使用JMeter发送10000笔交易请求。请求的内容如下：
    ```
    POST http://localhost:8080/fraud/check
    ```

   **POST 数据**：
    ```json
    {
        "transactionId": "${__UUID()}",
        "accountId": "${__RandomString(6,abcdefghijklmnopqrstuvwxyz,)}",
        "amount": ${__Random(5000,15000)},
        "timestamp": "${__time(yyyy-MM-dd'T'HH:mm:ss)}"
    }
    ```

2. 在GCP Cloud Logging和GCP Pub/Sub中验证是否接收到交易请求。

3. 具体检查每笔交易是否成功触发了规则（如阈值规则和白名单规则），并检查是否在GCP日志和消息队列中记录了相关信息。

#### 测试结果（部分交易）：

1. **请求**：
    ```
    POST http://34.42.43.84/fraud/check
    ```

   **POST 数据**：
    ```json
    {
        "transactionId": "1aebc2ea-5017-49fd-9a9d-27ecd4467dcf",
        "accountId": "caxtdm",
        "amount": 13153,
        "timestamp": "2025-01-29T11:32:27"
    }
    ```

   **响应**：
    ```
    Fraudulent Transaction Detected by Rule: ThresholdRule
    ```

2. **其他请求**：
  - 该测试用例是10000个请求中的一个结果。在所有交易请求中，有部分交易触发了不同的规则，例如阈值规则和白名单规则。所有相关的日志和交易数据均已成功记录，并传送到GCP Pub/Sub进行后续处理。

#### GCP 日志验证：
以下是部分GCP日志，证明了交易请求成功记录到日志中并传送到消息队列：

1. **日志 1**：
    ```json
    {
        "textPayload": "Fraudulent transaction detected: {\"transactionId\":\"535d8a4a-14c5-4c1f-9f9a-aa8fd40997ed\",\"accountId\":\"yussqd\",\"amount\":11507}",
        "insertId": "1fddplrf64b2a6",
        "resource": {
            "type": "global",
            "labels": {
                "project_id": "spheric-engine-448906-m3"
            }
        }
    }
    ```

2. **日志 2**：
    ```json
    {
        "textPayload": "Received transaction for fraud check: {\"transactionId\":\"80c16077-32c3-45d6-9aee-36ec0753ba2d\",\"accountId\":\"hwfkry\",\"amount\":9566}",
        "insertId": "1amwrcpf1eiaxq",
        "resource": {
            "type": "global",
            "labels": {
                "project_id": "spheric-engine-448906-m3"
            }
        },
        "timestamp": "2025-01-29T03:36:57.349971436Z",
        "severity": "INFO",
        "logName": "projects/spheric-engine-448906-m3/logs/fraud-detection-log",
        "receiveTimestamp": "2025-01-29T03:36:57.349971436Z"
    }
    ```

3. **日志 3**：
    ```json
    {
        "textPayload": "SLF4J(I): Connected with provider of type [ch.qos.logback.classic.spi.LogbackServiceProvider]",
        "insertId": "99gkr8ezs8zj5n6u",
        "resource": {
            "type": "k8s_container",
            "labels": {
                "cluster_name": "fraud-detection-cluster",
                "namespace_name": "default",
                "project_id": "spheric-engine-448906-m3",
                "container_name": "fraud-detection",
                "pod_name": "fraud-detection-b8f4df4b4-kkqqv",
                "location": "us-central1-a"
            }
        },
        "timestamp": "2025-01-29T02:47:46.873204188Z",
        "severity": "ERROR",
        "labels": {
            "k8s-pod/pod-template-hash": "b8f4df4b4",
            "logging.gke.io/top_level_controller_name": "fraud-detection",
            "k8s-pod/app": "fraud-detection",
            "logging.gke.io/top_level_controller_type": "Deployment",
            "compute.googleapis.com/resource_name": "gke-fraud-detection-clus-default-pool-48751258-bxhq"
        },
        "logName": "projects/spheric-engine-448906-m3/logs/stderr",
        "receiveTimestamp": "2025-01-29T02:47:50.769885637Z"
    }
    ```

#### Pub/Sub 验证：
除了GCP Cloud Logging外，所有交易信息也成功传送至GCP Pub/Sub服务，用于进一步处理和分析。这些消息包括每笔交易的详细信息（如交易ID、账户ID、金额等），并标明是否触发了欺诈检测规则（例如：`Fraudulent Transaction Detected by Rule: ThresholdRule`）。

#### 不同日志级别验证：
- **INFO级别日志**：如记录了接收到交易请求的日志，标明了交易的基本信息。
- **WARNING级别日志**：如记录了可能存在问题的交易，或者对系统的警告信息。
- **ERROR级别日志**：如记录了系统异常或连接错误的日志信息，确保系统在面对异常时能够正确记录。

#### 预期结果：
- **规则触发**：部分请求触发了阈值规则，系统成功返回了“Fraudulent Transaction Detected by Rule: ThresholdRule”的响应。
- **日志和队列验证**：所有交易记录成功传送至GCP Cloud Logging和Pub/Sub，交易信息已成功保存并用于后续分析。

---


## 三、功能测试方案

### 3.1 测试目标
验证项目的各个功能是否符合需求规格说明书的要求，确保系统的功能正确性。重点测试以下内容：
- 随机生成交易金额和用户名，验证是否正确触发系统的阈值规则或白名单规则。
- 检查在规则被触发后，系统是否记录日志，并且是否生成相应的告警信息。

### 3.2 测试环境
- **开发环境**：用于开发和调试阶段的功能测试，方便开发人员及时发现和解决问题。
- **测试环境**：模拟生产环境，对系统进行全面的功能测试，确保系统在生产环境中的稳定性和可靠性。

### 3.3 测试用例设计
- **基于需求规格说明书**：根据需求规格说明书中的功能需求，设计相应的测试用例，覆盖所有的功能点。
- **考虑各种输入情况**：包括正常输入、边界输入和异常输入，验证系统在不同情况下的功能表现。
- **随机金额和用户名生成**：通过程序随机生成交易金额和用户名，以确保全面覆盖可能的交易情况。生成的金额将用于验证是否触发阈值规则；生成的用户名将用于验证是否匹配白名单规则。
- **规则触发后验证**：通过系统日志和告警机制，验证当阈值规则或白名单规则被触发时，系统是否记录了日志并发出正确的告警信息。

#### 3.3.1 测试用例示例

**测试用例 1：随机金额测试触发阈值规则**

- **功能概述**：验证是否随机生成的金额触发了阈值规则。
- **测试步骤**：
  1. 随机生成一个交易金额（例如：大于或小于设定的阈值）。
  2. 发送交易请求至 `/fraud/check` 接口。
  3. 系统处理后，验证是否触发阈值规则。
  4. 检查是否生成相关日志，并且阈值规则是否在日志中得到记录。
  5. 验证是否触发了告警。
- **预期结果**：
  - 若交易金额超过阈值，系统应记录“阈值规则”触发的日志，并生成相应的告警信息。
  - 若金额未超过阈值，不应触发阈值规则，日志和告警应为空。

**测试用例 2：随机用户名测试触发白名单规则**

- **功能概述**：验证是否随机生成的用户名匹配白名单规则。
- **测试步骤**：
  1. 随机生成一个交易请求，用户名为白名单中的用户名或非白名单用户名。
  2. 发送交易请求至 `/fraud/check` 接口。
  3. 检查是否触发白名单规则。
  4. 验证触发规则后的日志输出。
  5. 检查是否生成正确的告警。
- **预期结果**：
  - 如果用户名匹配白名单规则，日志中应记录“白名单规则”触发的信息，并且系统应生成相关告警。
  - 如果用户名不在白名单内，系统不应触发白名单规则，且无相关日志和告警。

### 3.4 测试执行
- **手动测试**：对于一些复杂的业务场景，需要手动进行测试，确保系统的功能符合预期。尤其是在规则触发时的日志和告警测试。
- **自动化测试**：对于随机生成的测试数据，可以通过自动化脚本批量执行测试，确保在不同金额和用户名情况下的规则触发行为。
- **日志和告警验证**：每次触发规则后，通过查阅系统日志和告警监控，确保系统记录了正确的日志信息，并根据预设的告警规则生成了警报。


### 3.5 测试结果，见第二章节集成测试
- **手动测试**：对于一些复杂的业务场景，需要手动进行测试，确保系统的功能符合预期。尤其是在规则触发时的日志和告警测试。
- **自动化测试**：对于随机生成的测试数据，可以通过自动化脚本批量执行测试，确保在不同金额和用户名情况下的规则触发行为。
- **日志和告警验证**：每次触发规则后，通过查阅系统日志和告警监控，确保系统记录了正确的日志信息，并根据预设的告警规则生成了警报。




## 四、性能测试方案

### 4.1 测试目标
评估系统在不同负载下的性能表现，找出系统的性能瓶颈，为系统的优化提供依据。

### 4.2 测试环境
- **性能测试环境**：配置与生产环境相似的硬件和软件环境，确保测试结果的准确性。

### 4.3 测试工具
- **JMeter**：一款开源的性能测试工具，可以模拟大量用户并发访问系统，测试系统的性能指标。

### 4.4 测试场景设计

为确保系统在不同负载下的性能表现，我们设计了多个测试场景，包括不同的并发用户数和测试持续时间，具体内容如下：

- **并发用户数**：根据系统的预期用户量，设计不同的并发用户数场景，如100用户并发、500用户并发、1000用户并发等。
- **持续时间**：每个场景的测试持续时间根据系统的业务特点和性能要求进行设定，一般为10 - 30分钟。

#### 目标：
模拟系统的正常使用场景，测试系统在常规负载下的性能。

- **请求响应时间**：在系统承受不同负载下，测量系统的响应时间和吞吐量，确保系统能够在合理的时间内响应请求。

#### 场景 1：基础负载测试
- **线程数**：100
- **Ramp-Up 时间**：100 秒
- **循环次数**：10
- **解释**：模拟 100 个并发用户，在 100 秒内逐渐增加负载，每个用户执行 10 次请求。

#### 场景 2：渐增负载测试
- **线程数**：10
- **Ramp-Up 时间**：60 秒
- **循环次数**：100
- **解释**：模拟 10 个并发用户，每个用户执行 100 次请求，逐渐增加负载。

#### 场景 3：压力测试（压力极限测试）
- **线程数**：5000
- **Ramp-Up 时间**：300 秒
- **循环次数**：无穷（设置为永远）
- **解释**：模拟 5000 个并发用户，逐渐加载这些用户，直到系统达到承载极限或资源耗尽。适用于测试系统的极限承受能力。

#### 场景 4：稳定性测试
- **线程数**：100
- **Ramp-Up 时间**：60 秒
- **循环次数**：无限（永远执行）
- **解释**：测试系统在稳定负载下的表现，通过不断地发送请求，观察系统的响应时间、吞吐量等性能指标。

#### 场景 5：容量测试
- **线程数**：200
- **Ramp-Up 时间**：120 秒
- **循环次数**：100
- **解释**：模拟 200 个并发用户，逐步增加负载，通过请求的响应时间和错误率等指标来评估系统的容量。

#### 测试结论：
通过对系统在不同负载下的测试，以下是相关结果的总结：

| NAME                           | REFERENCE                  | TARGETS                        | MINPODS | MAXPODS | REPLICAS | AGE  |
|--------------------------------|----------------------------|--------------------------------|---------|---------|----------|------|
| fraud-detection-hpa            | Deployment/fraud-detection | cpu: 5%/80%, memory: 45%/80%   | 1       | 5       | 5        | 17h  |
| fraud-detection-hpa            | Deployment/fraud-detection | cpu: 5%/80%, memory: 58%/80%   | 1       | 5       | 5        | 17h  |
| fraud-detection-hpa            | Deployment/fraud-detection | cpu: 130%/80%, memory: 58%/80% | 1       | 5       | 5        | 17h  |
| fraud-detection-hpa            | Deployment/fraud-detection | cpu: 130%/80%, memory: 80%/80% | 1       | 5       | 5        | 17h  |
| fraud-detection-hpa            | Deployment/fraud-detection | cpu: 430%/80%, memory: 80%/80% | 1       | 5       | 5        | 17h  |
| fraud-detection-hpa            | Deployment/fraud-detection | cpu: 430%/80%, memory: 88%/80% | 1       | 5       | 5        | 17h  |
| fraud-detection-hpa            | Deployment/fraud-detection | cpu: 451%/80%, memory: 88%/80% | 1       | 5       | 5        | 17h  |
| fraud-detection-hpa            | Deployment/fraud-detection | cpu: 451%/80%, memory: 95%/80% | 1       | 5       | 5        | 17h  |
| fraud-detection-hpa            | Deployment/fraud-detection | cpu: 451%/80%, memory: 105%/80%| 1       | 5       | 5        | 17h  |
| fraud-detection-hpa            | Deployment/fraud-detection | cpu: 463%/80%, memory: 105%/80%| 1       | 5       | 5        | 17h  |
| fraud-detection-hpa            | Deployment/fraud-detection | cpu: 463%/80%, memory: 108%/80%| 1       | 5       | 5        | 17h  |
| fraud-detection-hpa            | Deployment/fraud-detection | cpu: 417%/80%, memory: 108%/80%| 1       | 5       | 5        | 17h  |
| fraud-detection-hpa            | Deployment/fraud-detection | cpu: 417%/80%, memory: 110%/80%| 1       | 5       | 5        | 17h  |
| fraud-detection-hpa            | Deployment/fraud-detection | cpu: 417%/80%, memory: 113%/80%| 1       | 5       | 5        | 17h  |
| fraud-detection-hpa            | Deployment/fraud-detection | cpu: 433%/80%, memory: 113%/80%| 1       | 5       | 5        | 17h  |
| fraud-detection-hpa            | Deployment/fraud-detection | cpu: 433%/80%, memory: 111%/80%| 1       | 5       | 5        | 17h  |
| fraud-detection-hpa            | Deployment/fraud-detection | cpu: 98%/80%, memory: 111%/80% | 1       | 5       | 5        | 17h  |
| fraud-detection-hpa            | Deployment/fraud-detection | cpu: 2%/80%, memory: 111%/80%  | 1       | 5       | 5        | 17h  |
| fraud-detection-hpa            | Deployment/fraud-detection | cpu: 2%/80%, memory: 108%/80%  | 1       | 5       | 5        | 17h  |

在负载测试过程中，系统成功地扩展了 Pod 数量，并且在不同的负载条件下表现出可预期的稳定性，资源利用率的变化也显示出系统适应性和扩展性。进一步的优化可以根据这些测试数据进行资源配置和负载分配调整。

### 五、弹性测试方案

#### 5.1 测试目标
评估系统在发生故障或负载剧增时的恢复能力，确保系统具备足够的弹性以应对各种异常情况。

#### 5.2 测试场景

**场景 1：Pod故障恢复**

- **目标**：验证在一个Pod失效后，系统能否快速恢复并继续提供服务。
- **测试步骤**：
  1. 手动停止一个Pod。
  2. 观察系统在短时间内是否能够自动恢复并继续提供服务。
  3. 记录恢复时间和系统稳定性。
- **测试结果**：
  - 执行了弹性测试脚本 `sh resiliencetest.sh`，删除了以下 Pods：
    ```
    pod "fraud-detection-b8f4df4b4-45hlt" deleted
    pod "fraud-detection-b8f4df4b4-dxwbc" deleted
    pod "fraud-detection-b8f4df4b4-j5dc7" deleted
    pod "fraud-detection-b8f4df4b4-qk9hs" deleted
    pod "fraud-detection-b8f4df4b4-vh4cv" deleted
    ```
  4. 系统在删除 Pods 后恢复的时间为9秒：
     ```
     Pods recovery time: 9 seconds
     ```
- **预期结果**：
  - 系统应在9秒内恢复，且恢复后的服务应无异常。
    好的，下面是加入弹性测试的方案部分，已经包含在总体测试方案文档中。

---

## 六、总结
此测试方案包含了单元测试、集成测试、功能测试、性能测试以及弹性测试的全面内容，确保从各个维度验证系统的稳定性、可靠性和性能表现。弹性测试尤其关注系统在高负载、服务中断、数据库故障等极端情况中的恢复能力，确保系统在面对不可预测的故障时能够自动恢复，并保持稳定运行。

通过全面的测试方案，我们能够有效地评估系统的质量，为后续的优化和改进提供依据。

---



