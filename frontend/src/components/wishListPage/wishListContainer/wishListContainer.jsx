import { useState, useEffect } from "react";
import styles from "./wishListContainer.module.css";
import WishListPortalInput from "../../portal/wishListInputPortal/wishListPortalInput";
import { CloseOutlined } from "@ant-design/icons";

const WishListContainer = ({ folderIn, wishlistArray, addNewWishList, deleteWishList }) => {
  const [portalOpened, setPortalOpened] = useState(false);
  const handlePortalOpen = () => setPortalOpened(!portalOpened);
  const handleFolderIn = (e) => {
    e.preventDefault();
    folderIn(e.currentTarget.id);
  };

  return (
    <div className={styles.container}>
      <div className={styles.cardsContainer}>
        {wishlistArray.map(({ id, name }) => {
          return (
            <div className={styles.cardBox} onClick={handleFolderIn} key={id} id={id}>
              {/* TODO : 클릭 이벤트 오류 */}
              {/* <div className={styles.cardBox__row1}></div>  */}
              <div className={styles.cardBox__row2}>
                <span className={styles.wishlistTitle}>{name}</span>
                <CloseOutlined
                  onClick={(e) => {
                    e.stopPropagation();
                    deleteWishList(e.target.closest("div").parentNode.id);
                  }}
                />
              </div>
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
