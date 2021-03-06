import React, { useCallback, useEffect, useRef } from "react";
import VerticalWishList from "../../common/verticalWishList/verticalWishList";
import Portal from "../portal";
import styles from "./wishListPortal.module.css";
const WishListPortal = ({ onClose, onAddItem }) => {
  const componentRef = useRef();
  const portalRef = useRef();

  const onCloseWithAnimation = useCallback(() => {
    portalRef.current.classList.add("slide-bottom-portal");
    setTimeout(() => {
      onClose();
    }, 500);
  }, [onClose]);

  const onHandleClose = useCallback(
    (event) => {
      if (
        portalRef.current &&
        !portalRef.current.contains(event.target) &&
        event.target.nodeName !== "SPAN" // span을 클릭할 때 리렌더링되는 이유로 예외케이스 추가
      ) {
        onCloseWithAnimation();
      }
    },
    [onCloseWithAnimation]
  );
  useEffect(() => {
    componentRef.current.addEventListener("click", onHandleClose);
  }, [onHandleClose]);

  return (
    <Portal>
      <div ref={componentRef} className={styles.WishListPortal}>
        <div ref={portalRef} className={styles.body}>
          <VerticalWishList onClose={onCloseWithAnimation} onAddItem={onAddItem} />
        </div>
      </div>
    </Portal>
  );
};

export default WishListPortal;
