import React from "react";
import styles from "./placeNameBox.module.css";
const PlaceNameBox = ({ item }) => {
  const handleClick = () => {
    // 삭제 api호출
    console.log("삭제 api 호출");
  };
  return (
    <li className={styles.PlaceNameBox}>
      <span>{item.placeName}</span>
      <button className={styles.deleteBtn} onClick={handleClick}>
        X
      </button>
    </li>
  );
};

export default PlaceNameBox;
