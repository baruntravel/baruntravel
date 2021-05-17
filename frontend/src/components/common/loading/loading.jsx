import React from "react";
import styles from "./loading.module.css";
const Loading = (props) => {
  return (
    <div className={styles.loading__box}>
      <div className={styles.circles}>
        <i></i>
        <i></i>
        <i></i>
      </div>
      <p>Loading...</p>
    </div>
  );
};

export default Loading;
