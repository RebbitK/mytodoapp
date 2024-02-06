# API 명세서
| 기능 | Method    | URL | request              |  response |
|----------|---------|-----|---------------------|---------------|
| 회원가입 | POST | /api/user/signup  | {"username":"아이디",  <br/> "password":"1234"}    | 회원가입 성공 여부와 상태 코드 |
| 로그인  | POST | /api/user/login  |  {"username":"아이디",  <br/> "password":"1234"}   | 로그인 성공 여부와 상태 코드 + jwt토큰 생성 |
| 할일카드 생성 | POST | /api/schedules/{id}|{"title":"제목", <br/> "text":"내용"} | 할일 카드 생성
| 전체 할일카드 조회  | GET  | /api/schedules  |     |  할일카드들의 목록 조회 작성일기준 내림차순 |
| 자신의 할일카드 조회 | GET| /api/my-schedules |  | 자신이 작성한 할일카드 목록 조회 |
| 선택한 할일카드 조회   | DELETE |/api/schedules/{id}/{password} | |비밀번호가 맞다면 선택한 일정 삭제 |
| 할일카드 삭제
| 할일카드 수정
| 할일카드 완료
| 댓글 작성
| 댓글 수정
| 댓글 삭제
