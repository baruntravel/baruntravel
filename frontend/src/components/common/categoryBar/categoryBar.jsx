import React from "react";
import styles from "./categoryBar.module.css";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import {
  faCoffee,
  faStore,
  faArchway,
  faHome,
} from "@fortawesome/free-solid-svg-icons";

const CategoryBar = (props) => {
  return (
    <div className={styles.CategoryBar}>
      <ul className={styles.CategoryBarList} id="category">
        <li id="CE7" data-order="0" className={styles.caffe}>
          <FontAwesomeIcon icon={faCoffee} className={styles.icon} />
          <span>카페</span>
        </li>
        <li id="FD6" data-order="1" className={styles.food}>
          <FontAwesomeIcon icon={faStore} className={styles.icon} />
          <span>맛집</span>
        </li>
        <li id="AT4" data-order="2" className={styles.tour}>
          <FontAwesomeIcon icon={faArchway} className={styles.icon} />
          <span>명소</span>
        </li>
        <li id="AD5" data-order="3" className={styles.hotel}>
          <FontAwesomeIcon icon={faHome} className={styles.icon} />
          <span>숙소</span>
        </li>
      </ul>
    </div>
  );
};

export default CategoryBar;
