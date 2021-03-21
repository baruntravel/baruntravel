import React from "react";
import PlaceNameBox from "./placeNameBox/placeNameBox";
import styles from "./routeListCard.module.css";
const RouteListCard = ({
  item,
  handleMarkers,
  clickedCardName,
  updateCardName,
}) => {
  const handleClick = () => {
    handleMarkers(item.places);
    updateCardName(item.routeName);
  };
  return (
    <div className={styles.routeListCard} onClick={handleClick}>
      <div className={styles.routeName}>
        <span>{item.routeName}</span>
      </div>
      <div className={styles.routeList}>
      {clickedCardName === item.routeName &&
        item.places.map((v, index) => <PlaceNameBox key={index} item={v} />)}
      </div>
    </div>
  );
};

export default RouteListCard;
