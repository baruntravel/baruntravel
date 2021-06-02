import React from "react";
import styles from "./wishPlaceCard.module.css";

const WishPlaceCard = () => {
  const index = 1;
  const src =
    "https://i.pinimg.com/474x/30/5a/21/305a216481dfaaec10fd59cf1f667652.jpg";
  return (
    <div className={styles.WishPlaceCard}>
      <img className={styles.image} src={src} />
      <div className={styles.nameBox}>
        <span className={styles.name}>place Card~</span>
      </div>
      <div className={styles.indexBox}>
        <span className={styles.index}>{index}</span>
      </div>
    </div>
  );
};

export default WishPlaceCard;
