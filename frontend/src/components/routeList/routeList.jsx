import React from "react";
import RouteListCard from "./routeListCard/routeListCard";
import styles from "./routeList.module.css";
import PlaceNameBox from "./routeListCard/placeNameBox/placeNameBox";

const RouteList = ({
  handleMarkers,
  clickedCardName,
  updateCardName,
  routeItems,
}) => {
  return (
    <div className={styles.RouteList}>
      {Object.keys(routeItems).map((v, index) => (
        <div>
          <RouteListCard
            key={index}
            item={routeItems[v]}
            handleMarkers={handleMarkers}
            clickedCardName={clickedCardName}
            updateCardName={updateCardName}
          />
          {clickedCardName === routeItems[v].routeName && (
            <div className={styles.places}>
              {routeItems[v].places.map((v, index) => (
                <PlaceNameBox key={index} item={v} />
              ))}
            </div>
          )}
        </div>
      ))}
    </div>
  );
};

export default RouteList;
