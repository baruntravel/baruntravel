import React, { useCallback, useState } from "react";
import styles from "./reviewCard.module.css";
// import { getYear, getMonth, getDate } from "date-fns";
import PostImages from "../../postImages/postImages";
import ReviewScoreText from "../../reviewScoreText/reviewScoreText";
import ReviewCardBottom from "./reviewCardBottom/reviewCardBottom";
import ReviewUserProfile from "../../../reviewUserProfile/reviewUserProfile";

const ReviewCard = ({
  review,
  review: { id, content, score, likes, isLike, images, createdAt, updatedAt },
  creator: { name, avatarUrl, email },
  isUserReview,
  onOpenDeleteConfirm,
  onHandleSelected,
  onOpenEditForm,
  onLikeReview,
  onUnlikeReview,
}) => {
  const [liked, setLiked] = useState(isLike); // post의 좋아요를 누른 사람들 중 유저가 있는 지 확인하는 작업,
  const [likeCount, setLikeCount] = useState(likes);

  const onLike = useCallback(() => {
    onLikeReview(id);
    setLiked(true);
    setLikeCount(likeCount + 1);
  }, [onLikeReview, id, likeCount]);
  const onUnlike = useCallback(() => {
    onUnlikeReview(id);
    setLiked(false);
    setLikeCount(likeCount - 1);
  }, [onUnlikeReview, id, likeCount]);

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
        avatar={avatarUrl}
        name={name}
        isUserReview={isUserReview}
        onClickEdit={onClickEdit}
        onClickDelete={onClickDelete}
      />
      <ReviewScoreText content={content} score={score} />
      {images.length > 0 && (
        <div className={styles.imageContainer}>
          <PostImages images={images.map((file) => file.url)} />
        </div>
      )}
      <ReviewCardBottom
        liked={liked}
        onLike={onLike}
        onUnlike={onUnlike}
        date={updatedAt || createdAt}
        likeCount={likeCount}
      />
    </div>
  );
};

export default ReviewCard;
