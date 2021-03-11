import React from "react";
import styles from "./categoryBar.module.css";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import {
  faCoffee,
  faStore,
  faSubway,
  faArchway,
  faBriefcaseMedical,
  faStoreAlt,
  faHome,
} from "@fortawesome/free-solid-svg-icons";

const CategoryBar = (props) => {
  return (
    <ul className={styles.CategoryBar}>
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
      <li id="PM9" data-order="4" className={styles.drug}>
        <FontAwesomeIcon icon={faBriefcaseMedical} className={styles.icon} />
        <span>약국</span>
      </li>
      <li id="CS2" data-order="5" className={styles.convenience}>
        <FontAwesomeIcon icon={faStoreAlt} className={styles.icon} />
        <span>편의점</span>
      </li>
      <li id="SW8" data-order="6" className={styles.subway}>
        <FontAwesomeIcon icon={faSubway} className={styles.icon} />
        <span>지하철</span>
      </li>
    </ul>
  );
};

export default CategoryBar;
