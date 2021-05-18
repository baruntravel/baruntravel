import React from "react";
import Portal from "../portal/portal";
import styles from "./portalPlaceList.module.css";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faMap } from "@fortawesome/free-solid-svg-icons";
import PlaceCard from "../placeCard/placeCard";

const PortalPlaceList = ({ onClose, places }) => {
  return (
    <Portal>
      <div className={styles.PortalPlaceList}>
        <div className={styles.placeList}>
          {places.map((place, index) => (
            <div key={index} className={styles.placeCardContainer}>
              <PlaceCard place={place}></PlaceCard>
            </div>
          ))}
        </div>
        <button className={styles.mapButton} onClick={onClose}>
          <FontAwesomeIcon icon={faMap} color="white" size="lg" />
        </button>
      </div>
    </Portal>
  );
};

export default PortalPlaceList;
