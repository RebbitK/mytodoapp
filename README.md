# API 명세서
| 기능 | Method    | URL | request              |  response |
|----------|---------|-----|---------------------|---------------|
| 회원가입 | POST | /api/user/signup  | {"username":"아이디",  <br/> "password":"1234"}    | 회원가입 성공 여부와 상태 코드 |
| 로그인  | POST | /api/user/login  |  {"username":"아이디",  <br/> "password":"1234"}   | 로그인 성공 여부와 상태 코드 + jwt토큰 생성 |
| 할일카드 생성 | POST | /api/schedules/{id}|{"title":"제목", <br/> "text":"내용"} | 할일 카드 생성
| 전체 할일카드 조회  | GET  | /api/schedules  |     |  할일카드들의 목록 조회 작성일기준 내림차순 |
| 자신의 할일카드 조회 | GET| /api/my-schedules |  | 자신이 작성한 할일카드 목록 조회 |
| 선택한 할일카드 조회   | GET |/api/schedules/{id} | |선택한 할일카드 조회 |
| 할일카드 삭제 | DELETE | /api/schedules/{id} | | 유저가 작성한 할일카드가 맞으면 삭제
| 할일카드 수정 | PUT | /api/schedules/{id}| {"title":"제목", <br/> "text":"내용"} | 선택한 할일카드 생성
| 할일카드 완료 | GET | /api/schedules-complee/{id} | | 선택한 할일카드 완료처리
| 댓글 작성 | POST | /api/comments/{id} | {"comment":"댓글내용"} | 선택한 할일카드에 댓글 작성
| 댓글 수정 | PUT |  /api/comments/{id}| {"comment":"댓글내용"} | 선택한 댓글 수정
| 댓글 삭제 | DELETE | /api/comments/{id}| | 선택한 댓글 삭제

# ERD
![다이어그램4](https://github.com/RebbitK/mytodoapp/assets/154823447/0723915c-e176-4686-9980-f4d1c8afd35f)
