import React, { useCallback, useRef, useState } from "react";
import CategoryBar from "../../components/map/hotplaceMap/categoryBar/categoryBar";
import OurPlaceMap from "../../components/map/ourPlaceMap/ourPlaceMap";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faList } from "@fortawesome/free-solid-svg-icons";

import styles from "./ourPlacePage.module.css";
import PortalPlaceList from "../../components/portalPlaceList/portalPlaceList";

const OurPlacePage = (props) => {
  const placeListRef = useRef();
  const [openListPortal, setOpenListPortal] = useState(false);
  const [searchPlaces, setSearchPlaces] = useState([]);

  const onOpenListPortal = useCallback(() => {
    setOpenListPortal(true);
  }, []);
  const onCloseListPortal = useCallback(() => {
    setOpenListPortal(false);
  }, []);

  return (
    <div className={styles.OurPlacePage}>
      <div ref={placeListRef} className={styles.carouselContainer}>
        <div className={styles.mapButtonBox}>
          <button
            className={styles.listPortalButton}
            onClick={onOpenListPortal}
          >
            <FontAwesomeIcon icon={faList} color="white" size="lg" />
          </button>
        </div>
      </div>
      <div className={styles.mapContainer}>
        <OurPlaceMap />
      </div>
      <div className={styles.categoryContainer}>
        <CategoryBar />
      </div>
      {openListPortal && (
        <PortalPlaceList onClose={onCloseListPortal} places={searchPlaces} />
      )}
    </div>
  );
};

export default OurPlacePage;
