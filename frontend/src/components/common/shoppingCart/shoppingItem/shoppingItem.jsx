import { FormOutlined, HeartTwoTone } from "@ant-design/icons";
import { Rate } from "antd";
import React, { useCallback } from "react";
import PlaceCard from "../../../placeCard/placeCard";
import styles from "./shoppingItem.module.css";
const ShoppingItem = ({ setConfirmPortalTrue }) => {
  const onHandleDelete = useCallback(() => {
    setConfirmPortalTrue();
  }, []);
  return (
    <div className={styles.shoppingItem}>
      <div className={styles.placeInfo}>
        <PlaceCard onHandleDelete={onHandleDelete} />
      </div>
      <div className={styles.card__bottom}>
        <div className={styles.memoBox}>
          <FormOutlined className={styles.memoIcon} />
          <span className={styles.memo}>메모 적기</span>
        </div>
      </div>
    </div>
  );
};

export default ShoppingItem;
