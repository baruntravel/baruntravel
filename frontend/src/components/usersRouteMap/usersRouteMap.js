import { useState } from "react";
import styles from "./usersRouteMap.module.css";
import RouteList from "../../components/routeList/routeList";
import RouteMap from "../../components/routeMap/routeMap";

import { useRecoilState } from "recoil";
import { usersRouteItems } from "../../recoil/routeAtom";

const UsersRouteMap = () => {
  const [markers, setMarkers] = useState([]);
  const [clickedCardName, setClickedCardName] = useState("");
  const [routeItems, setRouteItems] = useRecoilState(usersRouteItems);

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
        />
        <div className={styles.map}>
          <RouteMap markers={markers} />
        </div>
      </section>
    </div>
  );
};

export default UsersRouteMap;
