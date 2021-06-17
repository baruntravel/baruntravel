import { useState, useEffect, useCallback } from "react";
import styles from "./wishListContainer.module.css";
import WishListPortalInput from "../../portal/wishListInputPortal/wishListPortalInput";
import { CloseOutlined } from "@ant-design/icons";
// import DeleteConfirm from "../deleteConfirm/deleteConfirm";
import DeleteConfirm from "../../common/deleteConfirm/deleteConfirm";

const WishListContainer = ({ folderIn, wishlistArray, addNewWishList, deleteWishList }) => {
  const [portalOpened, setPortalOpened] = useState(false);
  const [deleteItemId, setDeleteItemId] = useState();

  const handlePortalOpen = () => setPortalOpened(!portalOpened);
  const handleFolderIn = (e) => {
    e.preventDefault();
    folderIn(e.currentTarget.id);
  };
  const onOpenDelete = useCallback((id) => setDeleteItemId(id), []);
  const onCloseDelete = useCallback(() => setDeleteItemId(false), []);

  return (
    <div className={styles.container}>
      <div className={styles.cardsContainer}>
        {wishlistArray.map(({ id, name }) => {
          return (
            <div className={styles.cardBox} onClick={handleFolderIn} key={id} id={id}>
              {/* <div className={styles.cardBox__row1}></div> */}
              <div className={styles.cardBox__row2}>
                <span className={styles.wishlistTitle}>{name}</span>
                <CloseOutlined
                  onClick={(e) => {
                    e.stopPropagation();
                    onOpenDelete(e.target.closest("div").parentNode.id);
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
      {deleteItemId && (
        <DeleteConfirm onClose={onCloseDelete} onDeleteItem={deleteWishList} deleteItemId={deleteItemId} />
      )}
    </div>
  );
};

export default WishListContainer;
