#!/usr/bin/env bash
# ─────────────────────────────────────────────────────────────
#  git-commit-wizard.sh
#  Cross-platform interactive git commit wizard
#  macOS · Linux · Windows (Git Bash / WSL)
#  의존성 없음 — bash 3.2+ 만 있으면 됩니다
# ─────────────────────────────────────────────────────────────

# ── ANSI 색상 & 커서 ──────────────────────────────────────────
GRN='\033[0;32m'; CYN='\033[0;36m'; YLW='\033[0;33m'
RED='\033[0;31m'; GRY='\033[0;90m'
BOLD='\033[1m'; DIM='\033[2m'; RST='\033[0m'

_hide_cursor()    { printf '\033[?25l'; }
_show_cursor()    { printf '\033[?25h'; }
_save_cursor()    { printf '\033[s';    }
_restore_cursor() { printf '\033[u';    }

trap '_show_cursor' EXIT

# ── 키 입력 읽기 ──────────────────────────────────────────────
read_key() {
    KEY=""
    local temp_key rest

    # Zsh인지 Bash인지 확인하여 첫 글자 읽기
    if [[ -n "$ZSH_VERSION" ]]; then
        read -k 1 -rs temp_key
    else
        read -rn 1 -rs temp_key
    fi

    KEY="$temp_key"

    # 만약 ESC 키($'\e')가 들어왔다면 (화살표 키 시작 신호)
    if [[ "$temp_key" == $'\e' ]]; then
        # 소수점 타임아웃 에러를 피하기 위해 1초로 설정하되,
        # 실제로는 데이터가 바로 들어오므로 렉이 거의 없습니다.
        if [[ -n "$ZSH_VERSION" ]]; then
            read -k 2 -t 0.1 -rs rest
        else
            # 구식 Bash를 위해 -t 옵션을 정수 1로 쓰거나 아예 짧게 줌
            read -rn 2 -t 1 -rs rest
        fi
        KEY+="$rest"
    fi
}

# ── 커밋 타입 데이터 ───────────────────────────────────────────
TYPES=(feat    fix    refactor  style              docs   test      chore         perf)
ICONS=("✨"   "🐛"  "♻️ "    "💅"              "📝"  "🧪"     "🔧"          "⚡")
DESCS=(
    "새로운 기능 추가"
    "버그 수정"
    "코드 리팩토링"
    "포맷팅 등 (로직 변경 없음)"
    "문서 수정"
    "테스트 추가/수정"
    "빌드, 패키지 등 기타"
    "성능 개선"
)

# ── 타입 메뉴 렌더 ─────────────────────────────────────────────
render_types() {
    local cur=$1
    for i in "${!TYPES[@]}"; do
        printf '\033[2K\r'
        if [ "$i" -eq "$cur" ]; then
            printf "  ${CYN}${BOLD}> ${ICONS[$i]} %-12s${RST}  ${GRY}${DESCS[$i]}${RST}\n" "${TYPES[$i]}"
        else
            printf "  ${DIM}  ${ICONS[$i]} %-12s${RST}  ${GRY}${DESCS[$i]}${RST}\n" "${TYPES[$i]}"
        fi
    done
}

# ── 일반 목록 렌더 ─────────────────────────────────────────────
render_items() {
    local cur=$1; shift
    local items=("$@")
    for i in "${!items[@]}"; do
        printf '\033[2K\r'
        if [ "$i" -eq "$cur" ]; then
            printf "  ${CYN}${BOLD}> %s${RST}\n" "${items[$i]}"
        else
            printf "  ${DIM}  %s${RST}\n" "${items[$i]}"
        fi
    done
}

# ── 커밋 타입 선택 ─────────────────────────────────────────────
select_type() {
    local idx=0
    local total=${#TYPES[@]}

    _hide_cursor
    printf "${BOLD}${CYN}?${RST} ${BOLD}커밋 타입 선택${RST}  ${GRY}(up/down 이동, Enter 선택, Ctrl+C 취소)${RST}\n"

    # ─── 이 부분을 수정 ───
    render_types $idx

    while true; do
        read_key
        case "$KEY" in
            $'\x1b[A'|$'\e[A') idx=$(( (idx - 1 + total) % total )) ;;
            $'\x1b[B'|$'\e[B') idx=$(( (idx + 1) % total )) ;;
            '')         break ;;
            $'\x03')    _show_cursor; printf '\n'; exit 0 ;;
        esac

        # 커서를 메뉴 줄 수만큼 위로 올림 (TYPES가 8개이므로 8줄 위로)
        printf "\033[%dA" "$total"
        render_types $idx
    done
    # ──────────────────────

    _restore_cursor # (이건 이제 필요 없지만 둬도 무방합니다)
    printf '\033[J'
    printf "${CYN}${BOLD}✔${RST} ${BOLD}타입${RST}  ${GRN}${BOLD}${ICONS[$idx]} ${TYPES[$idx]}${RST}\n"
    COMMIT_TYPE="${TYPES[$idx]}"
}

