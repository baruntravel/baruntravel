import {
  HeartOutlined,
  HeartTwoTone,
  LeftOutlined,
  LikeOutlined,
  LikeTwoTone,
} from "@ant-design/icons";
import React from "react";
import styles from "./detailHeader.module.css";

const DetailHeader = ({
  liked,
  category,
  needLogin,
  portalAuthOpen,
  onClickLike,
  onClickUnlike,
}) => {
  // onShareDetail은 만약 공유하기 기능이 추가된다면 공유
  return (
    <div className={styles.DetailHeader}>
      <div className={styles.navigation} role="뒤로 가기">
        <div className={styles.navigationBtn}>
          <div className={styles.navigationIcon}>
            <LeftOutlined />
          </div>
          <div className={styles.categoryBox}>
            <span className={styles.category}>{category || "카테고리"}</span>
          </div>
        </div>
      </div>
      <div className={styles.serviceIcons}>
        <div className={styles.heart}>
          {liked ? (
            <LikeTwoTone
              className={styles.likeBtn}
              twoToneColor="#eb2f96"
              onClick={onClickUnlike}
            />
          ) : (
            <LikeOutlined
              className={styles.likeBtn}
              style={{ color: "grey" }}
              onClick={onClickLike}
            />
          )}
        </div>
      </div>
    </div>
  );
};

export default DetailHeader;
