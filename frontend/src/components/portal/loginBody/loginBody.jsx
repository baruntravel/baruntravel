import React, { useCallback, useRef, useState } from "react";
import styles from "./loginBody.module.css";
import { onLogin } from "../../../api/authAPI";
import { useRecoilState } from "recoil";
import useInput from "../../../hooks/useInput";
import { userState, userWishList } from "../../../recoil/userState";
import Loading from "../../common/loading/loading";
import { onReceiveWishList } from "../../../api/wishListAPI";

const LoginBody = ({ onClickRegister, onClose }) => {
  const formRef = useRef();
  const [email, onHandleEmail] = useInput();
  const [password, onHandlePassword] = useInput();
  const [loading, setLoading] = useState(false);
  const [userStates, setUserStates] = useRecoilState(userState);
  const [userWishListItems, setUserWishListItems] = useRecoilState(
    userWishList
  );
  const [loginFail, setLoginFail] = useState(false);
  const updateUserLogin = useCallback(
    (isLogin, email, name, avatar) => {
      setUserStates((prev) => {
        const updated = { ...prev, isLogin, email, name, avatar };
        return updated;
      });
    },
    [setUserStates]
  );

  const handleSubmit = useCallback(
    async (event) => {
      event.preventDefault();
      setLoading(true);
      const [isLogin, userEmail, userName, avatar] = await onLogin(
        email,
        password
      );
      updateUserLogin(isLogin, userEmail, userName, avatar);
      setLoading(false);
      if (isLogin) {
        const myWishList = await onReceiveWishList();
        if (myWishList) setUserWishListItems(myWishList);

        formRef.current.reset();
        onClose();
      } else {
        setLoginFail(true);
      }
    },
    [email, onClose, password, setUserWishListItems, updateUserLogin]
  );

  if (loading) {
    return <Loading />;
  }

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
    </form>
  );
};

export default LoginBody;
