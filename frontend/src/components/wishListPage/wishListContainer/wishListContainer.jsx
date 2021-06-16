import { useState, useEffect } from "react";
import styles from "./wishListContainer.module.css";
import WishListPortalInput from "../../portal/wishListInputPortal/wishListPortalInput";

const WishListContainer = ({ folderIn, wishlistArray, addNewWishList }) => {
  const [portalOpened, setPortalOpened] = useState(false);
  const handlePortalOpen = () => setPortalOpened(!portalOpened);
  // const addWishList = (name) => setWishList((wishList) => [...wishList, name]);
  const handleFolderIn = (e) => folderIn(e.target.textContent);

  return (
    <div className={styles.container}>
      <div className={styles.cardsContainer}>
        {wishlistArray.map(({ id, name }) => {
          return (
            <div className={styles.cardBox} onClick={handleFolderIn} key={id} id={id}>
              <div className={styles.wishlistTitle}>{name}</div>
            </div>
          );
        })}
      </div>
      <button onClick={handlePortalOpen} className={styles.addButton}>
        새 찜목록 만들기
      </button>
      {portalOpened && <WishListPortalInput onClose={handlePortalOpen} addNewWishList={addNewWishList} />}
    </div>
  );
};

export default WishListContainer;
