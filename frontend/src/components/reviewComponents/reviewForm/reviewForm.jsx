import { Card, Rate } from "antd";
import TextArea from "antd/lib/input/TextArea";
import React, { useCallback, useRef, useState } from "react";
import styles from "./reviewForm.module.css";

import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faImages } from "@fortawesome/free-solid-svg-icons";
import ReviewImageEdit from "./reviewImageEdit/reviewImageEdit";

const ReviewForm = ({ placeName }) => {
  const [images, setImages] = useState({}); // 미리보기용 URL 저장소
  const [files, setFiles] = useState({});
  const imageInput = useRef();
  const onSubmit = () => {
    const imageFormData = new FormData();
    Object.keys(files).forEach((key) => {
      imageFormData.append("images", files[key]);
    });
    // place Id도 있어야할 것 같다.
    // image update API 호출
  };
  const onClickImageUpload = useCallback(() => {
    imageInput.current.click();
  }, [imageInput]);
  const onChangeImages = useCallback(
    (e) => {
      const addFiles = {};
      const addImages = {};
      Array.prototype.forEach.call(e.target.files, (f) => {
        const imageUrl = URL.createObjectURL(f);
        addFiles[f.name] = f;
        addImages[f.name] = imageUrl;
      }); //
      setFiles({ ...files, ...addFiles });
      setImages({ ...images, ...addImages });
    },
    [files, images]
  );
  const onDeleteImages = (name) => {
    // 이미지 업로드를 하고 코드 작성을하자
    // imageFormData;
    const updatedFiles = { ...files };
    const updatedImages = { ...images };
    delete updatedFiles[name];
    delete updatedImages[name];
    setFiles(updatedImages);
    setImages(updatedImages);
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
            Object.keys(images).map((v, index) => (
              <ReviewImageEdit
                key={index}
                item={images[v]}
                name={v}
                onDeleteImages={onDeleteImages}
              />
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
        <button onClick={onSubmit}>작성</button>
      </Card>
    </div>
  );
};

export default ReviewForm;
