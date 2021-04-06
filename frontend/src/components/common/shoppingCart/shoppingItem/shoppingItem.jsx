import { FormOutlined, HeartTwoTone } from "@ant-design/icons";
import { Rate } from "antd";
import React, { useCallback } from "react";
import styles from "./shoppingItem.module.css";
const ShoppingItem = ({ setConfirmPortalTrue }) => {
  const onHandleDelete = useCallback(() => {
    setConfirmPortalTrue();
  }, []);
  return (
    <div className={styles.shoppingItem}>
      <div className={styles.placeInfo}>
        <div className={styles.imageBox}>
          <img
            className={styles.placeImage}
            src="https://blog.hmgjournal.com/images_n/contents/171013_N1.png"
            alt="placeImg"
          />
        </div>
        <div className={styles.contentBox}>
          <div className={styles.placeNameBox}>
            <span className={styles.placeName}>place이름</span>
            <HeartTwoTone
              className={styles.delete}
              twoToneColor="#eb2f96"
              onClick={onHandleDelete}
            />
          </div>
          <span className={styles.categoryName}>음식점(카페)카테고리</span>
          <div className={styles.placeRateBox}>
            <Rate
              className={styles.rate}
              disabled={true}
              defaultValue={4}
              allowClear={false}
              style={{
                animation: "none",
                fontSize: "0.8em",
              }}
            />
            <span className={styles.reviewCount}>6</span>
          </div>
          <span className={styles.placeAddress}>place주소</span>
        </div>
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
