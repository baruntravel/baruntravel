import React from "react";
import styles from "./placeContainer.module.css";

const PlaceContainer = ({ handleOpen }) => {
  return <div onClick={handleOpen}>플레이스 컨테이너</div>;
};

export default PlaceContainer;
