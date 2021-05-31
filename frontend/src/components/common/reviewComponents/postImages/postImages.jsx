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
  if (images.length === 1) {
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
  }
  if (images.length === 2) {
    return (
      <div className={styles.PostImages}>
        <img
          className={styles.imageTwo}
          role="presentation"
          src={images[0]}
          alt={images[0]}
          onClick={onZoom}
        />
        <div className={styles.verticalLine} />
        <img
          className={styles.imageTwo}
          role="presentation"
          src={images[1]}
          alt={images[1]}
          onClick={onZoom}
        />
        {showImagesZoom && <ImagesZoom images={images} onClose={onClose} />}
      </div>
    );
  }
  if (images.length === 3) {
    return (
      <div className={styles.PostImages}>
        <div className={styles.imageLeftContainer}>
          <img
            className={styles.imageleft}
            role="presentation"
            src={images[0]}
            alt={images[0]}
            onClick={onZoom}
          />
        </div>
        <div className={styles.verticalLine} />
        <div className={styles.imageRightContainer}>
          <img
            className={styles.imageRight}
            role="presentation"
            src={images[1]}
            alt={images[1]}
            onClick={onZoom}
          />
          <div className={styles.horizontalLine} />
          <img
            className={styles.imageRight}
            role="presentation"
            src={images[1]}
            alt={images[1]}
            onClick={onZoom}
          />
        </div>
        {showImagesZoom && <ImagesZoom images={images} onClose={onClose} />}
      </div>
    );
  }
  return (
    <div className={styles.PostImages}>
      <div className={styles.imageLeftContainer}>
        <img
          className={styles.imageleft}
          role="presentation"
          src={images[0]}
          alt={images[0]}
          onClick={onZoom}
        />
      </div>
      <div className={styles.verticalLine} />
      <div className={styles.imageRightContainer}>
        <img
          className={styles.imageRight}
          role="presentation"
          src={images[1]}
          alt={images[1]}
          onClick={onZoom}
        />
        <div className={styles.horizontalLine} />
        <div className={styles.imageLastContainer}>
          <img
            className={styles.imageLast}
            role="presentation"
            src={images[1]}
            alt={images[1]}
            onClick={onZoom}
          />
          <div className={styles.imageCounter}>
            <span className={styles.moreNumber}>{`+${images.length - 3}`}</span>
          </div>
        </div>
      </div>
      {showImagesZoom && <ImagesZoom images={images} onClose={onClose} />}
    </div>
  );
};

export default PostImages;
