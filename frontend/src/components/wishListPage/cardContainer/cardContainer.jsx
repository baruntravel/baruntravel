import { useState } from "react";
import styles from "./cardContainer.module.css";
import WishListPortal from "../../portal/wishListPortal/wishListPortal";

const CardContainer = ({ folderIn }) => {
  const [wishListName, setWishlistName] = useState();
  const [portalOpened, setPortalOpened] = useState(false);

  const handlePortalOpen = () => setPortalOpened(!portalOpened);
  const handleWishlistName = (name) => setWishlistName(name);
  const handleFolderIn = (e) => {
    // console.log(e.target);
    folderIn();
  };

  return (
    <div className={styles.container}>
      <div className={styles.cardsContainer} onClick={handleFolderIn}>
        <div className={styles.cardBox}>
          <div className={styles.imgBox}></div>
          <span className={styles.wishListTitle}>제주도 갈만한 곳들</span>
        </div>
        <h1>{wishListName}</h1>
      </div>
      <button onClick={handlePortalOpen} className={styles.addButton}>
        새 찜목록 만들기
      </button>
      {portalOpened && <WishListPortal onClose={handlePortalOpen} handleWishlistName={handleWishlistName} />}
    </div>
  );
};

export default CardContainer;
