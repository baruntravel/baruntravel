import styles from "./reviewForm.module.css";
import { Card, Rate } from "antd";
import TextArea from "antd/lib/input/TextArea";
import React, { useCallback, useEffect, useRef, useState } from "react";
// import { getYear, getMonth, getDate } from "date-fns";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faImages } from "@fortawesome/free-solid-svg-icons";
import ReviewImageEdit from "./reviewImageEdit/reviewImageEdit";
import useInput from "../../../../hooks/useInput";

const ReviewForm = ({ onClose, onUploadReview, prevReview }) => {
  const [images, setImages] = useState(prevReview ? prevReview.images.map((image) => image.url) : {}); // 미리보기용 URL 저장소
  const [files, setFiles] = useState({}); // 이미지 file 저장
  const [inputContext, handleInputContext, setInputContext] = useInput(prevReview ? prevReview.content : "");
  const [rate, setRate] = useState(prevReview ? prevReview.score : 5);
  const imageInput = useRef();
  const onSubmit = useCallback(
    (e) => {
      e.preventDefault();
      const formData = new FormData();
      Object.keys(files).forEach((key) => {
        formData.append("files", files[key]);
      }); // formData.getAll("images") 를 하면 모두 담겨있는 것을 확인했다.
      formData.append("content", inputContext);
      formData.append("score", rate);

      const editReview = prevReview
        ? {
            ...prevReview,
            rate,
            content: inputContext,
            images: images.map((url) => {
              return { url };
            }),
          }
        : null;
      onUploadReview(formData, editReview); // 두 번째 인자는 수정 후 update될 때만 반영된다.
      onClose();
      // image update API 호출
    },
    [files, images, inputContext, onClose, onUploadReview, prevReview, rate]
  );
  const onChangeRate = useCallback((number) => {
    setRate(number);
  }, []);
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
  const onDeleteImages = useCallback(
    (name) => {
      // 이미지 업로드를 하고 코드 작성을하자
      // imageFormData;
      const updatedFiles = { ...files };
      const updatedImages = { ...images };
      delete updatedFiles[name];
      delete updatedImages[name];
      setFiles(updatedImages);
      setImages(updatedImages);
    },
    [files, images]
  );

  // const onResetWhenClose = useCallback(() => {
  //   setImages([]);
  //   set;
  // }, []);
  return (
    <div className={styles.ReviewForm}>
      <Card
        title={
          <div className={styles.title}>
            <span>리뷰</span>
            <div className={styles.rateBox}>
              <Rate
                defaultValue={5}
                allowClear={false}
                className={styles.rate}
                style={{
                  animation: "none",
                  fontSize: "0.9em",
                  marginLeft: "12px",
                }}
                value={rate}
                onChange={onChangeRate}
              />
            </div>
          </div>
        }
        className={styles.card}
        headStyle={{
          display: "flex",
          alignItems: "center",
          justifyContent: "flex-start",
          height: "15vh",
        }}
        bodyStyle={{
          height: "85vh",
        }}
      >
        <TextArea
          className={styles.textArea}
          style={{ width: "100%", height: "50%" }}
          value={inputContext}
          onChange={handleInputContext}
        />
        <div className={styles.imageBox}>
          <button className={styles.imageUpload} onClick={onClickImageUpload}>
            <FontAwesomeIcon icon={faImages} className={styles.uploadIcon} />
          </button>
          <div className={styles.imageUploadedBox}>
            {images &&
              Object.keys(images).map((v, index) => (
                <div className={styles.imageUploaded} key={index}>
                  <ReviewImageEdit key={index} item={images[v]} name={v} onDeleteImages={onDeleteImages} />
                </div>
              ))}
          </div>
        </div>
        <input type="file" name="image" multiple hidden ref={imageInput} onChange={onChangeImages} />
        <div className={styles.bottom}>
          <button className={styles.submitBtn} onClick={onSubmit}>
            {prevReview ? "리뷰 수정" : "리뷰 등록"}
          </button>
        </div>
      </Card>
    </div>
  );
};

export default ReviewForm;
