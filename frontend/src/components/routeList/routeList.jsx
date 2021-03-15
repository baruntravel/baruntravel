import React from "react";
import { useRecoilState } from "recoil";
import { myRouteCart } from "../../recoil/routeAtom";
import RouteListCard from "../routeListCard/routeListCard";
import styles from "./routeList.module.css";

const RouteList = ({ handleMarkers, clickedCardName, updateCardName }) => {
  const [myRouteList, setMyRouteList] = useRecoilState(myRouteCart);

  return (
    <div className={styles.RouteList}>
      {Object.keys(myRouteList).map((v, index) => (
        <RouteListCard
          key={index}
          item={myRouteList[v]}
          handleMarkers={handleMarkers}
          clickedCardName={clickedCardName}
          updateCardName={updateCardName}
        />
      ))}
    </div>
  );
};

export default RouteList;
