import React from "react";
import styles from "./logo.module.css";
import logoPath from "./logo.png";

const Logo = () => {
  return (
    <>
      <div className={styles.logoWrap}>
        <img src={logoPath} alt="logo" className={styles.logoImg} />
      </div>
    </>
  );
};

export default Logo;
