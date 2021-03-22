import React, { useCallback, useRef, useState } from "react";
import styles from "./loginBody.module.css";
import { onLogin } from "../../api/authAPI";
import { Spin } from "antd";

const LoginBody = ({ onClickRegister }) => {
  const formRef = useRef();
  const emailRef = useRef();
  const passwordRef = useRef();
  const [loading, setLoading] = useState(false);

  const handleSubmit = async (event) => {
    event.preventDefault();
    setLoading(true);
    const email = emailRef.current.value;
    const password = passwordRef.current.value;
    const result = await onLogin(email, password);
    setLoading(false);
    formRef.current.reset();
  };

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
      {loading && (
        <div className={styles.loadingBody}>
          <Spin tip="Logging in..."></Spin>
        </div>
      )}
    </form>
  );
};

export default LoginBody;
