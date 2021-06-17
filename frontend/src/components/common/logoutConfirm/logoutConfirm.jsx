import React, { useCallback, useEffect, useRef } from "react";
import styles from "./logoutConfirm.module.css";
import Portal from "../../portal/portal";
const LogoutConfirm = ({ onClose, onLogout }) => {
  const portalRef = useRef();
  const handleLogout = useCallback(() => {
    onLogout();
    onClose();
  }, [onClose, onLogout]);
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
    <Portal>
      <div className={styles.LogoutConfirm}>
        <div ref={portalRef} className={styles.confirmBox}>
          <div className={styles.textBox}>
            <p className={styles.alertText}>정말 로그아웃하시겠습니까?</p>
          </div>
          <div className={styles.buttonBox}>
            <div className={styles.closeBtnBox} onClick={onClose}>
              <span>취소</span>
            </div>
            <div className={styles.confirmBtnBox} onClick={handleLogout}>
              <span>로그아웃</span>
            </div>
          </div>
        </div>
      </div>
    </Portal>
  );
};

export default LogoutConfirm;
