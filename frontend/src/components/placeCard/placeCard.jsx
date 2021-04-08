import { HeartOutlined, HeartTwoTone } from "@ant-design/icons";
import { Rate } from "antd";
import React, { useCallback, useState } from "react";
import styles from "./placeCard.module.css";
const PlaceCard = ({ place, onHandleDelete }) => {
  const [liked, setLiked] = useState(false);
  const onClickDelete = useCallback(() => {
    onHandleDelete();
  }, []);
  const onHandleLike = useCallback(() => {
    setLiked(true);
    console.log("Liked");
  }, []);
  const placeAddress = place.road_address_name || place.address_name;
  return (
    <div className={styles.PlaceCard}>
      <div className={styles.imageBox}>
        <img
          className={styles.placeImage}
          src={
            // place.ImageUrl ||
            "https://blog.hmgjournal.com/images_n/contents/171013_N1.png"
          }
          alt="placeImg"
        />
      </div>
      <div className={styles.contentBox}>
        <div className={styles.placeNameBox}>
          <span className={styles.placeName}>{place.place_name}</span>
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
        <span className={styles.categoryName}>{place.category_group_name}</span>
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
        <div className={styles.bottom}>
          <span className={styles.placeAddress}>{placeAddress}</span>
          <span className={styles.morePage}>상세 보기</span>
        </div>
      </div>
    </div>
  );
};

export default PlaceCard;
