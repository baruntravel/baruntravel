import React, { useCallback, useState } from "react";
import ReviewCard from "./reviewCard/reviewCard";
import styles from "./reviewList.module.css";
import { EditTwoTone } from "@ant-design/icons";
import { Drawer } from "antd";
import ReviewForm from "../reviewForm/reviewForm";
import DeleteConfirm from "../../deleteConfirm/deleteConfirm";
import SortBox from "../../sortBox/sortBox";

const ReviewList = ({
  onOpenPortalAuth,
  onUploadReview,
  onDeleteReview,
  onEditReview,
  onLikeReview,
  onUnlikeReview,
  userStates,
  reviewDatas,
  setReviewDatas,
}) => {
  const [selectedCard, setSelectedCard] = useState(false);
  const [openDeleteConfrim, setOpenDeleteConfirm] = useState(false);
  const [openEditform, setOpenEditForm] = useState(false);
  const [reviewWrite, setReviewWrite] = useState(false);

  const onOpenConfirm = useCallback(() => {
    setOpenDeleteConfirm(true);
  }, []);
  const onCloseConfirm = useCallback(() => {
    setOpenDeleteConfirm(false);
  }, []);
  const onOpenEditForm = useCallback(() => {
    setOpenEditForm(true);
  }, []);
  const onCloseEditForm = useCallback(() => {
    setOpenEditForm(false);
  }, []);
  const onClickReviewWrite = useCallback(() => {
    if (userStates.isLogin) {
      setReviewWrite(true);
    } else {
      onOpenPortalAuth();
    }
  }, [onOpenPortalAuth, userStates]);
  const onCloseReviewWrite = useCallback(() => {
    setReviewWrite(false);
  }, []);
  const onHandleSelected = useCallback((review) => {
    setSelectedCard(review);
  }, []);

  const onHandleDeleteReview = useCallback(
    (id) => {
      onDeleteReview(id);
      setReviewDatas((prev) => {
        const updated = prev.filter((item) => item.id !== id);
        return updated;
      });
    },
    [onDeleteReview, setReviewDatas]
  );
  const onHandleEditReview = useCallback(
    (formData) => {
      // 수정할 리뷰 ID를 받아와야한다.
      onEditReview(selectedCard.id, formData);
    },
    [onEditReview, selectedCard]
  );

  return (
    <div className={styles.reviewList}>
      <header className={styles.reviewList__header}>
        <div className={styles.reviewList__first}>
          <div className={styles.reviewCountBox}>
            <h2>리뷰</h2>
            <h2 className={styles.reviewCount}>{reviewDatas.length}</h2>
          </div>
          <h2 onClick={onClickReviewWrite}>
            <EditTwoTone />
          </h2>
        </div>
        <div className={styles.sortBoxContainer}>
          <SortBox
          // onHandleRecommend={viewListLikeCount}
          // onHandleNewest={viewListDate}
          />
        </div>
      </header>
      <div className={styles.reviewList__body}>
        {reviewDatas.map((item, index) => (
          <div key={index} className={styles.reviewContainer}>
            <ReviewCard
              review={item}
              creator={item.createdBy || item.creator}
              isUserReview={item.mine || item.creator.name === userStates.name}
              onOpenDeleteConfirm={onOpenConfirm}
              onHandleSelected={onHandleSelected}
              onOpenEditForm={onOpenEditForm}
              onLikeReview={onLikeReview}
              onUnlikeReview={onUnlikeReview}
            />
          </div>
        ))}
      </div>
      {openDeleteConfrim && (
        <DeleteConfirm onClose={onCloseConfirm} onDeleteItem={onHandleDeleteReview} deleteItemId={selectedCard.id} />
      )}
      <Drawer // 리뷰 작성
        className={styles.reviewFormDrawer}
        visible={reviewWrite}
        placement="bottom"
        height="100vh"
        bodyStyle={{ padding: 0 }}
        onClose={onCloseReviewWrite}
      >
        <ReviewForm onUploadReview={onUploadReview} onClose={onCloseReviewWrite} />
      </Drawer>
      <Drawer // 리뷰 수정
        className={styles.reviewFormDrawer}
        visible={openEditform}
        placement="bottom"
        height="100vh"
        bodyStyle={{ padding: 0 }}
        onClose={onCloseEditForm}
      >
        <ReviewForm prevReview={selectedCard} onUploadReview={onHandleEditReview} onClose={onCloseEditForm} />
      </Drawer>
    </div>
  );
};

export default ReviewList;
