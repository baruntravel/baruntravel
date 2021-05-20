import { UserOutlined } from "@ant-design/icons";
import Avatar from "antd/lib/avatar/avatar";
import React from "react";
import styles from "./reviewUserProfile.module.css";

const ReviewUserProfile = ({
  avatar,
  name,
  isUserReview,
  onClickEdit,
  onClickDelete,
}) => {
  return (
    <div className={styles.userProfile}>
      <div className={styles.profileBox}>
        <Avatar src={avatar} size="large" icon={<UserOutlined />} />
        <div className={styles.userName}>
          <strong className={styles.nickname}>{name}</strong>
        </div>
      </div>
      {isUserReview && (
        <div className={styles.editBox}>
          <span onClick={onClickEdit}>수정</span>
          <span onClick={onClickDelete}>삭제</span>
        </div>
      )}
    </div>
  );
};

export default ReviewUserProfile;
