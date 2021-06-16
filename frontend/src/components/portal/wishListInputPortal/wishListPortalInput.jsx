import React, { useCallback, useEffect, useRef, useState } from "react";
import Portal from "../portal";
import styles from "./wishListPortalInput.module.css";

const WishListPortalInput = ({ onClose, addNewWishList }) => {
  const portalRef = useRef();
  let wishListName;

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
      <div className={styles.portalContainer}>
        <div ref={portalRef} className={styles.portalBody}>
          <div className={styles.header}>
            <span className={styles.title}>찜 목록 이름 정하기</span>
          </div>
          <form className={styles.form}>
            <input
              className={styles.nameInput}
              placeholder="이름"
              autoFocus
              onInput={(e) => (wishListName = e.target.value)}
            />
            <button
              onClick={(e) => {
                e.preventDefault();
                addNewWishList(wishListName);
                onClose();
              }}
              className={styles.inputButton}
            >
              새로 만들기
            </button>
          </form>
        </div>
      </div>
    </Portal>
  );
};

export default WishListPortalInput;
