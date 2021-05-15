import React, { useCallback, useState } from "react";
import styles from "./reviewCard.module.css";
import { Rate } from "antd";
import Avatar from "antd/lib/avatar/avatar";
import { getYear, getMonth, getDate } from "date-fns";
import { LikeOutlined, LikeTwoTone, UserOutlined } from "@ant-design/icons";
import PostImages from "../../postImages/postImages";
import ImagesZoom from "../../imagesZoom/imagesZoom";
const ReviewCard = ({
  review: {
    id,
    content,
    score,
    createdBy,
    likeCount,
    likeCheck,
    files,
    createdAt,
    updatedAt,
  },
  isUserReview,
  onOpenDeleteConfirm,
  onHandleSelected,
}) => {
  const [liked, setLiked] = useState(likeCheck); // post의 좋아요를 누른 사람들 중 유저가 있는 지 확인하는 작업,
  const onUnlike = () => {
    // 좋아요 취소 -> DB에 반영,
    setLiked(!liked);
  };
  const onClickDelete = () => {
    onHandleSelected(id);
    onOpenDeleteConfirm();
  };
  return (
    <div className={styles.ReviewCard}>
      <div className={styles.userProfile}>
        <div className={styles.profileBox}>
          <Avatar src={""} size="large" icon={<UserOutlined />} />
          <div className={styles.userName}>
            <strong className={styles.nickname}>{createdBy}</strong>
          </div>
        </div>
        {isUserReview && (
          <div className={styles.editBox}>
            <span>수정</span>
            <span onClick={onClickDelete}>삭제</span>
          </div>
        )}
      </div>
      <div className={styles.content}>
        <div className={styles.info}>
          <div className={styles.info__first}>
            <Rate
              className={styles.rate}
              defaultValue={parseInt(score)}
              disabled={true}
              style={{
                fontSize: "0.9em",
                marginBottom: "12px",
              }}
            />
            <span className={styles.score}>{parseInt(score)}</span>
          </div>
          <section>
            <span className={styles.context}>{content}</span>
          </section>
        </div>
      </div>
      {files.length > 0 && (
        <div className={styles.imageContainer}>
          <PostImages images={files.map((file) => file.url)} />
        </div>
      )}
      <div className={styles.review__bottom}>
        <div>
          {liked ? (
            <LikeTwoTone
              style={{ padding: "0.5em" }}
              twoToneColor="#eb2f96"
              key="Like"
              onClick={onUnlike}
            />
          ) : (
            <LikeOutlined
              style={{ padding: "0.5em" }}
              key="Like"
              onClick={onUnlike}
            />
          )}
          <span className={styles.likeCount}>{likeCount}</span>
        </div>
        {/* <span className={styles.date}>{`${getYear(
          createdAt || updatedAt
        )}.${getMonth(createdAt || updatedAt)}.${getDate(
          createdAt || updatedAt
        )}`}</span> */}
        <span className={styles.date}>{createdAt || updatedAt}</span>
      </div>
    </div>
  );
};

export default ReviewCard;
