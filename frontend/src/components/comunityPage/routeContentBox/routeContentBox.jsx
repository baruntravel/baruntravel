import { useState, useEffect, useCallback } from "react";
import styles from "./routeContentBox.module.css";
import SortBox from "../../../components/common/sortBox/sortBox";
import RouteCard from "../../common/routeCard/routeCard";
import MapButton from "../mapButton/mapButton";
import { useHistory } from "react-router-dom";

const RouteContentBox = ({ area }) => {
  const history = useHistory();
  const mapHandler = () => history.push("/route-map");

  return (
    <div className={styles.container}>
      <div className={styles.functionBox}>
        <MapButton mapHandler={mapHandler} />
        <SortBox />
      </div>
      <div className={styles.routeCards}>
        <RouteCard />
      </div>
    </div>
  );
};

export default RouteContentBox;
