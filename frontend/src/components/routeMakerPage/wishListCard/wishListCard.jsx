import React from "react";
import styles from "./wishListCard.module.css";

const WishListCard = ({ item }) => {
  const src =
    "https://i.pinimg.com/564x/d7/ec/75/d7ec75c9e68873ee75b734ac4ab09ced.jpg";
  return (
    <div className={styles.WishListCard}>
      <img className={styles.image} src={src} />
      <div className={styles.nameBox}>
        <span className={styles.name}>{item.name}</span>
      </div>
    </div>
  );
};

export default WishListCard;
