import React from "react";
import styles from "./cardContainer.module.css";
const CardContainer = ({ handleOpen }) => {
  return (
    <div>
      <div className={styles.cardsContainer}>
        <div className={styles.cardBox} onClick={handleOpen}>
          <div className={styles.imgBox}>
            <div className={styles.img1}>img1</div>
            <div className={styles.img2}>img2</div>
            <div className={styles.img3}>img3</div>
          </div>
          <div className={styles.wishListTitle}>제주도 갈만한 곳들</div>
        </div>

        <div className={styles.cardBox} onClick={handleOpen}>
          <div className={styles.imgBox}></div>
          <div className={styles.wishListTitle}>서울 핫플레이스</div>
        </div>
      </div>
    </div>
  );
};

export default CardContainer;
