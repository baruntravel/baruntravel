import React from "react";
import styles from "./placeNameBox.module.css";

const PlaceNameBox = ({ item, usersRoutePage }) => {
  const handleClick = () => {
    // 삭제 api호출
    console.log("삭제 api 호출");
  };
  return (
    //usersRoute 페이지에서 보일 모습
    <>
      {usersRoutePage === true ? (
        <li className={styles.PlaceNameBox}>
          <span>{item.placeName}</span>
        </li>
      ) : (
        //myRoute 페이지에서 보일 모습
        <li className={styles.PlaceNameBox}>
          <span>{item.placeName}</span>
          <button className={styles.deleteBtn} onClick={handleClick}>
            X
          </button>
        </li>
      )}
    </>
  );
};

export default PlaceNameBox;
