import React from "react";
import WishListCard from "../wishListCard/wishListCard";
import WishListHeader from "../wishListHeader/wishListHeader";
import styles from "./wishList.module.css";

const WishList = (props) => {
  const test = [1, 2, 3, 4, 5, 65, 7, 1];

  return (
    <div className={styles.WishList}>
      <WishListHeader isClicked={false} />
      <ul className={styles.listContainer}>
        {test.map((item) => (
          <li className={styles.cardContainer}>
            <WishListCard />
          </li>
        ))}
      </ul>
    </div>
  );
};

export default WishList;
