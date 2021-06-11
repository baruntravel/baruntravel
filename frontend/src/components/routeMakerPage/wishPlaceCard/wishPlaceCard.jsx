import React, { useCallback } from "react";
import styles from "./wishPlaceCard.module.css";

const WishPlaceCard = ({ item, onAddCart, index }) => {
  const onAddCartFromCard = useCallback(() => {
    onAddCart(item);
  }, [item, onAddCart]);

  return (
    <div className={styles.WishPlaceCard} onClick={onAddCartFromCard}>
      <img className={styles.image} src={item.image} />
      <div className={styles.nameBox}>
        <span className={styles.name}>{item.name}</span>
      </div>
      {index && (
        <div className={styles.indexBox}>
          <span className={styles.index}>{index}</span>
        </div>
      )}
    </div>
  );
};

export default WishPlaceCard;
