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
KEY=""
read_key() {
    KEY=""
    local c
    IFS= read -rsn1 KEY
    if [[ $KEY == $'\x1b' ]]; then
        while IFS= read -rsn1 -t 0.1 c; do
            KEY+="$c"
            [[ $c =~ [a-zA-Z~] ]] && break
        done
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
    _save_cursor
    render_types $idx

    while true; do
        read_key
        case "$KEY" in
            $'\x1b[A') idx=$(( (idx - 1 + total) % total )) ;;
            $'\x1b[B') idx=$(( (idx + 1) % total )) ;;
            '')         break ;;
            $'\x03')    _show_cursor; printf '\n'; exit 0 ;;
        esac
        _restore_cursor
        render_types $idx
    done

    _restore_cursor
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
    _save_cursor
    render_items $idx "${opts[@]}"

    while true; do
        read_key
        case "$KEY" in
            $'\x1b[A') idx=$(( (idx - 1 + total) % total )) ;;
            $'\x1b[B') idx=$(( (idx + 1) % total )) ;;
            '')         break ;;
            $'\x03')    _show_cursor; printf '\n'; exit 0 ;;
        esac
        _restore_cursor
        render_items $idx "${opts[@]}"
    done

    _restore_cursor
    printf '\033[J'
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
        if [ -n "$is_required" ]; then
            printf "${BOLD}${CYN}?${RST} ${BOLD}%s${RST} ${RED}(필수)${RST} " "$question"
        else
            printf "${BOLD}${CYN}?${RST} ${BOLD}%s${RST} ${GRY}(Enter로 건너뜀)${RST} " "$question"
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
