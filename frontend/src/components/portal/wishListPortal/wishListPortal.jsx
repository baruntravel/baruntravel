import React, { useEffect, useRef } from "react";
import VerticalWishList from "../../common/verticalWishList/verticalWishList";
import Portal from "../portal";
import styles from "./wishListPortal.module.css";
const WishListPortal = ({ onClose }) => {
  const portalRef = useRef();
  const onHandleClose = (event) => {
    if (
      portalRef.current &&
      !portalRef.current.contains(event.target) &&
      event.target.nodeName !== "SPAN" // span을 클릭할 때 리렌더링되는 이유로 예외케이스 추가
    ) {
      portalRef.current.classList.add("slide-bottom-portal");
      setTimeout(() => {
        onClose();
      }, 500);
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
      <div className={styles.WishListPortal}>
        <div ref={portalRef} className={styles.body}>
          <VerticalWishList onClose={onClose} />
        </div>
      </div>
    </Portal>
  );
};

export default WishListPortal;
