import React, { useState, useCallback } from "react";
import ImagesZoom from "../imagesZoom/imagesZoom";
import styles from "./postImages.module.css";

const PostImages = ({ images }) => {
  const [showImagesZoom, setShowImagesZoom] = useState(false);
  const onZoom = useCallback(() => {
    setShowImagesZoom(true);
  }, []);
  const onClose = useCallback(() => {
    setShowImagesZoom(false);
  }, []);
  return (
    <div className={styles.PostImages}>
      <img
        className={styles.image}
        role="presentation"
        src={images[0]}
        alt={images[0]}
        onClick={onZoom}
      />
      {showImagesZoom && <ImagesZoom images={images} onClose={onClose} />}
    </div>
  );
};

export default PostImages;