# ── Yes/No 확인 선택 ───────────────────────────────────────────
confirm_commit() {
    local idx=0
    local opts=("[Y] 예, 커밋합니다" "[N] 아니요, 취소합니다")
    local total=2

    _hide_cursor
    printf "${BOLD}${CYN}?${RST} ${BOLD}이대로 커밋할까요?${RST}  ${GRY}(up/down, Enter)${RST}\n"

    # 처음 한 번 메뉴를 그립니다.
    render_items $idx "${opts[@]}"

    while true; do
        read_key
        case "$KEY" in
            $'\x1b[A'|$'\e[A') idx=$(( (idx - 1 + total) % total )) ;;
            $'\x1b[B'|$'\e[B') idx=$(( (idx + 1) % total )) ;;
            '')         break ;;
            $'\x03')    _show_cursor; printf '\n'; exit 0 ;;
        esac

        # 핵심: 출력했던 옵션 개수(2줄)만큼 커서를 위로 올립니다.
        printf "\033[%dA" "$total"
        # 그리고 그 자리에 다시 그립니다.
        render_items $idx "${opts[@]}"
    done

    # 선택이 완료되면 출력했던 메뉴를 지우고 깔끔하게 결과를 보여줍니다.
    printf "\033[%dA\033[J" "$total"

    if [ "$idx" -eq 0 ]; then
        CONFIRM_RESULT="yes"
        printf "${CYN}${BOLD}✔${RST} ${GRN}커밋 진행${RST}\n"
    else
        CONFIRM_RESULT="no"
        printf "${CYN}${BOLD}✔${RST} ${YLW}취소${RST}\n"
    fi
}

# ── 텍스트 입력 프롬프트 ───────────────────────────────────────
prompt_input() {
    local varname=$1
    local question=$2
    local is_required=${3:-""}
    local val=""

    _show_cursor
    while true; do
        # %s 대신 %b를 사용하여 ANSI 색상 코드가 동작하도록 수정
        if [ -n "$is_required" ]; then
            printf "${BOLD}${CYN}?${RST} ${BOLD}%b${RST} ${RED}(필수)${RST} " "$question"
        else
            printf "${BOLD}${CYN}?${RST} ${BOLD}%b${RST} ${GRY}(Enter로 건너뜀)${RST} " "$question"
        fi

        IFS= read -r val

        if [ -n "$is_required" ] && [ -z "$val" ]; then
            printf "  ${RED}X 값을 입력해주세요.${RST}\n"
            continue
        fi
        break
    done
    printf -v "$varname" '%s' "$val"
}

# ── MAIN ──────────────────────────────────────────────────────
main() {
    if ! git rev-parse --git-dir > /dev/null 2>&1; then
        printf "${RED}X git 저장소가 아닙니다.${RST}\n"
        exit 1
    fi

    printf "\n${BOLD}  git commit wizard${RST}  ${GRY}-- 커밋 메시지를 작성합니다${RST}\n\n"

    select_type

    prompt_input COMMIT_SCOPE "스코프  ${GRY}예: auth, api, ui${RST}"

    local scope_part=""
    [ -n "$COMMIT_SCOPE" ] && scope_part="(${COMMIT_SCOPE})"
    prompt_input COMMIT_TITLE "제목  ${GRY}[${COMMIT_TYPE}${scope_part}: ...]${RST}" required

    prompt_input COMMIT_DESC "상세 설명"

    local headline="${COMMIT_TYPE}${scope_part}: ${COMMIT_TITLE}"
    local full_msg="$headline"
    [ -n "$COMMIT_DESC" ] && full_msg="$(printf '%s\n\n%s' "$headline" "$COMMIT_DESC")"

    printf "\n${GRY}------------------------------------${RST}\n"
    printf "  ${BOLD}커밋 메시지 미리보기${RST}\n"
    printf "${GRY}------------------------------------${RST}\n"
    printf "  ${GRN}${BOLD}%s${RST}\n" "$headline"
    [ -n "$COMMIT_DESC" ] && printf "\n  ${DIM}%s${RST}\n" "$COMMIT_DESC"
    printf "${GRY}------------------------------------${RST}\n\n"

    confirm_commit

    if [ "$CONFIRM_RESULT" != "yes" ]; then
        printf "\n${YLW}커밋이 취소되었습니다.${RST}\n\n"
        exit 0
    fi

    printf "\n"
    if git commit -m "$full_msg" "$@"; then
        printf "\n${GRN}${BOLD}✔ 커밋 완료!${RST}\n\n"
    else
        printf "\n${RED}X 커밋 실패. 위 git 출력을 확인하세요.${RST}\n\n"
        exit 1
    fi
}

main "$@"
