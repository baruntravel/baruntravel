import React, { useState } from "react";
import styles from "./reviewCard.module.css";
import { Rate } from "antd";
import Avatar from "antd/lib/avatar/avatar";
import { getYear, getMonth, getDate } from "date-fns";
import { LikeOutlined, LikeTwoTone } from "@ant-design/icons";
import PostImages from "../../postImages/postImages";
const ReviewCard = ({ data }) => {
  const [liked, setLiked] = useState(false); // post의 좋아요를 누른 사람들 중 유저가 있는 지 확인하는 작업,
  const onUnlike = () => {
    // 좋아요 취소 -> DB에 반영,
    setLiked(!liked);
  };
  return (
    <div className={styles.ReviewCard}>
      <Avatar src={data.profileImage} size="large" />
      <div className={styles.content}>
        <div className={styles.header}>
          <div>
            <Rate defaultValue={data.score} disabled={true} />
            <span className={styles.score}>{data.score}</span>
          </div>
          <div className={styles.reviewInfo}>
            <strong className={styles.nickname}>{data.nickname}</strong>
            <span className={styles.date}>{`${getYear(data.date)}.${getMonth(
              data.date
            )}.${getDate(data.date)}`}</span>
            {liked ? (
              <LikeTwoTone
                style={{ padding: "0.5em", fontSize: "1.2em" }}
                twoToneColor="#eb2f96"
                key="Like"
                onClick={onUnlike}
              />
            ) : (
              <LikeOutlined
                style={{ padding: "0.5em", fontSize: "1.2em" }}
                key="Like"
                onClick={onUnlike}
              />
            )}
            <span className={styles.likeCount}>{data.likeCount}</span>
          </div>
        </div>
        <section>
          <span className={styles.context}>{data.context}</span>
        </section>
      </div>
      <div className={styles.imageContainer}>
        <PostImages images={data.images} />
      </div>
    </div>
  );
};

export default ReviewCard;
