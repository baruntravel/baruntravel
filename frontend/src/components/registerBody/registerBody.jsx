import React, { useRef, useState } from "react";
import styles from "./registerBody.module.css";
import { onRegister } from "../../api/authAPI";
import { Spin } from "antd";

const RegisterBody = ({ onClickLogin }) => {
  const formRef = useRef();
  const nicknameRef = useRef();
  const emailRef = useRef();
  const passwordRef = useRef();
  const [loading, setLoading] = useState(false);
  const [registerFail, setRegisterFail] = useState(false);

  const handleSubmit = (event) => {
    event.preventDefault();
    setLoading(true);
    const avatar = "";
    const formData = new FormData();
    formData.append("email", emailRef.current.value || "");
    formData.append("name", nicknameRef.current.value || "");
    formData.append("password", passwordRef.current.value || "");
    const result = onRegister(formData);
    // 회원 가입 api호출
    setLoading(false);
    if (result) {
      formRef.current.reset();
      onClickLogin();
    } else {
      setRegisterFail(true);
    }
  };
  return (
    <form ref={formRef} className={styles.RegisterBody} onSubmit={handleSubmit}>
      {registerFail && (
        <div className={styles.registerFailBox}>
          <span className={styles.loginFail}>~~ 사유로 실패하였습니다</span>
        </div>
      )}
      <input
        ref={nicknameRef}
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
        placeholder="비밀번호 (6자리 이상 입력하세요.)"
        required
      />

      <button className={styles.registerBtn}>회원가입</button>
      <div className={styles.authManage}>
        <label className={styles.exist}>이미 가입하셨나요?</label>
        <span className={styles.loginOrRegister} onClick={onClickLogin}>
          로그인
        </span>
      </div>
      {loading && (
        <div className={styles.loadingBody}>
          <Spin tip="Registering..."></Spin>
        </div>
      )}
    </form>
  );
};

export default RegisterBody;
