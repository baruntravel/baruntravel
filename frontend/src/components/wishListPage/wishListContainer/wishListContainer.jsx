import { useState, useEffect } from "react";
import styles from "./wishListContainer.module.css";
import WishListPortal from "../../portal/wishListPortal/wishListPortal";

const WishListContainer = ({ folderIn }) => {
  const [portalOpened, setPortalOpened] = useState(false);
  const [wishList, setWishList] = useState(["찜목록 A", "찜목록 B", "찜목록 C"]);
  const handlePortalOpen = () => setPortalOpened(!portalOpened);
  const addWishList = (name) => setWishList((wishList) => [...wishList, name]);
  const handleFolderIn = (e) => folderIn(e.target.textContent);

  useEffect(() => {}, [wishList]);

  return (
    <div className={styles.container}>
      <div className={styles.cardsContainer}>
        {wishList.map((v, key) => {
          return (
            <div className={styles.cardBox} onClick={handleFolderIn} key={key}>
              <div className={styles.wishlistTitle}>{v}</div>
            </div>
          );
        })}
      </div>
      <button onClick={handlePortalOpen} className={styles.addButton}>
        새 찜목록 만들기
      </button>
      {portalOpened && <WishListPortal onClose={handlePortalOpen} addWishList={addWishList} />}
    </div>
  );
};

export default WishListContainer;
