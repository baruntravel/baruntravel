import React from "react";
import styles from "./wishListHeader.module.css";

const WishListHeader = ({ isClicked }) => {
  return (
    <header className={styles.WishListHeader}>
      {isClicked ? <span>찜 목록</span> : <span>아이콘 + 제목</span>}
    </header>
  );
};

export default WishListHeader;
