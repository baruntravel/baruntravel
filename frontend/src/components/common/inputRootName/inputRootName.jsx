import React, { useRef } from "react";
import Portal from "../../portal/portal";
import styles from "./inputRootName.module.css";
const InputRootName = ({ onClose, onSaveRoute }) => {
  const portalRef = useRef();
  const inputRef = useRef();
  const handleSave = () => {
    // onSaveRoute();
    console.log(inputRef.current.value);
    onClose();
  };
  return (
    <Portal>
      <div className={styles.InputRootName}>
        <div ref={portalRef} className={styles.confirmBox}>
          <form className={styles.inputBox}>
            <input
              ref={inputRef}
              className={styles.input}
              placeholder="경로 이름을 적어주세요."
            />
          </form>
          <div className={styles.buttonBox}>
            <div className={styles.closeBtnBox}>
              <span onClick={onClose}>취소</span>
            </div>
            <div className={styles.confirmBtnBox}>
              <span onClick={handleSave}>확인</span>
            </div>
          </div>
        </div>
      </div>
    </Portal>
  );
};

export default InputRootName;
