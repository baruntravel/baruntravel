import React, { useState } from "react";
import RouteList from "../../components/routeList/routeList";
import RouteMap from "../../components/map/routeMap/routeMap";
import styles from "./myRoutePage.module.css";

import { useRecoilState } from "recoil";
import { myRouteCart } from "../../recoil/routeAtom";

const MyRoutePage = (props) => {
  const [markers, setMarkers] = useState([]);
  const [clickedCardName, setClickedCardName] = useState("");
  const [routeItems, setRouteItems] = useRecoilState(myRouteCart);

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
          routeItems={routeItems}
          usersRoutePage={false}
        />
        <div className={styles.map}>
          <RouteMap markers={markers} />
        </div>
      </section>
    </div>
  );
};

export default MyRoutePage;
