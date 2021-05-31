import React, { useEffect, useRef } from "react";
import Portal from "../../portal/portal";
import styles from "./addSuccessConfirm.module.css";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faHeart } from "@fortawesome/free-solid-svg-icons";

const AddSuccessConfirm = ({ onClose, name }) => {
  const messageRef = useRef();
  useEffect(() => {
    messageRef.current.classList.remove("fade-in");
    messageRef.current.classList.add("fade-out");
    setTimeout(() => {
      onClose();
    }, 1500);
  }, [onClose]);
  return (
    <Portal>
      <div className={styles.AddSuccessConfirm}>
        <div ref={messageRef} className={`${styles.messageBox} fadein`}>
          <div className={styles.iconBox}>
            <FontAwesomeIcon icon={faHeart} size="lg" color="red" />
          </div>
          <div className={styles.message}>
            <span>위시리스트에 저장완료</span>
          </div>
        </div>
      </div>
    </Portal>
  );
};

export default AddSuccessConfirm;
