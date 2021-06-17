import React, { useCallback, useEffect, useRef } from "react";
import Portal from "../../portal/portal";
import styles from "./resetConfirm.module.css";

const ResetConfirm = ({ onClose, onReset }) => {
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
  const handleReset = useCallback(() => {
    onReset();
    onClose();
  }, [onReset, onClose]);
  return (
    <Portal>
      <div className={styles.ResetConfirm}>
        <div ref={portalRef} className={styles.confirmBox}>
          <div className={styles.textBox}>
            <p className={styles.alertText}>정말 카트를 정리하시겠습니까?</p>
          </div>
          <div className={styles.buttonBox}>
            <div className={styles.closeBtnBox} onClick={onClose}>
              <span>취소</span>
            </div>
            <div className={styles.confirmBtnBox} onClick={handleReset}>
              <span>초기화</span>
            </div>
          </div>
        </div>
      </div>
    </Portal>
  );
};

export default ResetConfirm;
