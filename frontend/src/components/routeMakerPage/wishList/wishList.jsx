import React, { useCallback, useState } from "react";
import WishListCard from "../wishListCard/wishListCard";
import WishListHeader from "../wishListHeader/wishListHeader";
import WishPlaceCard from "../wishPlaceCard/wishPlaceCard";
import styles from "./wishList.module.css";

const WishList = ({ wishList, onAddCart }) => {
  const [itemsInWish, setItemsInWish] = useState([]);
  const [isClicked, setIsClicked] = useState(false);
  const onOpenPlaceList = useCallback(() => {
    setIsClicked(true);
    setItemsInWish(wishList[0].places);
  }, [wishList]);
  const onClosePlaceList = useCallback(() => {
    setIsClicked(false);
  }, []);

  return (
    <div className={styles.WishList}>
      <WishListHeader isClicked={isClicked} onClose={onClosePlaceList} />
      {isClicked ? (
        <ul className={styles.listContainer}>
          {itemsInWish.map((item, index) => (
            <li key={index} className={styles.cardContainer} data-link={item}>
              <WishPlaceCard item={item} onAddCart={onAddCart} />
            </li>
          ))}
        </ul>
      ) : (
        <ul className={styles.listContainer} onClick={onOpenPlaceList}>
          {wishList.map((item, index) => (
            <li key={index} className={styles.cardContainer} data-link={item}>
              <WishListCard item={item} />
            </li>
          ))}
        </ul>
      )}
    </div>
  );
};

export default WishList;
