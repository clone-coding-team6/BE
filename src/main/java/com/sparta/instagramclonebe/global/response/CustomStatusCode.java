package com.sparta.instagramclonebe.global.response;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CustomStatusCode {

    //Global
    NOT_VALID_REQUEST(400,  "유효하지 않은 요청입니다."),
    NOT_VALID_TOKEN(400,"유효한 토큰이 아닙니다."),
    TOKEN_NOT_FOUND(400,  "토큰이 없습니다."),

    //Success

    //User 관련
    SIGN_UP_SUCCESS(200, "회원 가입에 성공했습니다"),
    LOG_IN_SUCCESS(200, "로그인되었습니다."),

    //Post 관련
    POST_UPLOAD_SUCCESS(200, "게시글이 업로드되었습니다."),
    POST_UPDATE_SUCCESS(200, "게시글이 수정되었습니다."),
    POST_DELETE_SUCCESS(200, "게시글이 삭제되었습니다."),
    POST_TOTAL_LOAD_SUCCESS(200, "전체 게시글이 로드되었습니다."),
    POST_LOAD_SUCCESS(200, "게시글이 로드되었습니다."),

    //Comment 관련
    COMMENT_UPLOAD_SUCCESS(200,"댓글이 작성되었습니다."),
    COMMENT_DELETE_SUCCESS(200,"댓글이 삭제되었습니다."),

    //Like 관련

    POST_LIKE_SUCCESS(200, "좋아요한 게시글에 추가되었습니다."),
    POST_LIKE_CANCEL(200, "좋아요한 게시글에서 제거되었습니다."),
    COMMENT_LIKE_SUCCESS(200, "좋아요한 댓글에 추가되었습니다."),
    COMMENT_LIKE_CANCEL(200, "좋아요한 댓글에서 제거되었습니다."),


    //FAILED


    //User 관련
    USER_EMAIL_EXIST(400, "이미 존재하는 email입니다."),
    USER_NICKNAME_EXIST(400, "이미 존재하는 닉네임입니다."),
    USER_ACCOUNT_NOT_EXIST(400,  "계정 정보가 존재하지 않습니다."),
    USER_NOT_FOUND(400, "사용자가 존재하지 않습니다."),
    PASSWORD_MISMATCH(400,  "비밀번호가 일치하지 않습니다."),

    //Post 관련
    POST_NOT_FOUND(400, "존재하지 않는 게시글입니다."),
    POST_DELETE_FAILED(400,  "게시글 작성자만 삭제할 수 있습니다."),
    POST_UPDATE_FAILED(400,  "게시글 작성자만 수정할 수 있습니다."),

    //Comment 관련

    COMMENT_NOT_FOUND(400,"존재하지 않는 댓글입니다."),
    COMMENT_DELETE_FAILED(400, "댓글 작성자만 삭제할 수 있습니다."),

    //Image 관련
    IMAGE_NOT_FOUND(400, "존재하지 않는 이미지입니다.");


    private final int statusCode;
    private final String message;
}
