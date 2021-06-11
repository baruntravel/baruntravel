import React, { useCallback } from "react";
import styles from "./wishPlaceCard.module.css";

const WishPlaceCard = ({ item, onAddCart, onDeleteCart, index }) => {
  const onAddCartFromCard = useCallback(() => {
    onAddCart(item);
  }, [item, onAddCart]);
  const onDeleteCartFromCard = useCallback(() => {
    onDeleteCart(item.id);
  }, [item, onDeleteCart]);
  return (
    <div className={styles.WishPlaceCard} onClick={onAddCartFromCard}>
      {item.image ? (
        <img className={styles.image} src={item.image} alt="place img" />
      ) : (
        <div className={styles.imageAlt} />
      )}
      <div className={styles.nameBox}>
        <span className={styles.name}>{item.name}</span>
      </div>
      {index !== 0 && (
        <div className={styles.indexBox}>
          <span className={styles.index}>{index}</span>
        </div>
      )}
    </div>
  );
};

export default WishPlaceCard;
