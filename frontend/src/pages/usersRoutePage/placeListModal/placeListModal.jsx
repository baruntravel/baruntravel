import styles from "./placeListModal.module.css";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faMap } from "@fortawesome/free-solid-svg-icons";
import { useState } from "react";
import { Link } from "react-router-dom";

//Todo : 화면 스크롤 되게, 루트 다 보여줄지, 해당 인덱스의 루트만 보여줄지

const PlaceListModal = ({ places, modalHandler }) => {
  return (
    <div className={styles.container}>
      <div className={styles.routeCardContainer}>
        {places.map(({ id, placeName, placeUrl, addressName, category }, index) => {
          return (
            <Link to={`place/${id}`} key={index}>
              <div className={styles.routeCard}>
                <div className={styles.left}>
                  <div className={styles.row1}>
                    <h2>{placeName}</h2>
                    <h3>{category}</h3>
                  </div>
                  <div className={styles.row2}>
                    <h3>{addressName}</h3>
                    <h3>02-2222-3333</h3>
                  </div>
                </div>
                <div className={styles.right}>
                  <div className={styles.thumbnail}></div>
                </div>
              </div>
            </Link>
          );
        })}
      </div>

      <button className={styles.mapButton} onClick={modalHandler}>
        <FontAwesomeIcon icon={faMap} color="white" size="lg" />
      </button>
    </div>
  );
};

export default PlaceListModal;
