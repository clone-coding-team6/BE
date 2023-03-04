package com.sparta.instagramclonebe.global.excpetion;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    //Global
    NOT_VALID_REQUEST(400, "G001", "유효하지 않은 요청입니다."),
    NOT_VALID_TOKEN(400,"G002", "유효한 토큰이 아닙니다."),
    TOKEN_NOT_FOUND(400, "G003", "토큰이 없습니다."),

    //User 관련
    USER_EMAIL_EXIST(400, "U001", "이미 존재하는 email입니다."),
    USER_NICKNAME_EXIST(400,"u002", "이미 존재하는 닉네임입니다."),
    USER_ACCOUNT_NOT_EXIST(400, "U003", "계정 정보가 존재하지 않습니다."),
    PASSWORD_MISMATCH(400, "U004", "비밀번호가 일치하지 않습니다."),

    //Post 관련
    POST_NOT_FOUND(400,"P001", "존재하지 않는 게시글입니다."),
    POST_DELETE_FAILED(400, "P002", "게시글 작성자만 삭제할 수 있습니다."),
    POST_UPDATE_FAILED(400, "P003", "게시글 작성자만 수정할 수 있습니다."),

    //Comment 관련
    COMMENT_NOT_FOUND(400,"C001", "존재하지 않는 댓글입니다."),
    COMMENT_DELETE_FAILED(400,"C002", "댓글 작성자만 삭제할 수 있습니다."),

    //Image 관련
    IMAGE_NOT_FOUND(400,"I001", "존재하지 않는 이미지입니다.");


    private final int code;
    private final String status;
    private final String message;
}
