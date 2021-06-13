import React from "react";
import VerticalWishList from "../../common/verticalWishList/verticalWishList";
import Portal from "../portal";
import styles from "./wishListPortal.module.css";
const WishListPortal = (props) => {
  return (
    <Portal>
      <div className={styles.WishListPortal}>
        <div className={styles.body}>
          <VerticalWishList />
        </div>
      </div>
    </Portal>
  );
};

export default WishListPortal;
