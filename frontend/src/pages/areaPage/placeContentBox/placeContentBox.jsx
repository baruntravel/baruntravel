import React from "react";
import styles from "./placeContentBox.module.css";
import MapButton from "./mapButton/mapButton";
import SortBox from "../common/sortBox/sortBox";

const PlaceContentBox = () => {
  return (
    <div className={styles.container}>
      <div className={styles.functionBox}>
        <MapButton />
        <SortBox />
      </div>
      <div className={styles.contentBox}>Place Contents</div>
    </div>
  );
};

export default PlaceContentBox;
