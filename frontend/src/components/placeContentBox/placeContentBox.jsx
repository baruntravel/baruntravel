import React from "react";
import SortBox from "../common/sortBox/sortBox";
import styles from "./placeContentBox.module.css";

const PlaceContentBox = ({ places }) => {
  // places : place id, place images
  return (
    <div className={styles.PlaceContentBox}>
      {/* <div className="functionBox"> */}
      <div className={styles.functionBox}>
        <span>아이콘 2개</span>
        <SortBox />
      </div>
    </div>
  );
};

export default PlaceContentBox;
