import React from "react";
import RouteListCard from "./routeListCard/routeListCard";
import styles from "./routeList.module.css";

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
        <RouteListCard
          key={index}
          item={routeItems[v]}
          handleMarkers={handleMarkers}
          clickedCardName={clickedCardName}
          updateCardName={updateCardName}
          usersRoutePage={usersRoutePage}
        />
      ))}
    </div>
  );
};

export default RouteList;
