import React from "react";
import styles from "./reviewImageEdit.module.css";
const ReviewImageEdit = ({ item, name, onDeleteImages }) => {
  const handleClick = () => {
    onDeleteImages(name);
  };
  return (
    <div className={styles.imageContainer}>
      <button className={styles.deleteImage} onClick={handleClick}>
        X
      </button>
      <img className={styles.image} src={item} alt="upladed" />
    </div>
  );
};

export default ReviewImageEdit;
