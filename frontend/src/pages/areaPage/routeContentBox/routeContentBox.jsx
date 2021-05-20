import React from "react";
import styles from "./routeContentBox.module.css";
import MapButton from "./mapButton/mapButton";
import SortBox from "../common/sortBox/sortBox";
import RouteCard from "./routeCard/routeCard";

const RouteContentBox = ({ area }) => {
  return (
    <div className={styles.container}>
      <div className={styles.functionBox}>
        <MapButton />
        <SortBox />
      </div>
      <div className={styles.routeCards}>
        <RouteCard />
      </div>
    </div>
  );
};

export default RouteContentBox;
