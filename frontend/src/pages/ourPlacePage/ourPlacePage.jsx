import React from "react";
import CategoryBar from "../../components/map/hotplaceMap/categoryBar/categoryBar";
import OurPlaceMap from "../../components/map/ourPlaceMap/ourPlaceMap";
import styles from "./ourPlacePage.module.css";

const OurPlacePage = (props) => {
  return (
    <div className={styles.OurPlacePage}>
      <div className={styles.mapContainer}>
        <OurPlaceMap />
      </div>
      <div className={styles.categoryContainer}>
        <CategoryBar />
      </div>
    </div>
  );
};

export default OurPlacePage;
