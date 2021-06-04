import React from "react";
import styles from "./wishListHeader.module.css";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faChevronLeft } from "@fortawesome/free-solid-svg-icons";

const WishListHeader = ({ isClicked, onClose }) => {
  return (
    <header className={styles.WishListHeader}>
      {isClicked ? (
        <div className={styles.header} onClick={onClose}>
          <FontAwesomeIcon icon={faChevronLeft} className={styles.backIcon} />
          <span className={styles.name}>찜 목록 이름!</span>
        </div>
      ) : (
        <span className={styles.title}>찜 목록</span>
      )}
    </header>
  );
};

export default WishListHeader;
