import { Card, Rate } from "antd";
import TextArea from "antd/lib/input/TextArea";
import React, { useRef, useState } from "react";
import styles from "./reviewForm.module.css";

import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faImages } from "@fortawesome/free-solid-svg-icons";

const ReviewForm = ({ placeName }) => {
  const [images, setImages] = useState([]);
  const imageInput = useRef();

  const onChangeImages = () => {};
  return (
    <div className={styles.ReviewForm}>
      <Card
        title={placeName}
        className={styles.card}
        extra={
          <Rate
            defaultValue={4}
            allowClear={false}
            className={styles.rate}
            style={{ animation: "none" }}
          />
        }
      >
        <TextArea className={styles.textArea} />
        <div className={styles.imageBox}>
          <button className={styles.imageUpload}>
            <FontAwesomeIcon icon={faImages} />
          </button>
          {images &&
            images.map((item) => {
              <img src={item} alt="upladed image" />;
            })}
        </div>
        <input
          type="file"
          name="image"
          multiple
          hidden
          ref={imageInput}
          onChange={onChangeImages}
        />
      </Card>
    </div>
  );
};

export default ReviewForm;
