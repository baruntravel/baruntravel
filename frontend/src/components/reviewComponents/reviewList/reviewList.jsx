import React, { useCallback, useRef, useState } from "react";
import ReviewCard from "./reviewCard/reviewCard";
import styles from "./reviewList.module.css";
import { EditTwoTone } from "@ant-design/icons";

const ReviewList = ({
  place,
  onClickReviewWrite,
  reviewDatas,
  setReviewDatas,
}) => {
  const newRef = useRef();
  const recommendRef = useRef();
  const viewListDate = () => {
    console.log("최신순");
    newRef.current.style = "color:black; opacity:1";
    recommendRef.current.style = "color:gray; opacity: 0.8;";
    setReviewDatas((prev) => {
      const updated = [...prev];
      updated.sort((a, b) => {
        if (a.createdAt > b.createdAt) {
          return 1;
        } else if (a.createdAt < b.createdAt) {
          return -1;
        } else {
          return 0;
        }
      });
      return updated;
    });
  };
  const viewListLikeCount = () => {
    console.log("추천순");
    newRef.current.style = "color:gray; opacity: 0.8;";
    recommendRef.current.style = "color:black; opacity:1";
    setReviewDatas((prev) => {
      const updated = [...prev];
      updated.sort((a, b) => {
        if (a.likeCount < b.likeCount) {
          return 1;
        } else if (a.likeCount > b.likeCount) {
          return -1;
        } else {
          return 0;
        }
      });
      return updated;
    });
  };
  return (
    <div className={styles.reviewList}>
      <div className={styles.reviewList__header}>
        <div className={styles.reviewList__first}>
          <div className={styles.reviewCountBox}>
            <h2>리뷰</h2>
            <h2 className={styles.reviewCount}>6</h2>
          </div>
          <h2 onClick={onClickReviewWrite}>
            <EditTwoTone />
          </h2>
        </div>
        <div className={styles.reviewList__case}>
          <span
            ref={newRef}
            className={styles.reviewList__new}
            onClick={viewListDate}
          >
            최신순
          </span>
          <span
            ref={recommendRef}
            className={styles.reviewList__recommend}
            onClick={viewListLikeCount}
          >
            추천순
          </span>
        </div>
      </div>
      <div className={styles.reviewList__body}>
        {reviewDatas.map((item, index) => (
          <div key={index} className={styles.reviewContainer}>
            <ReviewCard review={item} />
          </div>
        ))}
      </div>
    </div>
  );
};

export default ReviewList;
