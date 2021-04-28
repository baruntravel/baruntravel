import { HeartOutlined, HeartTwoTone } from "@ant-design/icons";
import { Rate } from "antd";
import React, { useCallback, useEffect, useRef, useState } from "react";
import { useHistory } from "react-router-dom";
import styles from "./placeCard.module.css";
const PlaceCard = ({ place, onHandleDelete, addShoppingCart, isLiked }) => {
  const history = useHistory();
  const likeIconRef = useRef();
  const onClickDelete = useCallback(() => {
    onHandleDelete(place.id);
  }, []);
  const onHandleLike = () => {
    const data = {
      id: place.id,
      category_name: place.category_group_name,
      address_name: place.road_address_name || place.address_name,
      name: place.place_name,
      x: place.x,
      y: place.y,
      url: place.place_url,
      memo: "",
    };
    addShoppingCart(data);
  };
  const onClickCard = useCallback((event) => {
    if (likeIconRef.current.contains(event.target) || event.target.nodeName === "A") {
      return;
    }
    history.push(`/place/${place.id}`);
  }, []);
  useEffect(() => {
    // isLiked && setLiked(true);
  });
  const placeAddress = place.road_address_name || place.address_name;
  return (
    <div className={styles.PlaceCard} onClick={onClickCard}>
      <div className={styles.imageBox}>
        <img
          className={styles.placeImage}
          src={place.ImageUrl || "https://blog.hmgjournal.com/images_n/contents/171013_N1.png"}
          alt="placeImg"
          draggable={false}
        />
      </div>
      <div className={styles.contentBox}>
        <div className={styles.placeNameBox}>
          <span className={styles.placeName}>{place.place_name || place.name}</span>
          {isLiked ? (
            <HeartTwoTone ref={likeIconRef} className={styles.delete} twoToneColor="#eb2f96" onClick={onClickDelete} />
          ) : (
            <HeartOutlined
              ref={likeIconRef}
              className={styles.delete}
              style={{ color: "grey" }}
              onClick={onHandleLike}
            />
          )}
        </div>
        <span className={styles.categoryName}>{place.category_group_name || place.category_name}</span>
        {/* <div className={styles.placeRateBox}>
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
        </div> */}
        <div className={styles.bottom}>
          <span className={styles.placeAddress}>{placeAddress}</span>
          <a href={place.place_url || place.url} target="_blank" className={styles.morePage}>
            카카오
          </a>
        </div>
      </div>
    </div>
  );
};

export default PlaceCard;
