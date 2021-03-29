import Modal from "antd/lib/modal/Modal";
import React, { useEffect, useRef } from "react";
import LoginBody from "../../components/loginBody/loginBody";
import RegisterBody from "../../components/registerBody/registerBody";
import styles from "./portalAuth.module.css";

const PortalAuth = ({
  loginClicked,
  onClose,
  onClickChange,
  handleLoading,
}) => {
  const portalRef = useRef();

  const handleClose = (event) => {
    if (
      portalRef.current &&
      !portalRef.current.contains(event.target) &&
      event.target.nodeName !== "SPAN" // span을 클릭할 때 리렌더링되는 이유로 예외케이스 추가
    ) {
      onClose();
    }
  };
  useEffect(() => {
    window.addEventListener("click", handleClose);
    return () => {
      window.removeEventListener("click", handleClose);
    };
  }, [handleClose]);
  return (
    <div className={styles.PortalAuth}>
      <div ref={portalRef} className={styles.authBody}>
        {loginClicked ? (
          <LoginBody onClickRegister={onClickChange} />
        ) : (
          <RegisterBody onClickLogin={onClickChange} />
        )}
      </div>
    </div>
  );
};

export default PortalAuth;
