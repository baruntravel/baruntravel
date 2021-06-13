import React, { useCallback, useEffect, useRef, useState } from "react";
import LoginBody from "../../components/portal/loginBody/loginBody";
import Portal from "../../components/portal/portal";
import RegisterBody from "../../components/portal/registerBody/registerBody";
import styles from "./portalAuth.module.css";

const PortalAuth = ({ onClose, handleLoading }) => {
  const portalRef = useRef();
  const [loginClicked, setLoginClicked] = useState(true);
  const onClickChange = () => {
    setLoginClicked(!loginClicked);
  };
  const onHandleClose = (event) => {
    if (
      portalRef.current &&
      !portalRef.current.contains(event.target) &&
      event.target.nodeName !== "SPAN" // span을 클릭할 때 리렌더링되는 이유로 예외케이스 추가
    ) {
      onClose();
    }
  };
  useEffect(() => {
    window.addEventListener("click", onHandleClose);
    return () => {
      window.removeEventListener("click", onHandleClose);
    };
  }, [onHandleClose]);
  return (
    <Portal>
      <div className={styles.PortalAuth}>
        <div ref={portalRef} className={styles.authBody}>
          <div className={styles.header}>
            <span className={styles.title}>바른 여행 길잡이</span>
          </div>
          {loginClicked ? (
            <LoginBody onClickRegister={onClickChange} onClose={onClose} />
          ) : (
            <RegisterBody onClickLogin={onClickChange} />
          )}
        </div>
      </div>
    </Portal>
  );
};

export default PortalAuth;
