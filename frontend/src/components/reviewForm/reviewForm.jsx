import { Card, Rate } from "antd";
import TextArea from "antd/lib/input/TextArea";
import React, { useCallback, useRef, useState } from "react";
import styles from "./reviewForm.module.css";

import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faImages } from "@fortawesome/free-solid-svg-icons";
import ReviewImageEdit from "../reviewImageEdit/reviewImageEdit";

const ReviewForm = ({ placeName }) => {
  const [images, setImages] = useState([]);
  const imageInput = useRef();
  const imageFormData = new FormData();

  console.log(images);

  const onClickImageUpload = useCallback(() => {
    imageInput.current.click();
  }, [imageInput]);
  const onChangeImages = (e) => {
    // FormData에 저장해뒀다가 작성을 완료하면 서버로 전송하게 하자.
    const imageFile = e.target.files[0];
    const imageUrl = URL.createObjectURL(imageFile);
    setImages((prev) => [...prev, imageUrl]);
    Array.prototype.forEach.call(e.target.files, (f) => {
      console.log(f);
      imageFormData.append("image", f);
    }); // 이미지 리스트를 어떻게 넘겨줄 지?
    console.log(imageFormData.values());
  };
  const onDeleteImages = () => {
    // 이미지 업로드를 하고 코드 작성을하자
  };
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
        bodyStyle={{
          height: "100%",
        }}
      >
        <TextArea className={styles.textArea} style={{ height: "70%" }} />
        <div className={styles.imageBox}>
          <button className={styles.imageUpload} onClick={onClickImageUpload}>
            <FontAwesomeIcon icon={faImages} />
          </button>
          {images &&
            images.map((item, index) => (
              <ReviewImageEdit key={index} item={item} />
            ))}
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
