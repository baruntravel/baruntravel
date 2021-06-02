import React, { useCallback, useState } from "react";
import WishListCard from "../wishListCard/wishListCard";
import WishListHeader from "../wishListHeader/wishListHeader";
import WishPlaceCard from "../wishPlaceCard/wishPlaceCard";
import styles from "./wishList.module.css";

const WishList = (props) => {
  const test = [1, 2, 3, 4, 5, 65, 7, 1];
  const [isClicked, setIsClicked] = useState(false);
  const onOpenPlaceList = useCallback(() => {
    setIsClicked(true);
  }, []);
  const onClosePlaceList = useCallback(() => {
    setIsClicked(false);
  }, []);
  return (
    <div className={styles.WishList}>
      <WishListHeader isClicked={isClicked} onClose={onClosePlaceList} />
      {isClicked ? (
        <ul className={styles.listContainer}>
          {test.map((item, index) => (
            <li key={index} className={styles.cardContainer} data-link={item}>
              <WishPlaceCard />
            </li>
          ))}
        </ul>
      ) : (
        <ul className={styles.listContainer} onClick={onOpenPlaceList}>
          {test.map((item, index) => (
            <li key={index} className={styles.cardContainer} data-link={item}>
              <WishListCard />
            </li>
          ))}
        </ul>
      )}
    </div>
  );
};

export default WishList;
