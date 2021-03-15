import React, { useCallback } from "react";
import styles from "./routeNameBox.module.css";

const RouteNameBox = ({ item, objKey, addPlace }) => {
  const handleClick = () => {
    addPlace && addPlace(objKey);
  };
  return <div onClick={handleClick}>{item.routeName}</div>;
};

export default RouteNameBox;
