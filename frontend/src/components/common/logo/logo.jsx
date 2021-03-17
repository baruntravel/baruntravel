import React from "react";
import styles from "./logo.module.css";

const Logo = () => {
  return (
    <>
      <div className={styles.logoWrap}>
        <div className={styles.logoTitle}>
          바른여행
          <br />
          길잡이
        </div>
      </div>
    </>
  );
};

export default Logo;
