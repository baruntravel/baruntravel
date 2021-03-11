import React, { useCallback, useRef } from "react";
import styles from "./loginBody.module.css";

const LoginBody = ({ onClickRegister }) => {
  const formRef = useRef();
  const emailRef = useRef();
  const passwordRef = useRef();
  // const successLogin = () => { }

  const handleSubmit = useCallback((event) => {
    event.preventDefault();
    const email = emailRef.current.value;
    const password = passwordRef.current.value;
    // axios를 통해서 email, password를 서버에 보낸다.

    formRef.current.reset();
  }, []);

  return (
    <form ref={formRef} className={styles.loginForm} onSubmit={handleSubmit}>
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
        placeholder="비밀번호"
        required
      />
      <button className={styles.loginBtn}>로그인</button>
      <div className={styles.authManage}>
        <h2 className={styles.lostPassword}>혹시 비밀번호를 잊어버리셨나요?</h2>
        <label className={styles.noAccount}>계정이 없으신가요?</label>
        <span className={styles.loginOrRegister} onClick={onClickRegister}>
          회원가입
        </span>
      </div>
    </form>
  );
};

export default LoginBody;
