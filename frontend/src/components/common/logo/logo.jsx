import React from "react";
import styles from "./logo.module.css";

const Logo = () => {
  return (
    <>
      <div className={styles.logoWrap}>
        <div className={styles.logoTitle}>
          바른
          <br />
          여행
        </div>
      </div>
    </>
  );
};

export default Logo;
