import React, { useCallback } from "react";
import styles from "./routeNameBox.module.css";

const RouteNameBox = ({ item, objKey, addPlace }) => {
  const handleClick = () => {
    addPlace && addPlace(objKey);
  };
  return (
    <div className={styles.RouteNameBox} onClick={handleClick}>
      <span>{item.routeName}</span>
    </div>
  );
};

export default RouteNameBox;
