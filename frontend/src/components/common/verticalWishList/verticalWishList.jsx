import React, { useCallback, useState } from "react";
import { useRecoilState } from "recoil";
import { onAddNewMyWish, onReceiveWishList } from "../../../api/wishListAPI";
import { userWishList } from "../../../recoil/userState";
import WishListPortalInput from "../../portal/wishListInputPortal/wishListPortalInput";
import styles from "./verticalWishList.module.css";

const VerticalWishList = ({ onClose }) => {
  // const wishListItems = [
  //   "https://i.pinimg.com/564x/d7/ec/75/d7ec75c9e68873ee75b734ac4ab09ced.jpg",
  //   "https://i.pinimg.com/474x/30/5a/21/305a216481dfaaec10fd59cf1f667652.jpg",
  //   "https://i.pinimg.com/474x/5f/a2/8e/5fa28eae2bdebd6ab2d30690304927b9.jpg",
  //   "https://i.pinimg.com/474x/a6/56/70/a65670944d4bf492a3a71c4a95bb3910.jpg",
  // ];
  const [wishListItems, setWishListItems] = useRecoilState(userWishList);
  const [openInput, setOpenInput] = useState(false);

  const onOpenInput = useCallback(() => {
    setOpenInput(true);
  }, []);
  const onCloseInput = useCallback(() => {
    setOpenInput(false);
  }, []);

  const onMakeNewWishList = useCallback(
    async (name) => {
      await onAddNewMyWish(name);
      const myList = await onReceiveWishList();
      setWishListItems(myList);
    },
    [setWishListItems]
  );
  return (
    <div className={styles.VerticalWishList}>
      <header className={styles.header}>
        <span>찜 목록에 플레이스 추가하기</span>
        <button className={styles.closeButton} onClick={onClose}>
          X
        </button>
      </header>
      <section className={styles.body}>
        <ul className={styles.list}>
          <li className={styles.wishItem} onClick={onOpenInput}>
            <div
              className={`${styles.imageContainer} ${styles.plusContainer}`}
            ></div>
            <span className={styles.name}>새로운 찜 목록</span>
          </li>
          {wishListItems.map((item, index) => (
            <li key={index} className={styles.wishItem}>
              <div className={styles.imageContainer}>
                <img className={styles.image} src={item} alt="thumbnail" />
              </div>
              <span className={styles.name}>이름</span>
            </li>
          ))}
        </ul>
      </section>
      {openInput && (
        <WishListPortalInput
          onClose={onCloseInput}
          addWishList={onMakeNewWishList}
        />
      )}
    </div>
  );
};

export default VerticalWishList;
