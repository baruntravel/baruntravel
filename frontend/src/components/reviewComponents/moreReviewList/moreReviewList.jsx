import { ArrowLeftOutlined } from "@ant-design/icons";
import React from "react";
import ReviewList from "../reviewList/reviewList";
import styles from "./moreReviewList.module.css";

const MoreReviewList = ({
  setReviewDatas,
  onClickReviewWrite,
  onCloseMoreReview,
  reviewDatas,
}) => {
  return (
    <div className={styles.moreReviewList}>
      <header className={styles.header}>
        <div className={styles.backIcon} onClick={onCloseMoreReview}>
          <ArrowLeftOutlined />
        </div>
      </header>
      <ReviewList
        reviewDatas={reviewDatas}
        onClickReviewWrite={onClickReviewWrite}
        setReviewDatas={setReviewDatas}
      />
    </div>
  );
};

export default MoreReviewList;
