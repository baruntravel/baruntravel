import React, { useEffect, useState } from "react";
import RouteList from "../../components/routeList/routeList";
import RouteMap from "../../components/map/routeMap/routeMap";
import styles from "./myRoutePage.module.css";

import { useRecoilState } from "recoil";
import { myRouteCart } from "../../recoil/routeAtom";
import { useHistory } from "react-router-dom";

const MyRoutePage = (props) => {
  const history = useHistory();
  const [routeItems, setRouteItems] = useRecoilState(myRouteCart);
  const [markers, setMarkers] = useState([]);
  const [clickedCardName, setClickedCardName] = useState();
  useEffect(() => {
    const historyState = history.location.state;
    setMarkers((historyState && historyState.places) || []);
    setClickedCardName(historyState && historyState.clickedCardName);
    history.push({ state: null }); // history State (초기화)
  }, []);

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
