import React, { useCallback, useEffect, useRef } from "react";
import Portal from "../../portal/portal";
import styles from "./deleteConfirm.module.css";

const DeleteConfirm = ({ onClose, onDelete }) => {
  const portalRef = useRef();
  const handleDelete = useCallback(() => {
    onDelete();
    onClose();
  }, [onClose, onDelete]);
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
    return () => window.removeEventListener("click", handleClose);
  }, [handleClose]);

  return (
    <Portal>
      <div className={styles.deleteConfirm}>
        <div ref={portalRef} className={styles.confirmBox}>
          <div className={styles.textBox}>
            <p className={styles.alertText}>찜 목록을 삭제하시겠습니까?</p>
          </div>
          <div className={styles.buttonBox}>
            <div className={styles.closeBtnBox}>
              <span onClick={onClose}>취소</span>
            </div>
            <div className={styles.confirmBtnBox}>
              <span onClick={handleDelete}>삭제하기</span>
            </div>
          </div>
        </div>
      </div>
    </Portal>
  );
};

export default DeleteConfirm;
