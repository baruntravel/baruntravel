import React, { useState } from "react";
import RouteList from "../../components/routeList/routeList";
import RouteMap from "../../components/routeMap/routeMap";
import styles from "./myRoutePage.module.css";

const MyRoutePage = (props) => {
  const [markers, setMarkers] = useState([]);
  const [clickedCardName, setClickedCardName] = useState("");
  const handleMarkers = (places) => {
    setMarkers(places);
  };
  const updateCardName = (name) => {
    setClickedCardName(name);
  };
  return (
    <div className={styles.MyRoutePage}>
      <section className={styles.section}>
        <RouteList
          handleMarkers={handleMarkers}
          clickedCardName={clickedCardName}
          updateCardName={updateCardName}
        />
        <div className={styles.map}>
          <RouteMap markers={markers} />
        </div>
      </section>
    </div>
  );
};

export default MyRoutePage;
