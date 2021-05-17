import React, { useEffect, useRef } from "react";
import Portal from "../../portal/portal";
import styles from "./deleteConfirm.module.css";
const DeleteConfirm = ({ deleteItemId, onDeleteItem, onClose }) => {
  const portalRef = useRef();
  const deleteItem = () => {
    onDeleteItem(deleteItemId);
    onClose();
  };
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
      <div className={styles.DeleteConfirm}>
        <div ref={portalRef} className={styles.confirmBox}>
          <div className={styles.textBox}>
            <p className={styles.deleteText}>정말 삭제하시겠습니까?</p>
            <p className={styles.confirmText}>삭제하시면 복구할 수 없습니다.</p>
            <p className={styles.confirmText}>삭제하시겠습니까?</p>
          </div>
          <div className={styles.buttonBox}>
            <div className={styles.closeBtnBox}>
              <span onClick={onClose}>취소</span>
            </div>
            <div className={styles.confirmBtnBox}>
              <span onClick={deleteItem}>확인</span>
            </div>
          </div>
        </div>
      </div>
    </Portal>
  );
};

export default DeleteConfirm;
