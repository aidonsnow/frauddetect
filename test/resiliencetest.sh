#!/bin/bash

# 获取删除时的时间戳
start_time=$(date +%s)

# 删除所有符合标签的 pod
kubectl delete pod -l app=fraud-detection

# 循环检查直到所有 Pod 都恢复为 Running 状态
while true; do
    pod_status=$(kubectl get pods -l app=fraud-detection --field-selector=status.phase=Running -o jsonpath='{.items[*].status.phase}')
    if [[ "$pod_status" == *"Running"* ]]; then
        break  # 如果至少有一个 pod 处于 Running 状态，跳出循环
    fi
    sleep 1  # 每秒检查一次状态
done

# 获取恢复时的时间戳
end_time=$(date +%s)

# 计算恢复时间（秒）
recovery_time=$((end_time - start_time))

echo "Pods recovery time: $recovery_time seconds"
