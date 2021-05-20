import React from "react";
import styles from "./routeContentBox.module.css";
import MapButton from "./mapButton/mapButton";
import SortBox from "../../../components/common/sortBox/sortBox";

const RouteContentBox = () => {
  return (
    <div className={styles.container}>
      <div className={styles.functionBox}>
        <MapButton />
        <SortBox />
      </div>
      <div className={styles.contentBox}>Route Contents</div>
    </div>
  );
};

export default RouteContentBox;
