import React from "react";
import styles from "./myRouteCard.module.css";

const MyRouteCard = ({ route }) => {
  return (
    <div className={styles.MyRouteCard}>
      <div className={styles.routeBox}>
        <div className={styles.thumbnail}>
          <span>사진</span>
        </div>
        <div className={styles.titleBox}>
          <span className={styles.title}>{route.name}</span>
        </div>
      </div>
      <div className={styles.buttonBox}>
        <button className={styles.seeButton}>보기</button>
        <button className={styles.uploadButton}>업로드</button>
      </div>
    </div>
  );
};

export default MyRouteCard;
