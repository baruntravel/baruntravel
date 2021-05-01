import React, { useCallback, useRef, useState } from "react";
import styles from "./loginBody.module.css";
import { onLogin } from "../../api/authAPI";
import { Spin } from "antd";
import { useRecoilState } from "recoil";
import { userState, userCart } from "../../recoil/userState";
import { onReceiveCart } from "../../api/cartAPI";
import useInput from "../../hooks/useInput";
const LoginBody = ({ onClickRegister, onClose }) => {
  const formRef = useRef();
  const [email, onHandleEmail] = useInput();
  const [password, onHandlePassword] = useInput();
  const [loading, setLoading] = useState(false);
  const [userStates, setUserStates] = useRecoilState(userState);
  const [shoppingItems, setShoppingItems] = useRecoilState(userCart);
  const [loginFail, setLoginFail] = useState(false);

  const updateUserLogin = (isLogin, email, name) => {
    setUserStates((prev) => {
      const updated = { ...prev };
      updated["isLogin"] = isLogin;
      updated["email"] = email;
      updated["name"] = name;
      return updated;
    });
  };

  const handleSubmit = useCallback(
    (event) => {
      event.preventDefault();
      setLoading(true);
      const [isLogin, userEmail, userName] = onLogin(email, password);
      updateUserLogin(isLogin, userEmail, userName);
      setLoading(false);
      if (isLogin) {
        // const cartItems = onReceiveCart();
        // if (cartItems) {
        //   setShoppingItems(cartItems);
        // } else {
        //   //
        // }
        formRef.current.reset();
        onClose();
      } else {
        setLoginFail(true);
      }
    },
    [email, onClose, password, setShoppingItems, updateUserLogin]
  );

  return (
    <form ref={formRef} className={styles.loginForm} onSubmit={handleSubmit}>
      {loginFail && (
        <div className={styles.loginFailBox}>
          <span className={styles.loginFail}>
            죄송합니다. 로그인에 실패했습니다.
          </span>
          <span className={styles.loginFail2}>올바르게 입력해주세요.</span>
        </div>
      )}
      <input
        type="email"
        className={styles.inputBar}
        placeholder="이메일"
        onChange={onHandleEmail}
        required
      />
      <input
        type="password"
        className={styles.inputBar}
        placeholder="비밀번호"
        onChange={onHandlePassword}
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
