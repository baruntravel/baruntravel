import React, { useRef } from "react";
import styles from "./registerBody.module.css";

const RegisterBody = ({ onClickLogin }) => {
  const formRef = useRef();
  const nickNameRef = useRef();
  const emailRef = useRef();
  const passwordRef = useRef();

  const handleSubmit = (event) => {
    event.preventDefault();
    const nickName = nickNameRef.current.value || "";
    const email = emailRef.current.value || "";
    const password = passwordRef.current.value || "";

    // 회원 가입 api호출
    formRef.current.reset();
  };
  return (
    <form ref={formRef} className={styles.RegisterBody} onSubmit={handleSubmit}>
      <input
        ref={nickNameRef}
        type="text"
        className={styles.inputBar}
        placeholder="닉네임"
        required
      />
      <input
        ref={emailRef}
        type="email"
        className={styles.inputBar}
        placeholder="이메일"
        required
      />
      <input
        ref={passwordRef}
        type="password"
        className={styles.inputBar}
        placeholder="비밀번호 (6자리 이상 입력해주세요.)"
        required
      />

      <button className={styles.registerBtn}>회원가입</button>
      <div className={styles.authManage}>
        <label className={styles.exist}>이미 가입하셨나요?</label>
        <span className={styles.loginOrRegister} onClick={onClickLogin}>
          로그인
        </span>
      </div>
    </form>
  );
};

export default RegisterBody;
