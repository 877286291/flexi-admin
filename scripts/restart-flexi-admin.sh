#!/usr/bin/env bash
set -euo pipefail

APP_NAME="flexi-admin"
JAR_PATH="/root/projects/flexi-admin/boot/target/${APP_NAME}.jar"
WORK_DIR="/root/projects/flexi-admin"
LOG_DIR="/root/projects/flexi-admin/logs"
PID_FILE="/root/projects/flexi-admin/${APP_NAME}.pid"
JAVA_OPTS=${JAVA_OPTS:-"-Xms512m -Xmx1024m"}
# 固定使用 dev 配置
SPRING_PROFILES_ACTIVE="dev"

mkdir -p "${LOG_DIR}" "${WORK_DIR}/scripts"

find_pid() {
	# 查找以目标jar运行的java进程
	pgrep -f "java .*${APP_NAME}\.jar" || true
}

latest_log() {
	# 优先返回 flexi-admin.log；如不存在则选择最新的 flexi-admin.log.*（轮转）
	if [[ -f "${LOG_DIR}/${APP_NAME}.log" ]]; then
		echo "${LOG_DIR}/${APP_NAME}.log"
		return 0
	fi
	ls -1t "${LOG_DIR}/${APP_NAME}.log".* 2>/dev/null | head -n 1 || true
}

tail_follow_latest_log() {
	# 始终跟踪主业务日志 flexi-admin.log；若暂不存在则等待文件创建
	local main_log_path="${LOG_DIR}/${APP_NAME}.log"
	echo "[INFO] Following log: ${main_log_path} (waiting if not present)"
	tail -n 200 -F "${main_log_path}"
}

stop_app() {
	local pid
	pid=$(find_pid)
	if [[ -n "${pid}" ]]; then
		echo "[INFO] Stopping ${APP_NAME} (pid=${pid})..."
		kill "${pid}" || true
		# 等待最多30秒优雅停止
		for i in {1..30}; do
			if ps -p "${pid}" > /dev/null 2>&1; then
				sleep 1
			else
				break
			fi
		done
		if ps -p "${pid}" > /dev/null 2>&1; then
			echo "[WARN] Force killing ${APP_NAME} (pid=${pid})..."
			kill -9 "${pid}" || true
		fi
		rm -f "${PID_FILE}" || true
	else
		echo "[INFO] No running ${APP_NAME} process found."
	fi
}

build_app() {
	echo "[INFO] Building ${APP_NAME}..."
	cd "${WORK_DIR}"
	mvn -q -DskipTests package -pl boot -am
}

start_app() {
	if [[ ! -f "${JAR_PATH}" ]]; then
		echo "[ERROR] Jar not found: ${JAR_PATH}" >&2
		exit 1
	fi
	local ts
	ts=$(date +"%Y-%m-%d_%H-%M-%S")
	local stdout_log="${LOG_DIR}/${APP_NAME}.out.${ts}.log"
	local stderr_log="${LOG_DIR}/${APP_NAME}.err.${ts}.log"
	echo "[INFO] Starting ${APP_NAME} with profile=${SPRING_PROFILES_ACTIVE}..."
	nohup java ${JAVA_OPTS} -Dspring.profiles.active="${SPRING_PROFILES_ACTIVE}" -jar "${JAR_PATH}" >"${stdout_log}" 2>"${stderr_log}" &
	local new_pid=$!
	echo "${new_pid}" > "${PID_FILE}"
	echo "[INFO] Started ${APP_NAME} (pid=${new_pid}). Logs: ${stdout_log} | ${stderr_log}"
}

# 默认命令为 restart
COMMAND="restart"
if [[ ${#} -gt 0 ]]; then
	COMMAND="${1}"
fi

case "${COMMAND}" in
	start)
		start_app
		# 直接跟踪业务日志
		tail_follow_latest_log
		;;
	stop)
		stop_app
		;;
	restart)
		stop_app
		build_app
		start_app
		# 重启后直接跟踪业务日志
		tail_follow_latest_log
		;;
	build)
		build_app
		;;
	status)
		pid=$(find_pid)
		if [[ -n "${pid}" ]]; then
			echo "[INFO] ${APP_NAME} running (pid=${pid})"
		else
			echo "[INFO] ${APP_NAME} not running"
		fi
		;;
	logs)
		# 直接输出并跟踪最新日志；若文件尚未创建将等待
		tail_follow_latest_log
		;;
	*)
		echo "Usage: $0 {start|stop|restart|build|status|logs}" >&2
		exit 2
		;;
esac 