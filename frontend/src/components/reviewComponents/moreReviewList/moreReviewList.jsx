import React from "react";
import ReviewList from "../reviewList/reviewList";
import styles from "./moreReviewList.module.css";

const MoreReviewList = ({ onCloseMoreReview }) => {
  return (
    <div className={styles.moreReviewList}>
      <header>
        <span>뒤로 가기</span>
        <button onClick={onCloseMoreReview}>Close</button>
      </header>
      <ReviewList />
    </div>
  );
};

export default MoreReviewList;
