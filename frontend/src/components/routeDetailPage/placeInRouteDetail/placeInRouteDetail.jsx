import { RightOutlined } from "@ant-design/icons";
import React, { useCallback } from "react";
import { useHistory } from "react-router-dom";
import styles from "./placeInRouteDetail.module.css";

const PlaceInRouteDetail = ({
  place: { id, image, name, address, category },
}) => {
  const history = useHistory();
  const goToPlaceDetail = useCallback(
    (e) => {
      history.push(`/place/${id}`);
    },
    [history, id]
  );
  return (
    <div className={styles.PlaceInRouteDetail}>
      <header
        className={styles.header}
        data-link={id}
        onClick={goToPlaceDetail}
      >
        <div className={styles.placeNameBox}>
          <span
            className={styles.placeName}
          >{`${name}dsasdasdasdasdasdasd`}</span>
        </div>
        <div className={styles.categoryBox}>
          <span className={styles.category}>{`(${category})`}</span>
        </div>
        <div className={styles.navigationIcon}>
          <RightOutlined style={{ color: "#ED6AB8" }} />
        </div>
      </header>
      <section className={styles.body}>
        {image && <img className={styles.image} src={image} alt="place img" />}
        {/* <div className={styles.infoBox}>
          <span className={styles.address}>{address}</span>
        </div> */}
        <div className={styles.endLine} />
      </section>
    </div>
  );
};

export default PlaceInRouteDetail;
