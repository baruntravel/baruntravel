import React, { useCallback, useState } from "react";
import styles from "./reviewCard.module.css";
// import { getYear, getMonth, getDate } from "date-fns";
import PostImages from "../../postImages/postImages";
import ReviewUserProfile from "../../../common/reviewUserProfile/reviewUserProfile";
import ReviewScoreText from "../../reviewScoreText/reviewScoreText";
import ReviewCardBottom from "./reviewCardBottom/reviewCardBottom";

const ReviewCard = ({
  review,
  review: {
    id,
    content,
    score,
    creator: { name, avatar },
    likeCount,
    likeCheck,
    files,
    createdAt,
    updatedAt,
  },
  isUserReview,
  onOpenDeleteConfirm,
  onHandleSelected,
  onOpenEditForm,
}) => {
  const [liked, setLiked] = useState(likeCheck); // post의 좋아요를 누른 사람들 중 유저가 있는 지 확인하는 작업,
  const onUnlike = () => {
    // 좋아요 취소 -> DB에 반영,
    setLiked(!liked);
  };
  const onClickDelete = useCallback(() => {
    onHandleSelected(review);
    onOpenDeleteConfirm();
  }, [onHandleSelected, onOpenDeleteConfirm, review]);
  const onClickEdit = useCallback(() => {
    onHandleSelected(review);
    onOpenEditForm();
  }, [onHandleSelected, onOpenEditForm, review]);
  return (
    <div className={styles.ReviewCard}>
      <ReviewUserProfile
        avatar={avatar}
        name={name}
        isUserReview={isUserReview}
        onClickEdit={onClickEdit}
        onClickDelete={onClickDelete}
      />
      <ReviewScoreText content={content} score={score} />
      {files.length > 0 && (
        <div className={styles.imageContainer}>
          <PostImages images={files.map((file) => file.url)} />
        </div>
      )}
      <ReviewCardBottom
        liked={liked}
        onUnlike={onUnlike}
        date={updatedAt || createdAt}
        likeCount={likeCount}
      />
    </div>
  );
};

export default ReviewCard;
