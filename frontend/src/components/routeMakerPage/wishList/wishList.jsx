import React, { useCallback, useEffect, useState } from "react";
import WishListCard from "../wishListCard/wishListCard";
import WishListHeader from "../wishListHeader/wishListHeader";
import WishPlaceCard from "../wishPlaceCard/wishPlaceCard";
import styles from "./wishList.module.css";

const WishList = ({ wishList, onAddCart, onDeleteCart, cartItems }) => {
  const [itemsInWish, setItemsInWish] = useState([]);
  const [isClicked, setIsClicked] = useState(false);

  const sortWishPlaces = useCallback(
    (places) => {
      const updated = cartItems.map((cartItem) => places.find((item) => item.id === cartItem.id));
      places.forEach((item) => {
        if (!updated.find((cartItem) => cartItem.id === item.id)) {
          updated.push(item);
        }
      });
      setItemsInWish(updated);
    },
    [cartItems]
  );

  const onOpenPlaceList = useCallback(() => {
    setIsClicked(true);
    sortWishPlaces(wishList[0].places);
  }, [sortWishPlaces, wishList]);

  const onClosePlaceList = useCallback(() => {
    setIsClicked(false);
  }, []);

  useEffect(() => {
    sortWishPlaces(itemsInWish);
  }, [cartItems]);

  return (
    <div className={styles.WishList}>
      <WishListHeader isClicked={isClicked} onClose={onClosePlaceList} />
      {isClicked ? (
        <ul className={styles.listContainer}>
          {itemsInWish.map((item, index) => (
            <li key={index} className={styles.cardContainer} data-link={item}>
              <WishPlaceCard
                item={item}
                onAddCart={onAddCart}
                onDeleteCart={onDeleteCart}
                index={cartItems.findIndex((cartItem) => cartItem.id === item.id) + 1}
              />
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
