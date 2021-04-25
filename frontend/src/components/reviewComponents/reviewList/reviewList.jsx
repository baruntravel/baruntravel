import React from "react";
import ReviewCard from "./reviewCard/reviewCard";
import styles from "./reviewList.module.css";
import { EditTwoTone } from "@ant-design/icons";

const ReviewList = ({ place, onClickReviewWrite }) => {
  return (
    <div className={styles.reviewList}>
      <div className={styles.reviewList__header}>
        <div className={styles.reviewCountBox}>
          <h2>리뷰</h2>
          <h2 className={styles.reviewCount}>6</h2>
        </div>
        <h2 onClick={onClickReviewWrite}>
          <EditTwoTone />
        </h2>
      </div>
      <div className={styles.reviewList__body}>
        <div className={styles.reviewContainer}>
          {[1, 2, 3, 4].map(() => (
            <ReviewCard />
          ))}
        </div>
      </div>
    </div>
  );
};

export default ReviewList;
