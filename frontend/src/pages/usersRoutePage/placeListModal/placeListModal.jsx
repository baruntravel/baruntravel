import styles from "./placeListModal.module.css";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faMap } from "@fortawesome/free-solid-svg-icons";
import { useState } from "react";
import { Link } from "react-router-dom";

const PlaceListModal = ({ routes, modalHandler }) => {
  return (
    <div className={styles.container}>
      <div className={styles.routeCardContainer}>
        {Object.keys(routes).map((value, index) => {
          return (
            <div className={styles.routeCard} key={index}>
              <div className={styles.col1}>
                <h2 className={styles.routeName}>{routes[value].routeName}</h2>
                <h3 className={styles.creator}>{routes[value].creator}</h3>
              </div>
              {routes[value].places.map(({ id, placeName, placeUrl, addressName, category }, index) => {
                return (
                  <Link to={`place/${id}`} key={index}>
                    <div className={styles.placeCard}>
                      <div className={styles.col2}>
                        <div className={styles.row1}>
                          <h2>{placeName}</h2>
                          <h3>{category}</h3>
                        </div>
                        <div className={styles.row2}>
                          <h3>{addressName}</h3>
                          <h3>02-2222-3333</h3>
                        </div>
                      </div>
                      <div className={styles.col3}>
                        <div className={styles.thumbnail}></div>
                      </div>
                    </div>
                  </Link>
                );
              })}
            </div>
          );
        })}

        <button className={styles.mapButton} onClick={modalHandler}>
          <FontAwesomeIcon icon={faMap} color="white" size="lg" />
        </button>
      </div>
    </div>
  );
};
export default PlaceListModal;
