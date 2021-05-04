import { FormOutlined } from "@ant-design/icons";
import React, { memo, useCallback, useState } from "react";
import PlaceCard from "../../../placeCard/placeCard";
import MemoForm from "../../memoForm/memoForm";
import styles from "./shoppingItem.module.css";

const ShoppingItem = memo(
  ({
    item,
    setConfirmPortalTrue,
    deleteClickedItemId,
    updateMemoShoppingItem,
  }) => {
    const [openMemo, setOpenMemo] = useState(false);

    const setOpenMemoTrue = useCallback(() => {
      setOpenMemo(true);
    }, []);
    const setOpenMemoFalse = useCallback(() => {
      setOpenMemo(false);
    }, []);
    const onHandleDelete = useCallback(() => {
      setConfirmPortalTrue();
      deleteClickedItemId(item.id);
    }, []);
    return (
      <div className={styles.shoppingItem}>
        <div className={styles.placeInfo}>
          <PlaceCard
            place={item}
            onHandleDelete={onHandleDelete}
            isLiked={true}
          />
        </div>
        <div className={styles.card__bottom}>
          <div className={styles.memoBox} onClick={setOpenMemoTrue}>
            <FormOutlined
              className={styles.memoIcon}
              style={{ color: "#00acee" }}
            />
            {item.memo ? (
              <span className={styles.memo}>{item.memo}</span>
            ) : (
              <span className={styles.memo}>메모 적기</span>
            )}
          </div>
        </div>
        {openMemo && (
          <MemoForm
            item={item}
            onClose={setOpenMemoFalse}
            updateMemoShoppingItem={updateMemoShoppingItem}
          />
        )}
      </div>
    );
  }
);

export default ShoppingItem;
