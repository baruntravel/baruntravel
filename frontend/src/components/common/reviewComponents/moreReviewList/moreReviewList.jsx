import { ArrowLeftOutlined } from "@ant-design/icons";
import React from "react";
import ReviewList from "../reviewList/reviewList";
import styles from "./moreReviewList.module.css";

const MoreReviewList = ({
  userStates,
  onCloseMoreReview,
  totalReviewCount,
  reviewDatas,
  onOpenPortalAuth,
  onUploadReview,
  onLikeReview,
  onUnlikeReview,
  onEditReview,
  onDeleteReview,
  onSortReviewForDate,
  onSortReviewForLike,
  onGetReviewWhenScroll,
}) => {
  return (
    <div className={styles.moreReviewList}>
      <header className={styles.header}>
        <div className={styles.backIcon} onClick={onCloseMoreReview}>
          <ArrowLeftOutlined />
        </div>
      </header>
      <ReviewList
        userStates={userStates}
        totalReviewCount={totalReviewCount}
        reviewDatas={reviewDatas}
        onOpenPortalAuth={onOpenPortalAuth}
        onUploadReview={onUploadReview}
        onLikeReview={onLikeReview}
        onUnlikeReview={onUnlikeReview}
        onEditReview={onEditReview}
        onDeleteReview={onDeleteReview}
        onSortReviewForDate={onSortReviewForDate}
        onSortReviewForLike={onSortReviewForLike}
        onGetReviewWhenScroll={onGetReviewWhenScroll}
      />
    </div>
  );
};

export default MoreReviewList;
