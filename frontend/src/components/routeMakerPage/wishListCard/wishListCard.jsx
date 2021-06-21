import React from "react";
import styles from "./wishListCard.module.css";

const WishListCard = ({ item }) => {
  const src = item.images.length > 0 ? item.images[0].url : null;
  return (
    <div className={styles.WishListCard}>
      {src ? <img className={styles.image} src={src} alt="thumbnail" /> : <div className={styles.imageAlt} />}
      <div className={styles.nameBox}>
        <span className={styles.name}>{item.name}</span>
      </div>
    </div>
  );
};

export default WishListCard;
