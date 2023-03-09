## 📔 프로젝트 이름

- 인스타그램_클론코딩

---

# 💎 클론코딩 프로젝트 소개

- 인스타그램에 있는 기능들을 하나하나 구현하는 방식으로 하였습니다.

---

## 📆 개발기간

2022.03.03 ~ 2022.03.09
 
---

## 🛠️ 기술스택
    
- BE
    
    <img src="https://img.shields.io/badge/Spring Boot-6DB33F?style=for-the-badge&logo=Spring Boot&logoColor=white">
    <img src="https://img.shields.io/badge/Spring Security-6DB33F?style=for-the-badge&logo=Spring Security&logoColor=white">
    <img src="https://img.shields.io/badge/Gradle-02303A?style=for-the-badge&logo=Gradle&logoColor=white">
    <img src="https://img.shields.io/badge/MySQL-4479A1?style=for-the-badge&logo=MySQL&logoColor=white">
    <img src="https://img.shields.io/badge/Amazon EC2-FF9900?style=for-the-badge&logo=Amazon EC2&logoColor=white">
    <img src="https://img.shields.io/badge/Amazon S3-569A31?style=for-the-badge&logo=Amazon S3&logoColor=white">
    <img src="https://img.shields.io/badge/Swagger-85EA2D?style=for-the-badge&logo=Swagger&logoColor=black">
    
    
---   

## 💡 구현기능
   
   - 로그인 기능
     - 유효성 검사를 통과한 ID, PW인 경우만 회원가입이 가능하다.
     - JWT 토큰을 이용하여 Spring Secuity로 인증/인가를 구현한다.
   - 게시물 등록
     - 로그인을 하면 게시물을 등록할 수 있다.
     - 본인이 작성한 게시물만 수정/삭제가 가능하다.
   - 게시물 조회
     - 모든 사용자가 작성한 게시물를 조회할 수 있다.
     - 작성일자, 좋아요 개수 순으로 정렬할 수 있다.
   - 마이페이지 조회
     - 내가 작성한 게시물을 볼 수 있다.
   - 댓글 등록
     - 게시물에 댓글을 달 수 있다.
     - 본인이 작성한 댓글만 수정/삭제가 가능하다.
   - 좋아요 기능
     - 게시물, 댓글에 좋아요를 누를 수 있다.
     - 이미 좋아요를 누른 상태에서 다시 한번 누르면 좋아요가 취소된다.

---

## 📄 ERD

<img width="1496" alt="image" src="https://user-images.githubusercontent.com/121671967/223958067-b9b92923-5c89-4b09-8c34-4c92bc9cd08f.png">


---

## 📜 API 문서

![API 명세서](https://user-images.githubusercontent.com/121671967/223951097-ca91acbb-93e7-4db3-b2ff-bb3c3608adbb.png)
   
---

## 👥 조원 정보
|이름|  구분   |        업무파트        |           Github 주소           |
|:---:|:-----:|:------------------:|:-----------------------------:|
|이승렬|  백엔드  | 이미지, 좋아요, S3 기능, 서버 배포  |  https://github.com/LEESEUNGRYEOL |
|조민성|  백엔드  | 회원가입, 로그인, JWT TOKEN |  https://github.com/Ganpyeon  |
|홍예석|  백엔드  | 게시굴, 댓글, SWAGGER, 무한 스크롤 | https://github.com/yshong1998 |

---

## 트러블 슈팅

Issue1: querydsl을 이용한 무한 스크롤 구현 과정에서, 버전에 맞는 설정을 찾지 못 해 코드는 작성했는데 build가 되지 않았습니다.

solution1: 자바 버전 교체, 스프링 버전 교체는 소용이 없었고, 구글링해서 나오는 많은 설정을 다 시도해서 가능한 설정을 찾았습니다. 그렇게 이 설정을 적용하자 빌드는 성공했지만 실행할 때 intellij에서 분명 쿼리 클래스가 만들어졌는데 찾을 수 없는 오류가 발생했고 알아보니까 annotation processor의 문제였습니다. 다만 구글링했을 때 대부분 컴파일러의 어노테이션 프로세서 설정에서 어노테이션 처리 활성화를 키는 방식으로 해결했는데 저희는 롬복 버전을 바꾸니까 해결됐습니다. 아무래도 대부분의 포스팅에서 2.6 버전 이하의 스프링을 사용할 때의 설정이라 2.7.9 버전과는 롬복 호환 문제가 있었던 것 같습니다.

Issue2: 게시글 작성하는 과정에서 S3를 이용해 이미지도 함께 업로드할 수 있도록 했는데, 어느 순간 게시글 작성은 되는데 삭제가 되지 않았습니다.

solution2: 깃허브에 secret key와 access key가 올라갈 경우, aws에서 자체적으로 삭제 요청에 대해 block하는 기능이 있어서 발생한 문제였습니다. S3에서 저장소를 다시 생성해 해결했습니다.

Issue3: 글로벌 예외처리 과정에서 토큰 에러가 잡히지 않는 문제가 있었습니다.

solution3: websecuritycofig 클래스에서 filter에서 authentication 관련한 오류가 났을 때 바로 EntryPoint를 직접 생성한 CunstomAuthenticationEntryPoint로 재설정하여 에러를 다시 직접 만든 exception 클래스를 throw하도록 변경하는 방식으로 해결했습니다.

Issue4: 프론트에서 서버로 요청을 보낼 때 전체 게시글 조회 요청이 토큰 에러에 잡히는 문제가 있었습니다.

solution4: 인스타그램이 로그인이 되어 있지 않으면 게시글 로드가 되는 게 아니라 직접 검색한 유저의 게시글로만 접근하도록 서비스가 만들어져 있었고, 프론트 엔드는 이를 알고 이대로 만들었지만 백 엔드에서는 이를 모르고 인스타그램에 들어갔을 때 토큰 요청 없이 바로 모든 게시글이 로드되어야 한다고 생각해서 request와 response의 모습이 서로 다르게 그려져 있어서 발생한 문제였습니다. 백엔드의 서비스 로직에서 전체 게시글 조회 시 토큰을 검증하도록 로직을 수정해 해결했습니다.

Issue5: 포스트맨으로 Token 사용을 하려고 하는데 맞는 토큰을 입력해도 403forbidden 에러가 뜨는 문제가 있었습니다.

solution5 : Request Header에 token을 입력할때 이번에 REFRESH TOKEN작업을 하면서 key 값을 ACCESS_TOKEN 으로 바꿧는데 이전에 사용하던 Authorization을 사용해서 뜨는 오류여서 제대로 ACCESS_TOKEN을 넣어보니 해결되었습니다. 토큰 이름 만들때도 팀원분들과 소통을 해서 만들어야 될것 같습니다. 
