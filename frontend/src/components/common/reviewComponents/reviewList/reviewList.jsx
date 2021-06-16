import React, { useCallback, useEffect, useRef, useState } from "react";
import ReviewCard from "./reviewCard/reviewCard";
import styles from "./reviewList.module.css";
import { EditTwoTone } from "@ant-design/icons";
import { Drawer } from "antd";
import ReviewForm from "../reviewForm/reviewForm";
import DeleteConfirm from "../../deleteConfirm/deleteConfirm";
import SortBox from "../../sortBox/sortBox";

const ReviewList = ({
  totalReviewCount,
  onOpenPortalAuth,
  onUploadReview,
  onDeleteReview,
  onEditReview,
  onLikeReview,
  onUnlikeReview,
  userStates,
  reviewDatas,
  onSortReviewForDate,
  onSortReviewForLike,
  onGetReviewWhenScroll,
}) => {
  const reviewListRef = useRef(null);
  const scrollTargetRef = useRef(null);
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
      setSelectedCard(null);
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
    async (id) => {
      await onDeleteReview(id);
    },
    [onDeleteReview]
  );
  const onHandleEditReview = useCallback(
    (formData, updateReviewContent) => {
      // 수정할 리뷰 ID를 받아와야한다.
      onEditReview(selectedCard.id, formData, updateReviewContent);
    },
    [onEditReview, selectedCard]
  );

  useEffect(() => {
    if (onGetReviewWhenScroll) {
      // 리뷰 더 보기로 무한 스크롤링을 진행할 때!
      const options = {
        threshold: 0,
      };
      const handleIntersection = (entries, observer) => {
        entries.forEach((entry) => {
          if (!entry.isIntersecting) {
            // 타겟과 겹쳤는 지 확인
            return;
          }
          onGetReviewWhenScroll();
          observer.unobserve(entry.target); // 기존 타겟을 unobserve 하고
          observer.observe(scrollTargetRef.current); // 새로운 타겟을 observe
        });
      };
      const io = new IntersectionObserver(handleIntersection, options);
      if (scrollTargetRef.current) {
        io.observe(scrollTargetRef.current); // target을 감시
      }
      return () => io && io.disconnect();
    }
  }, [onGetReviewWhenScroll, scrollTargetRef]);

  return (
    <div className={styles.reviewList}>
      <header className={styles.reviewList__header}>
        <div className={styles.reviewList__first}>
          <div className={styles.reviewCountBox}>
            <h2>리뷰</h2>
            <h2 className={styles.reviewCount}>{totalReviewCount}</h2>
          </div>
          <h2 onClick={onClickReviewWrite}>
            <EditTwoTone />
          </h2>
        </div>
        <div className={styles.sortBoxContainer}>
          <SortBox onSortReviewForDate={onSortReviewForDate} onSortReviewForLike={onSortReviewForLike} />
        </div>
      </header>
      <ul ref={reviewListRef} className={styles.reviewList__body}>
        {reviewDatas.map((item, index) => (
          <li
            ref={reviewDatas.length - 1 === index ? scrollTargetRef : null}
            key={index}
            className={styles.reviewContainer}
          >
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
          </li>
        ))}
      </ul>
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
        destroyOnClose={true}
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
        destroyOnClose={true}
      >
        <ReviewForm prevReview={selectedCard} onUploadReview={onHandleEditReview} onClose={onCloseEditForm} />
      </Drawer>
    </div>
  );
};

export default ReviewList;
