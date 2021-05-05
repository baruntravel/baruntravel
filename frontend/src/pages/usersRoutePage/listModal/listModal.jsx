import styles from "./listModal.module.css";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faMap } from "@fortawesome/free-solid-svg-icons";
import { useState } from "react";

//Todo : 화면 스크롤 되게, 루트 다 보여줄지, 해당 인덱스의 루트만 보여줄지

const ListModal = ({ places, modalHandler }) => {
  return (
    <div className={styles.container}>
      <div className={styles.routeCard}>
        {places.map(({ placeName, placeUrl, addressName }, index) => {
          return (
            <div className={styles.drawerCard} key={index}>
              <h2>{placeName}</h2>
              <h3>{placeUrl}</h3>
              <h3>{addressName}</h3>
            </div>
          );
        })}
      </div>

      <button className={styles.mapButton} onClick={modalHandler}>
        <FontAwesomeIcon icon={faMap} color="white" size="lg" />
      </button>
    </div>
  );
};

export default ListModal;
