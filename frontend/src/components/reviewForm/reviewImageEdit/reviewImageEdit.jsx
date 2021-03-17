import React from "react";
import styles from "./reviewImageEdit.module.css";
const ReviewImageEdit = ({ item }) => {
  return (
    <div className={styles.imageContainer}>
      <button className={styles.deleteImage}>X</button>
      <img className={styles.image} src={item} alt="upladed image" />
    </div>
  );
};

export default ReviewImageEdit;
