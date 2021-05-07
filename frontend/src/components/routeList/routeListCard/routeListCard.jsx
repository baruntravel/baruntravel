import React from "react";
import styles from "./routeListCard.module.css";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faMapMarkerAlt } from "@fortawesome/free-solid-svg-icons";

const RouteListCard = ({
  item,
  handleMarkers,
  clickedCardName,
  updateCardName,
  routeName,
}) => {
  // const handleClick = () => {
  //   handleMarkers(item.places);
  //   if (item.routeName === clickedCardName) {
  //     updateCardName(""); // 두번눌렀을 때 토글 닫히게
  //   } else {
  //     updateCardName(item.routeName);
  //   }
  // };
  return (
    // <div className={styles.RouteListCard} onClick={handleClick}>
    <div className={styles.RouteListCard}>
      <div className={styles.routeCard}>
        <div className={styles.routeCardInner}>
          <div className={styles.infoBox}>
            <span className={styles.rootName}>{routeName}</span>
            {/* <span
              className={styles.date}
            >{`${item.year}.${item.month}.${item.date}`}</span> */}
            <div className={styles}>
              <FontAwesomeIcon
                icon={faMapMarkerAlt}
                className={styles.mapIcon}
              />
              <span
                className={styles.placeCount}
              >{`${5}개의 장소가 있어요`}</span>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default RouteListCard;
