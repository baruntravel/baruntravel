import React from "react";
import RouteListCard from "./routeListCard/routeListCard";
import styles from "./routeList.module.css";
import PlaceNameBox from "./routeListCard/placeNameBox/placeNameBox";

const RouteList = ({
  handleMarkers,
  clickedCardName,
  updateCardName,
  routeItems,
  usersRoutePage,
}) => {
  return (
    <div className={styles.RouteList}>
      {Object.keys(routeItems).map((v, index) => (
        <div key={index}>
          <RouteListCard
            key={index}
            item={routeItems[v]}
            handleMarkers={handleMarkers}
            clickedCardName={clickedCardName}
            updateCardName={updateCardName}
            usersRoutePage={usersRoutePage}
          />
          {clickedCardName === routeItems[v].routeName && (
            <div className={styles.places}>
              {routeItems[v].places.map((v, index) => (
                <PlaceNameBox
                  usersRoutePage={usersRoutePage}
                  key={index}
                  item={v}
                  isRoute={false}
                />
              ))}
            </div>
          )}
        </div>
      ))}
    </div>
  );
};

export default RouteList;
