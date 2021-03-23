import React from "react";
import PlaceNameBox from "./placeNameBox/placeNameBox";
import styles from "./routeListCard.module.css";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faMapMarkerAlt } from "@fortawesome/free-solid-svg-icons";

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
    <div className={styles.RouteListCard} onClick={handleClick}>
      <div className={styles.routeCard}>
        <div>
          <div className={styles.infoBox}>
            <span className={styles.rootName}>{item.routeName}</span>
            <span
              className={styles.date}
            >{`${item.year}.${item.month}.${item.date}`}</span>
            <div className={styles}>
              <FontAwesomeIcon
                icon={faMapMarkerAlt}
                className={styles.mapIcon}
              />
              <span
                className={styles.placeCount}
              >{`${item.places.length}개의 장소가 있어요`}</span>
            </div>
          </div>
        </div>
        <div className={styles.toggle}></div>
      </div>
      {clickedCardName === item.routeName &&
        item.places.map((v, index) => <PlaceNameBox key={index} item={v} />)}
    </div>
  );
};

export default RouteListCard;
