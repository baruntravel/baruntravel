import React, { useCallback, useState } from "react";
import { useRecoilState } from "recoil";
import { onAddNewMyWish, onReceiveWishList } from "../../../api/wishListAPI";
import { userWishList } from "../../../recoil/userState";
import WishListPortalInput from "../../portal/wishListInputPortal/wishListPortalInput";
import styles from "./verticalWishList.module.css";

const VerticalWishList = ({ onClose, onAddItem }) => {
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

  const onAddItemToWishList = useCallback(
    (e) => {
      const link = e.target.closest("li")?.dataset.link;
      if (link) {
        if (link === "new") {
          onOpenInput();
        } else {
          onAddItem(link);
        }
      }
    },
    [onAddItem, onOpenInput]
  );

  return (
    <div className={styles.VerticalWishList}>
      <header className={styles.header}>
        <span>찜 목록에 플레이스 추가하기</span>
        <button className={styles.closeButton} onClick={onClose}>
          X
        </button>
      </header>
      <section className={styles.body} onClick={onAddItemToWishList}>
        <ul className={styles.list}>
          <li className={styles.wishItem} data-link="new">
            <div
              className={`${styles.imageContainer} ${styles.plusContainer}`}
            ></div>
            <span className={styles.name}>새로운 찜 목록</span>
          </li>
          {wishListItems.map((item, index) => (
            <li key={index} className={styles.wishItem} data-link={item.id}>
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
