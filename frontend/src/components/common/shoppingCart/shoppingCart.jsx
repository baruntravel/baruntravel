import React from "react";
import styles from "./shoppingCart.module.css";
import ShoppingItem from "./shoppingItem/shoppingItem";

const ShoppingCart = ({ items, setConfirmPortalTrue }) => {
  const test = [1, 2, 3, 4];
  return (
    <div className={styles.ShoppingCart}>
      <div className={styles.list}>
        {test.map((i, index) => (
          <div key={index} className={styles.itemContainer}>
            <ShoppingItem
              key={index}
              setConfirmPortalTrue={setConfirmPortalTrue}
            />
          </div>
        ))}
      </div>
      <div className={styles.bottom}>
        <button className={styles.addRouteBtn}>경로 저장하기</button>
        <span>바른 여행 길잡이</span>
      </div>
    </div>
  );
};

export default ShoppingCart;
