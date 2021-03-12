import React, { useEffect } from "react";
import CategoryBar from "../categoryBar/categoryBar";
import styles from "./hotplaceMap.module.css";

const HotplaceMap = (props) => {
  useEffect(() => {}, []);
  return (
    <div className={styles.HotplaceMap}>
      <CategoryBar />
      <div id="Map" className={styles.map} />
    </div>
  );
};

export default HotplaceMap;
