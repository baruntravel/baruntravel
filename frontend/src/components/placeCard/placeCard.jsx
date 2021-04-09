import { HeartOutlined, HeartTwoTone } from "@ant-design/icons";
import { Rate } from "antd";
import React, { useCallback, useState } from "react";
import styles from "./placeCard.module.css";
const PlaceCard = ({ onHandleDelete }) => {
  const [liked, setLiked] = useState(false);
  const onClickDelete = useCallback(() => {
    onHandleDelete();
  }, []);
  const onHandleLike = useCallback(() => {
    setLiked(true);
    console.log("Liked");
  }, []);
  return (
    <div className={styles.PlaceCard}>
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
          {liked ? (
            <HeartTwoTone
              className={styles.delete}
              twoToneColor="#eb2f96"
              onClick={onClickDelete}
            />
          ) : (
            <HeartOutlined
              className={styles.delete}
              style={{ color: "grey" }}
              onClick={onHandleLike}
            />
          )}
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
  );
};

export default PlaceCard;
